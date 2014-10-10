package androidParceling.test

import org.scalatest._, junit._, prop.PropertyChecks
import org.junit.runner.RunWith
import org.robolectric.{Robolectric, RobolectricTestRunner}

@RunWith(classOf[RobolectricTestRunner])
abstract class UnitSpec extends JUnitSuite with Matchers with PropertyChecks
