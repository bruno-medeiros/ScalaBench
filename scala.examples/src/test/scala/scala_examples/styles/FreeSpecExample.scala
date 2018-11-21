package scala_examples.styles

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

import org.scalatest.{Matchers, path}

import scala.collection.mutable.ListBuffer

class PathFreeSpecExample extends path.FreeSpec with Matchers {

  "A ListBuffer" - {

    val buf = ListBuffer.empty[Int] // This implements "A ListBuffer"

    "should be empty when created" in {
      // This test sees:
      //   val buf = ListBuffer.empty[Int]
      // So buf is: ListBuffer()
      buf should be ('empty)
    }

    "should have size 0 when created" in {
      // This test sees:
      //   val buf = ListBuffer.empty[Int]
      // So buf is: ListBuffer()
      buf should have size 0
    }

    "when 1 is appended" - {

      buf += 1 // This implements "when 1 is appended", etc...

      "should contain 1" in {
        // This test sees:
        //   val buf = ListBuffer.empty[Int]
        //   buf += 1
        // So buf is: ListBuffer(1)

        buf.remove(0) should equal (1)
        buf should be ('empty)
      }
    }
  }
}