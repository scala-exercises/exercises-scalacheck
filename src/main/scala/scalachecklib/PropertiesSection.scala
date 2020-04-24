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

import org.scalatest.matchers.should.Matchers
import org.scalatestplus.scalacheck.Checkers

/** A ''property'' is the testable unit in ScalaCheck, and is represented by the `org.scalacheck.Prop` class.
 * There are several ways to create properties in ScalaCheck, one of them is to use the `org.scalacheck.Prop.forAll`
 * method like in the example below.
 *
 * {{{
 * scala> val propConcatLists = forAll { (l1: List[Int], l2: List[Int]) =>
 *   l1.size + l2.size == (l1 ::: l2).size }
 * }}}
 *
 *
 * We can use the `check` method to test it:
 *
 * {{{
 * scala> propConcatLists.check
 * + OK, passed 100 tests.
 * }}}
 *
 *
 * OK, that seemed alright. Now, we'll define another property.
 *
 * {{{
 * scala> val propSqrt = forAll { (n: Int) => scala.math.sqrt(n*n) == n }
 * }}}
 *
 *
 * And check it:
 *
 * {{{
 * scala> propSqrt.check
 * ! Falsified after 2 passed tests.
 * > ARG_0: -1
 * > ARG_0_ORIGINAL: -488187735
 * }}}
 *
 * Not surprisingly, the property doesn't hold. The argument `-1` falsifies it. You can also see that the argument
 * `-488187735` falsifies the property. That was the first argument ScalaCheck found, and it was then
 * [[https://github.com/rickynils/scalacheck/blob/master/doc/UserGuide.md#test-case-minimisation simplified]] to
 * `-1`.
 *
 *
 * The `forAll` method creates universally quantified properties directly, but it is also
 * possible to create new properties by combining other properties, or to use any of the specialised
 * methods in the `org.scalacheck.Prop` object.
 *
 * @param name properties
 */
object PropertiesSection
    extends Checkers
    with Matchers
    with org.scalaexercises.definitions.Section {

  /** ==Universally quantified properties==
   *
   * As mentioned before, `org.scalacheck.Prop.forAll` creates universally quantified properties.
   * `forAll` takes a function as parameter, and creates a property out of it that can be tested with the `check`
   * method or with Scalatest, like in these examples.
   *
   *
   * The function passed to `forAll` should return `Boolean` or another property, and can take parameters of any types,
   * as long as there exist implicit `Arbitrary` instances for the types.
   * By default, ScalaCheck has instances for common types like `Int`, `String`, `List`, etc, but it is also possible
   * to define your own `Arbitrary` instances.
   *
   * For example:
   */
  def universallyQuantifiedPropertiesString(res0: Boolean) = {

    import org.scalacheck.Prop.forAll

    check {
      forAll((s1: String, s2: String) => (s1 + s2).endsWith(s2) == res0)
    }

  }

  /** When you run `check` on the properties, ScalaCheck generates random instances of the function parameters and
   * evaluates the results, reporting any failing cases.
   *
   * You can also give `forAll` a specific data generator. In the following example `smallInteger` defines a generator
   * that generates integers between `0` and `100`, inclusively.
   *
   * This way of using the `forAll` method is good to use when you want to control the data generation by specifying
   * exactly which generator that should be used, and not rely on a default generator for the given type.
   */
  def universallyQuantifiedPropertiesGen(res0: Boolean) = {

    import org.scalacheck.Gen
    import org.scalacheck.Prop.forAll

    val smallInteger = Gen.choose(0, 100)

    check {
      forAll(smallInteger)(n => (n >= 0 && n <= 100) == res0)
    }

  }

  /** ==Conditional properties==
   *
   * Sometimes, a specification takes the form of an implication. In ScalaCheck, you can use the implication
   * operator `==>` to filter the generated values.
   *
   * If the implication operator is given a condition that is hard or impossible to fulfill, ScalaCheck might
   * not find enough passing test cases to state that the property holds. In the following trivial example,
   * all cases where `n` is non-zero will be thrown away:
   *
   * {{{
   * scala> import org.scalacheck.Prop.{forAll, propBoolean}
   * scala> val propTrivial = forAll { n: Int =>
   *      |  (n == 0) ==> n + 10 == 10
   *      | }
   *
   * scala> propTrivial.check
   * ! Gave up after only 4 passed tests. 500 tests were discarded.
   * }}}
   *
   * It is possible to tell ScalaCheck to try harder when it generates test cases, but generally you should
   * try to refactor your property specification instead of generating more test cases, if you get this scenario.
   *
   * Using implications, we realise that a property might not just pass or fail, it could also be undecided if
   * the implication condition doesn't get fulfilled.
   *
   * In this example, ScalaCheck will only care for the cases when `n` is an even number.
   */
  def conditionalProperties(res0: Int) = {

    import org.scalacheck.Prop.{forAll, propBoolean}

    check {
      forAll { n: Int => (n % 2 == 0) ==> (n % 2 == res0) }
    }

  }

  /** ==Combining Properties==
   *
   * A third way of creating properties, is to combine existing properties into new ones.
   *
   * {{{
   * val p1 = forAll(...)
   * val p2 = forAll(...)
   * val p3 = p1 && p2
   * val p4 = p1 || p2
   * val p5 = p1 == p2
   * val p6 = all(p1, p2) // same as p1 && p2
   * val p7 = atLeastOne(p1, p2) // same as p1 || p2
   * }}}
   *
   * Here, `p3` will hold if and only if both `p1` and `p2` hold, `p4` will hold if either `p1` or `p2` holds,
   * and `p5` will hold if `p1` holds exactly when `p2` holds and vice versa.
   */
  def combiningProperties(res0: Boolean, res1: Boolean) = {

    import org.scalacheck.Gen
    import org.scalacheck.Prop.forAll

    val smallInteger = Gen.choose(0, 100)

    check {
      forAll(smallInteger) { n =>
        (n > 100) == res0
      } &&
      forAll(smallInteger)(n => (n >= 0) == res1)
    }

  }

  /** ==Grouping Properties==
   *
   * Often you want to specify several related properties, perhaps for all methods in a class.
   * ScalaCheck provides a simple way of doing this, through the `Properties` trait.
   * Look at the following specifications that define some properties for zero.
   *
   * You can use the check method of the `Properties` class to check all specified properties,
   * just like for simple `Prop` instances. In fact, `Properties` is a subtype of `Prop`,
   * so you can use it just as if it was a single property.
   *
   * That single property holds if and only if all of the contained properties hold.
   *
   */
  def groupingProperties(res0: Int, res1: Int, res2: Int) = {
    import org.scalacheck.{Prop, Properties}

    class ZeroSpecification extends Properties("Zero") {

      import org.scalacheck.Prop.{forAll, propBoolean}

      property("addition property") = forAll { n: Int => (n != 0) ==> (n + res0 == n) }

      property("additive inverse property") = forAll { n: Int => (n != 0) ==> (n + (-n) == res1) }

      property("multiplication property") = forAll { n: Int => (n != 0) ==> (n * res2 == 0) }

    }

    check(Prop.all(new ZeroSpecification().properties.to(List).map(_._2): _*))
  }
}
