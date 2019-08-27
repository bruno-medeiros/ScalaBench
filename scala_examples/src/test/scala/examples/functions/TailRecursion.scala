package examples.functions

import scala.annotation.tailrec

object TailRecursion extends App {

  // Tail Recursion
  @tailrec
  def foo(n: Int): BigInt = {
    if (n == 0) 1 else foo(n)
  }

  //@tailrec
  def fooNotTailRecursive(n: Int): BigInt = {
    if (n == 0) 1 else n * (n - 1)
  }
}