package akka_examples.common

import scala.concurrent.Await
import scala.concurrent.Future
import scala.concurrent.duration._

import akka.actor.ActorSystem
import akka.testkit.TestKit
import org.scalatest.BeforeAndAfterAll
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpecLike

abstract class AkkaExample(_system: ActorSystem)
    extends TestKit(_system)
    with Matchers
    with AnyWordSpecLike
    with BeforeAndAfterAll {

  def this() = this(ActorSystem("AkkaTest"))

  override def afterAll(): Unit = {
    // Invokes TestKit.shutdownActorSystem with dilated duration
    shutdown(system)
  }

  def assertFutureResult[T](future: Future[T], expected: T): Any = {
    val result = Await.result(future, 1 seconds)
    assert(result === expected)
  }
}
