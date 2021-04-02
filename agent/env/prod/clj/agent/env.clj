(ns agent.env
  (:require [clojure.tools.logging :as log]))

(def defaults
  {:init
   (fn []
     (log/info "[server started successfully]"))
   :stop
   (fn []
     (log/info "[server has shut down successfully]"))
   :middleware identity})
