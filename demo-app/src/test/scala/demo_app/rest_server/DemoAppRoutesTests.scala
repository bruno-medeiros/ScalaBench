package demo_app.rest_server

import akka.actor.typed.scaladsl.adapter._
import akka.http.scaladsl.marshalling.Marshal
import akka.http.scaladsl.model.ContentTypes
import akka.http.scaladsl.model.HttpRequest
import akka.http.scaladsl.model.MessageEntity
import akka.http.scaladsl.model.StatusCodes
import akka.http.scaladsl.testkit.RouteTest
import akka.http.scaladsl.testkit.ScalatestRouteTest
import demo_app.workspaces.WorkspaceRegistry
import demo_app.workspaces.WorkspaceRegistry.CreateWorkspaceInfo
import org.scalatest.Matchers
import org.scalatest.OneInstancePerTest
import org.scalatest.Outcome
import org.scalatest.concurrent.ScalaFutures
import org.scalatest.fixture



trait DemoAppApiBaseTests extends Object
  with fixture.FunSuiteLike
  with OneInstancePerTest
{

  override type FixtureParam = DemoAppApi

  test("list no elements") { api =>
    api.listElements("""[]""")
  }

  test("create element") { api =>
    import api._
    createElement(CreateWorkspaceInfo(nameId = "Alex", other = 31))
    createElement(CreateWorkspaceInfo(nameId = "Jamie", other = 123))
  }

  test("delete element") { api =>
    import api._
    createElement(CreateWorkspaceInfo(nameId = "Alex", other = 31))

    createElement(CreateWorkspaceInfo(nameId = "Jamie", other = 123))

    deleteWorkspace("Alex")
    deleteWorkspace("Jamie")
  }

  test("list elements") { api =>
    import api._
    createElement(CreateWorkspaceInfo(nameId = "Alex", other = 31))
    listElements("[Alex]")

    createElement(CreateWorkspaceInfo(nameId = "Jamie", other = 100))
    listElements("[Alex,Jamie]")

    deleteWorkspace("Alex")
    listElements("[Jamie]")
    deleteWorkspace("Jamie")
    listElements("[]")
  }

  test("get element") { api =>
    import api._
    createElement(CreateWorkspaceInfo(nameId = "Alex", other = 31))
    getElement("Alex")
  }

}

class DemoAppRoutesTests extends DemoAppApiBaseTests
  with DemoAppRoutesHelper with ScalatestRouteTest with OneInstancePerTest
{

  override protected def withFixture(test: OneArgTest): Outcome = {
    try {
      withFixture(test.toNoArgTest(this))
    } finally {
    }
  }
}

trait DemoAppRoutesHelper extends RouteTest
  with Matchers with ScalaFutures with DemoAppJsonSupport
  with DemoAppApi
{
  this: ScalatestRouteTest =>

  val workspaceRegistry = new WorkspaceRegistry
  val behavior = system.spawn(workspaceRegistry, "wks")
  val routes = new DemoAppRoutes(system, behavior).routes

  override def listElements(expectedEntity: String) = {
    val request = HttpRequest(uri = "/workspaces")

    request ~> routes ~> check {
      status should ===(StatusCodes.OK)
      contentType should ===(ContentTypes.`text/plain(UTF-8)`)

      entityAs[String] should ===(expectedEntity)
    }
  }

  override def createElement(createReq: WorkspaceRegistry.CreateWorkspaceInfo) = {
    val entity = Marshal(createReq).to[MessageEntity].futureValue

    val request = Post("/workspaces").withEntity(entity)

    request ~> routes ~> check {
      status should ===(StatusCodes.OK)
      contentType should ===(ContentTypes.`text/plain(UTF-8)`)

      entityAs[String] should include(createReq.nameId)
      workspaceRegistry.workspaces.get(createReq.nameId).isDefined shouldBe true
    }
  }

  override def deleteWorkspace(nameId: String): Any = {
    val request = Delete("/workspaces/" + nameId)

    request ~> routes ~> check {
      status should ===(StatusCodes.OK)
      contentType should ===(ContentTypes.`text/plain(UTF-8)`)

      entityAs[String] should ===("Success")
      workspaceRegistry.workspaces.get(nameId).isDefined shouldBe false
    }
  }

  override def getElement(nameId: String): Any = {
    workspaceRegistry.workspaces.get(nameId).isDefined shouldBe true

    val request = Get("/workspaces/" + nameId)

    request ~> routes ~> check {
      status should ===(StatusCodes.OK)
      contentType should ===(ContentTypes.`text/plain(UTF-8)`)

      entityAs[String] should ===(s"GetWorkspace result: $nameId")
    }
  }

}