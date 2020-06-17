/*
 * Copyright 2016-2020 47 Degrees Open Source <https://www.47deg.com>
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
