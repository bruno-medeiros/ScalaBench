package examples.collections

//noinspection SimplifiableFoldOrReduce
object Sequences extends App {
  // Literals:
  'a' to 'g'
  1 to 5

  //
  List(1, 2, 30, 40, 5).sliding(2, 2).foreach(e => {print(e + " || ")})
  println("---")
  List(1, 2, 30, 40, 5).sliding(2).foreach(e => {print(e + " || ")})

  // Folding and reduce:
  List(1, 2, 10, 20).foldLeft(0) ( (a, b) => { a + b } )
  List(1, 2, 10, 20).foldLeft(0) (_ + _ + 0)


  List(1, 2, 10, 20).reduce((a, b) => a + b)
  List(1).reduce((a, b) => a + b)

}
