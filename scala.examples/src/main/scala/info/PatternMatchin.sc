import info.PatternMatching

val matcher = new PatternMatching()
matcher.matchFoo(List(10))
matcher.matchFoo(List(1, 2, 3))
matcher.matchFoo(List(10, 20, 3))

matcher.example3()


val pairs: List[(Char, Int)] = ('a', 2) :: ('b', 3) :: Nil
val chars: List[Char] = pairs.map(p => p match {
  case (ch, num) => ch
})

val chars2: List[Char] = pairs map {
  case (ch, num) => (ch + 2).asInstanceOf[Char]
}

