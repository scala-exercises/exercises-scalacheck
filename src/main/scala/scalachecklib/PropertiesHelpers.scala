package scalachecklib

import org.scalacheck._

object PropertiesHelpers {

  class ZeroSpecification(value: Int) extends Properties("Zero") {

    import Prop.{BooleanOperators, forAll}

    property("sum") = forAll { n: Int => (n != 0) ==> (n + value == n) }

    property("prod") = forAll { n: Int => (n != 0) ==> (n * value == 0) }

  }

}
