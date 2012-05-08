package com.owtelse
import com.nerdintheherd.parser.CommandLineParser;

/**
 * Created by IntelliJ IDEA.
 * User: robertk
 */

object MyMain extends CommandLineParser{
  
  def main(args:Array[String]) {
	val parseString :String = "release -pprojectURI1, projectURI2 these aGroupId:AArtifactId, anotherArtifactId -faGroupId, bGroupId"
	
    System.out.println("Starting parser")
    System.out.println(parseAll(release,parseString))
    
  }   
 
}