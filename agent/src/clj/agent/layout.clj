(ns agent.layout
  (:require [cheshire.core :refer [generate-string]]
            [ring.util.response :refer [content-type response]]
            [agent.util :as util]
            [agent.config :refer [env]]))


(defn error-page [{:keys [message status]}]
  {:status  status
   :headers {"Content-Type" "application/json"}
   :body    (generate-string {:code status :errors message})})
