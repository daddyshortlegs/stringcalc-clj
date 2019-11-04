(ns stringcalc-clj.calc-test
  (:require [clojure.test :refer :all]
            [stringcalc-clj.calc :refer :all]))


(deftest simple-calculator
    (is (= 0 (add "")))
    (is (= 4 (add "4")))
    (is (= 3 (add "1,2")))
    )
