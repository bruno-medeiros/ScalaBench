package demo_app.workspaces

import akka.actor.typed.Behavior
import akka.actor.typed.scaladsl.ActorContext
import akka.actor.typed.scaladsl.Behaviors

object Workspace {

  sealed trait Msg

  def create(name: String, ctx: ActorContext[_]): Workspace = {
    val workspace = new Workspace(name)

    ctx.spawn(Behaviors.receive[Msg](workspace.handleMsg), name)

    workspace
  }

}

import demo_app.workspaces.Workspace._

class Workspace(val name: String) {

  def handleMsg(
    ctx: ActorContext[Msg],
    msg: Msg
  ): Behavior[Msg] = {
    ctx.log.info("Workspace received: " + msg)
    Behaviors.same
  }

}
