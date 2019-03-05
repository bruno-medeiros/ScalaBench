package examples.functions

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


  // Closure - return of local var
  {
    def foo(): Int => String = {
      var local = 10
      val result = (inc: Int) => (local + inc).toString
      local = 100
      result
    }
    // Local var is correctly captured
    assert(foo()(23) == "123")
  }
}