package com.owtelse

import org.specs2._
import matcher.ThrownExpectations
import org.scalacheck.{Gen, Arbitrary}
import Arbitrary.arbitrary
import org.scalacheck.Prop._

class HelloWorldSpec extends Specification {
  def is = "This is a specification to check the 'Hello world' string" ^ p ^
    "The 'Hello world' string should" ^
    "contain 11 characters" ! e1 ^
    "start with 'Hello'" ! e2 ^
    "end with 'world'" ! e3 ^
    end

  def e1 = "Hello world" must have size (11)

  def e2 = "Hello world" must startWith("Hello")

  def e3 = "Hello world" must endWith("world")
}


/**
 * Example test using spec2 specification style test with scalacheck 
 */
class DummySpec extends DummyTest {
  def is =
    "A Dummy:"                                                                                                          ^ p ^
      " must have a Monoid, so have a zero, ∅[Dummy]"                                                                  ! monoidzero ^
      " must combine using its monoid, a ⊹ b |+| c"                                                                    ! combineUsingMonoid ^
      " must allow a foldable to use Dummy's monoid to fold, ie"                                                        ^ p ^
      "   val x: Dummy = List(Dummy(999),Dummy(888),Dummy(777)).asMA.suml === Dummy(2664) "                             ! foldWithMonoid ^
      " must obey the Monoid Accociative Law, (a ⊹ ( b ⊹ c)) === (( a ⊹ b) ⊹ c)"                                      ! obeyMonoidAssociativeLaw ^
      " must obey Monoid id laws, ∅ ⊹ a === a ⊹ ∅  === a"                                                            ! obeyIdentityLaws ^
      " show how to use todo to create the spec before tests are defined (TDD)"                                         !showTodoUsage ^
                                                                                                                        end
}


trait DummyTest extends Specification with ScalaCheck with ThrownExpectations {
  
  
  // The tests themselves are mixed in here
  def monoidzero = dummies.monoidzero

  def combineUsingMonoid = dummies.combineUsingMonoid

  def foldWithMonoid = dummies.foldWithMonoid

  def obeyMonoidAssociativeLaw = dummies.obeyMonoidAssociativeLaw

  def obeyIdentityLaws = dummies.obeyIdentityLaws
  
  def showTodoUsage = todo

  /**
   * This inner object allows the test fixtures to be created.
   * A new encapsulation each time its called, so fixtures are not shared between tests,
   * the object scope isolates the tests.
   */
  object dummies extends DummyGen {
    import scalaz._
    import Scalaz._

    //fixtures
    val d1: Dummy = Dummy(999)
    val d2: Dummy = Dummy(888)
    val d3: Dummy = Dummy(777)
    val dcombined: Dummy = Dummy(2664)
    val l1: List[Dummy] = List(d1, d2, d3)

    //spec2 tests
    def monoidzero = d1 <+> ∅[Dummy] mustEqual d1
    def combineUsingMonoid = d1 ⊹ d2 |+| d3 mustEqual dcombined
    def foldWithMonoid = l1.asMA.sum must_== dcombined

    //this demos specs2 working with scalaCheck a,b and c are arbitrary generated Dummy's.
    // this is possible because the implicit arbDummy function is in scope in companion object
    def obeyMonoidAssociativeLaw = check { (a: Dummy, b: Dummy, c:Dummy) => (a ⊹ b) ⊹ c mustEqual ( a ⊹ (b ⊹ c)) }

    //this demos specs2 using scalacheck with a combined scalacheck property and using inferences to "filter" generated values
    // NB normally id assert this rule in same way as obeyMonoidAssociativeLaw, but this is a demo to show howto combine properties
    def obeyIdentityLaws = check { combinedProperty }


    //sclacheck property of leftID for pos Dummies ... uses inference ==>
    val propLeftID_posNums = forAll { (a: Dummy) =>
      (a.amount >= 0) ==> { ∅[Dummy] ⊹ a mustEqual a } }
    //sclacheck property of leftID for neg Dummies
    val propLeftID_negNums = forAll { (a: Dummy) =>
      (a.amount < 0) ==> { ∅[Dummy] ⊹ a mustEqual a } }
    //sclacheck property of rightID for Dummies
    val propRightID = forAll { (a: Dummy) => ∅[Dummy] ⊹ a mustEqual a  }
    //combine scalacheck properties
    val combinedProperty = propLeftID_posNums && propLeftID_negNums && propRightID

  }
}

trait DummyGen {
  //implicit function that if inscope allows :- arbitrary[Dummy] to be called to createan arbitrary Dummy
  implicit def arbDummy: Arbitrary[Dummy] = Arbitrary { genDummy }

  def genDummy: Gen[Dummy] = for {
    i <- arbitrary[Int]
  } yield Dummy(i)
}

case class Dummy(amount:Int)  {
  def <+>(other:Dummy):Dummy = Dummy(this.amount + other.amount)
}
object  Dummy {
  import scalaz._
  import Scalaz._

  //add Premium to Monoid typeclass
  implicit def dummy2Semigroup: Semigroup[Dummy] = semigroup(_ <+> _)
  implicit def dummy2Zero: Zero[Dummy] = zero(Dummy(0))
}


