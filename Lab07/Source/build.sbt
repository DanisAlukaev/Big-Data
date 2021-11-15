name := "spark-twitter"

version := "1.0"

scalaVersion := "2.12.10"
val sparkVersion = "3.0.3"


libraryDependencies += "org.apache.spark" %% "spark-core" % sparkVersion
// https://mvnrepository.com/artifact/org.apache.spark/spark-mllib
libraryDependencies += "org.apache.spark" %% "spark-mllib" % "3.0.3" % "provided"

