package examples.oo

object AccessProtection {

  case class Foo() {
    private val m1 = 123
    private[this] val m2 = 10

    def added(other: Foo): Int = {
      // Note: cannot use other.m2 here due to private[this]:
      m2 + other.m1
    }
  }

}
