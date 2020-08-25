(ns evolutive-model.main-test
  (:require [clojure.test :refer :all]
            [clojure.spec.test.alpha :as st]
            [evolutive-model.main :refer :all]))

(deftest a-test
  (testing "FIXME, I fail."
    (is (= 0 0))))

(def dic-10-2020
  (puntual 10 12 2020))

(def hoy
  (fecha 24 8 2020))

(def dic-25
  (anual 25 12))

(deftest test-feriado-puntual
  (testing "10-12-2020 es feriado puntual"
    (is (not (feriado? dic-10-2020 hoy )))
    (is (feriado? dic-10-2020 dic-10-2020))))

(deftest test-feriado-anual
  (testing "feriado anual"
    (is (not (feriado? dic-25 hoy)))
    (is (feriado? dic-25 dic-25))))

(deftest test-feriado-puntual-creacion
  (testing "feriado puntual invalido"
   (is (thrown? AssertionError (puntual 1 13 2020)))))

(deftest test-check
  (is (=
       (st/summarize-results (st/check `fecha))
       {:total 1, :check-passed 1})))
