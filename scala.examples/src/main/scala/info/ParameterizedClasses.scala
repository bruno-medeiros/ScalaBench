package info

class MyParamClass[T](arg: T) {

}


object MyParamClass {

  new MyParamClass[Int](1)
  new MyParamClass(1)   // the type is being inferred, i.e. determined based on the value arguments


  // T must derive from TopLevel or be TopLevel
  def myFct1[T <: TopLevel](arg: T): T = { ??? }
  // T must be a supertype of Level1
  def myFct2[T >: Level1](arg: T): T = { ??? }
  def myFct3[T >: Level1 <: TopLevel](arg: T): T = { ??? }

}