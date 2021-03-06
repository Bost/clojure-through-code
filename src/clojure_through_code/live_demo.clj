;; JAX London - Get Functional with Clojure


;;;;;;;;;;;;;;;;;;;;;;;
;; Namespaces

;; Define the scope of your functions and data structures, similar in concept to java packages

(ns clojure-through-code.live-demo)


;;;;;;;;;;;;;;;;;;;;;;;
;; Clojure Syntax

;; Clojure uses a prefix notation and () [] : {} # @ ! special characters
;; no need for lots of ; , and other unneccessary things.

;; bind a name to data
(def my-data [1 2 3 "frog"])

my-data

(class "whats my type")

(def conference-name "Java2Days")


;; bind a name to a function (behaviour)
(defn do-stuff [data]
  (str data))

 
;;;;;;;;;;;;;;;;;;;;;;;
;; Evaluating Clojure

;; Names can be bound to data or functions and when you evaluate the name it returns a value

;; The full clojure version, major, minor & point version of Clojure
*clojure-version*

;; Calling simple functions

(+ 1 2 3 4 5)
(+ 1 2 (- 4 -2) (* (/ 36 3) 4) 5)


;; calling a function we defined previously
(do-stuff my-data)

;; The first element of a list is evaluated as a function call.

;; In Clojure everything is in a List, after all its a dialect of LISP (which stands for List Processing).
;; Effectively you are writing your Clojure coe as an Abstract Syntax Tree (AST), so your code represents the structure.


;;;;;;;;;;;;;;;;;;;;;;;;;
;; Using Java Interoperability

;; java.lang is part of the Clojure runtime environment, so when ever you run a Clojure REPL you can call any Java methods
;; without having to import them or include any dependencies

;; Using java.lang.String methods
(.toUpperCase "fred")

;; From java.lang.System getProperty() as documented at:
;; http://docs.oracle.com/javase/8/docs/api/java/lang/System.html

;; Using java.lang.System static methods
(System/getProperty "java.version")

(System/getProperty "java.vm.name")

;; Make the result prettier using the Clojure str function
(str "Current Java version: " (System/getProperty "java.version"))

;; More functions to get information from our environment
(slurp "project.clj")
(read-string (slurp "project.clj"))
(nth (read-string (slurp "project.clj")) 2)


;; The above code is classic Lisp, you read it from the inside out, so in this case you
;; start with (slurp ...) and what it returns is used as the argument to read-string...

;; Get the contents of the project.clj file using `slurp`
;; Read the text of that file using read-string
;; Select just the third string using nth 2 (using an index starting at 0)


;; You can format the code differently, but in this case its not much easier to read
(nth
 (read-string
  (slurp "project.clj"))
 2)


;; the same behaviour as above can be written using the threading macro
;; which can make code easier to read by reading sequentially down the list of functions.

(->
 "./project.clj"
 slurp
 read-string
 (nth 2))

;; Using the threading macro, the result of every function is passed onto the next function
;; in the list.  This can be seen very clearly usng ,,, to denote where the value is passed
;; to the next function

(->
 "./project.clj"
 slurp ,,,
 read-string ,,,
 (nth ,,, 2))

;; Remember, commas in clojure are ignored

;; To make this really simple lets create a contrived example of the threading macro.
;; Here we use the str function to join strings together.  Each individual string call
;; joins its own strings together, then passes them as a single string as the first argument to the next function

(->
 (str "This" " " "is" " ")
 (str "the" " " "treading" " " "macro")
 (str " in" " " "action."))

;; Using the ->> threading macro, the result of a function is passed as the last parameter
;; of the next function call.  So in another simple series of str function calls,
;; our text comes out backwards.

(->>
 (str " This")
 (str " is")
 (str " backwards"))

;; add all project information to a map
(->> "project.clj"
     slurp
     read-string
     (drop 2)
     (cons :version)
     (apply hash-map)
     (def project))


