(ns stringcalc-clj.calc
  (:gen-class))

(require '[clojure.string :as str])

(defn add [values]
  (cond
    (= values "") 0
    :else   (->> (str/split values #",")
                 (map #(Integer/parseInt %))
                 (reduce +))
    )
  )
