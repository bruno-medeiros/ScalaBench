package examples.types

import scala.io.StdIn

object AnyVals extends App {

  final class IdString(val name: String) extends AnyVal

  StdIn.readLine()
  println("Creating IdSTring")
  val id1 = new IdString("abcdef")

  println("----Created")
  StdIn.readLine()
  println(id1.name.substring(1))
  println("----2")

  StdIn.readLine()
  println(s"${id1.name.hashCode} vs ${id1.hashCode()}")
  println("----3")

  StdIn.readLine()

  def generic[T](obj: T): Int = { obj.hashCode() }

  println(s"${id1.name.hashCode} vs " + generic(id1))

  println("---- generic")
  StdIn.readLine()

}

object AnyVals2 {
  type @@[A, B] = A with B

  implicit class TaggingExtensions[A](val a: A) extends AnyVal {
    @inline def taggedWith[B]: A @@ B = a.asInstanceOf[A @@ B]

    /** Synonym operator for `taggedWith`. */
    @inline def @@[B]: A @@ B = taggedWith[B]
  }

  trait TransactionID

  val id1: Long @@ TransactionID = 1L.@@[TransactionID]
//  val id2: Long with TransactionID = 123L
}
