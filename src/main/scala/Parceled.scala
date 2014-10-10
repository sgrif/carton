package androidParceling

import android.os.Parcel

object Parceled {
  def apply[A: Parceler](a: A) = {
    val parcel = Parcel.obtain
    Parceler.write(parcel, a)
    new ParcelableParcel(parcel)
  }

  def unapply[A: Parceler](p: ParcelableParcel): Option[A] =
    Some(Parceler.read[A](p.parcel))
}
