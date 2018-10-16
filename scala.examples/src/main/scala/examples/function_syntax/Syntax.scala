package examples.function_syntax

object Syntax extends App {

  // Basic Syntax:
  def f1 = (a: String) => a.length
  val f1b: String => Int = a => a.length
  def f2 (a: String)  = a.length

  // Empty parens:
  def empty1(): Int = { println("Empty1"); 1 }
  def empty2: Int = { println("Empty2"); 2 }
  empty1()
  empty1  // Warning: should be called with parenthesis
  empty2  // Error if parenthesis add, it's a no-parens method

  // Currying:
  def curryAdd(x: Int)(y: Int): Int = x + y
  curryAdd(1) _ // Must use underscore, or:
  val x: Int => Int = curryAdd(1) // explicit type parameter

}