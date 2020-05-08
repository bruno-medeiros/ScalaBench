package examples.collections

object ArrayExamples extends App {

  // Fails because uses Java's Array.equals
//  assert(Array(1, 2, 3) == Array(1,2, 3))

  assert(Array(1, 2, 3) sameElements Array(1, 2, 3))

  // Arrays and type parameters
  /*
  At run-time, when an element of an array of type Array[T] is accessed or updated there is a
  sequence of type tests that determine the actual array type, followed by the correct array
  operation on the Java array. These type tests slow down array operations somewhat. You can
  expect accesses to generic arrays to be three to four times slower than accesses to
  primitive or object arrays.
   */
  def foo[T](arr: Array[T]): Unit = {
    val elem = arr(1)
    println(elem.getClass)
  }

  foo(Array[Int](10, 20, 30))

}
