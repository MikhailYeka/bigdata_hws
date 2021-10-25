name := "made_bigdata"

version := "0.1"

scalaVersion := "2.13.6"

libraryDependencies  ++= Seq(
  // Last stable release
  "org.scalanlp" %% "breeze" % "1.2",

  // The visualization library is distributed separately as well.
  // It depends on LGPL code
  "org.scalanlp" %% "breeze-viz" % "1.2"
//  "org.scala" %% "scala.io.Source" % "latest.integration"
)
