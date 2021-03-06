package carton
package instances

import android.os.Parcel

trait PrimitiveInstances {
  implicit val parcelerByte = new Parceler[Byte] {
    def write(parcel: Parcel, v: Byte) = parcel.writeByte(v)
    def read(parcel: Parcel) = parcel.readByte
  }

  implicit val parcelerChar = objectParceler[Char]

  implicit val parcelerDouble = new Parceler[Double] {
    def write(parcel: Parcel, v: Double) = parcel.writeDouble(v)
    def read(parcel: Parcel) = parcel.readDouble
  }

  implicit val parcelerFloat = new Parceler[Float] {
    def write(parcel: Parcel, v: Float) = parcel.writeFloat(v)
    def read(parcel: Parcel) = parcel.readFloat
  }

  implicit val parcelerInt = new Parceler[Int] {
    def write(parcel: Parcel, v: Int) = parcel.writeInt(v)
    def read(parcel: Parcel) = parcel.readInt
  }

  implicit val parcelerShort = objectParceler[Short]

  implicit val parcelerString = new Parceler[String] {
    def write(parcel: Parcel, v: String) = parcel.writeString(v)
    def read(parcel: Parcel) = parcel.readString
  }

  private def objectParceler[A] = new Parceler[A] {
    def write(parcel: Parcel, v: A) = parcel.writeValue(v)
    def read(parcel: Parcel) = parcel.readValue(getClass.getClassLoader)
      .asInstanceOf[A]
  }

  implicit def optionParceler[A: Parceler] = new Parceler[Option[A]] {
    def read(p: Parcel) =
      if (p.readInt == 1)
        Some(Parceler.read[A](p))
      else
        None

    def write(p: Parcel, v: Option[A]) = {
      if (v.nonEmpty)
        p.writeInt(1)
      else
        p.writeInt(0)
      v.foreach(Parceler.write(p, _))
    }
  }
}
