package demo_app.workspaces

import scala.util.Failure
import scala.util.Success
import scala.util.Try

import akka.actor.typed.ActorRef
import akka.actor.typed.Behavior
import akka.actor.typed.ExtensibleBehavior
import akka.actor.typed.Signal
import akka.actor.typed.TypedActorContext
import akka.actor.typed.scaladsl.Behaviors
import demo_app.workspaces.WorkspaceRegistry._

object WorkspaceRegistry {

  sealed trait Msg

  case class ListWorkspaces(replyTo: ActorRef[Iterable[String]]) extends Msg
  case class CreateWorkspace(createInfo: CreateWorkspaceInfo, replyTo: ActorRef[Try[Workspace]]) extends Msg
  case class GetWorkspace(nameId: String, replyTo: ActorRef[Option[Workspace]]) extends Msg
  case class DeleteWorkspace(nameID: String, replyTo: ActorRef[Try[Unit]]) extends Msg

  case class CreateWorkspaceInfo(nameId: String, data: Option[String] = None, other: Int)
}

class WorkspaceRegistry() extends ExtensibleBehavior[Msg] {

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

      case CreateWorkspace(createInfo, replyTo) =>
        val nameId = createInfo.nameId
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
    Behaviors.same
  }

  override def receiveSignal(
    ctx: TypedActorContext[Msg],
    msg: Signal
  ): Behavior[Msg] = Behaviors.same
}
