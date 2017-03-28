/*
 * scala-exercises - exercises-scalacheck
 * Copyright (C) 2015-2016 47 Degrees, LLC. <http://www.47deg.com>
 */

package scalachecklib

import org.scalacheck.Shapeless._
import org.scalatest.FunSuite
import org.scalatest.prop.Checkers
import shapeless.HNil

class PropertiesSpec extends FunSuite with Checkers {

  test("always ends with the second string") {

    check(
      Test.testSuccess(
        PropertiesSection.universallyQuantifiedPropertiesString _,
        true :: HNil
      )
    )
  }

  test("all numbers are generated between the desired interval") {

    check(
      Test.testSuccess(
        PropertiesSection.universallyQuantifiedPropertiesGen _,
        true :: HNil
      )
    )
  }

  test("all generated numbers are even") {

    check(
      Test.testSuccess(
        PropertiesSection.conditionalProperties _,
        0 :: HNil
      )
    )
  }

  test("only the second condition is true") {

    check(
      Test.testSuccess(
        PropertiesSection.combiningProperties _,
        false :: true :: HNil
      )
    )
  }

  test("the zero specification only works for 0") {

    check(
      Test.testSuccess(
        PropertiesSection.groupingProperties _,
        0 :: 0 :: 0 :: HNil
      )
    )
  }

}
