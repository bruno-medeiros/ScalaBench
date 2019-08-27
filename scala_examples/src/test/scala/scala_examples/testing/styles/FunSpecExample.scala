package scala_examples.testing.styles

import org.scalatest.{FunSpec, path}
import scala_examples.testing.FailureExamples

class FunSpecExample extends FunSpec {

  describe("A Set") {
    describe("when empty") {
      it("should have size 0") {
        assert(Set.empty.isEmpty)
      }

      it("cannot register after ready phase", FailureExamples) {
        it("register fails") { } // Couldn't use describe either
      }


      it("should produce NoSuchElementException when head is invoked") {
        assertThrows[NoSuchElementException] {
          Set.empty.head
        }
      }
    }
  }
}

class PathFunSpecExample extends path.FunSpec {

  describe("A Set") {
    describe("when empty") {
      it("should have size 0") {
        assert(Set.empty.isEmpty)
      }

      it("should produce NoSuchElementException when head is invoked") {
        assertThrows[NoSuchElementException] {
          Set.empty.head
        }
      }
    }
  }
}

import org.scalatest.{Matchers, path}

import scala.collection.mutable.ListBuffer

class ExampleSpec extends path.FunSpec with Matchers {

  describe("A ListBuffer") {

    val buf = ListBuffer.empty[Int] // This implements "A ListBuffer"

    it("should be empty when created") {
      // This test sees:
      //   val buf = ListBuffer.empty[Int]
      // So buf is: ListBuffer()
      buf should be (Symbol("empty"))
    }

    it("should have size 0 when created") {
      // This test sees:
      //   val buf = ListBuffer.empty[Int]
      // So buf is: ListBuffer()
      buf should have size 0
    }

    describe("when 1 is appended") {

      buf += 1 // This implements "when 1 is appended", etc...

      it("should contain 1") {

        // This test sees:
        //   val buf = ListBuffer.empty[Int]
        //   buf += 1
        // So buf is: ListBuffer(1)

        buf.remove(0) should equal (1)
        buf should be (Symbol("empty"))
      }

      describe("when 2 is appended") {

        buf += 2

        it("should contain 1 and 2") {

          // This test sees:
          //   val buf = ListBuffer.empty[Int]
          //   buf += 1
          //   buf += 2
          // So buf is: ListBuffer(1, 2)

          buf.remove(0) should equal (1)
          buf.remove(0) should equal (2)
          buf should be (Symbol("empty"))
        }

      }

    }

  }
}