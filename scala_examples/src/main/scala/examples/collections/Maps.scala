package examples.collections


object Maps extends App {

  // Create a map
  val myMap = Map("One" -> 1, "Five" -> 5, "Ten" -> 10)
  println(myMap)

  assert(myMap("Five") == 5)
  assert(myMap.get("XXX").isEmpty)
  assert(myMap.get("Ten").contains(10))

  assert(myMap.getOrElse("XXX", 123) == 123)


  // ----------------------------------
  // updates

  // returns a new map where "V" maps to 15 (entry is updated)
  // if the key ("V" here) does not exist, a new entry is added
  println(myMap.updated("Five", 5555))

  {
    var map2 = Map.empty[String, Int]

    map2 += "One" -> 1
    map2 = map2 + ("Five" -> 5)
    map2 = map2.updated("One", 11)
    assert(map2 == Map("One" -> 11, "Five" -> 5))
  }


  // ----------------------------------
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