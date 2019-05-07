package demo_app.workspaces

import akka.actor.typed.Behavior
import akka.actor.typed.scaladsl.ActorContext
import akka.actor.typed.scaladsl.Behaviors

object Workspace {

  sealed trait Msg

}

import demo_app.workspaces.Workspace._

class Workspace(name: String) {

  def handleMsg(
    ctx: ActorContext[Msg],
    msg: Msg
  ): Behavior[Msg] = {
    ctx.log.info("Workspace received: " + msg)
    Behaviors.same
  }

}