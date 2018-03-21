import scala.util.parsing.combinator._

object problem4 extends App {
  class SimpleLanguageParser extends JavaTokenParsers {
    def expr: Parser[Boolean] = (term ~ rep("||" ~ term)) ^^ {
      case term ~ list => list.foldLeft(term) {
        case (a, "||" ~ b) => a || b
      }
    }

    def term: Parser[Boolean] = (factor ~ rep("&&" ~ factor)) ^^ {
      case factor ~ list => list.foldLeft(factor) {
        case (a, "&&" ~ b) => a && b
      }
    }

    def factor: Parser[Boolean] = (rep("!") ~ "true") ^^ {
      case list ~ a => if (list.length % 2 == 0) true else false
    } | (rep("!") ~ "false") ^^ {
      case list ~ a => if (list.length % 2 != 0) true else false
    } | "(" ~> expr <~ ")"
  }

  val parser = new SimpleLanguageParser
  val result = parser.parseAll(parser.expr, " !true || !false && !!true")
  println(result)
}


