package demo_app.serde

import demo_app.workspaces.WorkspaceRegistry.CreateWorkspaceInfo
import org.scalatest.FunSuiteLike
import org.scalatest.OneInstancePerTest
import spray.json.DefaultJsonProtocol
import spray.json.JsonFormat
import spray.json.RootJsonFormat

import scala.reflect.ClassTag
import scala.reflect.classTag

case class FooDto(name: String)
case class FooDto2(name: String, age: Int)


class JsonSerdeTests extends Object
  with FunSuiteLike
  with OneInstancePerTest
{

  val protocol = new DefaultJsonProtocol {}
  import protocol._

  test("serde basic") {

    val cwiFormat = jsonFormat3(CreateWorkspaceInfo)
    val obj = CreateWorkspaceInfo("wk1", Some("myData"), 12)

    assert(obj == CreateWorkspaceInfo.tupled("wk1", Some("myData"), 12))

    val jsValue = cwiFormat.write(obj)
    println(jsValue.prettyPrint)

    assert(cwiFormat.read(jsValue) == obj)
  }

  class DtoCache {
    var map: Map[Class[_], RootJsonFormat[_]] = Map.empty

    def putEntry[T <: Product : ClassTag, JsonFormat](klass: Class[T], format: RootJsonFormat[T]): Unit = {
      map += klass -> format
    }

    def getEntry[T <: Product :ClassTag](klass: Class[T]): RootJsonFormat[T] = {
      map.get(klass).map(_.asInstanceOf[RootJsonFormat[T]]).get
    }
  }

  test("serde - cached") {
    val dtoCache = new DtoCache
    dtoCache.putEntry(classOf[CreateWorkspaceInfo], jsonFormat3(CreateWorkspaceInfo))
    dtoCache.putEntry(classOf[FooDto], jsonFormat1(FooDto))

    testReadWrite(CreateWorkspaceInfo("wk1", Some("myData"), 12))
    testReadWrite(FooDto("blah"))

    def testReadWrite[T <: Product :ClassTag](obj: T) = {
      // FIXME: don't use Java's obj.getClass
      val format: RootJsonFormat[T] = dtoCache.getEntry(obj.getClass.asInstanceOf[Class[T]])
      val jsValue = format.write(obj)
      assert(format.read(jsValue) == obj)
    }
  }

}
