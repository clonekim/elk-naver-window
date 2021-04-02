(ns agent.routes.api
  (:require [clojure.tools.logging :as log]
            [compojure.core :refer [defroutes context GET PUT POST DELETE]]
            [ring.util.http-response :refer [created ok not-found bad-request found set-cookie]]
            [slingshot.slingshot :refer [throw+ try+]]
            [org.httpkit.server :refer [send! with-channel on-close on-receive]]
            [agent.wsocket :refer [process-handler]]
            [agent.command :refer [collect!]]
            [agent.util :as util]))



(defn index-page []
  (ok {:message "hello world"}))


(defroutes public-routes
  (GET "/*" []
    {:status 200
     :headers {"Content-Type" "text/html"}
     :body (slurp (clojure.java.io/resource "public/index.html"))}))


(defroutes api-routes
  (GET "/socket" [] process-handler)
  (POST "/trigger" [command buffer maxitem menuid]
    (case command
      "collect"
      (do
        (collect!
         {:buffer (util/parse-int buffer)
          :maxitem (util/parse-int maxitem)
          :menuid menuid})
        (ok))

      (bad-request {:message "Command not found"}))))
