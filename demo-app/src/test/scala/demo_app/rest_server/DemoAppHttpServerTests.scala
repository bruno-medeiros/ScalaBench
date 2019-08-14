package demo_app.rest_server

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.marshalling.Marshal
import akka.http.scaladsl.model.HttpEntity
import akka.http.scaladsl.model.HttpMethod
import akka.http.scaladsl.model.HttpMethods
import akka.http.scaladsl.model.HttpRequest
import akka.http.scaladsl.model.HttpResponse
import akka.http.scaladsl.model.MessageEntity
import akka.http.scaladsl.model.StatusCodes
import akka.stream.ActorMaterializer
import akka.util.ByteString
import demo_app.workspaces.WorkspaceRegistry
import org.scalatest.Matchers
import org.scalatest.OneInstancePerTest
import org.scalatest.Outcome
import org.scalatest.concurrent.PatienceConfiguration.Timeout
import org.scalatest.concurrent.ScalaFutures

import scala.concurrent.Future
import scala.concurrent.duration._


class DemoAppHttpServerTests extends DemoAppApiBaseTests
  with OneInstancePerTest
  with DemoAppJsonSupport
  with Matchers with ScalaFutures
{

  val host = "0.0.0.0"
  val port = 9000

  implicit val system: ActorSystem = ActorSystem("demo-app")
  implicit def executor = system.dispatcher
  implicit val materializer: ActorMaterializer = ActorMaterializer()(system)


  override protected def withFixture(test: OneArgTest): Outcome = {
    val fixture = new DemoAppHttpFixture
    try {
      withFixture(test.toNoArgTest(fixture))
    } finally {
      fixture.close()
    }
  }

  class DemoAppHttpFixture extends DemoAppApi with AutoCloseable {
    val server : DemoAppServer = new DemoAppServer(host, port)
    val bindingFt: Future[Http.ServerBinding] = server.serverBinding
    val binding: Http.ServerBinding = bindingFt.futureValue

    def dispose(): Unit = {
      binding.terminate(500 millis).futureValue
    }

    override def close(): Unit = dispose()

    def requestAndAwaitResponse(method: HttpMethod = HttpMethods.GET, path: String, entity: MessageEntity): String = {
      val request = HttpRequest(method, uri = s"http://127.0.0.1:$port/" + path)
        .withEntity(entity)
      val response: HttpResponse = Http().singleRequest(request).futureValue(Timeout(1 seconds))
      response.status should ===(StatusCodes.OK)

      val byteString = response.entity.dataBytes.runFold(ByteString(""))(_ ++ _).futureValue
      byteString.utf8String
    }

    override def listElements(expectedEntity: String) = {
      val entity = HttpEntity("")
      val bodyString = requestAndAwaitResponse(HttpMethods.GET, "workspaces", entity)
      bodyString.shouldBe(expectedEntity)
    }

    override def createElement(createReq: WorkspaceRegistry.CreateWorkspaceInfo) = {
      val entity = Marshal(createReq).to[MessageEntity].futureValue

      val bodyString = requestAndAwaitResponse(HttpMethods.POST, "workspaces", entity)
      bodyString should include(createReq.nameId)
    }

    override def deleteWorkspace(nameId: String) = {
      val entity = HttpEntity("")
      val bodyString = requestAndAwaitResponse(HttpMethods.DELETE, "workspaces/" + nameId, entity)
      bodyString.nonEmpty.shouldBe(true)
    }

    override def getElement(nameId: String) = {
      val entity = HttpEntity(nameId)

      val bodyString = requestAndAwaitResponse(HttpMethods.GET, "workspaces", entity)
      bodyString should include(nameId)
    }
  }
}