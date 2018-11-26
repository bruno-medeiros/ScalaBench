package examples.collections

object Arrays extends App {

  // Fails because uses Java's Array.equals
//  assert(Array(1, 2, 3) == Array(1,2, 3))

  assert(Array(1, 2, 3) sameElements Array(1,2, 3))
}
