package examples

object TypeAlias {

  type StringJoiner = Seq[String] => String

  val myJoiner: StringJoiner = strings => strings.mkString("--")

}
