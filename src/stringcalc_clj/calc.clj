(ns stringcalc-clj.calc
  (:gen-class))

(require '[clojure.string :as str])

(defn get-delimiter [input]
  (subs input (+ (str/index-of input "//") 2) (str/index-of input "\n")))

(defn has-delimiter? [input]
  (and (str/includes? input "//") (str/includes? input "\n"))
  )

(defn sum-values [values]
  (->> (str/split values #",|\n")
       (map #(Integer/parseInt %))
       (reduce +))
  )

(defn sum-values-custom-delimiter [values delimiter]
  (let [matcher (re-pattern (str delimiter "|\n"))]
    (->> (str/split values matcher)
         (map #(Integer/parseInt %))
         (reduce +))
    )
  )



(defn total-values [values]
  (if (has-delimiter? values)
    (let [delimiter (get-delimiter values)
          rest-of-string (subs values (+ 1 (str/index-of values "\n")))]
      (sum-values-custom-delimiter rest-of-string ";")
      )
    (sum-values values)
    )
  )

(defn add [values]
  (cond
    (= values "") 0
    :else (total-values values)
    )
  )
