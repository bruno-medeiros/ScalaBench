package scala_examples.matchers

import org.scalatest.{FunSuite, Matchers}
import Matchers._

class MatchersExample extends FunSuite with Matchers {

  test("test the should") {
    val result = 3

    result should equal (3) // can customize equality
    result should === (3)   // can customize equality and enforce type constraints
    result should be (3)    // cannot customize equality, so fastest to compile
    result shouldEqual 3    // can customize equality, no parentheses required
    result shouldBe 3       // cannot customize equality, so fastest to compile, no parentheses required


    result shouldNot equal("abc")
  }

  test("shouldEqual - FAILS") {
    val num = 1
    num shouldEqual 2
  }

  test("should equal() - FAILS") {
    val num = 1
    num should equal("abc")
  }

  test("test throwing") {
//    a { List().head } should equal(1)

    a [NoSuchElementException] should be thrownBy { List().head }
  }

  test("string matchers") {
    val str = "a string"
    str should startWith("a")
  }

  private case class Foo(value: Boolean) {
    def foo = true
    def notValue = !value
  }

  test("boolean property methods") {
    val result = Foo(true)
    result shouldBe 'foo  // UGH, uses reflection
  }

  test("containers") {
    val result = List(1, 2, 3)
    result should contain (2, 3)
  }

  test("contain in iterators - not supported") {
    //noinspection ScalaUnusedSymbol
    val result = List(1, 2, 3)
    assertCompiles("result should contain (2)")
    assertDoesNotCompile("result.iterator should contain (2)")
  }

  test("Inspectors") {
    val result = List(1, 2, 3)
    all(result) should be < 10
    atLeast(2, result) should be < 3
    every(result) should be < 5
  }

  import org.scalatest._
  import LoneElement._

  test("Lone element") {
    val set = Set(42)
    set.loneElement should be(42)
  }


  test("Options") {
    val option = Some("hi")
    option shouldEqual Some("hi")
    option shouldBe Some("hi")
    option should === (Some("hi"))
  }
  test("OptionValues .value") {
    import org.scalatest.OptionValues._
    val result = None
    result.value should be ("hi12")
  }


  test("Pattern matching") {
    new Inside {

      case class Name(first: String, middle: String, last: String)

      val name = Name("Jane", "Q", "Programmer")

      inside(name) { case Name(first, _, _) =>
        first should startWith("S")
      }

      // Same as:
      name should matchPattern { case Name("Sarah", _, _) => }
    }
  }

  test("Exceptions") {
    val s = "abc"
    an [IndexOutOfBoundsException] should be thrownBy s.charAt(-1)
  }
}