package examples.oo

object AnonymousClasses extends App {

  val anon = new {
    var foo = 123
    def incFoo(): Unit = {
      foo += 1
    }
  }

  anon.foo = 10
  anon.incFoo()

  // Note, compare with:
  StructuralTypes

}
