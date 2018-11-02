//import Dependencies._

lazy val scalaTest = "org.scalatest" %% "scalatest" % "3.0.5"

lazy val scala_examples = (project in file(".")).
  settings(
//    inThisBuild(List(
//      organization := "com.example",
//      scalaVersion := "2.12.6",
//      version      := "0.1.0-SNAPSHOT"
//    )),

    libraryDependencies += scalaTest % Test,
    libraryDependencies += "org.scalacheck" %% "scalacheck" % "1.14.0" % "test",

    name := "scala.examples"
)



//libraryDependencies += "junit" % "junit" % "4.10" % Test
//libraryDependencies += "org.scalacheck" %% "scalacheck" % "1.12.1"
