name := "tfidf_scala"

version := "0.1"

scalaVersion := "2.13.7"

val sparkVersion = "3.2.0"


//libraryDependencies += "org.apache.spark" %% "spark-sql" % "3.2.0"

libraryDependencies ++= Seq(
//  "org.apache.spark" %% sparkVersion
////  "org.apache.spark" %% "spark-core" % sparkVersion,
////  "org.apache.spark" %% "spark-streaming" % sparkVersion,
  "org.apache.spark" %% "spark-sql" % sparkVersion
)


