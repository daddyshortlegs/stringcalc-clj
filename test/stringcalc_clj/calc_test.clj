(ns stringcalc-clj.calc-test
  (:require [clojure.test :refer :all]
            [stringcalc-clj.calc :refer :all]))


(deftest simple-calculator
    (is (= 0 (add ""))))
