import scala.util.parsing.combinator._

class SimpleLanguageParser extends JavaTokenParsers {
  def expr: Parser[Any] = term ~ opt(("+" | "-") ~ expr)
  def term: Parser[Any] = factor ~ opt(("*" | "/" ) ~ term)
  def factor: Parser[Any] = wholeNumber | "(" ~ expr ~ ")"
  def eval(x : Any) : Int = x match {
    case a ~ Some("+" ~ b) => eval(a) + eval(b)
    case a ~ Some("-" ~ b) => eval(a) - eval(b)
    case a ~ Some("*" ~ b) => eval(a) * eval(b)
    case a ~ Some("/" ~ b) => eval(a) / eval(b)
    case a ~ None => eval(a)
    case a : String => Integer.parseInt(a)
    case "(" ~ a ~ ")" => eval(a)
  }
}

object Lab8 extends App {
  val parser = new SimpleLanguageParser
  val result = parser.parse(parser.expr, "(3+1)*2")
  println("Result: " + result)
  println("Evaled result: " + parser.eval(result.get))
}