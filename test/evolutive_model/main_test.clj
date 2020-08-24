(ns evolutive-model.main-test
  (:require [clojure.test :refer :all]
            [evolutive-model.main :refer :all]))

(deftest a-test
  (testing "FIXME, I fail."
    (is (= 0 0))))

(def dic-10-2020
  (feriado-puntual 10 12 2020))

(def hoy
  {:dia 24 :mes 8 :anio 2020})

(deftest test-feriado-puntual
  (testing "10-12-2020 es feriado puntual"
    (is (not (es-feriado? hoy [{:dia 10 :mes 12 :anio 2020}])))
    (is (es-feriado? dic-10-2020 [{:dia 10 :mes 12 :anio 2020}]))))

(deftest test-feriado-puntual-creacion
  (testing "feriadob puntual invalido"
   (is (thrown? AssertionError (feriado-puntual 1 13 2020)))))
