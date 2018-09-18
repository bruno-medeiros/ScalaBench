
var doubler = (a: Int) => a * 2
//var doubler = a: Int => a * 2
var doubler2 : (Int => Int) = a => a * 2
var doubler3 : (Int => Int) = _ * 2


def foo(cond: => Boolean): Unit = {
}
def foo2(cond: Boolean): Unit = {
}


foo({ println("foo:ARG"); true })
foo2({ println("foo2:ARG"); true })
