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
