package examples.function_syntax

import scala.annotation.tailrec

object Functions extends App {

  // Basic Syntax:
  def f1 = (a: String) => a.length
  val f1b: String => Int = a => a.length
  def f2 (a: String)  = a.length


  // Currying:
  def curryAdd(x: Int)(y: Int): Int = x + y
  curryAdd(1) _ // Must use underscore, or:
  val x: Int => Int = curryAdd(1) // explicit type parameter


  // Tail Recursion
  @tailrec
  def foo(n: Int): BigInt = {
    if (n == 0) 1 else foo(n)
  }

  //@tailrec
  def fooNotTailRecursive(n: Int): BigInt = {
    if (n == 0) 1 else n * (n - 1)
  }


  // Partial functions
  var partialFn: Int => String = { case 1 => "one" }
  // if used as predicate they become full functions:
  ('a' to 'g').zipWithIndex.filter { case (_, ix) =>  ix % 2 == 0 }
  // TODO: how, which type?

  var partialFn2: Int => Boolean = { case 1 => true }
  partialFn2(123) // This still blows with MatchError

}