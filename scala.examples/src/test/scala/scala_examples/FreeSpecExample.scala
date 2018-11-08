package scala_examples

import org.scalatest.FreeSpec

class FreeSpecExample extends FreeSpec {

  "A Set" - {
    "when empty" - {

      "should have size 0" in {
        assert(Set.empty.size == 0)
      }

      "should produce NoSuchElementException when head is invoked" in {
        assertThrows[NoSuchElementException] {
          Set.empty.head
        }
      }
    }

    "when size 1" - {

      "should have size 1" in {
        assert(Set.empty.size == 1)
      }

//      assert(1 == 2)
    }
  }
}