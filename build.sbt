//import Dependencies._
//
//ThisBuild / scalaVersion     := "2.13.12"
//ThisBuild / version          := "0.1.0-SNAPSHOT"
//ThisBuild / organization     := "com.waqar"
//ThisBuild / organizationName := "waqar"
//
//enablePlugins(ScalaJSPlugin)
//
//val scalaVersionUsed = "3.3.0"
//val laminarVersion = "0.14.5"
//val http4sVersion = "0.23.9"
//
//lazy val root = (project in file("."))
//  .aggregate(backend, frontend)
//  .settings(
//    name := "zakat-calculator",
//    scalaVersion := scalaVersionUsed
//  )
//
//lazy val backend = (project in file("backend"))
//  .settings(
//    name := "backend",
//    scalaVersion := scalaVersionUsed,
//    libraryDependencies ++= Seq(
//      "org.typelevel" %% "cats-effect" % "3.5.2",
//      "org.http4s" %% "http4s-dsl" % http4sVersion,
//      "org.http4s" %% "http4s-blaze-server" % http4sVersion,
//      "org.http4s" %% "http4s-circe" % http4sVersion,
//      "io.circe" %% "circe-generic" % "0.14.5"
//    )
//  )
//
//lazy val frontend = (project in file("frontend"))
//  .enablePlugins(ScalaJSPlugin)
//  .settings(
//    name := "frontend",
//    scalaVersion := scalaVersionUsed,
//    scalaJSUseMainModuleInitializer := true,
//    libraryDependencies ++= Seq(
//      "com.raquo" %%% "laminar" % laminarVersion
//    )
//  )
//
//
import sbt._
import sbt.Keys._
import org.scalajs.sbtplugin.ScalaJSPlugin.autoImport._

lazy val commonSettings = Seq(
  scalaVersion := "2.13.10"
)

lazy val frontend = (project in file("frontend"))
  .enablePlugins(org.scalajs.sbtplugin.ScalaJSPlugin)
  .settings(commonSettings ++ Seq(
    scalaJSUseMainModuleInitializer := true,
    libraryDependencies += "com.raquo" %%% "laminar" % "0.14.5"
  ): _*)

lazy val backend = (project in file("backend"))
  .dependsOn(frontend)
  .settings(commonSettings ++ Seq(
    libraryDependencies ++= Seq(
      "org.typelevel" %% "cats-effect" % "3.5.2",
      "org.http4s" %% "http4s-dsl" % "0.23.9",
      "org.http4s" %% "http4s-ember-server" % "0.23.9",
      "org.http4s" %% "http4s-circe" % "0.23.9",
      "io.circe" %% "circe-generic" % "0.14.5"
    ),
    // Add managed resources to the classpath:
    Compile / unmanagedResourceDirectories += (Compile / resourceManaged).value
  ): _*)


// Task to copy the Scala.js fastOptJS output into the backend's resources.
lazy val copyFrontendJS = taskKey[Seq[File]]("Copy frontend fastOptJS output to backend resources")

copyFrontendJS := {
  // Run the frontend fastOptJS task (under Compile) and get the generated JS file.
  val jsFile: File = (frontend / Compile / fastOptJS).value.data
  // Define the target directory in the backend's managed resources (using the Compile configuration)
  val targetDir = (backend / Compile / resourceManaged).value / "public"
  IO.createDirectory(targetDir)
  IO.copyFile(jsFile, targetDir / jsFile.getName)
  Seq(targetDir / jsFile.getName)
}

// Automatically run the copy task as part of backend's resource generation.
(backend / Compile / resourceGenerators) += copyFrontendJS.taskValue


// Root project aggregates both frontend and backend.
lazy val root = (project in file("."))
  .aggregate(frontend, backend)
  .settings(
    publish / skip := true  // Prevents publishing the root project.
  )
