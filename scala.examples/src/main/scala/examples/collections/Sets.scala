package examples.collections

import scala.collection.mutable

//noinspection SimplifyBoolean
object Sets extends App {

  // Immutable sets: (uses hash trie)

  val setAB: Set[Char] = Set('a','b')
  assert(setAB('a') == true)
  assert(setAB('z') == false)

  // append:
  assert(Set(1, 2) + 3 == Set(1, 2, 3))
  assert(Set(1, 2) + (3, 4) == Set(1, 2, 3, 4))

  // append another collection:
  assert(Set(1, 2) ++ List(3, 4, 3) == Set(1, 2, 3, 4))

  val setA_StringAppended: String = setAB + "c" // + plus comes from any2stringadd
  assert(setAB + "c" == "Set(a, b)c")

  {
    val mset = mutable.Set(1, 2)
    mset + 3 // Note: copying
    mset += 4 // Note: mutation even though mset is a val
    assert(mset == Set(1, 2, 4))

    assert(mset.add(1) == false)
    assert(mset.add(10) == true)
  }

}