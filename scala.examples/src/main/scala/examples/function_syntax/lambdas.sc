
var doubler = (a: Int) => a * 2
//var doubler = a: Int => a * 2
var doubler2 : (Int => Int) = a => a * 2
var doubler3 : (Int => Int) = _ * 2


// By name argument:
def foo(byName: => String): Unit = {
  println("IN foo");
}
def foo2(arg: String): Unit = {
  println("IN foo2");
}


foo({ println("foo:ARG"); "ARG" })
foo2({ println("foo2:ARG"); "ARG2" })
