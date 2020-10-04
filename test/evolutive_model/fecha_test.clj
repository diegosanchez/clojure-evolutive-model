(ns evolutive-model.fecha-test
  (:require [clojure.test :refer :all]
            [clojure.spec.gen.alpha :as gen]
            [clojure.spec.test.alpha :as st]
            [evolutive-model.fecha :as f]))

(deftest creacion-fecha-ok
  (is (not (nil? (f/fecha 2020 2 29))))
  (is (not (nil? (f/fecha 2019 2 28))))
  (is (not (nil? (f/fecha 2005 4 30))))
  (is (not (nil? (f/fecha 2005 1 2)))))

(deftest creacion-fecha-mes-invalido
  (is (thrown? AssertionError (f/fecha 2005 13 1))))

(deftest creacion-fecha-dia-invalido
  (is (thrown? AssertionError (f/fecha 2019 2 29)))
  (is (thrown? AssertionError (f/fecha 2005 4 31)))
  (is (thrown? AssertionError (f/fecha 2005 1 32)))
)

(deftest generators-fecha
  (is (= 2
         (count
          (gen/sample f/gen-fecha 2)))))
