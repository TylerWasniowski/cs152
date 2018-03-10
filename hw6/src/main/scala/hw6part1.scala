import java.io._
import scala.util.parsing.combinator._

object hw6part1 {

  class Expr

  case class Number(value: Int) extends Expr
  case class Variable(name: String) extends Expr
  case class Operator(left: Expr, right: Expr, f: (Int, Int) => Int) extends Expr
  case class Def(name: String, value: Expr) extends Expr

  case class Prog(defs: List[Def], expr: Expr) {
    def eval: Int = {
      expr match {
        case Number(n) => n
        case Variable(name) => eval(defs, defs.find(_.name == name).get.value)
        case Operator(left, right, f) => f(eval(defs, left), eval(defs, right))
        case definition: Def => eval(definition :: defs, expr)
      }
    }

    private def eval(defs: List[Def], expr: Expr): Int = {
      Prog(defs, expr).eval
    }
  }

  class SimpleLanguageParser extends JavaTokenParsers {
    def prog: Parser[Prog] = (rep(valdef) ~ expr) ^^ {
      case lst ~ expr => Prog(lst, expr)
    }

    def expr: Parser[Expr] = (term ~ rep(("+" | "-") ~ term)) ^^ {
      case a ~ lst => (a /: lst) {
        case (x, "+" ~ y) => Operator(x, y, _ + _)
        case (x, "-" ~ y) => Operator(x, y, _ - _)
      }
    }

    def term: Parser[Expr] = (factor ~ rep(("*" | "/") ~ term)) ^^ {
      case a ~ lst => (a /: lst) {
        case (x, "+" ~ y) => Operator(x, y, _ + _)
        case (x, "-" ~ y) => Operator(x, y, _ - _)
      }
    }

    def factor: Parser[Expr] = ident ^^ (x => Variable(x)) | wholeNumber ^^ (x => Number(x.toInt)) | "(" ~> expr <~ ")"

    def valdef: Parser[Def] = ("val" ~> ident <~ "=") ~ expr <~ ";" ^^ { case s ~ e => Def(s, e) }
  }

  val parser = new SimpleLanguageParser
  parser.parseAll(parser.prog, new InputStreamReader(System.in)) match {
    case parser.Success(result, next) => println(result.eval)
    case _ => println("Error")
  }
}