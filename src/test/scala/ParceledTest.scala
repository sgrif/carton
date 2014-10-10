package androidParceling.test

import androidParceling.{Parceler, Parceled, ParcelableParcel}

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

    bundle.putParcelable("foo", Parceled(Foo(1, "foo")))
    //force round trip
    bundle.writeToParcel(parcel, 0)
    parcel.setDataPosition(0)
    val newBundle = Bundle.CREATOR.createFromParcel(parcel)

    val foo = Parceled.unapply[Foo](newBundle.getParcelable[ParcelableParcel]("foo")).get

    foo should equal (Foo(1, "foo"))
  }
}

object ParceledTest {
  case class Foo(x: Int, y: String)

  object Foo {
    implicit val parceler: Parceler[Foo] = Parceler[Foo]
  }
}
