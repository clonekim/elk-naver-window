(ns agent.handler
  (:require [clojure.tools.logging :as log]
            [ring.middleware.defaults :refer [site-defaults wrap-defaults]]
            [ring.middleware.json :refer [wrap-json-body wrap-json-params wrap-json-response]]
            [ring.middleware.cookies :refer [wrap-cookies]]
            [ring.middleware.keyword-params :refer [wrap-keyword-params]]
            [compojure.core :refer [routes wrap-routes]]
            [compojure.route :as route]
            [slingshot.slingshot :refer [try+]]
            [agent.layout :refer [error-page]]
            [agent.config :refer [env]]
            [agent.env :refer [defaults]]
            [agent.routes.api :refer [public-routes api-routes]]))



(defn wrap-internal-error [handler]
  (fn [req]
    (try+
      (handler req)
      (catch Throwable t
        (log/error t)
        (error-page {:status 500
                     :message (if (:dev env)
                                (or (.getMessage t) "Internal Server Error")
                                "Internal Server Error")})))))


(defn wrap-nocache [handler]
  (fn [request]
    (let [response (handler request)]
      (update-in response [:headers]
                 merge {"Cache-Control" "no-cache, no-store, must-revalidate"
                        "Pragma" "no-cache"
                        "Expires" "-1"}))))


(defn wrap-base [handler]
  (-> ((:middleware defaults) handler)
      (wrap-json-response)
      (wrap-json-body :keywords? true :bigdecimals? true)
      (wrap-cookies {:path "/"})
      (wrap-keyword-params)
      (wrap-json-params)
      (wrap-defaults
       (-> site-defaults
           (assoc-in [:security :anti-forgery] false)))
      (wrap-nocache)
      (wrap-internal-error)))



(def default-routes
  (routes
   #'public-routes
   #'api-routes
   (route/not-found "404")))


(def app
  (wrap-base
   #'default-routes))
