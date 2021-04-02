(ns agent.env
  (:require [clojure.tools.logging :as log]
            [agent.dev-middleware :refer [wrap-dev]]))

(def defaults
  {:init
   (fn []
     (log/info "[server started successfully using the development profile]"))
   :stop
   (fn []
     (log/info "[server has shut down successfully]"))
   :middleware wrap-dev})
