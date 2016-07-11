package scalachecklib

import org.scalacheck.Shapeless._
import org.scalaexercises.Test
import org.scalatest.Spec
import org.scalatest.prop.Checkers
import shapeless.HNil

class GeneratorsSpec extends Spec with Checkers {

  def `for-comprehension generator` = {

    check(
      Test.testSuccess(
        GeneratorsSection.forComprehension _,
        true :: HNil
      )
    )

  }

  def `oneOf method` = {

    check(
      Test.testSuccess(
        GeneratorsSection.genOf _,
        Seq('A', 'E', 'I', 'O', 'U') :: HNil
      )
    )

  }

  def `alphaChar, posNum and listOfN` = {

    check(
      Test.testSuccess(
        GeneratorsSection.genAPI _,
        false :: true :: 10 :: HNil
      )
    )

  }

  def `suchThat condition` = {

    check(
      Test.testSuccess(
        GeneratorsSection.conditionalOperators _,
        0 :: HNil
      )
    )

  }

  def `case class generator` = {

    check(
      Test.testSuccess(
        GeneratorsSection.caseClassGenerator _,
        false :: HNil
      )
    )

  }

  def `sized generator` = {

    check(
      Test.testSuccess(
        GeneratorsSection.sizedGenerator _,
        3 :: 2 :: HNil
      )
    )

  }

  def `list container` = {

    check(
      Test.testSuccess(
        GeneratorsSection.generatingContainers _,
        List(2, 4, 6) :: HNil
      )
    )

  }

}
