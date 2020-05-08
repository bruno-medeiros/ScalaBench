package demo_app.rest_server

import scala.annotation.nowarn
import scala.concurrent.Future
import scala.concurrent.duration._

import akka.actor.ActorSystem
import akka.http.scaladsl.marshalling.{ Marshal, ToResponseMarshaller }
import akka.http.scaladsl.model._
import org.scalatest.concurrent.ScalaFutures
import org.scalatest.freespec.AnyFreeSpecLike

class AkkaHttpMarshallingTest extends AnyFreeSpecLike with ScalaFutures {

  implicit val system = ActorSystem("marshalling")
  import system.dispatcher

  "various types of marshall" in {
    val string = "Yeah"
    val entity = Marshal(string).to[MessageEntity].futureValue
    assert(entity.contentType == ContentTypes.`text/plain(UTF-8)`)

    val errorMsg = "Easy, pal!"
    val response = Marshal(420 -> errorMsg).to[HttpResponse].futureValue
    assert(response.status === StatusCodes.EnhanceYourCalm)
    assert(response.entity.contentType === ContentTypes.`text/plain(UTF-8)`)

    val request = HttpRequest(headers = List(headers.Accept(MediaTypes.`application/json`)))
    val responseText = "Plaintext"
    intercept[Exception] {
//      intercept[Marshal.UnacceptableResponseContentTypeException] {
      Marshal(responseText).toResponseFor(request).futureValue // with content negotiation!
    }
  }

  def marshallToBody[T : ToResponseMarshaller](obj: T): String = {
    val response: HttpResponse = Marshal(obj).to[HttpResponse].futureValue
    val entity = response.entity.toStrict(1 seconds).futureValue
    entity.data.utf8String
  }

  "marshall future body" in {
    assert(marshallToBody("123") == "123")
//    assert(marshallToBody(()) == "")
    assert(marshallToBody(Future { "123" }) == "123")
//    assert(marshallToBody[Future[Unit]](Future { () }) == "")

    @nowarn
    val failedFutureUnit = Future[Unit] { throw new Exception }
//    val response: HttpResponse = Marshal(failedFutureUnit).to[HttpResponse].futureValue
//
//    assert(response.status == StatusCodes.InternalServerError)
  }

}
