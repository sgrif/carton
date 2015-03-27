package androidParceling.test

import androidParceling.Parceler

import org.junit.Test
import org.robolectric.annotation.Config

import android.os.Parcel

@Config(manifest=Config.NONE)
class ParcelTest extends UnitSpec {
  @Test def testBytes = testRoundTrip[Byte]
  @Test def testChars = testRoundTrip[Char]
  @Test def testDoubles = testRoundTrip[Double]
  @Test def testFloats = testRoundTrip[Float]
  @Test def testInts = testRoundTrip[Int]
  @Test def testShorts = testRoundTrip[Short]
  @Test def testStrings = testRoundTrip[String]
}
