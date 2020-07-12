package examples.types

import examples.MiscUtil.getTypeTag
import org.scalatest.Assertions

object PathDependentTypes_SingletonTypes extends App {

  // Simplest example:
  val foo = "123"
  val foo2 = foo
  val bar: foo.type = foo
  // Following does not compile, since foo2's type is just String
  //  val bar2: foo.type = foo2

  // Notice return type, it's path-dependant
  def identity(obj: Object): obj.type = obj

  // Path-dependent type:
  val x1: String = identity("abc")
  val x2: List[Int] = identity(List(123))

}

object PathDependentTypes extends App with Assertions {

  class Foo {
    class Bar {}

    def foo(bar: Bar): Unit = {}
    def foo_alt(bar: Foo#Bar): Unit = {}
  }

  val f1 = new Foo
  val f2 = new Foo

  f1.foo(new f1.Bar)
  println("Path dependent type: " + getTypeTag(new f1.Bar))

  // Does not work, needs f1
//  f2.foo(new f1.Bar)
  assertDoesNotCompile("f2.foo(new f1.Bar)")

  // But this works (foo_alt):
  f2.foo_alt(new f1.Bar)
}
