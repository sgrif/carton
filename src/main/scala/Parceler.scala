package androidParceling

import instances._

import android.os.Parcel
import shapeless._

trait Parceler[A] {
  def write(parcel: Parcel, a: A): Unit
  def read(parcel: Parcel): A
}

object Parceler
extends ProductTypeClassCompanion[Parceler]
with PrimitiveInstances
with Derivation {
  def write[A](parcel: Parcel, a: A)(implicit parceler: Parceler[A]) =
    parceler.write(parcel, a)

  def read[A](parcel: Parcel)(implicit parceler: Parceler[A]) =
    parceler.read(parcel)
}
