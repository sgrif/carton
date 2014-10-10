package androidParceling

import android.os.{Parcel, Parcelable}

class ParcelableParcel(val parcel: Parcel) extends Parcelable {
  def describeContents = 0

  def writeToParcel(out: Parcel, flags: Int) = {
    out.writeInt(parcel.dataSize)
    out.appendFrom(parcel, 0, parcel.dataSize);
  }
}

object ParcelableParcel {
  val CREATOR = new Parcelable.Creator[ParcelableParcel] {
    def newArray(size: Int) = new Array[ParcelableParcel](size)
    def createFromParcel(in: Parcel) = {
      val out = Parcel.obtain
      val size = in.readInt
      val pos = in.dataPosition
      out.appendFrom(in, pos, size)
      in.setDataPosition(pos + size)
      new ParcelableParcel(out)
    }
  }
}
