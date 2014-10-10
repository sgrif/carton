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
}

object ParceledTest {
  import Parceler.auto._

  case class Foo(x: Int, y: String) extends Parceled[Foo] with Parcelable {
    val parceler = implicitly[Parceler[Foo]]
  }

  object Foo {
    val CREATOR = Parceler.creator[Foo]
  }
}
