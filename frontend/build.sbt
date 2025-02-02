enablePlugins(ScalaJSPlugin)

ThisBuild / scalaVersion := "3.3.0"

scalaJSUseMainModuleInitializer := true // Required for generating JS files

Compile / fastOptJS / artifactPath := (Compile / crossTarget).value / "frontend-fastopt.js"


lazy val frontend = (project in file("."))
  .enablePlugins(ScalaJSPlugin)
  .settings(
    name := "frontend",
    scalaJSUseMainModuleInitializer := true,
    libraryDependencies ++= Seq(
      "com.raquo" %%% "laminar" % "0.14.5"
    )
  )
