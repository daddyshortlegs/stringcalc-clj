(ns stringcalc-clj.calc
  (:gen-class))

(require '[clojure.string :as str])

(defn add [values]
  (cond
    (= values "") 0
    :else   (->> (str/split values #",|\n")
                 (map #(Integer/parseInt %))
                 (reduce +))
    )
  )
