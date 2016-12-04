package scalachecklib

import org.scalacheck.Shapeless._
import org.scalatest.FunSuite
import org.scalatest.prop.Checkers
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
