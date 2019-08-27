package examples.oo

object AnonymousClasses extends App {

  trait FooTrait {
    def foo(): String
  }

  val anon = new FooTrait {
    override def foo(): String = "blah"

    def structuralMember(): Unit = {
      foo()
    }
  }

  // Call method of anonymous class
  println(anon.foo())

  {
    import scala.language.reflectiveCalls

    // Note: this is reflective access:
    anon.structuralMember()
    // Note, compare with:
    StructuralTypes
  }

}
