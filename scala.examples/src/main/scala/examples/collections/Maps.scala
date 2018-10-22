package examples.collections


//noinspection OptionEqualsSome,EmptyCheck
object Maps extends App {
  // Create a mapP
  val myMap = Map("One" -> 1, "Five" -> 5, "Ten" -> 10)
  println(myMap)
  assert(myMap("Five") == 5)
  assert(myMap.get("Two") == None)
  assert(myMap.get("Ten") == Some(10))

  println(myMap.map { _._2 + 100 })
  println(myMap.map { entry => entry._2 + 100 })
  println(myMap.map { case (_, v) => v + 100 })

  // returns a new map where "V" maps to 15 (entry is updated)
  // if the key ("V" here) does not exist, a new entry is added
  println(myMap.updated("Five", 5555))
}