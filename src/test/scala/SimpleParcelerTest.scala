package carton.test

import carton.Parceler

import org.junit.Test
import org.robolectric.annotation.Config

import android.os.Parcel
import shapeless._

@Config(manifest=Config.NONE)
class ParcelTest extends UnitSpec {
  @Test def testBytes = testRoundTrip[Byte]
  @Test def testChars = testRoundTrip[Char]
  @Test def testDoubles = testRoundTrip[Double]
  @Test def testFloats = testRoundTrip[Float]
  @Test def testInts = testRoundTrip[Int]
  @Test def testShorts = testRoundTrip[Short]
  @Test def testStrings = testRoundTrip[String]
  @Test def testTuples = testRoundTrip[(String, Int)]

  @Test def testSeq = testRoundTrip[Seq[String]]
  @Test def testLists = testRoundTrip[List[String]]
  @Test def testIndexedSeq = testRoundTrip[IndexedSeq[String]]
  @Test def testVectors = testRoundTrip[Vector[String]]
  @Test def testSets = testRoundTrip[Set[String]]
  @Test def testMaps = testRoundTrip[Map[String, Int]]
}
