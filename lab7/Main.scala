object Main extends App {
  class Expr
  case class Number(value : Int) extends Expr
  case class Variable(name : String) extends Expr
  case class Operator(left : Expr, right : Expr,
                      f: (Int, Int) => Int) extends Expr

  case class Definition(name : String, expr : Expr)

  def eval(expr : Expr, symbols : Map[String, Int]) : Int =
    expr match {
      case Number(num) => num
      case Variable(name) => symbols(name)
      case Operator(left, right, f) => f(eval(left, symbols), eval(right, symbols))
    }

  def eval(definition: Definition, symbols: Map[String, Int]) : Map[String, Int] =
    symbols + (definition.name -> eval(definition.expr, symbols))

  val tree = Operator(Number(3), Operator(Number(4), Variable("x"), _ * _), _ + _)
  println("Tree: " + tree)
  println("Eval with x = 5: " + eval(tree, Map("x" -> 5)))
  val def1 = Definition("x", Number(2))
  val def2 = Definition("y", Variable("x"))
  val sym0 = Map[String, Int]()
  val sym1 = eval(def1, sym0) // "x" -> 2
  val sym2 = eval(def2, sym1) // "x" -> 2, "y" -> 2
  println("Sym1: " + sym1)
  println("Sym2: " + sym2)
}
