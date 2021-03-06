package scala_examples.testing.styles

import org.scalatest._
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should
import org.scalatest.tagobjects.Slow

class FlatSpecExample extends AnyFlatSpec with should.Matchers {

  object DbTest extends Tag("com.mycompany.tags.DbTest")

  "A Stack" should "pop values in last-in-first-out order" in {
    val stack = List[Int]() :+ 1 :+ 2

    stack.head should be(1)
    stack.tail.head should be(2)
  }

  it should "throw NoSuchElementException if an empty stack is popped" in {
    val emptyStack = List()
    a[NoSuchElementException] should be thrownBy {
      emptyStack.head
    }
  }

  "The Scala language" must "add correctly" taggedAs Slow in {
    val sum = 1 + 1
    assert(sum === 2)
  }

  it must "subtract correctly" taggedAs (Slow, DbTest) in {
    val diff = 4 - 1
    assert(diff === 3)
  }

  behavior of "foo the SUT"

  it should "do the needful" in {
    println("Needful")
  }
}
