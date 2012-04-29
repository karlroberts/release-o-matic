package com.owtelse

import org.scalacheck._


/**
 * Created by IntelliJ IDEA.
 * User: robertk
 */

object DefaultScalaCheckTest extends Properties("String") {
  import Prop.forAll

  val propReverseList  = forAll { l: List[String] => l.reverse.reverse == l }

  val propConcatString = forAll { (s1: String, s2: String) =>
    (s1 + s2).endsWith(s2)
  }

  propReverseList.check


  val x = propConcatString.check
}

