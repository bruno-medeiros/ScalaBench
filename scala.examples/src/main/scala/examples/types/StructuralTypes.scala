package examples.types

import scala.language.reflectiveCalls

object StructuralTypes {

  class MyClass {
    def foo(): String = { "123" }
  }

  type Fooable = { def foo(): String }

  val structureTypeValue: Fooable = new MyClass
  // This is a reflective call!:
  structureTypeValue.foo()

  // Doesn't work because it's an anonymous type:
//  x.isInstanceOf[Fooable.type]

}