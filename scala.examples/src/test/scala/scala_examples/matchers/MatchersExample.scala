package scala_examples.matchers

import org.scalatest.{FunSuite, Matchers}

class MatchersExample extends FunSuite with Matchers {

  test("test the should") {
    val num = 1
    num shouldEqual 1
    num should equal(1)

    num shouldNot equal("abc")
  }

  test("shouldEqual - fail") {
    val num = 1
    num shouldEqual 2
  }

  test("should equal() - fail") {
    val num = 1
    num should equal("abc")
  }

  test("test throwing") {
//    a { List().head } should equal(1)

    a [NoSuchElementException] should be thrownBy { List().head }
  }
}