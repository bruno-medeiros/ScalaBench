package examples.function_syntax

object Lambdas extends App {

  // Some basic syntax:
  var doubler1 = (a: Int) => a * 2
  var doubler2 = (_: Int) * 2
  var doubler1b : Int => Int = a => a * 2
  var doubler2b : Int => Int = { _ * 2 }


  // By name argument:
  def foo_byName(byName: => String): Unit = {
    println("In foo_byName")
  }
  foo_byName({ println("(foo_byName arg evaluated)"); "ARG" })

  def foo(arg: String): Unit = {
    println("In foo")
  }
  foo({ println("(foo arg evaluated)"); "ARG" })

}