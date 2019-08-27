package examples.types


object IntersectionTypes extends App {

  trait ServiceA
  trait ServiceB
  trait ServiceC

  type Services = ServiceA with ServiceB with ServiceC

  // Note: intersection types can only be used in context bounds?
  def xxx[T <: Services](v: T): String = {
    v.getClass.toGenericString
  }

  // ... not as in "Object with Services"
  val obj = new Object with ServiceA with ServiceB with ServiceC
  println(xxx(obj))
  println(TypeTags.typeOfExp(obj))

  assert(TypeTags.typeOfExp(obj).toString() ==
    "TypeTag[examples.types.IntersectionTypes.ServiceA " +
    "with examples.types.IntersectionTypes.ServiceB " +
    "with examples.types.IntersectionTypes.ServiceC]")

}