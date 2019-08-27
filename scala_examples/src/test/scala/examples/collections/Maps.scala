package examples.collections

import scala.collection.SeqMap


object Maps extends App {

  // Create a map
  val myMap = SeqMap("One" -> 1, "Five" -> 5, "Ten" -> 10)
  println(myMap)

  assert(myMap("Five") == 5)
  assert(myMap.get("XXX").isEmpty)
  assert(myMap.get("Ten").contains(10))

  assert(myMap.getOrElse("XXX", 123) == 123)


  // ----------------------------------
  // updates

  // returns a new map where "Five" maps to 500 (entry is updated)
  // if the key ("V" here) does not exist, a new entry is added
  assert(myMap.updated("Five", 500) == myMap + ("Five" -> 500))

  assert(myMap.updatedWith("Five")(value => value.map(_ + 100)) == myMap + ("Five" -> 105))

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
    // note: map must be a SeqMap
    assert(
      myMap.map[Int] { entry => entry._2 + 100 } == List(101, 105, 110)
    )

    // mapValues creates a map, with only values changed (only available in view)
    {
      val mappedMap = myMap.view.mapValues { value => value + 100 }
      assert(mappedMap.toMap == Map("One" -> 101, "Five" -> 105, "Ten" -> 110))
    }

    // transform is similar to mapValues, with only values changed, but has key argument too
    assert(
      myMap.transform { (key, value) => value + key.length * 100 } ==
      Map("One" -> 301, "Five" -> 405, "Ten" -> 310)
    )
  }
}