(ns stringcalc-clj.calc
  (:gen-class))

(require '[clojure.string :as str])


(defn convert-to-valid-ints [values matcher]
  (->> (str/split values matcher)
       (filter #(not (str/blank? %)))
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

(defn sum-values-custom-separators [values separators]
  (sum-values values (re-pattern (str separators "|\n"))))

(defn get-separator-string [input]
  (let [delimiter (subs input (+ (str/index-of input "//") 2) (str/index-of input "\n"))]
    (str/replace delimiter "][" "]|[")
    ))

(defn has-separator? [input]
  (and (str/includes? input "//") (str/includes? input "\n")))

(defn get-rest-of-string [values]
  (if (has-separator? values)
    (let [pos-of-new-line (str/index-of values "\n")]
      (if (= pos-of-new-line nil)
        values
        (subs values (+ pos-of-new-line 1))
        )
      )
    values
    )
  )

(defn get-control-string [input]
  (if (has-separator? input)
    (get-separator-string input)
    ","
    )
  )

(defn total-values [values]
  (let [delimiter (get-control-string values)
        rest-of-string (get-rest-of-string values)]
    (sum-values-custom-separators rest-of-string delimiter)
    )
  )

(defn add [values]
  (cond
    (= values "") 0
    :else (total-values values)
    )
  )
