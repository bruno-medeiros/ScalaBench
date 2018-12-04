package akka_examples

import akka.actor.ActorSystem
import akka.testkit.TestKit
import org.scalatest.{BeforeAndAfterAll, Matchers, WordSpecLike}

import scala.language.postfixOps

class AkkaTest(_system: ActorSystem)
  extends TestKit(_system)
  with Matchers
  with WordSpecLike
  with BeforeAndAfterAll {

  def this() = this(ActorSystem("AkkaTest"))

  override def afterAll: Unit = {
    TestKit.shutdownActorSystem(system)
//    shutdown(system)
  }
}