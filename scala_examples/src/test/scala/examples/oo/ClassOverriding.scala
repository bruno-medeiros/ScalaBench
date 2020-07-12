package examples.oo

import scala.annotation.nowarn

import org.scalatest.freespec.AnyFreeSpec

class ClassOverriding extends AnyFreeSpec {

  class Base() {
    val foo2 = "foo2"
    println(foo2)

    val (x, y) = ({ println("IN xx"); "xx" }, 11)

    println(s"x: $x , y: $y")
    println(toString)
  }

  class ImplicitOps

  trait Trait {
    val example: Object
    val exampleToString = example
    def exampleToString2 = example

    implicit def anImplicit: ImplicitOps
  }

  "overriding" in {
    new BaseExt

    case class BaseExt() extends Base() with Trait {
      override val foo2: String = "foo2_XXX"
      override val x: String = "xx_2"

      override val example = Integer.valueOf(123)

      // is null because of vall
      assert(exampleToString == null)
      // is 123
      assert(exampleToString2 == 123)

      @nowarn("msg=resolves to enclosing")
      override implicit val anImplicit: ImplicitOps = implicitly // This is crap because it's self-referencing...
      assert(anImplicit == null)
    }
  }
}
