package examples.oo.types

object TypeContextBounds extends App {

  case class MyBox[T](obj: T) { }

  // Context bound here - contrain type T such than an implicit MyBox[T] exists
  def foo[T: MyBox](): String = {
    implicitly[MyBox[T]].toString
  }

  implicit val b1 = MyBox[Int](123)
  // Can't have two implicits available for call to foo
//  implicit val b2 = MyBox("xpto")

  foo()
}
