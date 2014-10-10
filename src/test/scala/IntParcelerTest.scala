package androidParceling.test

import androidParceling.Parceler

import org.junit.Test
import org.robolectric.annotation.Config

import android.os.Parcel

@Config(manifest=Config.NONE)
class ParcelTest extends UnitSpec {
  @Test
  def roundTrip {
    forAll { (x: Int) =>
      val parcel: Parcel = Parcel.obtain

      Parceler.write(parcel, x)
      parcel.setDataPosition(0)
      Parceler.read[Int](parcel) should equal (x)
    }
  }
}
