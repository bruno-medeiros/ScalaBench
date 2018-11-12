package examples.oo.types

object AnonymousTypes {

  val anon = new {
    var foo = 123
    def incFoo(): Unit = {
      foo += 1
    }
  }

  anon.foo = 10
  anon.incFoo()
}
