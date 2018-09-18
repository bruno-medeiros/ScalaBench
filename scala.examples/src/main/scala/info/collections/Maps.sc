// Operations on maps
val myMap = Map("I" -> 1, "V" -> 5, "X" -> 10)  // create a map
myMap("I")      // => 1
//myMap("A")      // => java.util.NoSuchElementException

myMap get "A"   // => None
myMap get "I"   // => Some(1)
myMap.updated("V", 15)  // returns a new map where "V" maps to 15 (entry is updated)
// if the key ("V" here) does not exist, a new entry is added
