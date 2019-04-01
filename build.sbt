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

lazy val scalaTest = "org.scalatest" %% "scalatest" % "3.0.5"
lazy val scalaCheck = "org.scalacheck" %% "scalacheck" % "1.14.0"



lazy val scala_examples = (project in file("scala_examples"))
  .settings(
    libraryDependencies += scalaTest
  )

lazy val scala_test_examples = (project in file("scala_test_examples"))
  .settings(
    libraryDependencies += scalaTest % Test,
    libraryDependencies += scalaCheck % Test
  )


lazy val akkaVersion = "2.5.21"

lazy val akka_examples = (project in file("akka_examples"))
  .settings(
    libraryDependencies += scalaTest % Test,
    libraryDependencies += scalaCheck % Test,
    libraryDependencies ++= Seq(
      "com.typesafe.akka" %% "akka-actor" % akkaVersion,
      "com.typesafe.akka" %% "akka-testkit" % akkaVersion,
      "com.typesafe.akka" %% "akka-actor-typed" % akkaVersion,
      "com.typesafe.akka" %% "akka-stream" % akkaVersion
    ),
    libraryDependencies += "com.typesafe.akka" %% "akka-persistence" % akkaVersion
)

lazy val root = (project in file("."))
  .aggregate(
    scala_examples,
    scala_test_examples,
    akka_examples
  )
