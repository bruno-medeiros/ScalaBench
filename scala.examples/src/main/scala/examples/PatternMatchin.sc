def matchFoo[T](someList: List[T]) = {

  (someList: List[T]) match {
    case Nil => ???          // empty list

    case x :: Nil => s"x :: Nil, list with only ONE element $x"
    //case List(x) => s"same as above: $x"

    // xs to the tail. xs could be Nil or some other list.
    case 1 :: 2 :: xs => s"1 :: 2 :: xs case. xs: $xs"

    case list if list.size == 5 => "size == 5, using pattern guard"

    case head :: xs =>  s"head :: xs case: $head + $xs"
    //case (x, y) :: ps => "a list where the head element is a pair"
    case _ => "default case if none of the above matches"
  }
}

matchFoo(List(10))
matchFoo(List(1, 2, 3))
matchFoo(List(10, 20, 30))
matchFoo(List(10, 20, 3, 5, 6))

// match with backtick:
def matchWithBackTick(foo: Int, bar: String): Unit = {
  (123, "abc")  match {
    case (f2 @ `foo`, b2 @ `bar`) =>
      println(s"Match any, assigning to $f2 , $b2")
    case (foo, bar) =>
      println(s"Match any, assigning to $foo , $bar")
  }
}

// Matchin in var declarations:
val stationNames = List("Paris", "Geneva", "London")
val list @ List(paris, geneva, london, other) = stationNames // error
