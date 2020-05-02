package examples.collections

import scala.collection.immutable
import scala.collection.immutable.NumericRange
import scala.collection.mutable

import org.scalatest.matchers.should.Matchers

//noinspection SimplifiableFoldOrReduce,SimplifyBoolean,CorrespondsUnsorted
object Sequences extends App with Matchers {

  // Ranges:
  val atoz: NumericRange[Char] = 'a' to 'z'
  val oneToTen: Range = 1 until 10

  // ----------------------------------
  // Queries

  private val ints = Vector(1, 2, 3)
  assert(ints.contains(2) == true)
  assert(ints.contains(2, 3) == false) // Surprising, but it's because (2, 3) gets transformed to tuple.

  // ----------------------------------
  // "modifications" - element operations

  // List construction:
  assert(1 :: List(2, 3) == List(1, 2, 3))

  // prepended element:
  assert(1 +: List(2, 3, 4) == List(1, 2, 3, 4))
  assert(List(2, 3, 4).prepended(1) == List(1, 2, 3, 4))

  // Appended element:
  assert(List(1, 2, 3) :+ 4 == List(1, 2, 3, 4))
  assert(Vector(1, 2, 3) :+ 4 == Vector(1, 2, 3, 4))
  assert(List(1, 2, 3).appended(4) == List(1, 2, 3, 4))

  // Appending strings
  {
    val appendedStringA: String = "abc" :+ '1'
    assert(appendedStringA === "abc1")
    val appendedStringB: String = "abc" + '2'
    assert(appendedStringB === "abc2")
    val appendedStringC: String = "abc" ++ "3x"
    assert(appendedStringC === "abc3x")

    // WEIRD one, it would be be better if this didn't even compile:
    val appendedWeird: IndexedSeq[Any] = "abc" :+ "3x"
    assert(appendedWeird === Seq('a', 'b', 'c', "3x"))
  }

  // :+ does not preserve collection type, can widen:
  assert(List(1, 2) :+ "blah" == List(1, 2, "blah"))
  // Append for Vector:
  assert(Vector(1, 2, 3) :+ 4 == Vector(1, 2, 3, 4))

  // Append another sequence (note, can widen element TYPE)
  assert((List(1, 2) ++ List(3, 4)) == List(1, 2, 3, 4))
  assert(List(1, 2).concat(List(3, 4)) == List(1, 2, 3, 4))
  assert("abc" ++ "def" == "abcdef")
  assert({
    var list = List(1, 2, 3)
    list ++= List(4, 5)
    list
  } == List(1, 2, 3, 4, 5))

  // mutable append
  {
    val buf = mutable.Buffer[Int]()
    buf.append(1)
    buf += 2
    10 +=: buf
    assert(buf == List(10, 1, 2))
  }

  // ----------------------------------
  // HOFs - Transformations (create new collections):

  // .map
  assert(List(1, 2, 3).map { _ + 10 } == List(11, 12, 13))

  // .transform for sequences (must map to same element type)
  assert(Array(1, 2, 3).mapInPlace(_ + 10).sameElements(Seq(11, 12, 13)))

  // Take a sequence, create a map from it using elements as keys
  assert(
    List(1, 2, 1, 4).map(a => (a, a + 100)).toMap
      == Map(1 -> 101, 2 -> 102, 4 -> 104)
  )

  // Collection builder type is maintained even if concrete type is upcast
  // This is called Uniform Return Type Principle
  val upcastList: immutable.Iterable[Int] = List(1, 2, 3)
  assert(upcastList.map { _ + 10 }.isInstanceOf[List[_]])

  // ----------------------------------
  // HOFs - Accessors (create single value):

  // Folding and reduce:
  {
    val list = List(1, 2, 10, 20)
    assert(list.reduce((a, b) => a + b) == 33)

    assert(list.foldLeft(0)((a, b) => a + b) == 33)
    assert(list.foldLeft(0)(_ + _) == 33)
    assert(list.foldLeft("")(_ + _) == "121020")
  }

  // .aggregate (only applicable to parallel collections, not sequential ones)
//  assert(List("foo", "bar", "xpto").aggregate(0)((acc, s) => acc + s.length, _ + _) == 10)

  // ----------------------------------
  // List sliding
  List(1, 2, 30, 40, 5)
    .sliding(2, 2)
    .foreach(e => {
      print(s"$e || ")
    })
  println("---")
  List(1, 2, 30, 40, 5)
    .sliding(2)
    .foreach(e => {
      print(s"$e || ")
    })
  println

  // -- Other
  val res: Seq[String] = Vector("one", "two", "three", "four")
    .groupBy(_.charAt(0)) // The collection type from new value is taken from original collection
    .apply('t')
  println(res.getClass)

}
