package examples.implicits

import scala.language.implicitConversions

//noinspection ScalaUnusedSymbol
object ImplicitsRecursive extends App {

  class Pong {}
  class Ping(val pong: Pong) {}

  implicit def pong(num: Int)(implicit pingx: Int => Ping): Pong = new Pong
//  implicit def pong2(num: Int): Pong = {
//    println("Pong2")
//    new Pong
//  }
  implicit def ping(num: Int)(implicit pongx: Int => Pong): Ping = new Ping(pongx(num))

  // Uncommenting this does not work, because of recursive invocation ?
//  var xxx: Pong = 12

}