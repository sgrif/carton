package androidParceling

import instances._

import android.os.Parcel
import shapeless._

trait Parceler[A] {
  self =>
  def write(parcel: Parcel, a: A): Unit
  def read(parcel: Parcel): A

  def map[B](f: A => B)(cf: B => A) = new Parceler[B] {
    def read(p: Parcel): B = f(self.read(p))
    def write(p: Parcel, b: B) = self.write(p, cf(b))
  }
}

object Parceler
extends ProductTypeClassCompanion[Parceler]
with PrimitiveInstances
with CollectionInstances
with Derivation {
  def write[A](parcel: Parcel, a: A)(implicit parceler: Parceler[A]) =
    parceler.write(parcel, a)

  def read[A](parcel: Parcel)(implicit parceler: Parceler[A]) =
    parceler.read(parcel)
}
