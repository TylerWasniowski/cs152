import java.io._
import scala.util.parsing.combinator._

class Expr2
case class Number(value : Int) extends Expr2
case class Variable(name : String) extends Expr2
case class Operator(left : Expr2, right : Expr2,
                    f: (Int, Int) => Int) extends Expr2

class SimpleLanguageParser2 extends JavaTokenParsers {
  def expr: Parser[Expr2] = (expr ~ opt(("+" | "-") ~ term)) ^^ {
    case a ~ None => a
    case a ~ Some("+" ~ b) => Operator(a, b, _ + _)
    case a ~ Some("-" ~ b) => Operator(a, b, _ - _)
  }

  def term: Parser[Expr2] = (factor ~ opt(("*" | "/") ~ term)) ^^ {
    case a ~ None => a
    case a ~ Some("*" ~ b) => Operator(a, b, _ * _)
    case a ~ Some("/" ~ b) => Operator(a, b, _ / _)
  }

  def factor: Parser[Expr2] = wholeNumber ^^ (x => Number(x.toInt)) | "(" ~> expr <~ ")"

}

object Main2 {
  def main(args : Array[String]) : Unit = {}
  val parser = new SimpleLanguageParser2
  val result = parser.parse(parser.expr, new InputStreamReader(System.in))
  println(result)
}