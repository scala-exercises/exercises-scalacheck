package scalachecklib

/** ScalaCheck is a tool for testing Scala and Java programs, based on property specifications and automatic test data generation.
  *
  * @param name scalacheck
  */
object ScalacheckLibrary extends org.scalaexercises.definitions.Library {
  override def owner = "scala-exercises"
  override def repository = "exercises-scalacheck"

  override def color = Some("#EBC477")

  override def sections = List(
    PropertiesSection,
    GeneratorsSection
  )

  override def logoPath = "scalacheck"
}
