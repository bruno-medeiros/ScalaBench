// Operations on Streams
val xs = Stream(1, 2, 3)
val xs2 = Stream.cons(1, Stream.cons(2, Stream.cons(3, Stream.empty))) // same as above
(1 to 1000).toStream // => Stream(1, ?)

val x = 1
x #:: xs // Same as Stream.cons(x, xs)
// In the Stream's cons operator, the second parameter (the tail)
// is defined as a "call by name" parameter.
// Note that x::xs always produces a List


(1 to 5)