package examples

import scala.reflect.runtime.universe
import scala.reflect.runtime.universe.TypeTag
import scala.reflect.runtime.universe.typeTag

object MiscUtil {

  def getTypeTag[T: TypeTag](obj: T): universe.Type = { typeTag[T].tpe }

}
