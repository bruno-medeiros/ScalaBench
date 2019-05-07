package demo_app.workspaces

import akka.actor.typed.Behavior
import akka.actor.typed.ExtensibleBehavior
import akka.actor.typed.Signal
import akka.actor.typed.TypedActorContext
import demo_app.workspaces.WorkspaceRegistry._

object WorkspaceRegistry {

  sealed trait Msg

  case class CreateWorkspace(name: String) extends Msg
  case class DeleteWorkspace(name: String) extends Msg

}


class WorkspaceRegistry extends ExtensibleBehavior[Msg] {

  var workspaces: Map[String, Workspace] = Map.empty

  override def receive(
    ctx: TypedActorContext[Msg],
    msg: Msg
  ): Behavior[Msg] = {
    msg match {
      case CreateWorkspace(name) =>
        // BUG here check existing

        val workspace = Workspace.create(name, ctx.asScala)
        workspaces += name -> workspace
      case DeleteWorkspace(name) =>
        workspaces -= name
    }
    Behavior.same
  }

  override def receiveSignal(
    ctx: TypedActorContext[Msg],
    msg: Signal
  ): Behavior[Msg] = Behavior.same
}