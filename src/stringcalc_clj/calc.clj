(ns stringcalc-clj.calc
  (:gen-class))

(defn add [values]
  (case values
     "" 0
     (Integer/parseInt values)
    ))
