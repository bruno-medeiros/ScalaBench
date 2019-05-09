package demo_app.rest_server

import akka.actor.typed.scaladsl.adapter._
import akka.http.scaladsl.marshalling.Marshal
import akka.http.scaladsl.model.ContentTypes
import akka.http.scaladsl.model.HttpRequest
import akka.http.scaladsl.model.MessageEntity
import akka.http.scaladsl.model.StatusCodes
import akka.http.scaladsl.testkit.ScalatestRouteTest
import demo_app.workspaces.WorkspaceRegistry
import demo_app.workspaces.WorkspaceRegistry.CreateWorkspaceInfo
import org.scalatest.FunSuite
import org.scalatest.Matchers
import org.scalatest.OneInstancePerTest
import org.scalatest.concurrent.ScalaFutures


class DemoAppRoutesTests extends FunSuite
  with Matchers with ScalaFutures with DemoAppJsonSupport
  with ScalatestRouteTest
  with OneInstancePerTest
{

  val workspaceRegistry = new WorkspaceRegistry
  val behavior = system.spawn(workspaceRegistry, "wks")
  val routes = new DemoAppRoutes(system, behavior).routes

  test("list no elements") {
    listElements("""[]""")
  }

  private def listElements(expectedEntity: String) = {
    val request = HttpRequest(uri = "/workspaces")

    request ~> routes ~> check {
      status should ===(StatusCodes.OK)
      contentType should ===(ContentTypes.`text/plain(UTF-8)`)

      entityAs[String] should ===(expectedEntity)
    }
  }

  test("create element") {
    createElement(CreateWorkspaceInfo(nameId = "Alex", other = 31))
    createElement(CreateWorkspaceInfo(nameId = "Jamie", other = 123))
  }

  private def createElement(createReq: WorkspaceRegistry.CreateWorkspaceInfo) = {
    val entity = Marshal(createReq).to[MessageEntity].futureValue

    val request = Post("/workspaces").withEntity(entity)

    request ~> routes ~> check {
      status should ===(StatusCodes.OK)
      contentType should ===(ContentTypes.`text/plain(UTF-8)`)

      entityAs[String] should include(createReq.nameId)
      workspaceRegistry.workspaces.get(createReq.nameId).isDefined shouldBe true
    }
  }

  test("delete element") {
    createElement(CreateWorkspaceInfo(nameId = "Alex", other = 31))

    createElement(CreateWorkspaceInfo(nameId = "Jamie", other = 123))

    deleteWorkspace("Alex")
    deleteWorkspace("Jamie")
  }

  private def deleteWorkspace(nameId: String): Any = {
    val request = Delete("/workspaces/" + nameId)

    request ~> routes ~> check {
      status should ===(StatusCodes.OK)
      contentType should ===(ContentTypes.`text/plain(UTF-8)`)

      entityAs[String] should ===("Success")
      workspaceRegistry.workspaces.get(nameId).isDefined shouldBe false
    }
  }

  test("list elements") {
    createElement(CreateWorkspaceInfo(nameId = "Alex", other = 31))
    listElements("[Alex]")

    createElement(CreateWorkspaceInfo(nameId = "Jamie", other = 100))
    listElements("[Alex,Jamie]")

    deleteWorkspace("Alex")
    listElements("[Jamie]")
    deleteWorkspace("Jamie")
    listElements("[]")
  }

  test("get element") {
    createElement(CreateWorkspaceInfo(nameId = "Alex", other = 31))
    getElement("Alex")
  }

  private def getElement(nameId: String): Any = {
    workspaceRegistry.workspaces.get(nameId).isDefined shouldBe true

    val request = Get("/workspaces/" + nameId)

    request ~> routes ~> check {
      status should ===(StatusCodes.OK)
      contentType should ===(ContentTypes.`text/plain(UTF-8)`)

      entityAs[String] should ===(s"GetWorkspace result: $nameId")
    }
  }

}