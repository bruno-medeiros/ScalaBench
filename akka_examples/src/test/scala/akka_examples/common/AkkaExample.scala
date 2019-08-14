package akka_examples.common

import akka.actor.ActorSystem
import akka.testkit.TestKit
import org.scalatest.{BeforeAndAfterAll, Matchers, WordSpecLike}

import scala.concurrent.duration._
import scala.concurrent.{Await, Future}


abstract class AkkaExample(_system: ActorSystem)
  extends TestKit(_system)
  with Matchers
  with WordSpecLike
  with BeforeAndAfterAll {


  def this() = this(ActorSystem("AkkaTest"))

  override def afterAll: Unit = {
    // Invokes TestKit.shutdownActorSystem with dilated duration
    shutdown(system)
  }

  def assertFutureResult[T](future: Future[T], expected: T): Any = {
    val result = Await.result(future, 1 seconds)
    assert(result === expected)
  }
}