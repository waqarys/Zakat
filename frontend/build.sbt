enablePlugins(ScalaJSPlugin)

ThisBuild / scalaVersion := "3.3.0"

lazy val frontend = (project in file("."))
  .enablePlugins(ScalaJSPlugin)
  .settings(
    name := "frontend",
    scalaJSUseMainModuleInitializer := true,
    libraryDependencies ++= Seq(
      "com.raquo" %%% "laminar" % "0.14.5"
    )
  )
