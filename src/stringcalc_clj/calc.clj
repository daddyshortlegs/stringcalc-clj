(ns stringcalc-clj.calc
  (:gen-class))

(require '[clojure.string :as str])


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

(defn has-delimiter? [input]
  (and (str/includes? input "//") (str/includes? input "\n")))

(defn get-delimiter-command [input]
  (subs input (+ (str/index-of input "//") 2) (str/index-of input "\n"))
  )

(defn get-delimiter [input]
  (if (has-delimiter? input)
    (get-delimiter-command input)
    ","
    )
  )


(defn is-arbitary-length-separator [input]
  (and (str/starts-with? input "[") (str/ends-with? input "]")))


(defn get-rest-of-string [values]
  (if (has-delimiter? values)
    (let [pos-of-new-line (str/index-of values "\n")]
      (if (= pos-of-new-line nil)
        values
        (subs values (+ pos-of-new-line 1))
        )
      )
    values
    )
  )

(defn total-values [values]
  (let [delimiter (get-delimiter values)
        rest-of-string (get-rest-of-string values)]
    (sum-values-custom-delimiter rest-of-string delimiter)
    )
  )

(defn add [values]
  (cond
    (= values "") 0
    :else (total-values values)
    )
  )
