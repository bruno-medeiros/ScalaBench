package examples.types

trait MyIter {
  // Abstract type member:
  type InnerType

  def display(innerType: InnerType): String = {
    innerType.toString
  }
}

class MyIterA extends MyIter {
  // can be concretely define by explicit new type declaration...
  type InnerType = Integer

  display(123)
}

class MyIterB extends MyIter {
  // Or can be defined by name shadowing! WHOA!
  case class InnerType(num: Float)

  display(InnerType(123.3f))
}