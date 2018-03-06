import java.io._
import scala.util.parsing.combinator._

class Expr3
case class Number(value : Int) extends Expr3
case class Variable(name : String) extends Expr3
case class Operator(left : Expr3, right : Expr3,
                    f: (Int, Int) => Int) extends Expr3

class SimpleLanguageParser3 extends JavaTokenParsers {
  def expr: Parser[Expr3] = (term ~ rep(("+" | "-") ~ term)) ^^ {
    case a ~ lst =>  (a /: lst) {
      case (x, "+" ~ y) => Operator(x, y, _ + _)
      case (x, "-" ~ y) => Operator(x, y, _ - _)
    }
  }

  def term: Parser[Expr3] = (factor ~ rep(("*" | "/") ~ term)) ^^ {
    case a ~ lst =>  (a /: lst) {
      case (x, "+" ~ y) => Operator(x, y, _ + _)
      case (x, "-" ~ y) => Operator(x, y, _ - _)
    }
  }

  def factor: Parser[Expr3] = wholeNumber ^^ (x => Number(x.toInt)) | "(" ~> expr <~ ")"
}

object Main3 extends App {
  val parser = new SimpleLanguageParser3
  val result = parser.parse(parser.expr, new InputStreamReader(System.in))
  println(result)
}