package examples.oo

object StructuralTypes extends App {

  class MyClass {
    def foo(): String = { "123" }
  }

  type Fooable = { def foo(): String }

  var aStructuralType: Fooable = new MyClass
  // This is a reflective call!:
  aStructuralType.foo()

  // Try again, with anonymous type
  aStructuralType = new {
    def foo(): String = { "blah"}
  }
  // Again, This is a reflective call!:
  aStructuralType.foo()

  // Doesn't work because it's an anonymous type:
//  x.isInstanceOf[Fooable.type]

}
