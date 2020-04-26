package scala_examples.testing.styles

import org.scalatest.freespec.AnyFreeSpec
import org.scalatest.matchers.should
import scala_examples.testing.FailureExamples

class FreeSpecExample extends AnyFreeSpec {

  "A Set" - {
    "when empty" - {

      "should have size 0" in {
        println("PRINTLN should have size 0")
//        Then("should have size 0")
        assert(Set.empty.size == 0)
      }

      "should produce NoSuchElementException when head is invoked" in {
        assertThrows[NoSuchElementException] {
          Set.empty.head
        }
      }
    }

    "when size 1" - {

      "should have size 1" taggedAs FailureExamples in {
        println("PRINTLN should have size 1 (FAILURE)")
//        Then("should have size 1 (FAILURE)")
        assert(Set.empty.size == 1)
      }

    }
  }
}

import org.scalatest.path

class PathFreeSpecExample extends path.FreeSpec with should.Matchers {

  import scala.collection.mutable.ListBuffer

  "A ListBuffer" - {

    val buf = ListBuffer.empty[Int] // This implements "A ListBuffer"

    "should have size 0 when created" in {
      // This test sees:
      //   val buf = ListBuffer.empty[Int]
      // So buf is: ListBuffer()
      buf.size should equal(0)
    }

    "when 1 is appended" - {

      buf += 1 // This implements "when 1 is appended", etc...

      "should contain 1" in {
        // This test sees:
        //   val buf = ListBuffer.empty[Int]
        //   buf += 1
        // So buf is: ListBuffer(1)

        buf.remove(0) should equal(1)
        buf.isEmpty shouldBe true
      }

      "should not contain 2" in {
        buf.size shouldNot equal(0)
      }

      buf.clear()
      println("Buf cleared")
    }
  }
}
