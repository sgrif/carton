package androidParceling.test

import androidParceling.Parceler

import org.junit.Test
import org.robolectric.annotation.Config

import android.os.Parcel

@Config(manifest=Config.NONE)
class ParcelTest extends UnitSpec {
  @Test def testInts = testRoundTrip[Int]
  @Test def testDoubles = testRoundTrip[Double]
  @Test def testStrings = testRoundTrip[String]
}
