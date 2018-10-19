package examples.implicits

object ImplicitLambda extends App {

  def foo() (implicit name: String): Unit = {
    println(name)
  }

  implicit val name: String = "Bruno"
  foo()

  // Dunno how to make this work:
//  val fooLambda : String => Unit = foo _

  // This doesn't work yet, as of Scala 2.12. Is it a Scala 3 feature? :

//  def dslBlock(inner: implicit String => Unit) = {
//    inner("123")
//  }
//  dslBlock {
//    println(implicitly[String])
//  }
}