;;;;;;;;;;;;;;;;;;;;;;;
;; Working with strings & side-effects

;; You could use the Java-like function `println` to output strings.

(println "Hello, whats different with me?  What value do I return")

;; However, something different happens when you evaluate this expression.  This is refered to as a side-effect because when you call this function it returns nil.  The actual text is output to the REPL or console.

;; In Clojure, you are more likely to use the `str` function when working with strings.
(str "Hello, I am returned as a value of this expression")

;; join strings together with the function str
(str "Hello" ", " "HackTheTower UK")


;; using println shows the results in console window, as its a side affect
;; using srt you see the results of the evaluation inline with the code,
;  as the result of a definition or an expression.

;; Avoid code that creates side-effects where possible to keep your software less complex.
;; using the fast feedback of the REPL usually works beter than println statements in debuging


;;;;;;;;;;;;;;;;;;;;;;;;
;; Simple math to show you the basic structure of Clojure

; Math is straightforward
(+ 1 1 2489 459 2.)
(- 2 1)
(* 1 2)
(/ 2 1)
(/ 22 7.0)
(/ 5 20)
(/ (* 22/7 3) 3)
(/ 22 7)
(/ 38 4)

;; Ratios delay the need to drop into decimal numbers.  Once you create a decimal number then everything it touches had a greater potential to becoming a decimal

;; Clojure uses Prefix notation, so math operations on many arguments is easy.
(+ 1 2 3 4 5)
(+ 1 2 (* 3 4) (- 5 6 -7))

(+)
(*)
(* 2)
(+ 4)
(+ 1 2 3)
(< 1 2 3)
(< 1 3 8 4)

;; Clojure functions typically support a variable number of arguments (Variadic functions).

;; Functions can also be defined to respond with different behaviour based on the number of arguments given (multi-arity).  Arity means the number of arguments a function can be called with.

(inc 3)
(dec 4)

(min 1 2 3 5 8 13)
(max 1 2 3 5 8 13)

(apply + [1 2 3])

(apply / [1 2 3])
(/ 53)

(map + [1 2 3.0] [4.0 5 6])

(repeat 4 9)

;; Data oriented
;; Clojure & FP typically have many functions to manipulate data


;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; Equality & Identity
;; Clojure values make these things relatively trivial 

; Equality is =

(= 1 1)
(= 2 1) 

(identical? "foo" "bar")
(identical? "foo" "foo")
(= "foo" "bar")
(= "foo" "foo")

(identical? :foo :bar)
(identical? :foo :foo)

;; Equality is very useful when your data structures are immutable

;; Keywords exist as identifiers and for very fast comparisons

(def my-map {:foo "a"})

(= "a" (:foo my-map))


; Use the not function for logic

(not true)

(if nil
  (str "Return if true")
  (str "Return if false"))

(defn is-small? [number]
  (if (< number 100) "yes" "no"))

(is-small? 50)



;;;;;;;;;;;;;;;;;
;; Persistent Data Structures

;; Lists
;; you can use the list function to create a new list
(list 1 2 3 4)
(list -1 -0.234 0 1.3 8/5 3.1415926)
(list "cat" "dog" "rabit" "fish" 12 22/7)
(list :cat :dog :rabit :fish)

;; you can mix types because Clojure is dynamic and it will work it out later,
;; you can even have functions as elements, because functions always return a value
(list :cat 1 "fish" 22/7 (str "fish" "n" "chips"))


;;;(1 2 3 4)

;; This list causes an error when evaluated


(quote (1 2 3 4))

'(1 2 3 4)
'(-1 -0.234 0 1.3 8/5 3.1415926)
'("cat" "dog" "rabit" "fish")
'(:cat :dog :rabit :fish)
'(:cat 1 "fish" 22/7 (str "fish" "n" "chips"))


;; one unique thing about lists is that the first element is always evaluated as a function call,
;; with the remaining elements as arguments.



