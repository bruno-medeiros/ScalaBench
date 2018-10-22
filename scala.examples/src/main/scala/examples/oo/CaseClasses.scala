package examples.oo

object CaseClasses extends App {

  case class Foo(id: Int, name: String) {}

  // TODO: need to review this further and see if there is additional syntax
  // This is not allowed actually
  //case class FooExt(size: Double, override val id: Int) extends Foo(id, "FooExtName") {}

}
