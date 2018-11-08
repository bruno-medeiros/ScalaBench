package examples.collections

import scala.collection.mutable

//noinspection SimplifiableFoldOrReduce
object Sequences extends App {
  // Ranges:
  'a' to 'z'
  1 until 10

  assert((List(1, 2, 3) :+ 4) == List(1, 2, 3, 4))
  assert((List(1, 2) ++ List(3, 4)) == List(1, 2, 3, 4))



  /// List HOF's
  // .map
  assert(List(1, 2, 3).map{ _ + 10} == List(11, 12, 13))

  // .transform for sequences (must map to same element type)
  assert(Array(1, 2, 3).transform(i => {
    println(s"Foo: $i")
    i + 10
  }).toArray eq Array(11, 12, 13))


  // Folding and reduce:
  List(1, 2, 10, 20).foldLeft(0)((a, b) => {
    a + b
  })
  List(1, 2, 10, 20).foldLeft(0)(_ + _ + 0)


  // List sliding
  List(1, 2, 30, 40, 5).sliding(2, 2).foreach(e => {
    print(e + " || ")
  })
  println("---")
  List(1, 2, 30, 40, 5).sliding(2).foreach(e => {
    print(e + " || ")
  })




  List(1, 2, 10, 20).reduce((a, b) => a + b)
  List(1).reduce((a, b) => a + b)
}