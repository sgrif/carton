seq(bintraySettings:_*)

seq(bintrayPublishSettings:_*)

licenses += ("MIT", url("http://opensource.org/licenses/MIT"))

scalaVersion := "2.11.6"

resolvers += "Android Repository" at (new File(System.getenv("ANDROID_HOME")) / "extras" / "android" / "m2repository").getCanonicalFile.toURI.toString

addCompilerPlugin("org.scalamacros" % "paradise_2.11.6" % "2.1.0-M5")

javacOptions ++= Seq("-source", "1.7", "-target", "1.7")

scalacOptions ++= Seq("-feature", "-deprecation", "-target:jvm-1.7")

name := "Carton"

organization := "carton"

version := "0.1-M3"

libraryDependencies ++= Seq(
  "com.chuusai" %% "shapeless" % "2.1.0",
  "org.scala-lang" % "scala-reflect" % "2.11.6",
  "com.google.android" % "android" % "4.1.1.4" % "provided",
  "org.robolectric" % "robolectric" % "2.3" % "test",
  "org.scalatest" %% "scalatest" % "2.2.1" % "test",
  "org.scalacheck" %% "scalacheck" % "1.11.6" % "test",
  "junit" % "junit" % "4.11" % "test",
  "com.novocode" % "junit-interface" % "0.11" % "test",
  "com.google.android" % "android" % "4.1.1.4" % "test"
)

fork in Test := true
