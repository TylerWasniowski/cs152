object hw5part2 {
  abstract class Expr[T] {
    def eval(symbols: Map[String, T]): T
  }

  class Op[T](fun: Seq[T] => T, args: Expr[T]*) extends Expr[T] {
    override def eval(symbols: Map[String, T]): T = fun.apply(args.map(_.eval(symbols)))
  }

  case class Var[T](name: String) extends Expr[T] {
    override def eval(symbols: Map[String, T]): T = symbols(name)
  }

  case class Const[T](value: T) extends Expr[T] {
    override def eval(symbols: Map[String, T]): T = value
  }

  case class Def[T](name: String, value: Expr[T])

  case class Product(args: Expr[Int]*) extends Op[Int](_.product, args: _*)

  case class Sum(args: Expr[Int]*) extends Op[Int](_.sum, args: _*)

  def eval[T](d: Def[T], symbols: Map[String, T]): Map[String, T] = symbols + (d.name -> d.value.eval(symbols))
}