;; Vectors
(vector 1 2 3 4)
[1 2 3 4]
[1 2.4 3.1435893 11/4 5.0 6 7]
[:cat :dog :rabit :fish]
[:cat 1 "fish" 22/7 (str "fish" "n" "chips")]
[]


;; Maps
;; Key - Value pairs, think of a Hash Map

{:key "value"}

(:live-the-universe-and-everything 42)

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




;; Combination of data structures

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




;; Sets



;;;;;;;;;;;;;;;;;;;;;;;;
;; Types in Clojure

;; There are types underneath Clojure, however Clojure manages them for your
;; You can take a peek at them if you want...


; Vectors and Lists are java classes too!
(class [1 2 3])
(class '(1 2 3))
(class "Guess what type I am")





;;;;;;;;;;;;;;;;;;;;;;
;; Using Functions 


;; Lets define a very simple anonymous function, that returns a string

(fn [] "Hello Clojurian, hope you are enjoying the REPL")


;; Anonnymous function that squares a number 

(fn [x] (* x x))


; We can also give a name to a function using def

(def i-have-a-name (fn [] "I am not a number, I am a named function - actually we call the name a symbol and it can be used as a reference to the function."))

(i-have-a-name)

; You can shorten this process by using defn
(defn i-have-a-name [] "Oh, I am a new function definition, but have the same name (symbol).")

(i-have-a-name)

; The [] is the list of arguments for the function.

(defn hello [name]
  (str "Hello there " name))

(hello "Steve") ; => "Hello Steve"

; You can also use the annonymous function shorthand, # (), to create functions, (not that useful in this simple example).  The %1 placeholder takes the first argument to the function.  You can use %1, %2 and %3

(def hello2 #(str "Hello " %1 ", are you awake yet?"))
(hello2 "Mike")



;; A function definition that calls different behaviour based on the number of
;; arguments it is called with

(defn greet
  ([] (greet "you"))
  ([name] (print "Hello" name)))

(greet)

;; When greet is called with no arguments, then the first line of the functions behaviour is called.  If you look closely, you see this is not adding duplicate code to our function as 

(greet "World")

;; Refactor greet function

(defn greet
  ([] (greet "you"))
  ([name] (greet name 21 "London"))
  ([name age address] (print "Hello" name, "I see you are" age "and live at" address)))


;; Variadic functions

(defn greet [name & rest]
  (str "Hello " name " " rest))

(greet "John" "Paul" "George" "Ringo")
;; => Hello John (Paul George Ring)


;; unpack the additional arguments from the list

(defn greet [name & rest]
  (apply print "Hello" name rest))





;; Using functions as parameters


;; Pattern mantching example

;; The classic fizzbuzz game were you substitute any number cleanly divisible by 3 with fix and any number cleanly divisible by 5 with buzz.
;; If the number is cleanly divisible by 3 & 5 then substitute fizzbuzz.


;; TODO: find library that contains match

;; (defn fizzbuzz
;;   [n]
;;   (match [(mod n 3) (mod n 5)]
;;          [0 0] :fizzbuzz
;;          [0 _] :fizz
;;          [_ 0] :buzz
;;          :else n))


;; When you realise that this is a simple pattern matching problem, then the solution becomes very easy.
;; We use the match function to compare the two results returned when dividing a number by 3 then by 5
;; We put the result of these two function calls into a vector (an array-like strucutre)
;; Then we compare the modulus results with the 3 possible patterns, returning the appropriate value (fizz, buzz, or fizzbuzz)
;; If there is no match, then the value


;; (defn play-fizbuzz [max-number]
;;   (->> (range max-number)
;;        (map fizzbuzz)
;;        (str)))

;; (play-fizbuzz 30)


;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; Java Interoperability

(def hello-message
  (str "Hello " conference-name))

;; Create a simple Swing dialog box

(javax.swing.JOptionPane/showMessageDialog nil hello-message)


