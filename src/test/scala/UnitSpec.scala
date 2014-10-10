package androidParceling.test

import androidParceling.Parceler

import org.scalatest._, junit._, prop.PropertyChecks
import org.junit.runner.RunWith
import org.robolectric.{Robolectric, RobolectricTestRunner}
import org.scalacheck.Arbitrary

import android.os.Parcel

@RunWith(classOf[RobolectricTestRunner])
abstract class UnitSpec extends JUnitSuite with Matchers with PropertyChecks {
  def testRoundTrip[A: Arbitrary: Parceler] {
    forAll { (a: A) =>
      val parcel: Parcel = Parcel.obtain

      Parceler.write(parcel, a)
      parcel.setDataPosition(0)
      Parceler.read[A](parcel) should equal (a)
    }
  }
}
