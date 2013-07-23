(ns ast-dojo.core
  (:require [clojure.math.combinatorics :as combo]))

(defn read-dict [] (
     with-open [rdr (clojure.java.io/resource "words.txt")]
       (doall (line-seq rdr))
     )
)

(def dictionary (read-dict))

(def letter-permutations
  (group-by sort dictionary))

(defn get-combinations
  ([letters lower-bound] (get-combinations letters lower-bound lower-bound))
  ([letters lower-bound upper-bound]
     (mapcat #(distinct (combo/combinations letters %)) (range lower-bound (inc upper-bound)))))


(defn find-words
  ([letters] (find-words letters 2))
  ([letters lower-bound]
     (mapcat letter-permutations (get-combinations (sort letters) lower-bound 15))))  

(defn charAtPos [word index char]
  (= (.charAt word index) char)
)

(defn filter-words [charSet index char]
   (def all-words (find-words charSet))
   (filter (fn [x] (charAtPos x index char)) all-words)
)

