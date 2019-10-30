package basic

class FormattingDemo {

  val xxx =  1234
  val x =    123
  val xxxxxxxxxxxxx  = 123

  def foo(
    param1: Integer,
       param12: String, param13: Integer,
    param14: Integer ,
    param2: List[String])
  : Iterable[String] = {
    ???
  }

  def foo(
    param1: Integer,
    param2: List[String]
  )
  : Iterable[String] = {
    ???
  }


  foo(123,
    "3dddddfddddddddddddddddddddddddddddddddddddddfffffffffffffffffffffffffffffffffffddddddddddddddddd333fffffffffffffffff31234"
,  434,
  23, List())

  def hello(how: String)(
    are: String
  )(you: String) = how + are + you

}
