;;;;;;;;;;;;;;;;;;;;;;;;
;; Creating data structures in Clojure

(ns clojure-through-code.02-data-structures)

;; Clojure has several data structures as part of the language,
;; list (a linked list),
;; vector (indexed access like an array),
;; map (key / Value pairs, usually using clojure :keyword type for the keys,
;; set (unique elements, not ,ordered by default).
;; These data structures are typically used rather than defining your own types



;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; Clojure Built-in data structures


;; list ()
;; a general set of elements with a sequential lookup time

;; you can use the list function to create a new list
(list 1 2 3 4)
(list -1 -0.234 0 1.3 8/5 3.1415926)
(list "cat" "dog" "rabit" "fish" 12 22/7)

;; you can use any "types" in your list or any other Clojure collection
(list :cat :dog :rabit :fish)

;; you can mix types because Clojure is dynamic and it will work it out later,
;; you can even have functions as elements, because functions always return a value
(list :cat 1 "fish" 22/7 (str "fish" "n" "chips"))


;; further examples of mixing types

(list )
(list 1 2 3 4) 

(def five 5)  ;; bind the name five to the value 5, clojure's equivalent to assignment

(list 1 2 "three" [4] five '(6 7 8 9))

;; (1 2 3 4) ;; This list causes an error when evaluated

'(1 2 3 4)

(quote (1 2 3 4))

;; one unique thing about lists is that the first element is always evaluated as a function call,
;; with the remaining elements as arguments.

;; So, defining a list just using () will cause an error

;; This list definition will fail, unless you have defined a function called 1
;; ( 1 2 3 4)

;; There is a special function called quote that tells Clojure to just treat the
;; list as data.

(quote (1 2 3 4))


;; This syntax is actually more code to type than (list 1 2 3 4),
;; so there is a shortcut for the quote function using the ' character

'(1 2 3 4)
'(-1 -0.234 0 1.3 8/5 3.1415926)
'("cat" "dog" "rabit" "fish")
'(:cat :dog :rabit :fish)
'(:cat 1 "fish" 22/7 (str "fish" "n" "chips"))

;; The quote shortcut is uses where ever you have a list that you want to treat just as data.
;; Another example is when you are including functions from other namespaces
;;(ns my-namespace.core
;;  use 'my-namespace.library)


;; Duplicate elements in a list ?

(list 1 2 3 4 1)
(list "one" "two" "one")
(list :fred :barney :fred)


;; Sets #{}

;; #{1 2 3 4 1}
;; duplicate key error

(set [1 2 3 4 1])
;; only returns unique set from the collection
(sorted-set 1 4 0 2 9 3 5 3 0 2 7 6 5 5 3 8)



;; Vector []
;; A vector looks like an array and is better for random access.
;; A vector has an index to look up elements at a specific point, speeding up random access
;; Vectors and maps are the most common data structures use to hold data in Clojure

;; you can use the vector function to create a new vector
(vector 1 2 3 4)

;; Usually you just use the [] notation to create a vector to help keep your code readable
[1 2 3 4]
[1 2.4 3.1435893 11/4 5.0 6 7]
[:cat :dog :rabit :fish]
[:cat 1 "fish" 22/7 (str "fish" "n" "chips")]
[]

;; You can also put other data structures in vectors, in this example a list is an element of the vector
[1 2 3 '(4 5 6)]

;; remember we defined five earlier in the code
[1 2 3 4 (list five)]


;; Duplicate elements ?
[1 2 3 4 1]



;; Map {}
;; A key / value pair data structure
;; keys are usually defined as a :keyword, although they can be anything

;; Typicall a :keyword is used for a the key in a map, with the value being
;; a string, number or another keyword
{:key "value"}
(:key 42)
{:key symbol}
{:key :value}
{"key" "value"}
 
;; As with other collections, you can use anything as a key or a value,
;; they are all values underneath.
{:key 42}
{"key" "value"}


{:a 1 :b 2 :c 3 }

;; Its also quite common to have maps made up of other maps

;; Here is an example of a map made up of a :keyword as the key and
;; a map as the value.  The map representing the value is itself
;; defined with :keywords and strings

{:starwars {
    :characters {
      :jedi   ["Luke Skywalker"
               "Obiwan Kenobi"]
      :sith   ["Darth Vader"
               "Darth Sideous"]
      :droids ["C3P0"
               "R2D2"]}
    :ships {
      :rebel-alliance  ["Millenium Falcon"
                        "X-wing figher"]
      :imperial-empire ["Intergalactic Cruser"
                        "Destroyer"
                        "Im just making these up now"]}}}



;; Individual starwars characters can be defined using a map of maps
 {:luke   {:fullname "Luke Skywarker" :skill "Targeting Swamp Rats"}
  :vader  {:fullname "Darth Vader"    :skill "Crank phone calls"}
  :jarjar {:fullname "JarJar Binks"   :skill "Upsetting a generation of fans"}}


;; To make the starwars character information easier to use, lets give the collection of characters a name using the def function

(def starwars-characters
   {:luke   {:fullname "Luke Skywarker" :skill "Targeting Swamp Rats"}
    :vader  {:fullname "Darth Vader"    :skill "Crank phone calls"}
    :jarjar {:fullname "JarJar Binks"   :skill "Upsetting a generation of fans"}})

;; Now we can refer to the characters using keywords

;; Using the get function we return all the informatoin about Luke
(get starwars-characters :luke)

;; By wrapping the get function around our first, we can get a specific
;; piece of information about Luke
(get (get starwars-characters :luke) :fullname)

;; There is also the get-in function that makes the syntax a little easier to read
(get-in starwars-characters [:luke :fullname])
(get-in starwars-characters [:vader :fullname])

;; Or you can get really Clojurey by just talking to the map directly
(starwars-characters :luke)
(:fullname (:luke starwars-characters))
(:skill (:luke starwars-characters))

(starwars-characters :vader)
(:skill (:vader starwars-characters))
(:fullname (:vader starwars-characters))


;; And finally we can also use the threading macro to minimise our code further

(-> starwars-characters
    :luke)

(-> starwars-characters
    :luke
    :fullname)

(-> starwars-characters
    :luke
    :skill)

;; More on Destructuring
;; https://gist.github.com/john2x/e1dca953548bfdfb9844


;; Duplicate keys in a map are not allowed, so the following maps...

;; {"fish" "battered" "chips" "fried" "fish" "battered and fried"}
;; {:fish "battered" :chips "fried" :fish "battered & fried"}

;; ...throw dupicate key errors

;; Duplicate values are okay though
{"fish" "battered" "chips" "fried" "cod" "fried"}


; What is the difference between Collections & Sequences
;;;;;;;;;;;;;;;;;;;

; Vectors and Lists are java classes too!
(class [1 2 3])
(class '(1 2 3))

; A list would be written as just (1 2 3), but we have to quote
; it to stop the reader thinking it's a function.
; Also, (list 1 2 3) is the same as '(1 2 3)

; Both lists and vectors are collections:
(coll? '(1 2 3)) ; => true
(coll? [1 2 3]) ; => true

; Only lists are seqs.
(seq? '(1 2 3)) ; => true
(seq? [1 2 3]) ; => false
