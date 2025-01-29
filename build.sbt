import Dependencies._

ThisBuild / scalaVersion     := "2.13.12"
ThisBuild / version          := "0.1.0-SNAPSHOT"
ThisBuild / organization     := "com.waqar"
ThisBuild / organizationName := "waqar"

enablePlugins(ScalaJSPlugin)

val scalaVersionUsed = "3.3.0"
val laminarVersion = "0.14.5"
val http4sVersion = "0.23.9"

lazy val root = (project in file("."))
  .aggregate(backend, frontend)
  .settings(
    name := "zakat-calculator",
    scalaVersion := scalaVersionUsed
  )

lazy val backend = (project in file("backend"))
  .settings(
    name := "backend",
    scalaVersion := scalaVersionUsed,
    libraryDependencies ++= Seq(
      "org.typelevel" %% "cats-effect" % "3.5.2",
      "org.http4s" %% "http4s-dsl" % http4sVersion,
      "org.http4s" %% "http4s-blaze-server" % http4sVersion,
      "org.http4s" %% "http4s-circe" % http4sVersion,
      "io.circe" %% "circe-generic" % "0.14.5"
    )
  )

lazy val frontend = (project in file("frontend"))
  .enablePlugins(ScalaJSPlugin)
  .settings(
    name := "frontend",
    scalaVersion := scalaVersionUsed,
    scalaJSUseMainModuleInitializer := true,
    libraryDependencies ++= Seq(
      "com.raquo" %%% "laminar" % laminarVersion
    )
  )


