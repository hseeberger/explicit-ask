// *****************************************************************************
// Projects
// *****************************************************************************

lazy val `explicit-ask` =
  project
    .in(file("."))
    .enablePlugins(AutomateHeaderPlugin)
    .settings(settings)
    .settings(
      libraryDependencies ++= Seq(
        library.akkaActor,
        library.junit      % Test,
        library.junitIf    % Test
      )
    )

// *****************************************************************************
// Library dependencies
// *****************************************************************************

lazy val library =
  new {
    object Version {
      val akka    = "2.5.9"
      val junit   = "4.12"
      val junitIf = "0.11"
    }
    val akkaActor = "com.typesafe.akka" %% "akka-actor"     % Version.akka
    val junit     = "junit"             %  "junit"          % Version.junit
    val junitIf   = "com.novocode"      % "junit-interface" % Version.junitIf
  }

// *****************************************************************************
// Settings
// *****************************************************************************

lazy val settings =
  commonSettings ++
  scalafmtSettings ++
  publishSettings

lazy val commonSettings =
  Seq(
    // scalaVersion from .travis.yml via sbt-travisci
    // scalaVersion := "2.12.4",
    organization := "rocks.heikoseeberger",
    organizationName := "Heiko Seeberger",
    startYear := Some(2018),
    licenses += ("Apache-2.0", url("http://www.apache.org/licenses/LICENSE-2.0")),
    scalacOptions ++= Seq(
      "-unchecked",
      "-deprecation",
      "-language:_",
      "-target:jvm-1.8",
      "-encoding", "UTF-8",
      "-Ypartial-unification",
      "-Ywarn-unused-import"
    ),
    Compile / unmanagedSourceDirectories := Seq((Compile / scalaSource).value),
    Test / unmanagedSourceDirectories := Seq((Test / javaSource).value),
    testFrameworks += new TestFramework("utest.runner.Framework")
)

lazy val scalafmtSettings =
  Seq(
    scalafmtOnCompile := true
  )

lazy val publishSettings =
  Seq(
    homepage := Some(url("https://github.com/hseeberger/explicit-ask")),
    scmInfo := Some(ScmInfo(url("https://github.com/hseeberger/explicit-ask"),
                            "git@github.com:hseeberger/explicit-ask.git")),
    developers += Developer("hseeberger",
                            "Heiko Seeberger",
                            "mail@heikoseeberger.rocks",
                            url("https://github.com/hseeberger")),
    pomIncludeRepository := (_ => false),
    bintrayPackage := "akka-http-json"
  )
