name := "CP2324"
version := "1.0"
scalaVersion := "2.12.18"

resolvers ++= Seq(
        "Sonatype OSS Snapshots" at
        "https://oss.sonatype.org/content/repositories/snapshots",
        "Sonatype OSS Releases" at
        "https://oss.sonatype.org/content/repositories/releases",
        "Typesafe Repository" at
        "https://repo.typesafe.com/typesafe/releases/"
        )
libraryDependencies ++= Seq(
        "commons-io" % "commons-io" % "2.4"
            )
