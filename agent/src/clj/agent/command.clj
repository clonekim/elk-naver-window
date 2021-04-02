(ns agent.command
  (:use agent.common)
  (:import [org.jsoup Jsoup])
  (:require [cambium.core :as log]
            [cambium.mdc :refer [with-raw-mdc]]
            [clojure.core.async :as async]
            [clojure.string :as str]
            [clj-time.format :as f]
            [cheshire.core :refer [parse-string generate-string]]
            [org.httpkit.client :as http]
            [agent.util :as util]
            [agent.common :refer [replace-products]]
            [agent.wsocket :refer [notify-clients]]))


(def user-agent "Mozilla/5.0 (Linux; Android 6.0; Nexus 5 Build/MRA58N) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.77 Mobile Safari/537.36")
(def store-url "https://m.shopping.naver.com/style/style/stores/%s")
(def zzim-url "https://shopping.naver.com/v1/zzims/products/%s")
(def women-top-url "https://shopping.naver.com/v1/products?subVertical=STYLE&page=%d&pageSize=%d&sort=POPULARITY&filter=NORMAL&displayType=GENERAL&includeZzim=true&includeViewCount=true&includeStoreCardInfo=true&includeStockQuantity=true&menuId=%s")
(def store-top-url "https://shopping.naver.com/v1/products?subVertical=STYLE&page=%d&pageSize=%d&sort=POPULARITY&filter=NORMAL&displayType=GENERAL&includeZzim=true&includeViewCount=true&includeStoreCardInfo=true&includeStockQuantity=true&menuId=10000531&storeId=%s")
(def coordy-top-url "https://shopping.naver.com/v1/relation-products?subVertical=STYLE&page=%d&pageSize=%d&sort=POPULARITY&filter=COORDI&includeZzim=true&includeViewCount=true&includeStoreCardInfo=true&menuId=10000531&relationProductType=COORDI")
(def trend-index-url "http://m.trend.shopping.naver.com/trendshop/index.nhn")
(def trend-post-url "http://m.trend.shopping.naver.com/api/v1/trendshop/bridge/contents.nhn")

(declare scan-women)
(defmulti scan (fn [params] (keyword (:type params))))


(defn to-chunk [items]
  (loop [i items likes []]
    (if (empty? i)
      likes
      (recur
       (rest i)
       (conj likes (str (:_id (first i))))))))


(defn scan-zzim [products]
  (let [chunks (chunk-by-size (to-chunk products) 10)]
    (-> (for [i chunks]
          (->> (connect-url (format zzim-url (str/join "," i)))
               (map
                #(assoc {}
                        :id (name (first %))
                        :count (:count (second %))))
               (flatten)))
        (flatten))))



(defn compare-zzim [{:keys [_id] :as t} zzims]
  (loop [i zzims]
    (let [{:keys [id count]} (first i)]
      (if (or (= id _id) (empty? i))
        (assoc t :zzim (or count 0))
        (recur (rest i))))))


(defn merge-zzim [products zzims]
  (loop [i products new-products []]
    (if (empty? i)
      new-products
      (recur
       (rest i)
       (conj new-products (compare-zzim (first i) zzims))))))





(defn scanner [url page page-size store-id]
  (let [products (:products (connect-url (format url page page-size store-id)))
        zzims    (scan-zzim products)
        items    (merge-zzim products zzims)]
    (if (empty? products)
      nil
      (merge-zzim products (scan-zzim products)))))




(defmethod scan :women [{:keys [buffer maxitem]}]
  (let [d (promise)
        ch (async/chan 10)
        _     (async/go-loop [accum 0 page 1]
                (let [coll (scanner women-top-url page buffer "10000531")]
                  (if (or (empty? coll) (>= accum maxitem))
                    (deliver d true)
                    (do
                      (async/put! ch coll)
                      (recur (+ accum (count coll)) (inc page))))))]

    (async/go-loop []
      (let [c (async/<! ch)]
        (with-raw-mdc {"filename" "logstash"}
          (doseq [item (replace-products c)]
            (log/info item "stored!")))
        (recur)))

    (async/go
      (when @d
        (notify-clients (generate-string {:status "FIN"}))))))






(defn scan-women [params]
  (scan (assoc params :type :women)))

(defn collect! [params]
  (scan-women params))
