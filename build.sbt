scalaVersion := "2.11.6"

resolvers += "Android Repository" at (new File(System.getenv("ANDROID_HOME")) / "extras" / "android" / "m2repository").getCanonicalFile.toURI.toString

libraryDependencies ++= Seq(
  "com.chuusai" %% "shapeless" % "2.1.0",
  "com.google.android" % "android" % "4.1.1.4" % "provided",
  "org.robolectric" % "robolectric" % "2.3" % "test",
  "org.scalatest" %% "scalatest" % "2.2.1" % "test",
  "org.scalacheck" %% "scalacheck" % "1.11.6" % "test",
  "junit" % "junit" % "4.11" % "test",
  "com.novocode" % "junit-interface" % "0.11" % "test",
  "com.google.android" % "android" % "4.1.1.4" % "test"
)

fork in Test := true
