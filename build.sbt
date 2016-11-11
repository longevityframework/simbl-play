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
  val longevityVersion = "0.16.0"
  val scalaTestVersion = "2.2.6"
  val scalaTimeVersion = "2.14.0"
  Seq(
    ws, cache,
    "com.github.nscala-time" %% "nscala-time" % scalaTimeVersion,
    "org.longevityframework" %% "longevity" % longevityVersion,
    "org.longevityframework" %% "longevity-cassandra-deps" % longevityVersion,
    "org.longevityframework" %% "longevity-mongo-deps" % longevityVersion,
    "org.scalatest" %% "scalatest" % scalaTestVersion % Test
  )
}

fork in run := true
