package carton
package instances

import android.os.{Parcel, Parcelable}
import shapeless._

trait Derivation {
  def creator[A <: AnyRef](implicit parceler: Parceler[A], m: Manifest[A]) =
    new Parcelable.Creator[A] {
      def newArray(size: Int) = m.newArray(size)
      def createFromParcel(p: Parcel) = parceler.read(p)
    }

  object typeClass extends ProductTypeClass[Parceler] {
    def emptyProduct = new Parceler[HNil]{
      def write(parcel: Parcel, a: HNil) = ()
      def read(parcel: Parcel) = HNil
    }

    def product[F, T <: HList](FHead: Parceler[F], FTail: Parceler[T]) = new Parceler[F :: T] {
      def write(parcel: Parcel, a: F :: T) {
        FHead.write(parcel, a.head)
        FTail.write(parcel, a.tail)
      }

      def read(parcel: Parcel) = {
        FHead.read(parcel) :: FTail.read(parcel)
      }
    }

    def project[F, G](instance: => Parceler[G], to: F => G, from: G => F) = new Parceler[F] {
      def write(parcel: Parcel, a: F) = instance.write(parcel, to(a))
      def read(parcel: Parcel) = from(instance.read(parcel))
    }
  }
}
