(ns agent.env)

(def defaults
  {:init
   (fn []
     (println "[server started successfully]"))
   :stop
   (fn []
     (println "[server has shut down successfully]"))
   :middleware identity})
