package examples.types

import org.scalatest.Assertions

object MagnetPattern extends App with Assertions {

  sealed trait Magnet {
    type Result

    def get(): Result
  }

  val magnetOfString = new Magnet {
    override type Result = String
    override def get(): Result = "A string!"
  }

  val magnetOfInteger = new Magnet {
    override type Result = Int
    override def get(): Result = 1234
  }

  def withMagnet(magnet: Magnet): magnet.Result = magnet.get()

  val r1: String = withMagnet(magnetOfString)
  val r2: Int = withMagnet(magnetOfInteger)

}

object MagnetPatternWithTypeParameters extends App with Assertions {

  sealed trait Magnet[RESULT] {
    def get(): RESULT
  }

  val magnetOfString = new Magnet[String] {
    override def get(): String = "A string!"
  }

  val magnetOfInteger = new Magnet[Int] {
    override def get(): Int = 1234
  }

  def withMagnet[T](magnet: Magnet[T]): T = magnet.get()

  val r1: String = withMagnet(magnetOfString)
  val r2: Int = withMagnet(magnetOfInteger)

}