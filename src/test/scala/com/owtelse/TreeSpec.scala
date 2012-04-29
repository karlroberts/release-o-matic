package com.owtelse

import org.specs2.{ScalaCheck, Specification}
import org.specs2.matcher.ThrownExpectations
import org.specs2.execute.StandardResults._
import scalaz.TreeLoc
import org.scalacheck.Arbitrary._
//import scalaz.{Scalaz, TreeLoc, Tree}

/**
 * Created by IntelliJ IDEA.
 * User: robertk
 */

class TreeSpec extends Specification with ScalaCheck with ThrownExpectations {
  def is =
    "A tree" ^
      "Must belong to the Functor Typclass" ! treeIsAFunctor ^
      "Must belong to the Applicative Typclass" ! treeIsApplicative ^
      "Must obey the applicative Laws:- 1st law :- fmap id = id" ! treeApplicativeLaws ^
      "List Must belong to the Applicative Typclass" ! listIsApplicative ^
      end

  def treeIsAFunctor = trees.treeIsAFunctor

  def treeIsApplicative = trees.treeIsApplicative

  def treeApplicativeLaws = trees.treeApplicativeLaws

  def listIsApplicative = trees.listIsApplicative

  object trees {

    import scalaz._
    import Scalaz._


    val t1: Tree[Int] = 1.leaf
    val t11: Tree[Int] = 11.leaf
    val texpect11: Tree[Int] = t1 ∘ (10 +)

    val add10Applic: List[(Int) => Int] = List(10) ∘ ((_: Int) + (_: Int)).curried
    val plus2 = (a: Int) => (b: Int) => a + b


    val add10Applied = List(20) <*> add10Applic

    val add10AppliedToBiggerList = List(20, 30) <*> add10Applic
    val listApplic1: List[Int] = ((List(40, 50, 60) <*> (List(1, 2, 3) ∘ ((_: Int) * (_: Int)).curried)))

    val liftedFuncPlusApplied = List(10).<**>(List(20))(Function.uncurried(plus2))

    val liftedFuncPlusAppliedToTree1 = leaf(10).<**>(leaf(20))(Function.uncurried(plus2))


    val f = (i: Int) => i.toString
    val g = plus2

    val mapPlus4 = 12.leaf ∘ g(4)
    val applyPlus = 12.leaf <*> (4.leaf ∘ g)

    val af = leaf(f)
    val ag = leaf(g)
    // val c = f compose (_:Int)


    def equal[A: Equal](b: => A) =
      (a: A) => (implicitly[Equal[A]].equal(a, b), a + " is not equal to " + b)

    def treeIsAFunctor = {
      println("-->>t11  " + t11.shows)
      println("-->>texpect11 = " + texpect11.shows)
      t11 must equal(texpect11)
    }

    def treeIsApplicative = {
      println("---->>mapPlus4 = " + mapPlus4.shows)
      println("---->>applyPlus = " + applyPlus.shows)
      println("---->>liftedFuncPlusAppliedToTree1  " + liftedFuncPlusAppliedToTree1.shows)
      (liftedFuncPlusAppliedToTree1 must equal(leaf(30)))

    }

    /**
     * <li><strong>identity</strong><br/><code>fmap id a=== id a  ie forall a. a == apply(a, pure(identity))</code></li>
     * <li><strong>composition</strong><br/><code>fmap (f . g) = fmap f . fmap g  ie forall af ag a. apply(apply(a, ag), af) == apply(a, apply(ag, apply(af, pure(compose))))</code></li>
     * <li><strong>homomorphism</strong><br/><code>forall f a. apply(pure(a), pure(f)) == pure(f(a))</code></li>
     * <li><strong>interchange</strong><br/><code>forall af a. apply(pure(a), af) == apply(af, pure(f => f(x)))</code></li>
     * @return
     */
    def treeApplicativeLaws = {
      import scalaz.scalacheck.ScalazArbitrary._
      println("-->> 12.leaf ∘ (f compose  g(4))  " + (12.leaf ∘ (f compose g(4))).drawTree)
      // println("-->> af " +af.drawTree)
      todo
      // check { (a: Tree[Char]) => pure(identity)) <*> a must ( equal(a))  }
      // check { (a: Tree[Char]) =>  a ∘ identity  must ( equal(a))  } and
      // check { (a: Tree[Int]) =>  a ∘ identity  must ( equal(a))  } //and
      // check { (a: Tree[Int]) =>  a ∘ identity  must ( equal(a))  } //and
      //check { (a: Tree[Int], x: Int) =>  a ∘ (f compose  g(x))  must ( equal(a <*> pure(compose))  }

    }


    def listIsApplicative = {
      println("-->listApplic1  " + listApplic1.shows)
      println("-->add10Applic  " + add10Applic.shows)
      println("-->add10AppliedToBiggerList  " + add10AppliedToBiggerList.shows)
      println("-->liftedFuncAdd10Applied  " + liftedFuncPlusApplied.shows)
      (add10Applied must equal(List(30))) and
        (add10AppliedToBiggerList must equal(List(30, 40))) and
        (listApplic1 must equal(List(40, 50, 60, 80, 100, 120, 120, 150, 180))) and
        (liftedFuncPlusApplied must equal(List(30)))

    }
  }

}

/**
 * Borrowed from http://scalaz.github.com/scalaz/scalaz-2.9.1-6.0.4/doc.sxr/scalaz/example/ExampleTree.scala.html
 * So I can understand what I'm doing.
 */
object ExampleTree {
  def main(args: Array[String]) = run
  import scalaz._
  import Scalaz._


  def run {
    val tree: Tree[Int] =
      1.node(
        2.leaf,
        3.node(
          4.leaf))

    // Tree is a Pointed Functor...
    1.η[Tree] assert_=== 1.leaf
    tree ∘ (1 +) assert_=== 2.node(3.leaf, 4.node(5.leaf))

    // ...and a Monad
    val t2 = tree ∗ (x => (x == 2) ? x.leaf | x.node((-x).leaf))
    t2 assert_=== 1.node((-1).leaf, 2.leaf, 3.node((-3).leaf, 4.node((-4).leaf)))

    // ...and Traversable
    tree.collapse assert_=== 10

    // ...and Foldable
    tree.foldMap(_.toString) assert_=== "1234"

    // A tree of TreeLocs (aka Zipper). Each TreeLoc is rooted at `tree` but focussed on a different node.
    val allTreeLocs: Tree[TreeLoc[Int]] = tree.loc.cojoin.toTree
    // Getting the label of the focussed node from each TreeLoc restores the original tree
    allTreeLocs.map(_.getLabel) assert_=== tree
    // Alternatively, we can get the path to root from each node
    allTreeLocs.map(_.path).drawTree.println

    // And finally wrap this up as a function:
    leafPaths(tree).toList.map(_.toList.reverse) assert_=== List(List(1, 2), List(1, 3, 4))
  }

  /**
   * Returns the paths from each leaf node back to the root node.
   */
  def leafPaths[T](tree: Tree[T]): Stream[Stream[T]]
  = tree.loc.cojoin.toTree.flatten.filter(_.isLeaf).map(_.path)
}


object treeGenerator {
  import scalaz.{Tree}
  import scalaz.scalacheck.ScalazArbitrary._

  def genTree = arbitrary[Tree[Char]]
}
