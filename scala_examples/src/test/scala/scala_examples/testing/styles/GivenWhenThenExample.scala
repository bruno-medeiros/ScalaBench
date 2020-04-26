package scala_examples.testing.styles

import scala.collection.mutable

import org.scalatest.GivenWhenThen
import org.scalatest.flatspec.AnyFlatSpec

/**
  * Takeway: this doesn't mix well with println,
  * because GivenWhenThen output is delayed? Even with
  */
class GivenWhenThenExample extends AnyFlatSpec with GivenWhenThen {

  "A mutable Set" should "allow an element to be added" in {
    Given("an empty mutable Set")
    val set = mutable.Set.empty[String]

    When("an element is added")
    set += "clarity"
//    set += "clarity2"

    println("PRINTLN: set.size === 1")
    Then("the Set should have size 1")
    assert(set.size === 1)

    And("the Set should contain the added element")
    assert(set.contains("clarity"))

    info("That's all folks!")
  }
}
