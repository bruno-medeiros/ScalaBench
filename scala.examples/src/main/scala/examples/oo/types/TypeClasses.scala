package examples.oo.types


object TypeClasses extends App {

  // TODO: type classes

  // Example of typed helper for compile time verification:
  implicit class TypeOps[T](obj: T) {
    def typed[U >: T]: U = { obj }
  }

  val x = (1 to 10)
    .map(e => (e, e.toString))
    .map(t => (t._1.toFloat, t._2))
    .typed[Seq[(Float, String)]]

}