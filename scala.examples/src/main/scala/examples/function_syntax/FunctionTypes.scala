package examples.function_syntax

import org.scalatest.FunSuite
import scala.reflect.runtime.universe
import universe.TypeTag
import universe.typeTag

class FunctionTypes extends FunSuite {

  case class Dummy()

  def foo(b: String): Unit = {}

  def fooImplicit(implicit b: String): Unit = { val _ = b; () }

  val fnVal = (_: Dummy) => { 123d }
  val fnValImplicit: Dummy => Double = { implicit opt: Dummy => val _ = opt; 123d }


  test("type of function") {

    // This jumps prints mangled name
    println("foo1: " + foo _)

    // print function signature / type:
    println("foo1 typeTag: " + getTypeTag(foo _))

    // This doesn't work too, can't unapply method if it has implicits:
//    println("fooImplicit typeTag: " + getTypeTag(fooImplicit _))

    println("fnVal typeTag: " + getTypeTag(fnVal))
    println("fnValImplicit typeTag: " + getTypeTag(fnValImplicit))
  }


  def getTypeTag[T: TypeTag](obj: T) = { typeTag[T].tpe }

}