package demo_app.rest_server

import akka.actor.ActorSystem
import akka.actor.typed.ActorRef
import akka.actor.typed.scaladsl.AskPattern._
import akka.event.Logging
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Directives.entity
import akka.http.scaladsl.server.Directives.post
import akka.http.scaladsl.server.Route
import akka.util.Timeout
import demo_app.workspaces.Workspace
import demo_app.workspaces.WorkspaceRegistry
import demo_app.workspaces.WorkspaceRegistry.CreateWorkspaceInfo

import scala.concurrent.duration._
import scala.util.Try



class DemoAppRoutes(system: ActorSystem, workspaceRegistry: ActorRef[WorkspaceRegistry.Msg])
  extends DemoAppJsonSupport
{

  val log = Logging(system, classOf[DemoAppRoutes])

  implicit lazy val timeout = Timeout(5.seconds) // usually we'd obtain the timeout from the system's configuration
  implicit val scheduler = system.scheduler
  implicit val dispatcher = system.dispatcher

  val routes: Route = concat(
    pathPrefix("workspaces") {
      concat(
        pathEnd {
          get {
            log.info(s"ListWorkspaces")

            val result = workspaceRegistry.ask[Iterable[String]](WorkspaceRegistry.ListWorkspaces(_))

            complete(result.map(_.mkString("[", ",", "]")))
          },
        },
        pathEnd {
          post {
            entity(as[CreateWorkspaceInfo]) { createInfo =>
              log.info(s"CreateWorkspace:\n $createInfo \n------")

              val result = workspaceRegistry
                .ask[Try[Workspace]](WorkspaceRegistry.CreateWorkspace(createInfo, _))
                .transform(s => s.flatten)(dispatcher)

              complete(result.map("CreatedWorkspace: " + _.name))
            }
          },
        },

        path(Segment) { name =>
          concat(
            get {
              log.info(s"GetWorkspace: $name")

              val result = workspaceRegistry.ask[Option[Workspace]](WorkspaceRegistry.GetWorkspace(name, _))

              rejectEmptyResponse {
                complete(result.map(_.map(wks => s"GetWorkspace result: " + wks.name)))
              }
            },
            delete {
              log.info(s"DeleteWorkspace: $name")

              val result = workspaceRegistry.ask[Try[Unit]](WorkspaceRegistry.DeleteWorkspace(name, _))

              complete(result.map(_ => "Success"))
            },
          )
        },
      )
    },

  )

}