package examples.function_syntax

object Lambdas_NonLocalReturn2 extends App {

  private class LambdaNonLocalReturn {

    var foo: String => Int = {
      a => a.length
    }

    def modifyFoo(): String = {
      this.foo = (str: String) => {
        println("inside modifyFoo")
        return str + "xxx"
      }
      "XXX"
    }
  }

  private val x = new LambdaNonLocalReturn()
  x.modifyFoo()
  x.foo("123")
}