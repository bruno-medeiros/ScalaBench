//package examples.functions
//
////import scala.annotation.nowarn
//
//object Lambdas_NonLocalReturn extends App {
//
//  def foo(): String => Int = { _: String =>
//    {
//      println("inside fooResult")
//      return str2 => str2.length
//    }
//  //: @nowarn("cat=lint-nonlocal-return")
//  }
//
//  val fooResult = foo()
//  fooResult("abc")
//
//}
