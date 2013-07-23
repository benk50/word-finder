(ns ast-dojo.core
  (:require [clojure.math.combinatorics :as combo])
  (:require [clojure.java.io :as io]))

(defn read-dict [] (
     with-open [rdr (io/reader(io/resource "public/words.txt"))]
       (doall (line-seq rdr))))

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

(defn ^String substring?
  "True if s contains the substring."
  [substring ^String s]
  (.contains s substring))

(defn filter-words
  "Takes the available tiles and the character to 
  intersect with, and finds words from word list"
  [tiles req-char]
  (filter (fn [word] (substring? req-char word)) (find-words (str tiles req-char)))
)
