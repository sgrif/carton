package androidParceling

import android.os.Parcel

trait Parceler[A] {
  def write(parcel: Parcel, a: A): Unit
  def read(parcel: Parcel): A
}

object Parceler {
  def write[A](parcel: Parcel, a: A)(implicit parceler: Parceler[A]) =
    parceler.write(parcel, a)

  def read[A](parcel: Parcel)(implicit parceler: Parceler[A]) =
    parceler.read(parcel)

  implicit val intInstance = new Parceler[Int] {
    def write(parcel: Parcel, i: Int) = parcel.writeInt(i)
    def read(parcel: Parcel) = parcel.readInt
  }
}
