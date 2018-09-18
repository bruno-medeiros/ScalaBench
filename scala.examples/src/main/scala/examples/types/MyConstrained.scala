package examples.types

// TODO: explore more

class MyConstrained[S <: Number] private {

}

object MyConstrained {
  def asInteger: MyConstrained[Integer] = {
    new MyConstrained()
  }

  def creator[T <: Number](): MyConstrained[T] = {
    new MyConstrained()
  }
}

