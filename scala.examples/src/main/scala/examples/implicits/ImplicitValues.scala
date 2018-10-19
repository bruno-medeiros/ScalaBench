package examples.implicits

object ImplicitValues extends App {

  class MyClass { def foo(string: String): Unit = println(s"Myclass + $string") }

  // Implicit value:
  implicit val imp: MyClass = new MyClass

  implicitly[MyClass].foo("Hello")

  def withImplicit(name: String) (implicit myClass: MyClass): Unit = {
    myClass.foo(name)
  }

  withImplicit("Implicit function")
}