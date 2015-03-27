package androidParceling.test

import androidParceling.{Parceler, Parceled}

import org.junit.Test
import org.robolectric.annotation.Config

import android.content.Intent
import android.os.{Parcel, Parcelable, Bundle}

@Config(manifest=Config.NONE)
class ParceledTest extends UnitSpec {
  import ParceledTest._

  @Test
  def parceledTypesWorkWithBundle {
    val bundle = new Bundle
    val parcel = Parcel.obtain

    bundle.putParcelable("foo", Foo(1, "foo").asInstanceOf[Parcelable])
    //force round trip
    bundle.writeToParcel(parcel, 0)
    parcel.setDataPosition(0)
    val newBundle = Bundle.CREATOR.createFromParcel(parcel)

    val foo = newBundle.getParcelable[Foo]("foo")

    foo should equal (Foo(1, "foo"))
  }

  @Test
  def parceledTypesCanBeComposed {
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
}

object ParceledTest {
  case class Foo(x: Int, y: String) extends Parceled[Foo] with Parcelable {
    val parceler: Parceler[Foo] = Parceler[Foo]
  }

  object Foo {
    val CREATOR = Parceler.creator[Foo]
  }

  case class Bar(x: String, y: Foo) extends Parceled[Bar] with Parcelable {
    val parceler: Parceler[Bar] = Parceler[Bar]
  }

  object Bar {
    val CREATOR = Parceler.creator[Bar]
  }
}
