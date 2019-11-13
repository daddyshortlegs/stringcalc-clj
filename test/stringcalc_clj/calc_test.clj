(ns stringcalc-clj.calc-test
  (:require [clojure.test :refer :all]
            [stringcalc-clj.calc :refer :all]))


(deftest simple-calculator
    (is (= 0 (add "")))
    (is (= 4 (add "4")))
    (is (= 3 (add "1,2")))
    )

(deftest arbitrary-number-size
  (is (= 45 (add "1,2,3,4,5,6,7,8,9")))
  )

(deftest newline-separator
  (is (= 6 (add "1\n2,3")))
  )

(deftest custom-separators
  (is (= 3 (add "//;\n1;2")))
  (is (= 3 (add "//:\n1:2")))
  )

(deftest disallow-negatives
  (is (thrown-with-msg? RuntimeException #"error: negatives not allowed: -2 -3" (add "1,-2,-3")))
  (is (thrown-with-msg? RuntimeException #"error: negatives not allowed: -4 -6" (add "1,-4,-6")))
  )

(deftest ignore-numbers-greater-than-1000
  (is (= 2 (add "1001,2")))
  )

;(deftest arbitrary-length-separators
;  (is (= 3 (add "//[***]\n1***2***3"))))