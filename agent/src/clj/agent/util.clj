(ns agent.util
  (:require [clj-time.core :as t]
            [clj-time.coerce :as c]
            [clj-time.format :as f]
            [clj-time.local :as l]))

(declare apply-date-pattern)


(defmulti parse-int type)
(defmethod parse-int java.lang.Integer [n] n)
(defmethod parse-int java.lang.Long [n] n)
(defmethod parse-int java.math.BigDecimal [n] (. n intValue))
(defmethod parse-int java.lang.Double [n] (. n intValue))
(defmethod parse-int java.lang.String [s] (Integer/parseInt s))
(defmethod parse-int java.util.Date [n] (. n getTime))
(defmethod parse-int org.joda.time.DateTime [n] (c/to-long n))

(defmulti parse-boolean type)
(defmethod parse-boolean java.lang.Boolean [n] n)
(defmethod parse-boolean java.lang.String [n] (java.lang.Boolean. n))
(defmethod parse-boolean nil [n] false)

(defmulti parse-date type)
(defmethod parse-date nil [n] nil)
(defmethod parse-date org.joda.time.DateTime [n] (java.util.Date. (c/to-long n)))
(defmethod parse-date java.lang.Integer [n] (java.util.Date. (* 1000 n)))
(defmethod parse-date java.lang.Long [n] (java.util.Date. (* 1000 n)))
(defmethod parse-date java.util.Date [n] n)
(defmethod parse-date java.lang.String [n]
  (let [len (if (empty? n)
              nil
              (.length n))]
    (case len
      10  (or
           (apply-date-pattern "yyyy-MM-dd" n)
           (apply-date-pattern "yyyy/MM/dd" n))

      19  (or
           (apply-date-pattern "yyyy-MM-dd HH:mm:ss" n)
           (apply-date-pattern "yyyy/MM/dd HH:mm:ss" n))
      nil)))


(defmulti unix-time type)
(defmethod unix-time java.lang.Long [n] (quot n 1000))
(defmethod unix-time java.util.Date [n] (quot (.getTime n) 1000))
(defmethod unix-time org.joda.time.DateTime [n] (quot (c/to-long n) 1000))

(def char-pool
  (apply concat [(range 0 10)
                 (map char (range 97 123))
                 (map char (range 65 91))]))


(defn time-uuid []
  (f/unparse
   (f/formatter "YYYYMMddHHmmssSSS") (t/now)))


(defn uuid []
  (->
   (java.util.UUID/randomUUID)
   (.toString)))

(defn rand-chars [x]
  (let [rnd (java.util.Random.) limit (count char-pool)]
    (loop [result [] y x]
      (if (zero? y)
        result
        (recur (conj result (nth char-pool (.nextInt rnd limit))) (dec y))))))



(defn rand-numbers [x]
  (let [rnd (java.util.Random.)]
    (loop [result [] limit 0]
      (if (= limit x)
        (apply str result)
        (recur (conj result (.nextInt rnd 10)) (inc limit))))))


(defn rand-str [x]
  (apply
   str
   (rand-chars x)))


(defn split-dot [filename]
  (let [i (.lastIndexOf filename ".")]
    (if (pos? i)
      [(subs filename 0 i) (.toLowerCase
                            (subs filename (+ i 1)))])))


(defn postfix [filename]
  "abc.jpg -> jpg"
  (let [i (.lastIndexOf filename ".")]
    (if (pos? i)
      (.toLowerCase(subs filename (+ i 1))))))


(defn suffix 
  "(suffix abc.jpg thumb) => abc_thumb.jpg
   (suffix abc.jpg thumb png) => abc_thumb.png"
  ([filename suffix-name]
   (suffix filename suffix-name nil))

  ([filename suffix-name postfix]
   (let [f (split-dot filename)]
     (if (= 2 (count f))
       (str
        (nth f 0) "_" suffix-name "." (or postfix (nth f 1)))
       filename))))


(defn to-uuid
  ([filename]
   (if-let [f (split-dot filename)]
     (str (uuid) "." (nth f 1))))

  ([filename suffix]
   (let [f (split-dot filename)]
     (if (= 2 (count f))
         (str
          (uuid) "_" suffix "." (nth f 1))
         filename))))


(defn- keyword-replace [coll ^String a ^String b]
  (cond 
    (map? coll)
    (reduce
     (fn [m [k v]]
       (assoc m (keyword (.replace (name k) a b)) v)) {} coll)

    (instance? clojure.lang.IPersistentCollection coll)
    (reduce 
     (fn [arry v]
       (conj arry (keyword-replace v a b))) [] coll)
    
    :else (.replace (name coll) a b)))


(def ->dash
  (fn [coll]
    (keyword-replace coll "_" "-")))


(def ->underscore
  (fn [coll] 
    (keyword-replace coll "-" "_")))


(defn apply-date-pattern [^String pattern ^String str]
  (try
    (-> (java.text.SimpleDateFormat. pattern)
        (.parse str))
    (catch Exception e
      nil)))

(defn format-date-pattern [^String pattern ^java.util.Date date]
  (try
    (-> (java.text.SimpleDateFormat. pattern)
        (.format date))
    (catch Exception e
      nil)))



(defn java-date [time]
  (java.util.Date. (* time 1000)))


(defn add-secs
  ([secs]
   (add-secs (l/local-now) secs))

  ([time secs]
   (-> (t/plus
        time
        (t/seconds (parse-int secs))))))


(defn add-days
  ([days]
   (add-days (l/local-now) days))

  ([time days]
   (-> (t/plus
        time
        (t/days (parse-int days))))))


