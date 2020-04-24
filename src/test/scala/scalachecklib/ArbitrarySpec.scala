/*
 * Copyright 2016-2020 47 Degrees <https://47deg.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package scalachecklib

import org.scalacheck.ScalacheckShapeless._
import org.scalaexercises.Test
import org.scalatest.funsuite.AnyFunSuite
import org.scalatestplus.scalacheck.Checkers
import shapeless.HNil

class ArbitrarySpec extends AnyFunSuite with Checkers {

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
