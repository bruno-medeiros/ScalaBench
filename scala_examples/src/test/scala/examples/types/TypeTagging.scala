package examples.types

import scala.annotation.nowarn
import scala.reflect.ClassTag

import org.scalatest.freespec.AnyFreeSpec

class Foo {
  val foo = "foo"
}
class FooBar extends Foo
class FooBaz extends Foo {
  val z = 1
}

object TypeTagging {

  trait T1 {
    val t1 = "t1"
  }

  type FooX <: Foo

  val f1: Foo = new FooBar
  val f2: Foo = new FooBaz

  val f1x: Object with FooX = (new FooBar).asInstanceOf[FooBar with FooX]

}

class TypeTagging extends AnyFreeSpec {

  import TypeTagging._

  assert(f1.isInstanceOf[FooBar])

  assert(f1.isInstanceOf[FooBar with FooBaz] == false)

  @nowarn("msg=abstract type examples.types.TypeTagging.FooX")
  val x = (assert(f1.isInstanceOf[FooBar with FooX]))

  def xxx[R](foo: Foo)(implicit classTag: ClassTag[R]): R = foo match {
    case _ if classTag.runtimeClass.isAssignableFrom(foo.getClass) => {
      println("YES: " + classTag)
      foo.asInstanceOf[R]
    }
    case _ => throw new Exception
  }

  implicit class FooOps[OG](foo: OG) {
    def toType[TARGET](implicit classTag: ClassTag[TARGET]): Option[TARGET with OG] = foo match {
      case _ if classTag.runtimeClass.isAssignableFrom(foo.getClass) =>
        println("toType: " + classTag)
        Some(foo.asInstanceOf[TARGET with OG])
      case _ => None
    }
  }

  "type union works " in {
    val r: FooBar with FooX = xxx[FooBar with FooX](f1)
    println(r)
  }

  "throws simple " in {
    val r: FooBar with T1 = xxx[FooBar with T1](f1)
    println(r.foo)
    val thrown = intercept[Throwable] {
      println(r.t1)
    }
    System.err.println(thrown)
  }

  // ---------------

  "toType " in {
    f1.toType[FooBar].get

    val r: FooBar with FooX = f1x.toType[FooBar].get
    println(r)

    assert(f1x.toType[FooBaz] == None)
    // Next one should not even compile, but it does
    assert(f1.toType[FooBar with FooX].isDefined)

  }

}
