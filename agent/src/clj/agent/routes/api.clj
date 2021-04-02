(ns agent.routes.api
  (:require [clojure.tools.logging :as log]
            [compojure.core :refer [defroutes context GET PUT POST DELETE]]
            [ring.util.http-response :refer [created ok not-found bad-request found set-cookie]]
            [slingshot.slingshot :refer [throw+ try+]]
            [org.httpkit.server :refer [send! with-channel on-close on-receive]]
            [agent.wsocket :refer [process-handler]]
            [agent.command :refer [collect!]]))



(defn index-page []
  (ok {:message "hello world"}))


(defroutes public-routes
  (GET "/" [] (index-page)))


(defroutes api-routes
  (GET "/socket" [] process-handler)
  (POST "/trigger" [command buffer maxitem]
    (case command
      "collect" (do
                  (collect! {:buffer buffer :maxitem maxitem})
                  (ok)))))
