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

class PropertiesSpec extends AnyFunSuite with Checkers {

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
