package androidParceling.test

import androidParceling.Parceler

import org.junit.Test
import org.robolectric.annotation.Config
import org.scalacheck._, Gen._, Arbitrary.arbitrary

import android.os.Parcel

@Config(manifest=Config.NONE)
class CompositionTest extends UnitSpec {
  import CompositionTest._

  @Test
  def composingSimpleCaseClasses =
    testRoundTrip[Foo]

  @Test
  def composingNestedCaseClasses =
    testRoundTrip[Bar]

  implicit val genFoo = Arbitrary(for {
    x <- arbitrary[Int]
    y <- arbitrary[Int]
  } yield Foo(x, y))

  implicit val genBar = Arbitrary(for {
    foo <- arbitrary[Foo]
    s <- arbitrary[String]
    d <- arbitrary[Double]
  } yield Bar(foo, s, d))
}

object CompositionTest {
  case class Foo(x: Int, y: Int)
  case class Bar(foo: Foo, s: String, d: Double)

  object Foo {
    implicit val parceler = Parceler[Foo]
  }

  object Bar {
    implicit val parceler = Parceler[Bar]
  }
}
