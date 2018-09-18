package example

import org.scalatest.FunSuite

class PatterMatchingSuite extends FunSuite {

  test("An empty Set should have size 0") {
    assert(Set.empty.size == 0)
  }

  test("Blah") {
    val pairs: List[(Char, Int)] = ('a', 2) :: ('b', 3) :: Nil
    val chars: List[Char] = pairs.map(p => p match {
      case (ch, num) => ch
    })

    val chars2: List[Char] = pairs map {
      case (ch, num) => (ch + 2).asInstanceOf[Char]
    }

  }
}