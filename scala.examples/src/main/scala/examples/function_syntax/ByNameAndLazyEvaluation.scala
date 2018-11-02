package examples.function_syntax

object ByNameAndLazyEvaluation extends App {

  // Evaluation methods other than STRICT evaluation

  // Call by name parameter
  def callByName(x: => Int): Unit = {
    println(s"$x + $x")
  }

  var c = 0
  def sideEffecting = {
    println(s"Side effect! ${c += 1}");
    123
  }
  callByName(sideEffecting)

  def hof(x: () => Int): Unit = {
    println(s"$x + $x")
  }
  hof(() => sideEffecting)

  // Call by name parameter
  def callWithLazy(x: Int): Unit = {
    println(s"$x + $x")
  }

  lazy val lazyFoo = { println("Lazy Foo evaluated"); 123 }
  println("About to eval lazy:")
  lazyFoo
  lazyFoo

}