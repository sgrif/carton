package androidParceling

import android.os.{Parcel, Parcelable}
import shapeless._

trait Parceler[A] {
  def write(parcel: Parcel, a: A): Unit
  def read(parcel: Parcel): A
}

object Parceler extends ProductTypeClassCompanion[Parceler] {
  def write[A](parcel: Parcel, a: A)(implicit parceler: Parceler[A]) =
    parceler.write(parcel, a)

  def read[A](parcel: Parcel)(implicit parceler: Parceler[A]) =
    parceler.read(parcel)

  def creator[A <: AnyRef](implicit parceler: Parceler[A], m: Manifest[A]) =
    new Parcelable.Creator[A] {
      def newArray(size: Int) = m.newArray(size)
      def createFromParcel(p: Parcel) = parceler.read(p)
    }

  implicit val parcelerInt = new Parceler[Int] {
    def write(parcel: Parcel, i: Int) = parcel.writeInt(i)
    def read(parcel: Parcel) = parcel.readInt
  }

  implicit val parcelerDouble = new Parceler[Double] {
    def write(parcel: Parcel, d: Double) = parcel.writeDouble(d)
    def read(parcel: Parcel) = parcel.readDouble
  }

  implicit val parcelerString = new Parceler[String] {
    def write(parcel: Parcel, s: String) = parcel.writeString(s)
    def read(parcel: Parcel) = parcel.readString
  }

  implicit val parcelerInstance: ProductTypeClass[Parceler] = new ProductTypeClass[Parceler] {
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
