package scala_examples

import org.scalatest.{FlatSpec, _}
import org.scalatest.tagobjects.Slow

import scala.collection.mutable.Stack

object DbTest extends Tag("com.mycompany.tags.DbTest")


//noinspection ScalaUnnecessaryParentheses,ReferenceMustBePrefixed,ScalaDeprecation
class FlatSpecExample extends FlatSpec with Matchers {

  "A Stack" should "pop values in last-in-first-out order" in {
    val stack = new Stack[Int]
    stack.push(1)
    stack.push(2)
    println("DUH")

    stack.pop() should be (2)
    stack.pop() should be (1)
  }

  it should "throw NoSuchElementException if an empty stack is popped" in {
    val emptyStack = new Stack[Int]
    a [NoSuchElementException] should be thrownBy {
      emptyStack.pop()
    } 
  }


  "The Scala language" must "add correctly" taggedAs(Slow) in {
    val sum = 1 + 1
    assert(sum === 2)
  }

  it must "subtract correctly" taggedAs(Slow, DbTest) in {
    val diff = 4 - 1
    assert(diff === 3)
  }
}