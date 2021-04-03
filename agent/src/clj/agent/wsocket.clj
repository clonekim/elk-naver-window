(ns agent.wsocket
  (:require [org.httpkit.server :refer [send!
                                        with-channel
                                        on-close on-receive]]))


(def clients (atom #{}))


(defn notify-clients [msg]
  (doseq [client @clients]
    (send! client msg)))



(defn process-handler [req]
  (with-channel req channel
    (println channel "connected!")
    (swap! clients conj channel)
    (on-receive channel #'notify-clients)
    (on-close channel (fn [status]
                        (swap! clients #(remove #{channel} %))
                        (println channel "closed, status" status)))))
