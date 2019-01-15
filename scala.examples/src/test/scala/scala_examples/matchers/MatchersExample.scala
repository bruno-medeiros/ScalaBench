package scala_examples.matchers

import org.scalatest.{FunSuite, Matchers}

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
  test("Options FAILURE - ===") {
    //noinspection OptionEqualsSome
    assert(Some(List(1, 2, 3)) === Some(List(1, 2)))
  }
  test("Options FAILURE - .contains") {
    assert(Some(List(1, 2, 3)).contains(List(1, 2)))
  }
  test("Options FAILURE - should contain") {
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

  test("containers FAILURE - Set ==") {
    val result = Set(1, 2, 3)
    assert(result == Set(3, 2, 1, 4))
  }
  test("containers FAILURE - Set shouldEqual") {
    val result = Set(1, 2, 3)
    result shouldEqual Set(3, 2, 1, 4)
  }
  test("containers FAILURE - Set contain theSameElementsAs") {
    val result = Set(1, 2, 3)
    result should contain theSameElementsAs Set(1, 2, 4, 3)
  }

  test("containers FAILURE - List ==") {
    val result = List(1, 2, 3)
    assert(result == List(3, 2, 1, 4))
  }
  test("containers FAILURE - List shouldEqual") {
    val result = List(1, 2, 3)
    result shouldEqual List(3, 2, 1, 4)
  }
  test("containers FAILURE - List contain theSameElementsInOrderAs") {
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
    result shouldBe 'foo  // UGH, uses reflection
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

  // ----
  test("Exceptions") {
    val s = "abc"
    an [IndexOutOfBoundsException] should be thrownBy s.charAt(-1)
  }

  test("test throwing") {
    a [NoSuchElementException] should be thrownBy { List().head }
  }
}