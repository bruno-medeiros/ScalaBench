package examples.collections


//noinspection OptionEqualsSome,EmptyCheck
object Maps extends App {

  // Create a map
  val myMap = Map("One" -> 1, "Five" -> 5, "Ten" -> 10)
  println(myMap)
  assert(myMap("Five") == 5)
  assert(myMap.get("Two") == None)
  assert(myMap.get("Ten") == Some(10))

  // returns a new map where "V" maps to 15 (entry is updated)
  // if the key ("V" here) does not exist, a new entry is added
  println(myMap.updated("Five", 5555))

  {
    var map2 = Map.empty[String, Integer]

    map2 += "One" -> 1
    map2 += "Five" -> 5
    assert(map2.contains("Five"))
  }

  // Map HOFs
  {
    // map - the generated output is a list...
    assert(
      myMap.map { entry => entry._2 + 100 } ==
      List(101, 105, 110)
    )
    // Note List order might be different, TODO

    // mapValues creates a map, with only values changed
    assert(
      myMap.mapValues { entry => entry + 100} ==
      Map("One" -> 101, "Five" -> 105, "Ten" -> 110)
    )

    // transform is similar to mapValues, with only values changed, but has key argument too
    assert(
      myMap.transform { (key, value) => value + key.length * 100 } ==
      Map("One" -> 301, "Five" -> 405, "Ten" -> 310)
    )
  }
}