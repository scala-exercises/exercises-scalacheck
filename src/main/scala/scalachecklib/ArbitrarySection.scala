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

/** ==The `arbitrary` Generator==
 *
 * There is a special generator, `org.scalacheck.Arbitrary.arbitrary`, which generates arbitrary values of any
 * supported type.
 *
 * {{{
 * val evenInteger = Arbitrary.arbitrary[Int] suchThat (_ % 2 == 0)
 * val squares = for {
 *   xs <- Arbitrary.arbitrary[List[Int]]
 * } yield xs.map(x => x*x)
 * }}}
 *
 * The `arbitrary` generator is the generator used by ScalaCheck when it generates values for ''property'' parameters.
 * Most of the times, you have to supply the type of the value to `arbitrary`, like above, since Scala often can't
 * infer the type automatically. You can use `arbitrary` for any type that has an implicit `Arbitrary` instance.
 * As mentioned earlier, ScalaCheck has default support for common types, but it is also possible to define your own
 * implicit `Arbitrary` instances for unsupported types. See the following implicit Arbitrary definition for booleans,
 * that comes from the ScalaCheck implementation.
 *
 * {{{
 * implicit lazy val arbBool: Arbitrary[Boolean] = Arbitrary(oneOf(true, false))
 * }}}
 *
 * @param name arbitrary
 */
object ArbitrarySection extends Checkers with Matchers with org.scalaexercises.definitions.Section {

  import GeneratorsHelper._

  /** Let's see an example where we're defining an `implicit` `arbitrary` instance for `Char`
   */
  def implicitArbitraryChar(res0: Seq[Char]) = {

    import org.scalacheck.Arbitrary
    import org.scalacheck.Gen
    import org.scalacheck.Prop.forAll

    implicit lazy val myCharArbitrary = Arbitrary(Gen.oneOf('A', 'E', 'I', 'O', 'U'))

    val validChars: Seq[Char] = res0

    check(forAll { c: Char => validChars.contains(c) })
  }

  /** This becomes more useful when we're dealing with our own data types.
   * We'll use the case class defined in the ''Generators Section'':
   *
   * {{{
   * case class Foo(intValue: Int, charValue: Char)
   * }}}
   *
   * Having an implicit `def` or `val` of our data type in the scope allow us to use the `forAll` method without
   * specifying the ''generator''
   */
  def implicitArbitraryCaseClass(res0: Boolean) = {

    import org.scalacheck.Arbitrary
    import org.scalacheck.Gen
    import org.scalacheck.Prop.forAll

    val fooGen = for {
      intValue  <- Gen.posNum[Int]
      charValue <- Gen.alphaChar
    } yield Foo(intValue, charValue)

    implicit lazy val myFooArbitrary = Arbitrary(fooGen)

    check(forAll { foo: Foo => (foo.intValue < 0) == res0 && !foo.charValue.isDigit })
  }

  /** The `Arbitrary.arbitrary` method also returns a `Gen` object.
   */
  def useArbitraryOnGen(res0: Int) = {

    import org.scalacheck.Arbitrary
    import org.scalacheck.Gen.listOfN
    import org.scalacheck.Prop.forAll

    val genEightBytes = listOfN(8, Arbitrary.arbitrary[Byte])

    check(forAll(genEightBytes)(list => list.size == res0))

  }

}
