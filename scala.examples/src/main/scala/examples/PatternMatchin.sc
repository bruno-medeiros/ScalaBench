
def matchFoo[T](someList: List[T]) = {

  (someList: List[T]) match {
    case Nil => ???          // empty list

    case x :: Nil => s"x :: Nil, list with only ONE element $x"
    //case List(x) => s"same as above: $x"
    // xs to the tail. xs could be Nil or some other list.
    case 1 :: 2 :: xs => s"1 :: 2 :: xs case. xs: $xs"
    case head :: xs =>  s"head :: xs case: $head + $xs"


    //case (x, y) :: ps => "a list where the head element is a pair"
    case _ => "default case if none of the above matches"
  }
}

matchFoo(List(10))
matchFoo(List(1, 2, 3))
matchFoo(List(10, 20, 3))


val pairs: List[(Char, Int)] = ('a', 2) :: ('b', 3) :: Nil
val chars: List[Char] = pairs.map(p => p match {
  case (ch, num) => ch
})

val chars2: List[Char] = pairs map {
  case (ch, num) => (ch + 2).asInstanceOf[Char]
}

