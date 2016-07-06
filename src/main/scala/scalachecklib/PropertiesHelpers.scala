package scalachecklib

import org.scalacheck._

object PropertiesHelpers {

  class ZeroSpecification(value: Int) extends Properties("Zero") {

    import Prop.{BooleanOperators, forAll}

    property("addition property") = forAll { n: Int => (n != 0) ==> (n + value == n) }

    property("additive inverse property") = forAll { n: Int => (n != 0) ==> (n + (-n) == value) }

    property("multiplication property") = forAll { n: Int => (n != 0) ==> (n * value == 0) }

  }

}
