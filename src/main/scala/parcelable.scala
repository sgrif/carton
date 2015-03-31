package carton

import scala.reflect.macros._
import scala.language.experimental.macros
import scala.annotation.{compileTimeOnly, StaticAnnotation}

@compileTimeOnly("enable macro paradise to expand macro annotations")
class parcelable extends StaticAnnotation {
  def macroTransform(annottees: Any*): Any = macro parcelable.impl
}

object parcelable {
  def impl(c: whitebox.Context)(annottees: c.Expr[Any]*): c.Expr[Any] = {
    import c.universe._

    def extractDefinition(classDecl: ClassDef) = {
      val q"$mods class $className(..$fields) extends ..$bases { ..$body }" = classDecl
      (mods, className, fields, bases, body)
    }

    def parcelableInstanceDef(classDecl: ClassDef) = {
      val (mods, name, fields, parents, body) = extractDefinition(classDecl)
      q"""
        $mods class $name(..$fields) extends ..$parents with android.os.Parcelable {
          ..$body

          def describeContents: Int = 0

          def writeToParcel(p: android.os.Parcel, flags: Int) {
            implicitly[carton.Parceler[$name]].write(p, this)
          }
        }
      """
    }

    def parcelableCompanionDef(compDeclOrName: Either[TypeName, ModuleDef]) = {
      def parcelerBody(name: TypeName) = q"""
        implicit val parceler = carton.Parceler[$name]

        val CREATOR = new android.os.Parcelable.Creator[$name] {
          def newArray(size: Int) = new Array[$name](size)
          def createFromParcel(p: android.os.Parcel) = parceler.read(p)
        }
      """

      compDeclOrName match {
        case Right(q"object $obj extends ..$bases { ..$body }") =>
          q"""
          object $obj extends ..$bases {
            ..$body
            ..${parcelerBody(obj.toTypeName)}
          }
          """
        case Left(name) =>
          q"""
          object ${name.toTermName} {
            ..${parcelerBody(name)}
          }
          """
      }
    }

    def modifiedDeclaration(classDecl: ClassDef, compDecl: Option[ModuleDef] = None) = {
      val compOrName = compDecl.toRight(classDecl.name)

      c.Expr(q"""
        ${parcelableInstanceDef(classDecl)}
        ${parcelableCompanionDef(compOrName)}
      """)
    }

    annottees.map(_.tree) match {
      case (classDecl: ClassDef) :: Nil => modifiedDeclaration(classDecl)
      case (classDecl: ClassDef) :: (compDecl: ModuleDef) :: Nil => modifiedDeclaration(classDecl, Some(compDecl))
      case _ => c.abort(c.enclosingPosition, "Invalid annottee")
    }
  }
}
