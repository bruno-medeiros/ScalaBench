package examples

object TypeAlias extends App {

  type StringJoiner = Seq[String] => String
  val myJoiner: StringJoiner = strings => strings.mkString("--")

}
