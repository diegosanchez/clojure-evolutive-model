(ns evolutive-model.main-test
  (:require [clojure.test :refer :all]
            [evolutive-model.main :refer :all]))

(deftest a-test
  (testing "FIXME, I fail."
    (is (= 0 0))))

(def dic-10-2020
  (feriado-puntual 10 12 2020))

(def hoy
  (fecha 24 8 2020))

(deftest test-feriado-puntual
  (testing "10-12-2020 es feriado puntual"
    (is (not (es-feriado? hoy [dic-10-2020])))
    (is (es-feriado? dic-10-2020 [dic-10-2020]))))

(def dic-25
  (feriado-anual 25 12))

(deftest test-feriado-anual
  (testing "feriado anual"
    (is (not (es-feriado? hoy [dic-25])))
    (is (es-feriado? dic-25 [dic-25]))))

(deftest test-feriado-puntual-creacion
  (testing "feriadob puntual invalido"
   (is (thrown? AssertionError (feriado-puntual 1 13 2020)))))
