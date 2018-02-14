val x = List(2, 3, 5, 1, 4)
val y = List(2, 1)
x.take(3)
x.drop(2)
x.dropRight(2)
val pair = x.span(_ < 4)
pair._1
pair._2

val lst = List("Hello", "Scala", "World")
("" /: lst)(_ + " " + _).drop(1)
lst.mkString(" ")
