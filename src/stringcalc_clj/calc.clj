(ns stringcalc-clj.calc
  (:gen-class))

(require '[clojure.string :as str])

(defn get-delimiter [input]
  (subs input (+ (str/index-of input "//") 2) (str/index-of input "\n")))

(defn has-delimiter? [input]
  (and (str/includes? input "//") (str/includes? input "\n"))
  )

(defn convert-to-valid-ints [values matcher]
  (->> (str/split values matcher)
       (map #(Integer/parseInt %))
       (filter #(< % 1001)))
  )

(defn create-error-msg [negatives]
  (str "error: negatives not allowed:" (reduce #(str %1 " " %2) "" negatives))
  )

(defn sum-values [values matcher]
  (let [no-values-too-big (convert-to-valid-ints values matcher)
        negative-values (filter #(< % 0) no-values-too-big)]
    (cond
      (> (count negative-values) 0) (throw (RuntimeException. (create-error-msg negative-values)))
      :else (reduce + no-values-too-big)
      )
    )
  )

(defn sum-values-custom-delimiter [values delimiter]
  (let [matcher (re-pattern (str delimiter "|\n"))]
    (sum-values values matcher)
    )
  )

(defn total-values [values]
  (if (has-delimiter? values)
    (let [delimiter (get-delimiter values)
          rest-of-string (subs values (+ 1 (str/index-of values "\n")))]
      (sum-values-custom-delimiter rest-of-string delimiter)
      )
    (sum-values-custom-delimiter values ",")
    )
  )

(defn add [values]
  (cond
    (= values "") 0
    :else (total-values values)
    )
  )
