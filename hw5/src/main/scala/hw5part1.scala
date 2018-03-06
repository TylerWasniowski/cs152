import java.util.Scanner

object hw5part1 {
  abstract class Expr[T] {
    def value: T
  }

  class Op[T](fun: Seq[T] => T, args: Expr[T]*) extends Expr[T] {
    override def value: T = fun.apply(args.map(_.value))
  }

  case class Const[T](value: T) extends Expr[T]

  case class Product(args: Expr[Int]*) extends Op[Int](_.product, args: _*)

  case class Sum(args: Expr[Int]*) extends Op[Int](_.sum, args: _*)

  object Rand extends Expr[Int] {
    private val gen = new java.util.Random(42)
    override def value: Int = gen.nextInt()
  }

  object Read extends Expr[Int] {
    private val in = new Scanner(System.in)
    override def value: Int = in.nextInt()
  }
}