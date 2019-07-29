import sbt.Keys.libraryDependencies

ThisBuild / scalaVersion     := "2.12.8"
ThisBuild / version          := "0.1.0-SNAPSHOT"
ThisBuild / organization     := "com.thoughtworks.gatling"
ThisBuild / organizationName := "project2"

lazy val root = (project in file("."))
  .enablePlugins(GatlingPlugin)
  .settings(
    name := "gatlingProject",
      libraryDependencies ++= Seq(
        "io.gatling.highcharts" % "gatling-charts-highcharts" % "3.0.0" % "test",
        "io.gatling" % "gatling-test-framework" % "3.0.0" % "test"
      )
  )
