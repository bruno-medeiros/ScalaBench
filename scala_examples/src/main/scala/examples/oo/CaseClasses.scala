package examples.oo

object CaseClasses extends App {

  abstract case class Foo(id: Int, name: String) {

    def abstractMethod: String

  }

  class FooBar() extends Foo(123, "blah") {
    override def abstractMethod: String = name + "XXX"
  }

  object FooObj extends Foo(456, "FooObje") {
    override def abstractMethod: String = ???
  }


  case class Bar(num: Int, name: String) {
    // Secondary constructor example (is secondary right name?)
    def this(num: Int) = this(123, "default")
  }

  // Copying of case class
  Bar(123, "foo").copy(name = "other")

}
