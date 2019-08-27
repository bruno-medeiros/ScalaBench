package examples.collections

import org.scalatest.Matchers

import scala.collection.mutable

//noinspection SimplifyBoolean
object Sets extends App with Matchers {

  // Immutable sets: (uses hash trie)

  val setAB: Set[Char] = Set('a','b')
  assert(setAB('a') === true)
  assert(setAB('z') === false)

  // Append:
  assert(Set(1, 2) + 3 === Set(1, 2, 3))
//  assert(Set(1, 2) + (3, 4) === Set(1, 2, 3, 4))
  assert((Set(1, 2) & Set(3, 4)) === Set(1, 2, 3, 4))
  Set(1, 2) & Set(3, 4) shouldBe Set(1, 2, 3, 4)

  // Append another collection:
  assert(Set(1, 2) ++ List(3, 4, 3) === Set(1, 2, 3, 4))

  // Remove
  assert(Set(1, 2) - 2 === Set(1))
//  assert(Set(1, 2, 3) - (1, 3) === Set(2))
  assert((Set(1, 2, 3) &~ Set(1, 3)) === Set(2))

  // Remove another collection:
  assert(Set(1, 2, 3) -- Set(2, 3, 4) === Set(1))

  // + plus comes from any2stringadd
  assert(setAB + "c" === "Set(a, b)c")


  // mutable var
  {
    var set = Set[Int]()
    set = set + 4
    set += 2
    assert(set === Set(2, 4))
  }

  // Mutable set
  {
    val mset = mutable.Set(1, 2)
//    mset + 3 // Note: copying
    mset += 4 // Note: mutation even though mset is a val
    assert(mset === Set(1, 2, 4))

    assert(mset.add(1) === false)
    assert(mset.add(10) === true)
  }

}