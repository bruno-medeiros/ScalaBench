package examples.oo.types

import examples.MiscUtil
import org.scalatest.FunSuite

object DependentTypes {
  class Box(opt: Option[Int]) {
    class HasSome

    def hasSome: Option[HasSome] = {
      if (opt.isEmpty) None else Some(new HasSome)
    }

    def strictGet(hasSome: HasSome): Int = {
      opt.get
    }
  }
}


class DependentTypes extends FunSuite {
  import DependentTypes.Box

  // Just an option
  val opt: Option[Int] = Some(123)

  test("dependent types using Box") {

    val box = new Box(Some(123))
    val hasSome = new box.HasSome

    println("typeTag of box: " + MiscUtil.getTypeTag(box))
    println("typeTag of hasSome: " + MiscUtil.getTypeTag(hasSome))

    // This is not a very meaningful example of dependent types
    val contents: Int = box.strictGet(hasSome)
    assert(contents === 123)

    // Note: following doesnt work, because hasSome is type dependent on box instance
    {
      assertDoesNotCompile(
"""
        val box2 = new Box(Some(123))
        box2.strictGet(hasSome)
"""
      )
    }
  }
}
