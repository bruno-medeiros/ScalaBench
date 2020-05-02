package scala_examples.testing.matchers

import org.scalatest.OptionValues
import org.scalatest.funsuite.AnyFunSuite
import scala_examples.testing.FailureExamples

class OptionValuesExample extends AnyFunSuite with OptionValues {

  val option: Option[String] = None

  test("inside assert", FailureExamples) {
    assert(option.get === "123")
  }

  test("outside assert", FailureExamples) {
    val value = option.get
    assert(value === "123")
  }

  test("with OptionValues", FailureExamples) {
    val value = option.value
    assert(value === "123")
  }

  {
    import org.scalatest.Inspectors._

    val options = List(Some("abc"), None)

    test("INSPECTORS - inside assert", FailureExamples) {
      forAll(options) { elem =>
        assert(elem.get === "abc")
      }
    }

    // Exception output is more clear here:
    test("INSPECTORS - with OptionValues", FailureExamples) {
      forAll(options) { elem =>
        val value = elem.value
        assert(value === "abc")
      }
    }
  }

}
