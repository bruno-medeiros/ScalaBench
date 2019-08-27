package examples.matching

object StringPatternMatching extends App {

  val s"$name, $age, $city" = "Alex, 25, London"

  assert(name == "Alex" && age == "25" && city == "London")

}
