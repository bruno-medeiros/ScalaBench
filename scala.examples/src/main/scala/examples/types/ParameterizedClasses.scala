package examples.types

import examples.oo.{Sub1, TopClass}

//noinspection NotImplementedCode
object ParameterizedClasses {

  class MyBox[+T](obj: T) {

    def get(): T = obj

  }

  // TODO explore covariance more

  class MyParamClass[T](arg: T) {

  }
  new MyParamClass[Int](1)
  new MyParamClass(1)   // the type is being inferred, i.e. determined based on the value arguments


  // T must derive from TopLevel or be TopLevel
  def myFct1[T <: TopClass](arg: T): T = { ??? }
  // T must be a supertype of Level1
  def myFct2[T >: Sub1](arg: T): T = { ??? }
  def myFct3[T >: Sub1 <: TopClass](arg: T): T = { ??? }

}