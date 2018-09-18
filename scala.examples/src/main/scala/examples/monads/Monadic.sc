
val x = List(10, 20, 30)

//List.un

val f: Int => List[Int] = a => { println(s"f($a"); List(a, a + 1) }
val g: Int => List[Int] = a => { println(s"g($a"); List(a * 2) }

// Associativity law for monad:
x.flatMap(f).flatMap(g)
x.flatMap(a => f(a).flatMap(g))