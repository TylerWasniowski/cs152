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
        case Variable(name) => Prog(defs, defs.find(_.name == name).get.value).eval
        case Operator(left, right, f) => f(eval(left, symbols), eval(right, symbols))
      }
    }
  }

  class SimpleLanguageParser extends JavaTokenParsers {
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

  object Main extends App {
    val parser = new SimpleLanguageParser
    parser.parseAll(parser.prog, new InputStreamReader(System.in)) match {
      case parser.Success(result, next) => println(result.eval)
      case _ => println("Error")
    }
  }
}