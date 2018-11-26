package examples.collections

//noinspection SimplifiableFoldOrReduce
object Sequences extends App {
  // Ranges:
  'a' to 'z'
  1 until 10

  assert((List(1, 2, 3) :+ 4) == List(1, 2, 3, 4))
  assert((List(1, 2) ++ List(3, 4)) == List(1, 2, 3, 4))


  // List sliding
  List(1, 2, 30, 40, 5).sliding(2, 2).foreach(e => {
    print(e + " || ")
  })
  println("---")
  List(1, 2, 30, 40, 5).sliding(2).foreach(e => {
    print(e + " || ")
  })


  /// List HOF's
  // .map
  assert(List(1, 2, 3).map{ _ + 10} == List(11, 12, 13))

  // .transform for sequences (must map to same element type)
  assert(Array(1, 2, 3).transform(i => {
    println(s"Foo: $i")
    i + 10
  }).toArray sameElements Array(11, 12, 13))

  // .aggregate
  assert(List("foo", "bar", "xpto").aggregate(0)((acc, s) => acc + s.length, _ + _)
  == 10)

  // Folding and reduce:
  List(1, 2, 10, 20).foldLeft(0)((a, b) => {
    a + b
  })
  List(1, 2, 10, 20).foldLeft(0)(_ + _ + 0)

  List(1, 2, 10, 20).reduce((a, b) => a + b)
  List(1).reduce((a, b) => a + b)


  // Take a list of element, create a map from that using elements as keys
  assert(List(1, 2, 1, 4).map(a => (a, a  + 100)).toMap
    == Map(1 -> 101, 2 -> 102, 4 -> 104))

}