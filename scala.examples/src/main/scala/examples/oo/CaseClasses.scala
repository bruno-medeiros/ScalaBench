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

  // TODO: need to review this further and see if there is additional syntax
  // This is not allowed actually
  //case class FooExt(size: Double, override val id: Int) extends Foo(id, "FooExtName") {}

}
