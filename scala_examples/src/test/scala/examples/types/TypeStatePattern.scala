package examples.types

import org.scalatest.Assertions._

object TypeStatePattern extends App {

  // This would be called Phantom Types
  // Note: dotty will add syntax for this
  sealed trait State
  trait Playing extends State
  trait Stopped extends State


  class Player[S] {

    def play[_ >: S <: Stopped](): Player[Playing] = {
      println("Playing..")
      this.asInstanceOf[Player[Playing]]
    }

    def stop[_ >: S <: Playing](): Player[Stopped] = {
      println("Stopped..")
      this.asInstanceOf[Player[Stopped]]
    }

    // Another way of doing the same
    def stop2()(implicit ev: S =:= Playing): Player[Stopped] = {
      println("Stopped..")
      this.asInstanceOf[Player[Stopped]]
    }
  }

  // This works:
  new Player[Stopped].play().stop().play().stop()
  new Player[Stopped].play().stop2().play().stop2()

  // These do not compile:
  assertDoesNotCompile("new Player[Playing].play()")
  assertDoesNotCompile("new Player[Stopped].stop()")
  assertDoesNotCompile("new Player[Stopped].stop2()")

//  new Player[Playing].play().play()
//  new Player[Stopped].stop()
//  new Player[Stopped].stop2()
//  new Player[Stopped].play().stop().stop()
}