package examples.oo

import scala.language.reflectiveCalls

object StructuralTypes extends App {

  // With anonymous type
  val anonClass = new {
    def foo(): String = { "blah"}
  }
  // This is a reflective call!:
  anonClass.foo()


  class MyClass {
    def foo(): String = { "123" }
  }

  // Structural type:
  type Fooable = { def foo(): String }

  var aStructuralType: Fooable = new MyClass
  // This is a reflective call!:
  aStructuralType.foo()


}
