import java.io._
import scala.util.parsing.combinator.JavaTokenParsers

object hw6part3 extends App {
  case class Element(name : String, attrs : List[(String, String)], children : List[Element])

  class XMLLikeParser extends JavaTokenParsers {
    def list: Parser[Element] = (("<" ~> ident ~ rep(attr) <~  ">") ~ rep(list) ~ ("</" ~> ident <~ ">")) ^? {
      case openingName ~ attrs ~ children ~ closingName
        if openingName == closingName => Element(openingName, attrs, children)
    } | ("<" ~> ident ~ rep(attr) <~ "/>") ^^ {
      case name ~ attrs => Element(name, attrs, List())
    }

    def attr: Parser[(String, String)] = (ident ~ ("=" ~> stringLiteral)) ^^ {
      case name ~ value => (name, value)
    }
  }

  val parser = new XMLLikeParser
  parser.parseAll(parser.list, new InputStreamReader(System.in)) match {
    case parser.Success(result, next) => println(result)
    case _ => println("Error")
  }
}