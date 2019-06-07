package demo_app.rest_server

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.marshalling.Marshal
import akka.http.scaladsl.model.HttpMethods
import akka.http.scaladsl.model.HttpRequest
import akka.http.scaladsl.model.HttpResponse
import akka.http.scaladsl.model.MessageEntity
import akka.http.scaladsl.model.StatusCodes
import akka.stream.ActorMaterializer
import akka.util.ByteString
import demo_app.workspaces.WorkspaceRegistry
import org.scalatest.BeforeAndAfterAll
import org.scalatest.FunSuite
import org.scalatest.Matchers
import org.scalatest.OneInstancePerTest
import org.scalatest.concurrent.PatienceConfiguration.Timeout
import org.scalatest.concurrent.ScalaFutures

import scala.concurrent.Future
import scala.concurrent.duration._

class DemoAppIntegrationTest extends FunSuite
  with Matchers with ScalaFutures {

  val host = "0.0.0.0"
  val port = 9000

  test("start and terminate") {

    new ServerContext {
      override def run(): Unit = {
        val request = HttpRequest(uri = "http://127.0.0.1:" + port + "/workspaces")
        val eventualResponse = Http().singleRequest(request)
        val response: HttpResponse = eventualResponse.futureValue(Timeout(1 seconds))

        response.status should ===(StatusCodes.OK)

        val byteString = response.entity.dataBytes.runFold(ByteString(""))(_ ++ _).futureValue
        val bodyString = byteString.utf8String
        bodyString.shouldBe("[]")
      }
    }
  }

  abstract class ServerContext() {

    implicit val system: ActorSystem = ActorSystem("demo-app")
    implicit val materializer: ActorMaterializer = ActorMaterializer()(system)

    val server = new DemoAppServer(host, port)
    val bindingFt: Future[Http.ServerBinding] = server.serverBinding

    val binding: Http.ServerBinding = bindingFt.futureValue

    run()

    binding.terminate(1 seconds)

    def run()

  }
}

class DemoAppRestIntegrationTests extends DemoAppRoutesTestsBase
  with DemoAppApi with OneInstancePerTest with BeforeAndAfterAll
  with DemoAppJsonSupport
  with Matchers with ScalaFutures
{

  val host = "0.0.0.0"
  val port = 9000

  implicit val system: ActorSystem = ActorSystem("demo-app")
  implicit def executor = system.dispatcher
  implicit val materializer: ActorMaterializer = ActorMaterializer()(system)


  val server = new DemoAppServer(host, port)
  val bindingFt: Future[Http.ServerBinding] = server.serverBinding

  val binding: Http.ServerBinding = bindingFt.futureValue


  override protected def afterAll(): Unit = {
    binding.terminate(1 seconds).futureValue
    super.afterAll()
  }

  override def listElements(expectedEntity: String) {
    val request = HttpRequest(uri = "http://127.0.0.1:" + port + "/workspaces")
    val eventualResponse = Http().singleRequest(request)
    val response: HttpResponse = eventualResponse.futureValue(Timeout(1 seconds))

    response.status should ===(StatusCodes.OK)

    val byteString = response.entity.dataBytes.runFold(ByteString(""))(_ ++ _).futureValue
    val bodyString = byteString.utf8String
    bodyString.shouldBe("[]")
  }

  override def createElement(createReq: WorkspaceRegistry.CreateWorkspaceInfo) = {
    val entity = Marshal(createReq).to[MessageEntity].futureValue

    val request = HttpRequest(uri = "http://127.0.0.1:" + port + "/workspace", method = HttpMethods.POST)
      .withEntity(entity)

    val response: HttpResponse = Http().singleRequest(request).futureValue(Timeout(1 seconds))
    response.status should ===(StatusCodes.OK)

    val byteString = response.entity.dataBytes.runFold(ByteString(""))(_ ++ _).futureValue
    val bodyString = byteString.utf8String
    bodyString should include(createReq.nameId)
  }

  override def deleteWorkspace(nameId: String): Any = ???

  override def getElement(nameId: String): Any = ???
}
