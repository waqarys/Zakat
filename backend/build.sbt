ThisBuild / scalaVersion := "3.3.0"

lazy val backend = (project in file("."))
  .settings(
    name := "backend",
    libraryDependencies ++= Seq(
      "org.typelevel" %% "cats-effect" % "3.5.2",
      "org.http4s" %% "http4s-dsl" % "0.23.9",
      "org.http4s" %% "http4s-ember-server" % "0.23.9",
      "org.http4s" %% "http4s-blaze-server" % "0.23.9",
      "org.http4s" %% "http4s-circe" % "0.23.9",
      "io.circe" %% "circe-generic" % "0.14.5"
    )
  )
