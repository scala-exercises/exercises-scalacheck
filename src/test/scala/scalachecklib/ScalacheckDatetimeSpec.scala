/*
 *  scala-exercises - exercises-scalacheck
 *  Copyright (C) 2015-2019 47 Degrees, LLC. <http://www.47deg.com>
 *
 */

package scalachecklib

import org.scalacheck.ScalacheckShapeless._
import org.scalaexercises.Test
import org.scalatest.FunSuite
import org.scalatestplus.scalacheck.Checkers
import shapeless.HNil

class ScalacheckDatetimeSpec extends FunSuite with Checkers {

  test("simple usage") {

    check(
      Test.testSuccess(
        ScalacheckDatetimeSection.usage _,
        true :: HNil
      )
    )
  }

  test("granularity") {

    check(
      Test.testSuccess(
        ScalacheckDatetimeSection.granularity _,
        1 :: 0 :: 0 :: 0 :: 0 :: HNil
      )
    )
  }

  test("ranges") {

    check(
      Test.testSuccess(
        ScalacheckDatetimeSection.ranges _,
        2016 :: HNil
      )
    )
  }

  test("granularity and ranges together") {

    check(
      Test.testSuccess(
        ScalacheckDatetimeSection.granularityAndRanges _,
        2016 :: 0 :: 0 :: 0 :: 0 :: HNil
      )
    )
  }

}
