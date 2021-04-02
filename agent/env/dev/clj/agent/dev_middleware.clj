(ns agent.dev-middleware
  (:require [ring.middleware.reload :refer [wrap-reload]]
            [ring.middleware.cors :refer [wrap-cors]]
            [ring.logger :refer [wrap-with-logger]]
            [prone.middleware :refer [wrap-exceptions]]))

(defn wrap-dev [handler]
  (-> handler
      (wrap-cors :access-control-allow-origin [#".*"]
                 :access-control-allow-headers ["Content-Type" "Accept" "X-Requested-With"]
                 :access-control-allow-methods [:get :put :post :delete])
      (wrap-with-logger {:timing false})
      wrap-reload))
