package examples.function_syntax

import examples.LazyVariables

object CallByName extends App {

  // Evaluation methods other than STRICT evaluation

  // Call by name parameter
  def callByName_evalTwice(x: => Int): Unit = {
    println(s"$x + $x")
  }

  var c = 0
  def sideEffecting = {
    println(s"Side effect! ${c += 10}")
    c
  }
  callByName_evalTwice(sideEffecting)
  // sideEffecting is evaluated twice
  assert(c == 20)

  // See also:
  LazyVariables

}