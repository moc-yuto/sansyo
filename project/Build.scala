import sbt._
import Keys._

object build {
  lazy val scalaTest = "org.scalatest" %% "scalatest" % "3.0.1"

  private[this] val Scala211 = "2.11.8"

  lazy val commonSettings =
  Seq(
    organization := "com.github.moc-yuto",
    version      := "0.1.0",
    scalaVersion := Scala211,
    crossScalaVersions := "2.10.5" :: Scala211 :: "2.12.1" :: Nil,
    name := "sansyo",
    libraryDependencies ++= Seq(
      scalaTest
    ),
    TaskKey[Unit]("checkScalariform") := {
      val diff = "git diff".!!
      if(diff.nonEmpty){
        sys.error("Working directory is dirty!\n" + diff)
      }
    }
  ) ++ appPublishSettings

  lazy val appPublishSettings = Seq(
    publishMavenStyle := true,
    publishTo := {
      val nexus = "https://oss.sonatype.org/"
      if (isSnapshot.value)
        Some("snapshots" at nexus + "content/repositories/snapshots")
      else
        Some("releases"  at nexus + "service/local/staging/deploy/maven2")
    },
    publishArtifact in Test := false,
    pomIncludeRepository := { _ => false },
    pomExtra := (
      <url>https://github.com/moc-yuto/sansyo</url>
        <licenses>
          <license>
            <name>Apache License, Version 2.0</name>
            <url>http://www.apache.org/licenses/LICENSE-2.0</url>
            <distribution>repo</distribution>
          </license>
        </licenses>
        <scm>
          <url>git@github.com:moc-yuto/sansyo.git</url>
          <connection>scm:git:git@github.com:moc-yuto/sansyo.git</connection>
        </scm>
        <developers>
          <developer>
            <id>moc-yuto</id>
            <name>Yuto Suzuki</name>
            <url>http://github.com/moc-yuto</url>
          </developer>
        </developers>)
  )

}
