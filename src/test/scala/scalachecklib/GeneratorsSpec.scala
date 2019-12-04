/*
 *  scala-exercises - exercises-scalacheck
 *  Copyright (C) 2015-2019 47 Degrees, LLC. <http://www.47deg.com>
 *
 */

package scalachecklib

import org.scalacheck.ScalacheckShapeless._
import org.scalaexercises.Test
import org.scalatest.funsuite.AnyFunSuite
import org.scalatestplus.scalacheck.Checkers
import shapeless.HNil

class GeneratorsSpec extends AnyFunSuite with Checkers {

  test("for-comprehension generator") {

    check(
      Test.testSuccess(
        GeneratorsSection.forComprehension _,
        true :: HNil
      )
    )

  }

  test("oneOf method") {

    check(
      Test.testSuccess(
        GeneratorsSection.genOf _,
        Seq('A', 'E', 'I', 'O', 'U') :: HNil
      )
    )

  }

  test("alphaChar, posNum and listOfN") {

    check(
      Test.testSuccess(
        GeneratorsSection.genAPI _,
        false :: true :: 10 :: HNil
      )
    )

  }

  test("suchThat condition") {

    check(
      Test.testSuccess(
        GeneratorsSection.conditionalOperators _,
        0 :: HNil
      )
    )

  }

  test("case class generator") {

    check(
      Test.testSuccess(
        GeneratorsSection.caseClassGenerator _,
        false :: HNil
      )
    )

  }

  test("sized generator") {

    check(
      Test.testSuccess(
        GeneratorsSection.sizedGenerator _,
        3 :: 2 :: HNil
      )
    )

  }

  test("list container") {

    check(
      Test.testSuccess(
        GeneratorsSection.generatingContainers _,
        List(2, 4, 6) :: HNil
      )
    )

  }

}
