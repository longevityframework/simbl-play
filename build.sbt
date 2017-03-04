name := "simple-blogging-play"

version := "0.1"

scalaVersion := "2.11.8"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

resolvers += Resolver.bintrayRepo("hseeberger", "maven")

scalacOptions ++= Seq(
  "-Xfuture",
  "-Yno-adapted-args",
  "-Ywarn-numeric-widen",
  "-deprecation",
  "-encoding", "UTF-8",
  "-feature",
  "-language:existentials",
  "-language:higherKinds",
  "-language:implicitConversions",
  "-unchecked")

libraryDependencies ++= {
  val longevityVersion = "0.21.0"
  val scalaTestVersion = "3.0.1"
  val scalaTimeVersion = "2.16.0"
  Seq(
    ws, cache,
    "com.github.nscala-time" %% "nscala-time"              % scalaTimeVersion,
    "org.longevityframework" %% "longevity"                % longevityVersion,
    "org.longevityframework" %% "longevity-cassandra-deps" % longevityVersion,
    "org.longevityframework" %% "longevity-mongodb-deps"   % longevityVersion,
    "org.longevityframework" %% "longevity-sqlite-deps"    % longevityVersion,
    "org.scalatest"          %% "scalatest"                % scalaTestVersion % Test
  )
}

addCompilerPlugin("org.scalamacros" % "paradise" % "2.1.0" cross CrossVersion.full)

fork in run := true
