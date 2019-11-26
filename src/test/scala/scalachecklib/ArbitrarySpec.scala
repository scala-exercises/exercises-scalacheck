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

class ArbitrarySpec extends FunSuite with Checkers {

  test("implicit arbitrary char") {

    check(
      Test.testSuccess(
        ArbitrarySection.implicitArbitraryChar _,
        Seq('A', 'E', 'I', 'O', 'U') :: HNil
      )
    )
  }

  test("implicit arbitrary case class") {

    check(
      Test.testSuccess(
        ArbitrarySection.implicitArbitraryCaseClass _,
        false :: HNil
      )
    )
  }

  test("arbitrary on gen") {

    check(
      Test.testSuccess(
        ArbitrarySection.useArbitraryOnGen _,
        8 :: HNil
      )
    )
  }

}
