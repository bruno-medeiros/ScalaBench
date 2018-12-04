package examples.collections

import scala.collection.immutable.NumericRange

//noinspection SimplifiableFoldOrReduce,SimplifyBoolean
object Sequences extends App {

  // Ranges:
  val atoz: NumericRange[_] = 'a' to 'z'
  val oneToTen: Range = 1 until 10


  // "modifications" - element operations

  // Prepend elements:
  assert(1 :: List(2, 3) == List(1, 2, 3))

  // Append element:
  assert((List(1, 2, 3) :+ 4) == List(1, 2, 3, 4))
  // Append another sequence
  assert((List(1, 2) ++ List(3, 4)) == List(1, 2, 3, 4))
  var list123 = List(1, 2, 3)
  assert({ list123 ++= List(4, 5); list123} == List(1, 2, 3, 4, 5))


  // Queries

  private val ints = Vector(1, 2, 3)
  assert(ints.contains(2, 3) == false) // Surprising, but it's because (2, 3) gets transformed to tuple.


  // ----------------------------------
  // HOFs - Transformations (create new collections):

  // .map
  assert(List(1, 2, 3).map{ _ + 10} == List(11, 12, 13))

  // .transform for sequences (must map to same element type)
  assert(Array(1, 2, 3).transform(i => {
    println(s"Foo: $i")
    i + 10
  }).toArray sameElements Array(11, 12, 13))

  // Take a sequence, create a map from it using elements as keys
  assert(List(1, 2, 1, 4).map(a => (a, a  + 100)).toMap
    == Map(1 -> 101, 2 -> 102, 4 -> 104))


  // ----------------------------------
  // HOFs - Accessors (create single value):

  // Folding and reduce:
  List(1, 2, 10, 20).foldLeft(0)((a, b) => {
    a + b
  })
  List(1, 2, 10, 20).foldLeft(0)(_ + _ + 0)

  List(1, 2, 10, 20).reduce((a, b) => a + b)
  List(1).reduce((a, b) => a + b)

  // .aggregate (can work in parallell
  assert(List("foo", "bar", "xpto").aggregate(0)((acc, s) => acc + s.length, _ + _) == 10)


  // ----------------------------------
  // List sliding
  List(1, 2, 30, 40, 5).sliding(2, 2).foreach(e => {
    print(e + " || ")
  })
  println("---")
  List(1, 2, 30, 40, 5).sliding(2).foreach(e => {
    print(e + " || ")
  })
  println


  // -- Other
  val res: Seq[String] = Vector("one", "two", "three", "four")
    .groupBy(_.charAt(0)) // The collection type from new value is taken from original collection
    .apply('t')
  println(res.getClass)
}