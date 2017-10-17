/*
 * scala-exercises - exercises-scalacheck
 * Copyright (C) 2015-2016 47 Degrees, LLC. <http://www.47deg.com>
 */

package scalachecklib

import org.scalatest.Matchers
import org.scalatest.prop.Checkers

/** scalacheck-datetime is a library for helping use datetime libraries with ScalaCheck
 *
 * The motivation behind this library is to provide a simple, easy way to provide generated date and time instances
 * that are useful to your own domain.
 *
 * For SBT, you can add the dependency to your project’s build file:
 *
 * {{{
 * resolvers += Resolver.sonatypeRepo("releases")
 *
 * "com.fortysevendeg" %% "scalacheck-datetime" % "0.2.0" % "test"
 * }}}
 *
 * Please, visit the [[https://47deg.github.io/scalacheck-datetime homepage]] for more information
 *
 * @param name scalacheck-datetime
 */
object ScalacheckDatetimeSection
    extends Checkers
    with Matchers
    with org.scalaexercises.definitions.Section {

  /** ==Usage==
   *
   * To arbitrarily generate dates and times, you need to have the `Arbitrary` in scope for your date/time class.
   * Assuming Joda Time:
   */
  def usage(res0: Boolean) = {

    import com.fortysevendeg.scalacheck.datetime.joda.ArbitraryJoda._
    import org.joda.time.DateTime
    import org.scalacheck.Prop.forAll

    check {
      forAll { dt: DateTime =>
        (dt.getDayOfMonth >= 1 && dt.getDayOfMonth <= 31) == res0
      }
    }
  }

  /** ==A note on imports==
   *
   * For all of the examples given in this document, you can substitute `jdk8` for `joda` and vice-versa,
   * depending on which library you would like to generate instances for.
   *
   * ==Implementation==
   *
   * The infrastructure behind the generation of date/time instances for any given date/time library,
   * which may take ranges into account, is done using a fairly simple typeclass, which has the type signature
   * `ScalaCheckDateTimeInfra[D, R]`. That is to say, as long as there is an implicit `ScalaCheckDateTimeInfra`
   * instance in scope for a given date/time type `D` (such as Joda’s `DateTime`) and a range type `R`
   * (such as Joda’s `Period`), then the code will compile and be able to provide generated date/time instances.
   *
   * As stated, currently there are two instances, `ScalaCheckDateTimeInfra[DateTime, Period]` for Joda Time and
   * `ScalaCheckDateTimeInfra[ZonedDateTime, Duration]` for Java SE 8’s Date and Time.
   *
   * ==Granularity==
   *
   * If you wish to restrict the precision of the generated instances, this library refers to that as <i>granularity</i>.
   *
   * You can constrain the granularity to:
   *
   * <ul>
   *  <li>Seconds</li>
   *  <li>Minutes</li>
   *  <li>Hours</li>
   *  <li>Days</li>
   *  <li>Years</li>
   * </ul>
   *
   *  When a value is constrained, the time fields are set to zero, and the rest to the first day of the month,
   *  or day of the year. For example, if you constrain a field to be years, the generated instance will be midnight
   *  exactly, on the first day of January.
   *
   *  To constrain a generated type, you simply need to provide an import for the typeclass for your date/time and
   *  range, and also an import for the granularity. As an example, this time using Java SE 8's `java.time` package:
   */
  def granularity(res0: Int, res1: Int, res2: Int, res3: Int, res4: Int) = {

    import java.time._
    import com.fortysevendeg.scalacheck.datetime.jdk8.ArbitraryJdk8._
    import com.fortysevendeg.scalacheck.datetime.jdk8.granularity.years
    import org.scalacheck.Prop.forAll

    check {
      forAll { zdt: ZonedDateTime =>
        (zdt.getMonth == Month.JANUARY) &&
        (zdt.getDayOfMonth == res0) &&
        (zdt.getHour == res1) &&
        (zdt.getMinute == res2) &&
        (zdt.getSecond == res3) &&
        (zdt.getNano == res4)
      }
    }

  }

  /** ==Creating Ranges==
   *
   * You can generate date/time instances only within a certain range, using the `genDateTimeWithinRange` in the
   * `GenDateTime` class. The function takes two parameters, the date/time instances as a base from which to generate
   * new date/time instances, and a range for the generated instances.
   *
   * If the range is positive, it will be in the future from the base date/time, negative in the past.
   *
   * Showing this usage with Joda Time:
   */
  def ranges(res0: Int) = {

    import org.joda.time._
    import com.fortysevendeg.scalacheck.datetime.instances.joda._
    import com.fortysevendeg.scalacheck.datetime.GenDateTime.genDateTimeWithinRange
    import org.scalacheck.Prop.forAll

    val from  = new DateTime(2016, 1, 1, 0, 0)
    val range = Period.years(1)

    check {
      forAll(genDateTimeWithinRange(from, range)) { dt =>
        dt.getYear == res0
      }
    }
  }

  /** ==Using Granularity and Ranges Together==
   *
   * As you would expect, it is possible to use the granularity and range concepts together.
   * This example should not show anything surprising by now:
   */
  def granularityAndRanges(res0: Int, res1: Int, res2: Int, res3: Int, res4: Int) = {

    import org.joda.time._
    import com.fortysevendeg.scalacheck.datetime.instances.joda._
    import com.fortysevendeg.scalacheck.datetime.GenDateTime.genDateTimeWithinRange
    import com.fortysevendeg.scalacheck.datetime.joda.granularity.days
    import org.scalacheck.Prop.forAll

    val from  = new DateTime(2016, 1, 1, 0, 0)
    val range = Period.years(1)

    check {
      forAll(genDateTimeWithinRange(from, range)) { dt =>
        (dt.getYear == res0) &&
        (dt.getHourOfDay == res1) &&
        (dt.getMinuteOfHour == res2) &&
        (dt.getSecondOfMinute == res3) &&
        (dt.getMillisOfSecond == res4)
      }
    }
  }
}
