package examples.implicits

import scala.language.implicitConversions

//noinspection ScalaUnusedSymbol
object ImplicitsConversion extends App {

  class Pong {}
  class Ping(val pong: Pong) {}

  implicit def pong(num: Int): Pong = new Pong
  // Implicit conversion:
  val pong1: Pong = 12
  val x = implicitly[Int => Pong]
  val pong2: Pong = x(12)
  // This one doesn't work though:
//  val pong3: Pong = implicitly[Int => Pong](12)

  {
    implicit def ping(num: Int)(implicit pong: Int => Pong): Ping = new Ping(pong(num))
    // Implicit conversion from int to Ping, using pong conversion
    val xxx2: Ping = 12
  }
  // Alternative to above
  {
    implicit def ping(num: Int): Ping = new Ping(implicitly[Int => Pong].apply(num))

    val xxx3: Ping = 12
  }
}