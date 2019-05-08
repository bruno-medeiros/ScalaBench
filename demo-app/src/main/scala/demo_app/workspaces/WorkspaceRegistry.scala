package demo_app.workspaces

import akka.actor.typed.ActorRef
import akka.actor.typed.Behavior
import akka.actor.typed.ExtensibleBehavior
import akka.actor.typed.Signal
import akka.actor.typed.TypedActorContext
import demo_app.workspaces.WorkspaceRegistry._

import scala.util.Failure
import scala.util.Success
import scala.util.Try

object WorkspaceRegistry {

  sealed trait Msg

  case class ListWorkspaces(replyTo: ActorRef[Iterable[String]]) extends Msg
  case class CreateWorkspace(nameId: String, replyTo: ActorRef[Try[Workspace]]) extends Msg
  case class GetWorkspace(nameId: String, replyTo: ActorRef[Option[Workspace]]) extends Msg
  case class DeleteWorkspace(nameID: String, replyTo: ActorRef[Try[Unit]]) extends Msg

}


class WorkspaceRegistry extends ExtensibleBehavior[Msg] {

  var workspaces: Map[String, Workspace] = Map.empty

  override def receive(
    ctx: TypedActorContext[Msg],
    msg: Msg
  ): Behavior[Msg] = {
    msg match {
      case ListWorkspaces(replyTo) =>
        replyTo ! workspaces.keys

      case GetWorkspace(nameId, replyTo) =>
        replyTo ! workspaces.get(nameId)

      case CreateWorkspace(nameId, replyTo) =>
        if (workspaces.contains(nameId)) {

          replyTo ! Failure(new Exception(s"Workspace already exists: $nameId"))
        } else {
          val workspace = Workspace.create(nameId, ctx.asScala)
          workspaces += nameId -> workspace

          replyTo ! Success(workspace)
        }

      case DeleteWorkspace(nameId, replyTo) =>
        if (!workspaces.contains(nameId)) {

          replyTo ! Failure(new Exception(s"No such workspace: $nameId"))
        } else {
          workspaces -= nameId

          replyTo ! Success(())
        }

    }
    Behavior.same
  }

  override def receiveSignal(
    ctx: TypedActorContext[Msg],
    msg: Signal
  ): Behavior[Msg] = Behavior.same
}