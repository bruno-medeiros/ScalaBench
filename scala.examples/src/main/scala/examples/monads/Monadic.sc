
val x = List(10, 20, 30)

def f(a: Int) = { println(s"f($a)"); List(a, a + 1) }
def g(a: Int) = { println(s"g($a)"); List(a * 2) }

// Associativity law for monad:
x.flatMap(f).flatMap(g)
x.flatMap(a => f(a).flatMap(g))