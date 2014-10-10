package androidParceling

import android.os.Parcel

trait Parceled[T] { self: T =>
  def parceler: Parceler[T]
  def describeContents = 0
  def writeToParcel(p: Parcel, flags: Int) = parceler.write(p, this)
}
