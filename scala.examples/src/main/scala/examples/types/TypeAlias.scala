package examples.types

object TypeAlias {

  type StringJoiner = Seq[String] => String

  val myJoiner: StringJoiner = strings => strings.mkString("--")

}
