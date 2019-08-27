package examples

object LazyVariables extends App {

  lazy val lazyFoo = { println("Lazy Foo evaluated"); 123 }
  println("About to eval lazy:")
  lazyFoo
  lazyFoo
  lazyFoo
  // But it's only evaluated once

}