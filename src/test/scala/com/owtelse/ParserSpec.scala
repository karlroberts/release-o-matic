package com.owtelse

import org.specs2.Specification
import com.nerdintheherd.parser.CommandLineParser

/**
 *  @author Karl Roberts
 */
class ParserSpec extends Specification  {
  def is = "This is a specification to check the CommandLineParser, starting at lowest combinator" ^ p ^
    "The 'CommandLineParser' should" ^
    "parse artifactId" ! e1
    end

  def e1 = pTester.artifactIDParse

  def e2 = "Hello world" must startWith("Hello")

  def e3 = "Hello world" must endWith("world")

  object pTester extends CommandLineParser {

    def artifactIDParse = {
      val p: ParseResult[String] = parseAll(artifactId,"anArtifactID");
      val OK = p match {
        case x: Success[_] => {
          println("parser toString ---> " + p)
          println("parsed ---> " + p)
          val r = p.getOrElse("oops");
          println("parsed value ---> " + r)
          r == "anArtifactID"
        }
        case _ => {
          println("parser toString ---> " + p)
          false
        }
      }
      OK should_== true    }

  }
}
