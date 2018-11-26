import sbt.Keys.libraryDependencies

ThisBuild / organization := "com.example"
ThisBuild / version      := "0.1.0-SNAPSHOT"
ThisBuild / scalaVersion := "2.12.7"

lazy val root = (project in file("."))
  .aggregate()


lazy val scala_examples = (project in file("scala.examples"))
  .settings(
    // other settings
  )

lazy val akkaVersion = "2.5.16"

lazy val akka_examples = (project in file("akka_examples"))
  .settings(
    libraryDependencies ++= Seq(
      "com.typesafe.akka" %% "akka-actor" % akkaVersion,
      "com.typesafe.akka" %% "akka-testkit" % akkaVersion % "test",
      "org.scalatest" %% "scalatest" % "3.0.5" % "test"
    ),
    libraryDependencies += "com.typesafe.akka" %% "akka-persistence" % akkaVersion
)
