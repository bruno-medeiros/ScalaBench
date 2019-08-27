package examples.functions

object Lambdas_NonLocalReturn extends App {

  def foo(): String => Int = {
    _: String => {
      println("inside fooResult")
      return str2 => str2.length
    }
  }

  val fooResult = foo()
  fooResult("abc")

}