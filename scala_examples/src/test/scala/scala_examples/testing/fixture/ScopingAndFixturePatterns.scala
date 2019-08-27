package scala_examples.testing.fixture

import org.scalatest.BeforeAndAfter
import org.scalatest.FunSuite
import org.scalatest.OneInstancePerTest
import org.scalatest.Outcome
import org.scalatest.fixture
import scala_examples.common.Composable
import scala_examples.common.SingletonResource


// BeforeAfterAndAll doesn't work well with OneInstancePerTest
// because all instances are created beforehand, before afterAll is invoked
class InstancePerTestExample extends FunSuite
  with OneInstancePerTest with BeforeAndAfter
{
  val enabled = false
  if (enabled) {

    val singletonResource = new SingletonResource(new Composable())

    after(singletonResource.destroy())

    test("test 1") {}
    test("test 2") {}
    test("test 3") {}
  }
}


/**
  * Example with ComposableTest.
  * More flexible in that allows any fixture per test, but is more verbose
  */
class LoanPatternWithComposableExample extends FunSuite with ComposableTest {

  test("test 1") {
    val sr = new SingletonResource(testAfters)
    sr.toString
  }

  test("test 2") {
    val sr = new SingletonResource(testAfters)
    sr.toString
  }

  test("test 3") {
    val sr = new SingletonResource(testAfters)
    sr.toString
  }

}

/**
  * One-Arg test fixture example. Preferred solution.
  */
class WithFixtureArgExample extends fixture.FunSuiteLike {

  type FixtureParam = SingletonResource

  override protected def withFixture(test: OneArgTest): Outcome = {
    val theFixture = new SingletonResource(_ => {})

    try {
      withFixture(test.toNoArgTest(theFixture))
    } finally {
      theFixture.destroy()
    }
  }

  test("test 1") { sr =>
    sr.toString
  }

  test("test 2") { sr =>
    sr.toString
  }

  test("test 3") { sr =>
    sr.toString
  }

}