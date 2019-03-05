import scala.sys.process.Process
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