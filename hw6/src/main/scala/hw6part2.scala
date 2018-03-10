import java.io._
import scala.util.parsing.combinator.JavaTokenParsers

object hw6part2 extends App {
  class ListParser extends JavaTokenParsers {
    def list: Parser[List[Int]] = ("(" ~> opt(wholeNumber ~ rep("," ~> wholeNumber)) <~ ")") ^^
    {
      case Some(n ~ list) => n.toInt :: list.map(_.toInt)
      case None => Nil
    } | ((wholeNumber <~ "::") ~ list) ^^
    {
      case n ~ list => n.toInt :: list
    }
  }

  val parser = new ListParser
  parser.parseAll(parser.list, new InputStreamReader(System.in)) match {
    case parser.Success(result, next) => println(result)
    case _ => println("Error")
  }
}
