/*
 *  scala-exercises - exercises-scalacheck
 *  Copyright (C) 2015-2019 47 Degrees, LLC. <http://www.47deg.com>
 *
 */

package scalachecklib

import org.scalatest.matchers.should.Matchers
import org.scalatestplus.scalacheck.Checkers

/** Generators are responsible for generating test data in ScalaCheck, and are represented by the `org.scalacheck.Gen`
 * class. You need to know how to use this class if you want ScalaCheck to generate data of types that are not supported
 * by default, or if you want to use the `forAll` method mentioned above, to state properties about a specific subset of
 * a type. In the `Gen` object, there are several methods for creating new and modifying existing generators.
 * We will show how to use some of them in this section. For a more complete reference of what is available,
 * please see the API scaladoc.
 *
 *
 * A generator can be seen simply as a function that takes some generation parameters, and (maybe) returns a
 * generated value. That is, the type `Gen[T]` may be thought of as a function of type `Gen.Params => Option[T]`.
 * However, the `Gen` class contains additional methods to make it possible to map generators, use them in
 * for-comprehensions and so on. Conceptually, though, you should think of generators simply as functions, and the
 * combinators in the `Gen` object can be used to create or modify the behaviour of such generator functions.
 *
 * @param name generators
 */
object GeneratorsSection
    extends Checkers
    with Matchers
    with org.scalaexercises.definitions.Section {

  import GeneratorsHelper._

  /** Let's see how to create a new generator. The best way to do it is to use the generator combinators that exist
   * in the `org.scalacheck.Gen` module. These can be combined using a for-comprehension. Suppose you need a generator
   * which generates a tuple that contains two random integer values, one of them being at least twice as big as the
   * other. The following definition does this:
   */
  def forComprehension(res0: Boolean) = {

    import org.scalacheck.Gen
    import org.scalacheck.Prop.forAll

    val myGen = for {
      n <- Gen.choose(10, 20)
      m <- Gen.choose(2 * n, 500)
    } yield (n, m)

    check {
      forAll(myGen) {
        case (n, m) => (m >= 2 * n) == res0
      }
    }

  }

  /** You can create generators that pick one value out of a selection of values.
   * The `oneOf` method creates a generator that randomly picks one of its parameters each time it generates a value.
   * Notice that plain values are implicitly converted to generators (which always generate that value) if needed.
   *
   *
   * The following generator generates a vowel:
   */
  def genOf(res0: Seq[Char]) = {

    import org.scalacheck.Gen
    import org.scalacheck.Prop.forAll

    val vowel = Gen.oneOf('A', 'E', 'I', 'O', 'U')

    val validChars: Seq[Char] = res0

    check {
      forAll(vowel) { v =>
        validChars.contains(v)
      }
    }
  }

  /** The distribution is uniform, but if you want to control it you can use the frequency combinator:
   *
   * {{{
   * val vowel = Gen.frequency(
   *   (3, 'A'),
   *   (4, 'E'),
   *   (2, 'I'),
   *   (3, 'O'),
   *   (1, 'U')
   * )
   * }}}
   *
   * Now, the vowel generator will generate ''E:s'' more often than ''U:s''. Roughly, 4/14 of the values generated
   * will be ''E:s'', and 1/14 of them will be ''U:s''.
   *
   * Other methods in the `Gen` API:
   * {{{
   * def alphaChar: Gen[Char]
   *
   * def alphaStr: Gen[String]
   *
   * def posNum[T](implicit n: Numeric[T]): Gen[T]
   *
   * def listOf[T](g: Gen[T]): Gen[List[T]]
   *
   * def listOfN[T](n: Int, g: Gen[T]): Gen[List[T]]
   * }}}
   */
  def genAPI(res0: Boolean, res1: Boolean, res2: Int) = {

    import org.scalacheck.Gen.{alphaChar, listOfN, posNum}
    import org.scalacheck.Prop.forAll

    check {
      forAll(alphaChar)(_.isDigit == res0)
    }

    check {
      forAll(posNum[Int])(n => (n > 0) == res1)
    }

    check {
      forAll(listOfN(10, posNum[Int])) { list =>
        !list.exists(_ < 0) && list.length == res2
      }
    }
  }

  /** ==Conditional Generators==
   *
   * Conditional generators can be defined using `Gen.suchThat`.
   *
   * Conditional generators works just like conditional properties, in the sense that if the condition is too hard,
   * ScalaCheck might not be able to generate enough values, and it might report a property test as undecided.
   * The `smallEvenInteger` definition is probably OK, since it will only throw away half of the generated numbers,
   * but one has to be careful when using the `suchThat` operator.
   */
  def conditionalOperators(res0: Int) = {

    import org.scalacheck.Gen
    import org.scalacheck.Prop.forAll

    val smallEvenInteger = Gen.choose(0, 200) suchThat (_ % 2 == 0)

    check {
      forAll(smallEvenInteger)(_ % 2 == res0)
    }
  }

  /** ==Case class Generators==
   *
   * On the basis of the above we can create a generator for the following case class:
   *
   * {{{
   * case class Foo(intValue: Int, charValue: Char)
   * }}}
   */
  def caseClassGenerator(res0: Boolean) = {

    import org.scalacheck.Gen
    import org.scalacheck.Prop.forAll

    val fooGen = for {
      intValue  <- Gen.posNum[Int]
      charValue <- Gen.alphaChar
    } yield Foo(intValue, charValue)

    check {
      forAll(fooGen) { foo =>
        foo.intValue > 0 && foo.charValue.isDigit == res0
      }
    }
  }

  /** ==Sized Generators==
   *
   * When ScalaCheck uses a generator to generate a value, it feeds it with some parameters. One of the parameters
   * the generator is given is a size value, which some generators use to generate their values.
   *
   * If you want to use the size parameter in your own generator, you can use the `Gen.sized` method:
   *
   * {{{
   * def sized[T](f: Int => Gen[T])
   * }}}
   *
   * In this example we're creating a generator that produces two lists of numbers where 1/3 are positive and 2/3 are
   * negative. ''Note: we're also returning the original size to verify the behaviour.''
   */
  def sizedGenerator(res0: Int, res1: Int) = {

    import org.scalacheck.Gen
    import org.scalacheck.Prop.forAll

    val myGen = Gen.sized { size =>
      val positiveNumbers = size / 3
      val negativeNumbers = size * 2 / 3
      for {
        posNumList <- Gen.listOfN(positiveNumbers, Gen.posNum[Int])
        negNumList <- Gen.listOfN(negativeNumbers, Gen.posNum[Int] map (n => -n))
      } yield (size, posNumList, negNumList)
    }

    check {
      forAll(myGen) {
        case (genSize, posN, negN) =>
          posN.length == genSize / res0 && negN.length == genSize * res1 / 3
      }
    }
  }

  /** ==Generating Containers==
   *
   * There is a special generator, `Gen.containerOf`, that generates containers such as lists and arrays.
   * It takes another generator as argument which is responsible for generating the individual items.
   * You can use it in the following way:
   *
   * {{{
   * val genIntList      = Gen.containerOf[List,Int](Gen.oneOf(1, 3, 5))
   *
   * val genStringStream = Gen.containerOf[LazyList,String](Gen.alphaStr)
   *
   * val genBoolArray    = Gen.containerOf[Array,Boolean](true)
   * }}}
   *
   * By default, ScalaCheck supports generation of `List`, `Stream` (Scala 2.10 -
   * 2.12, deprecated in 2.13), `LazyList` (Scala 2.13), `Set`, `Array`, and
   * `ArrayList` (from `java.util`). You can add support for additional containers
   * by adding implicit `Buildable` instances. See `Buildable.scala` for examples.
   *
   * There is also `Gen.nonEmptyContainerOf` for generating non-empty containers, and `Gen.containerOfN` for
   * generating containers of a given size.
   */
  def generatingContainers(res0: List[Int]) = {

    import org.scalacheck.Gen
    import org.scalacheck.Prop.forAll

    val genIntList = Gen.containerOf[List, Int](Gen.oneOf(2, 4, 6))

    val validNumbers: List[Int] = res0

    check {
      forAll(genIntList)(_ forall (elem => validNumbers.contains(elem)))
    }
  }

}
