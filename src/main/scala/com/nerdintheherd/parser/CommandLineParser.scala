package com.nerdintheherd.parser

import scala.util.parsing.combinator._;

/**
 * @author Karen Davis
 * 
 * First attempt - not doing a whole lot :)
 */
class CommandLineParser extends JavaTokenParsers {
  
  def release: Parser[Any]="release"~projects~opt(dependencies)~filters;
  def projects: Parser[Any]="-p"~>repsep(projectURI, ",");
  def dependencies: Parser[Any] =  allDependencies | theseDependencies;
  def allDependencies : Parser[Any] = "all";
  def theseDependencies: Parser[Any] = "these"~>repsep(namedDependencies, "," );
  def namedDependencies: Parser[Any] = groupId~":"~artifactId | artifactId;
  def filters : Parser[Any] = "-i"~>repsep(groupId, ",");
  def projectURI: Parser[Any]=stringLiteral;
  def groupId: Parser[Any]=stringLiteral;
  def artifactId: Parser[String] = stringLiteral;
  
  

}
