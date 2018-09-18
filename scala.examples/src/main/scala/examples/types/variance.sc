import examples.types.MyBox

val b : MyBox[Integer] = new MyBox(123)
val b2 : MyBox[Number] = b;
val b3 : MyBox[AnyRef] = b;

val b1x: Int = b.get()
b3.get()

// ClassCastException:
new MyBox[Integer](123).asInstanceOf[MyBox[String]].get()
