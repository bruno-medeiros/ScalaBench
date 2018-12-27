package scala_examples.matchers

import org.scalatest.FunSuite

class AssertionsExample extends FunSuite {

  test("assert") {
    assert(1 == 1)
  }

  test("assertResult FAILURE") {
    assertResult(3, "this is a clue") { 1 + 1 }
  }

  test("assume - (will cancel)") {
    assume(10 == 1)
  }

  test("assertThrows") {
    assertThrows[IndexOutOfBoundsException] { // Result type: Assertion
      "abc".charAt(10)
    }

    val caught = intercept[IndexOutOfBoundsException] {
      "abc".charAt(10)
    }
    assert(caught.getMessage.contains("10"))
  }

  test("compilation") {
    assertDoesNotCompile("123.contains()")

    assertCompiles("10 to 24")
    assertCompiles("import scala.concurrent.duration._; 10 seconds")
  }
}