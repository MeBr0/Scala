name := "week5_2"

version := "0.1"

scalaVersion := "2.13.5"

idePackagePrefix := Some("com.mebr0")

val AkkaVersion = "2.6.13"

libraryDependencies ++= Seq(
  "com.typesafe.akka" %% "akka-actor-typed" % AkkaVersion,
  "com.typesafe.akka" %% "akka-actor-testkit-typed" % AkkaVersion % Test,
  "ch.qos.logback" % "logback-classic" % "1.2.3"
)
