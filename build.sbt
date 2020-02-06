import ProjectPlugin.autoImport._

val scalaExercisesV = "0.6.0-SNAPSHOT"

def dep(artifactId: String) = "org.scala-exercises" %% artifactId % scalaExercisesV

lazy val scalacheck = (project in file("."))
  .enablePlugins(ExerciseCompilerPlugin)
  .settings(
    name := "exercises-scalacheck",
    libraryDependencies ++= Seq(
      dep("exercise-compiler"),
      dep("definitions"),
      %%("scalatest", V.scalatest),
      %%("scalacheck", V.scalacheck),
      %%("shapeless", V.shapeless),
      "joda-time" % "joda-time" % V.jodaTime,
      "com.github.alexarchambault" %% "scalacheck-shapeless_1.14"   % V.scalacheckShapeless,
      "com.47deg"                  %% "scalacheck-toolbox-datetime" % V.scalacheckDatetime,
      "org.scalatestplus"          %% "scalatestplus-scalacheck"    % V.scalatestplusScheck
    )
  )

// Distribution

pgpPassphrase := Some(getEnvVar("PGP_PASSPHRASE").getOrElse("").toCharArray)
pgpPublicRing := file(s"$gpgFolder/pubring.gpg")
pgpSecretRing := file(s"$gpgFolder/secring.gpg")
