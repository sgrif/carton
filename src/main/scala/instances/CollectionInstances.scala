package carton
package instances

import android.os.Parcel
import scala.annotation.tailrec

trait CollectionInstances {
  implicit def listParceler[A](implicit ap: Parceler[A]) = new Parceler[List[A]] {
    @tailrec
    def write(parcel: Parcel, v: List[A]) = v match {
      case a :: as => {
        parcel.writeByte(1)
        ap.write(parcel, a)
        write(parcel, as)
      }
      case _ => parcel.writeByte(0)
    }

    def read(parcel: Parcel) = parcel.readByte match {
      case 0 => Nil
      case _ => ap.read(parcel) :: read(parcel)
    }
  }

  implicit def iseqParceler[A](implicit ap: Parceler[A]) = new Parceler[IndexedSeq[A]] {
    def write(parcel: Parcel, v: IndexedSeq[A]) {
      parcel.writeInt(v.length)
      v.foreach(ap.write(parcel, _))
    }

    def read(parcel: Parcel) = {
      val length = parcel.readInt
      (0 until length).map(_ => ap.read(parcel))
    }
  }

  implicit def seqParceler[A: Parceler] =
    iseqParceler[A].map(_.toSeq)(_.toIndexedSeq)

  implicit def vectorParceler[A: Parceler] =
    iseqParceler[A].map(_.toVector)(identity _)

  implicit def setParceler[A: Parceler] =
    iseqParceler[A].map(_.toSet)(_.toIndexedSeq)

  implicit def mapParceler[A, B](implicit e: Parceler[(A, B)]) =
    iseqParceler[(A, B)].map(_.toMap)(_.toIndexedSeq)
}
