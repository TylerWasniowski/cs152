import scala.annotation.tailrec
import scala.util.matching._

val patterns = List("if|def|val".r, """\p{L}(\p{L}|\p{N}|_)*""".r,
  """[+-]?\p{N}+""".r, "[+*/%<=>-]".r, "[(){};]".r, """\p{Z}+""".r)
val input = "if(x<0) 0 else root(x);"

def firstMatch(input: String, patterns: List[Regex]) : String = {
  if (patterns.isEmpty)
    ""
  else
    patterns.head.findPrefixOf(input)
      .getOrElse(firstMatch(input, patterns.tail))
}

firstMatch(input, patterns)
firstMatch(input.substring(2), patterns)

def tokens(input: String, patterns: List[Regex]) : List[String] = {
  val first = firstMatch(input, patterns)
  if (first.isEmpty)
    Nil
  else {
    first :: tokens(input.substring(first.length), patterns)
  }
}

tokens(input, patterns)