package akka_examples.common

import akka.actor.ActorSystem
import akka.testkit.TestKit
import org.scalatest.{BeforeAndAfterAll, Matchers, WordSpecLike}

class AkkaTest(_system: ActorSystem)
  extends TestKit(_system)
  with Matchers
  with WordSpecLike
  with BeforeAndAfterAll {

  def this() = this(ActorSystem(getClass.getSimpleName.replace("$", "")))

  override def afterAll: Unit = {
    // Invokes TestKit.shutdownActorSystem with dilated duration
    shutdown(system)
  }
}