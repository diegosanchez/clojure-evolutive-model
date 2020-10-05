(ns evolutive-model.fecha
  (:require [evolutive-model.fecha-port :as jf]
            [clojure.spec.gen.alpha :as gen]
            [clojure.spec.alpha :as s]))

(defn mes-con-31-dias?
  [mes]
  (not (nil? (some #(= mes %) '(1 3 5 7 10 12)))))

(defn mes-con-30-dias?
  [mes]
  (not (nil? (some #(= mes %) '(4 6 8 11)))))

(defn anio-bisiesto?
  [anio]
  (or
   (zero? (mod anio 400))
   (zero? (mod anio 4))))

(defn mes-con-maximo-de-dias
  [maximo]
  (s/int-in 1 (+ maximo 1)))

(s/def :fecha/mes-spec
  (s/int-in 1 13))

(s/def :fecha/anio-spec
  (s/int-in 1900 2400))

(defn dia-spec
  [anio mes]
  (cond
    (mes-con-31-dias? mes)       (mes-con-maximo-de-dias 31)
    (mes-con-30-dias? mes)       (mes-con-maximo-de-dias 30)
    (anio-bisiesto? anio)        (mes-con-maximo-de-dias 29)
    :else                        (mes-con-maximo-de-dias 28)))

(defn fecha
  [anio mes dia]
  {:pre [(and (s/valid? :fecha/anio-spec anio)
              (s/valid? :fecha/mes-spec mes)
              (s/valid? (dia-spec anio mes) dia))]}
  {:dia dia :mes mes :anio anio})

(defn mayor
  [fecha1 fecha2]
  (> (jf/fecha-2-timestamp fecha1)
     (jf/fecha-2-timestamp fecha2)))

(def gen-anio-mes-dia
  (gen/bind
    (gen/tuple
     (s/gen :fecha/anio-spec)
     (s/gen :fecha/mes-spec))
    (fn [lst]
      (let [[anio mes] lst]
        (gen/tuple
         (gen/return anio)
         (gen/return mes)
         (s/gen (dia-spec anio mes)))))))

          
(def gen-fecha
  (gen/fmap #(apply fecha %)
            gen-anio-mes-dia))

