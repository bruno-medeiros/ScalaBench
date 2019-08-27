package examples.types

object SingletonTypes extends App {

  case class Residue[M <: Int](n: Int) extends AnyVal {
    def +(rhs: Residue[M])(implicit m: ValueOf[M]): Residue[M] =
      Residue((this.n + rhs.n) % valueOf[M])
    }

  val a1 = implicitly[ValueOf[10]]
  val a2 = implicitly[ValueOf[11]]


  val fiveModTen = Residue[10](5)
  val nineModTen = Residue[10](9)

  fiveModTen + nineModTen    // OK == Residue[10](4)

//  fiveModTen + Residue[11](4)  // ERROR


  (1: Any) match {
    case _: 1L => assert(true)
    case _: 1F => assert(true)
    case _ => assert(false)
  }

  assert(1.isInstanceOf[1])
  assert(1L.isInstanceOf[1] == false)
  assert(1L.isInstanceOf[1L] == true)
  assert((1: Short).isInstanceOf[1] == false)
}