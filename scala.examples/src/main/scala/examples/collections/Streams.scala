package examples.collections

object Streams extends App {

  // Operations on Streams
  val xs = Stream(1, 2, 3)
  val xs2 = Stream.cons(1, Stream.cons(2, Stream.cons(3, Stream.empty))) // same as above

  val x = 1
  x #:: xs // Same as Stream.cons(x, xs)
  // In the Stream's cons operator, the second parameter (the tail)
  // is defined as a "call by name" parameter.

  // Note that x::xs always produces a List
//  val list: List[_] = 1 :: Stream(1, 2)

  private val stream: Stream[Int] = (0 to 10).toStream
  assert(stream(5) == 5)
  assert(stream(1) == 1)
}
