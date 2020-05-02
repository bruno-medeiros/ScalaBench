package scala_examples.testing.fixture

import org.scalatest.funsuite.AnyFunSuite
import org.scalatest.{ BeforeAndAfter, OneInstancePerTest, Outcome, funsuite }
import scala_examples.common.{ Composable, SingletonResource }

// BeforeAfterAndAll doesn't work well with OneInstancePerTest
// because all instances are created beforehand, before afterAll is invoked
class InstancePerTestExample extends AnyFunSuite with OneInstancePerTest with BeforeAndAfter {
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
class LoanPatternWithComposableExample extends AnyFunSuite with ComposableTest {

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
class WithFixtureArgExample extends funsuite.FixtureAnyFunSuiteLike {

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
