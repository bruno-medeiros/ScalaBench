package scala_examples.testing.matchers

import org.scalatest.{FunSuite, Matchers}
import scala_examples.testing.FailureExamples

class MatchersExample extends FunSuite with Matchers {

  test("should") {
    val result = 3

    result should equal (3) // can customize equality
    result should === (3)   // can customize equality and enforce type constraints
    result should be (3)    // cannot customize equality, so fastest to compile
    result shouldEqual 3    // can customize equality, no parentheses required
    result shouldBe 3       // cannot customize equality, so fastest to compile, no parentheses required


    result shouldNot equal("abc")
  }

  test("shouldEqual - FAILURE", FailureExamples) {
    val num = 1
    num shouldEqual 2
  }

  test("should equal() - FAILURE", FailureExamples) {
    val num = 1
    num should equal("abc")
  }

  //

  test("Inspectors") {
    val result = List(1, 2, 3)
    all(result) should be < 10
    atLeast(2, result) should be < 3
    every(result) should be < 5
  }

  // --- Containers - Option

  test("Options") {
    val option = Some("hi")
    option shouldEqual Some("hi")
    option shouldBe Some("hi")
    option should === (Some("hi"))
  }
  test("Options FAILURE - ===", FailureExamples) {
    //noinspection OptionEqualsSome
    assert(Some(List(1, 2, 3)) === Some(List(1, 2)))
  }
  test("Options FAILURE - .contains", FailureExamples) {
    assert(Some(List(1, 2, 3)).contains(List(1, 2)))
  }
  test("Options FAILURE - should contain", FailureExamples) {
    Some(List(1, 2, 3)) should contain (List(1, 2))
  }

  test("OptionValues .value") {
    import org.scalatest.OptionValues._
    val result: Option[_] = Some("hi")
    result.value should be ("hi")
  }


  // ---- Containers / collections

  test("containers") {
    val result = List(1, 2, 3)

    result should contain(2) // SINGLE ELEMENT ONLY
//    result should contain(1, 2) // doesn't work because single element

    result should contain theSameElementsInOrderAs List(1, 2, 3)
    result should contain inOrder (1, 3)
    result should contain inOrderOnly (1, 2, 3)


    // Using equal
    result.toSet shouldEqual Set(3, 2, 1)
    result.toSet should equal(Set(3, 2, 1))
    //noinspection SameElementsToEquals
    assert(result.sameElements(Vector(1, 2, 3)))

    // These do not compile as expected
    //List(1, 2, 3) should contain (theSameElementsAs(List(1, 2, 3)))
    //result should contain(theSameElementsAs(xs = List(1, 2, 3)))
  }

  test("containers FAILURE - Set ==", FailureExamples) {
    val result = Set(1, 2, 3)
    assert(result == Set(3, 2, 1, 4))
  }
  test("containers FAILURE - Set shouldEqual", FailureExamples) {
    val result = Set(1, 2, 3)
    result shouldEqual Set(3, 2, 1, 4)
  }
  test("containers FAILURE - Set contain theSameElementsAs", FailureExamples) {
    val result = Set(1, 2, 3)
    result should contain theSameElementsAs Set(1, 2, 4, 3)
  }

  test("containers FAILURE - List ==", FailureExamples) {
    val result = List(1, 2, 3)
    assert(result == List(3, 2, 1, 4))
  }
  test("containers FAILURE - List shouldEqual", FailureExamples) {
    val result = List(1, 2, 3)
    result shouldEqual List(3, 2, 1, 4)
  }
  test("containers FAILURE - List contain theSameElementsInOrderAs", FailureExamples) {
    val result = List(1, 2, 3)
    result should contain theSameElementsInOrderAs List(1, 2, 4, 3)
  }

  test("contain in iterators - not supported") {
    val result = List(1, 2, 3)
    assertCompiles("result should contain (2)")
    assertDoesNotCompile("result.iterator should contain (2)")
    result.getClass // avoid not-used warning
  }

  // ------- String
  test("string matchers") {
    val str = "foobar"
    str should startWith("foo")
  }

  // -------

  test("Lone element") {
    import org.scalatest.LoneElement._

    val set = Set(42)
    set.loneElement should be(42)
  }


  // ---- Properties

  private case class Foo(value: Boolean) {
    def foo = true
    def notValue = !value
  }

  test("boolean property methods") {
    val result = Foo(true)
    result shouldBe Symbol("foo")  // UGH, uses reflection
  }

  // ----- Pattern matching

  test("Pattern matching") {
    case class Name(first: String, middle: String, last: String)
    val name = Name("Jane", "Q", "Programmer")

    // basic match
    name should matchPattern { case Name("Jane", _, _) => }

    // Using inside:
    import org.scalatest.Inside

    new Inside {
      inside(name) { case Name(first, "Q", _) =>
        first should startWith("Ja")
      }
    }

  }

  // ---- Exceptions
  test("Exceptions - intercept") {
    val thrown = intercept[IllegalArgumentException] {
      throw new IllegalArgumentException
    }
    assert(thrown.getMessage eq null)
  }

  test("Exceptions - should be thrownBy") {
    a [NoSuchElementException] should be thrownBy { List().head }
  }

  test("Exceptions - thrownBy should ...") {
    val thrown = the [IllegalStateException] thrownBy { throw new IllegalStateException("foo bar xxx") }
    thrown.getMessage should include("xxx")
  }

}