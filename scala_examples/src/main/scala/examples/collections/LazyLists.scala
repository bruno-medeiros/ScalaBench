package examples.collections

//noinspection ScalaUnusedExpression
object LazyLists extends App {

  // Operations on Streams
  val xs = LazyList(1, 2, 3)
  val xs2 = LazyList.cons(1, LazyList.cons(2, LazyList.cons(3, LazyList.empty))) // same as above

  val x = 1
  x #:: xs // Same as Stream.cons(x, xs)
  // In the Stream's cons operator, the second parameter (the tail)
  // is defined as a "call by name" parameter.

  // Note that x::xs always produces a List
//  val list: List[_] = 1 :: Stream(1, 2)

  private val stream: LazyList[Int] = (0 to 10).to(LazyList)
  assert(stream(5) == 5)
  assert(stream(1) == 1)

  {
    val streamA: LazyList[Int] = (0 to 4).to(LazyList).map(e => {
      println(s"First map for $e"); e
    })

    streamA.head
    streamA(1)
    println("Repeat...")
    streamA(1)

    println("Appending stream...")
    val streamAppended: LazyList[Int] = streamA.lazyAppendedAll(10 to 20)
    println("Appending stream complete.")
    assert(streamAppended(5) == 10)
  }
}