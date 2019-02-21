import scala.sys.process.Process
//import Dependencies._

lazy val scalaTest = "org.scalatest" %% "scalatest" % "3.0.5"


name := "scala.examples"


libraryDependencies += "org.scalactic" %% "scalactic" % "3.0.5"
libraryDependencies += "org.scalatest" %% "scalatest" % "3.0.5" // % Test
libraryDependencies += "org.scalacheck" %% "scalacheck" % "1.14.0" % Test

// SBT Test stuff:
//logBuffered in Test := false
//parallelExecution in Test := false
//testOptions in Test += Tests.Argument(TestFrameworks.ScalaTest, "-y", "org.scalatest.FunSpec")

val gitCommitCountTask = taskKey[String]("Prints commit count of the current branch")

gitCommitCountTask := {
  val branch = Process("git symbolic-ref -q HEAD").lineStream.head.replace("refs/heads/","")
  val commitCount = Process(s"git rev-list --count $branch").lineStream.head
  println(s"total number of commits on [$branch]: $commitCount")
  commitCount
}