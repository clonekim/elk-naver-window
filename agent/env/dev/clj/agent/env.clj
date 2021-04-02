(ns agent.env
  (:require [agent.dev-middleware :refer [wrap-dev]]))

(def defaults
  {:init
   (fn []
     (println "[server started successfully using the development profile]"))
   :stop
   (fn []
     (println "[server has shut down successfully]"))
   :middleware wrap-dev})
