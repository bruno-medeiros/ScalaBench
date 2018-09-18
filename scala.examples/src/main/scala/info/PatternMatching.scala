package info

class PatternMatching {

  var someList = List(1, 2, 3)

  def matchFoo[T](someList: List[T]) = {

    (someList: List[T]) match {
      case Nil => ???          // empty list
      case x :: Nil => s"list with only one element $x"
      case List(x) => s"same as above: $x"
      // xs to the tail. xs could be Nil or some other list.
      case 1 :: 2 :: xs => s"lists that starts with 1 and then 2. xs: $xs"

      case head :: xs =>  s"a list with at least one element: $head + $xs"

      case (x, y) :: ps => "a list where the head element is a pair"
      case _ => "default case if none of the above matches"
    }
  }

  def example3(): String ={
    return matchFoo(List(1, 2))
  }
}
