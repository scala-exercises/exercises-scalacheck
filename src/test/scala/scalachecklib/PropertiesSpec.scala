package scalachecklib

import org.scalacheck.Shapeless._
import org.scalaexercises.Test
import org.scalatest.Spec
import org.scalatest.prop.Checkers
import shapeless.HNil


class PropertiesSpec extends Spec with Checkers {

  def `always ends with the second string` = {

    check(
      Test.testSuccess(
        PropertiesSection.universallyQuantifiedPropertiesString _,
        true :: HNil
      )
    )
  }

  def `all numbers are generated between the desired interval` = {

    check(
      Test.testSuccess(
        PropertiesSection.universallyQuantifiedPropertiesGen _,
        true :: HNil
      )
    )
  }

  def `all generated numbers are even` = {

    check(
      Test.testSuccess(
        PropertiesSection.conditionalProperties _,
        0 :: HNil
      )
    )
  }

  def `only the second condition is true` = {

    check(
      Test.testSuccess(
        PropertiesSection.combiningProperties _,
        false :: true :: HNil
      )
    )
  }

  def `the zero specification only works for 0` = {

    check(
      Test.testSuccess(
        PropertiesSection.groupingProperties _,
        0 :: HNil
      )
    )
  }

}
