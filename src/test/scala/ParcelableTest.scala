package carton.test

import carton.parcelable

import org.junit.Test
import org.robolectric.annotation.Config

import android.content.Intent
import android.os.{Parcel, Parcelable, Bundle}

@Config(manifest=Config.NONE)
class ParcelableTest extends UnitSpec {
  import ParcelableTest._

  @Test
  def parcelableTypesWorkWithBundle {
    val bundle = new Bundle
    val parcel = Parcel.obtain

    bundle.putParcelable("foo", Foo(1, "foo"))
    //force round trip
    bundle.writeToParcel(parcel, 0)
    parcel.setDataPosition(0)
    val newBundle = Bundle.CREATOR.createFromParcel(parcel)

    val foo = newBundle.getParcelable[Foo]("foo")

    foo should equal (Foo(1, "foo"))
  }

  @Test
  def parcelableTypesCanBeComposed {
    val bundle = new Bundle
    val parcel = Parcel.obtain
    val bar = Bar("asdf", Foo(2, "jkl;"))

    bundle.putParcelable("bar", bar)
    //force round trip
    bundle.writeToParcel(parcel, 0)
    parcel.setDataPosition(0)
    val newBundle = Bundle.CREATOR.createFromParcel(parcel)

    val newBar = newBundle.getParcelable[Bar]("bar")

    newBar should equal (bar)
  }

  @Test
  def parcelableTypesWithDefinedCompanions {
    val bundle = new Bundle
    val parcel = Parcel.obtain

    bundle.putParcelable("baz", Baz.build)
    //force round trip
    bundle.writeToParcel(parcel, 0)
    parcel.setDataPosition(0)
    val newBundle = Bundle.CREATOR.createFromParcel(parcel)

    val baz = newBundle.getParcelable[Baz]("baz")

    baz should equal (Baz(1, "baz", List(1, 2, 3, 4)))
  }

  @Test
  def nonCaseClassParcelables {
    val bundle = new Bundle
    val parcel = Parcel.obtain

    bundle.putParcelable("foo", new Qux(1, "foo"))
    //force round trip
    bundle.writeToParcel(parcel, 0)
    parcel.setDataPosition(0)
    val newBundle = Bundle.CREATOR.createFromParcel(parcel)

    val foo = newBundle.getParcelable[Qux]("foo")

    foo should equal (new Qux(1, "foo"))
  }
}

object ParcelableTest {
  @parcelable
  case class Foo(x: Int, y: String)

  @parcelable
  case class Bar(x: String, y: Foo)

  @parcelable
  case class Baz(x: Int, y: String, z: List[Int])

  object Baz {
    def build = apply(1, "baz", List(1, 2, 3, 4))
  }

  @parcelable
  class Qux(val x: Int, val y: String) {
    override def equals(other: Any) = other match {
      case other: Qux => other.x == x && other.y == y
      case _ => false
    }
  }
}
