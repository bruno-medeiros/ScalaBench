package examples.types


trait MyIter {
  // Abstract type member:
  type AbstractType

  def get(): AbstractType

  def display(innerType: AbstractType): String = {
    innerType.toString
  }
}

class MyIterInt extends MyIter {
  // can be concretely define by explicit new type declaration...
  type AbstractType = Int

  override def get(): Int = 123
}

class MyIterFloat extends MyIter {
  // Or can be defined by name shadowing! WHOA!
  case class AbstractType(num: Float)

  override def get(): AbstractType = AbstractType(3.12f)

  display(AbstractType(123.3f))
}

object AbstractTypeMembers {
  val iterInt = new MyIterInt
  val xxxInt: Int = iterInt.get()

  val iterString: MyIter = new MyIter {
    override type AbstractType = String
    override def get(): String = "blah"
  }
  // get using abstract-type. Can only use for other abstract-type refs
  val xxxString: iterString.AbstractType = iterString.get()
  iterString.display(xxxString)

}

trait ParameterizedAbstractType {
  type AbstractType[_]

  def get(): AbstractType[Int]
  def get2(): AbstractType[String]
}

abstract class ParameterizedAbstractType2 extends ParameterizedAbstractType {
  override type AbstractType[E] = List[E]
}