import sbt.Keys.libraryDependencies

ThisBuild / organization := "com.github.bruno-medeiros"
ThisBuild / version      := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "2.12.7"
ThisBuild / scalacOptions ++= Seq(
  "-encoding", "UTF-8",   // source files are in UTF-8
  "-deprecation",         // warn about use of deprecated APIs
  "-unchecked",           // warn about unchecked type parameters
  "-feature",             // warn about misused language features
  "-Xlint",               // enable handy linter warnings
//  "-language:higherKinds",// allow higher kinded types without `import scala.language.higherKinds`
//  "-Xfatal-warnings",     // turn compiler warnings into errors
  "-Ypartial-unification" // allow the compiler to unify type constructors of different arities
)


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

lazy val root = (project in file("."))
  .aggregate(scala_examples, akka_examples)

