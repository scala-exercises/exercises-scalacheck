package scalachecklib

import org.scalacheck.Shapeless._
import org.scalaexercises.Test
import org.scalatest.Spec
import org.scalatest.prop.Checkers
import shapeless.HNil

class ArbitrarySpec extends Spec with Checkers {

  def `implicit arbitrary char` = {

    check(
      Test.testSuccess(
        ArbitrarySection.implicitArbitraryChar _,
        Seq('A', 'E', 'I', 'O', 'U') :: HNil
      )
    )
  }

  def `implicit arbitrary case class` = {

    check(
      Test.testSuccess(
        ArbitrarySection.implicitArbitraryCaseClass _,
        false :: HNil
      )
    )
  }

  def `arbitrary on gen` = {

    check(
      Test.testSuccess(
        ArbitrarySection.useArbitraryOnGen _,
        8 :: HNil
      )
    )
  }

}
