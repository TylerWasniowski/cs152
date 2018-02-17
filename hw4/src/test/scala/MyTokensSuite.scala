import org.scalatest.FunSuite
import hw4._

import scala.util.parsing.json.JSON

class MyTokensSuite extends FunSuite {
  test("tokens test, empty input") {
    val reported = List("if|def|val".r, """\p{L}(\p{L}|\p{N}|_)*""".r,
      """[+-]?\p{N}+""".r, "[+*/%<=>-]".r, "[(){};]".r, """[:.]""".r, "\".*\"".r)
    val ignored = List("""\p{Z}+""".r, """//.*""".r)

    val input = ""

    assert(tokens(input, reported, ignored) == (List(), -1))
  }

  test("tokens test, lab6 input") {
    val reported = List("if|def|val".r, """\p{L}(\p{L}|\p{N}|_)*""".r,
      """[+-]?\p{N}+""".r, "[+*/%<=>-]".r, "[(){};]".r, """[:.]""".r, "\".*\"".r)
    val ignored = List("""\p{Z}+""".r, """//.*""".r)

    val input = "if(x<0) 0 else root(x);"

    assert(tokens(input, reported, ignored) ==
      (List("if", "(", "x", "<", "0", ")", "0", "else", "root", "(", "x", ")", ";"), -1))
  }

  test("tokens custom input") {
    val reported = List("if|def|val".r, """\p{L}(\p{L}|\p{N}|_)*""".r,
      """[+-]?\p{N}+""".r, "[+*/%<=>-]".r, "[(){};]".r, """[:.]""".r, "\".*\"".r)
    val ignored = List("""\p{Z}+""".r, """//.*""".r)

    val input = "def test(hello: String) = if (hello.isEmpty) \"\" else " +
      "hello.substring(hello.length()/2) + test(hello.substring(1)"

    assert(tokens(input, reported, ignored) ==
      (List("def", "test", "(", "hello", ":", "String", ")", "=", "if", "(", "hello", ".", "isEmpty", ")",
        "\"\"", "else", "hello", ".", "substring", "(", "hello", ".", "length", "(", ")", "/", "2", ")",
        "+", "test", "(", "hello", ".", "substring", "(", "1", ")"), -1))
  }

  test("tokens error") {
    val reported = List("if|def|val".r, """\p{L}(\p{L}|\p{N}|_)*""".r,
      """[+-]?\p{N}+""".r, "[+*/%<=>-]".r, "[(){};]".r, """[:.]""".r, "\".*\"".r)
    val ignored = List("""\p{Z}+""".r, """//.*""".r)

    val input = "if(x<0)& 0 else root(x);"

    assert(tokens(input, reported, ignored) ==
      (List("if", "(", "x", "<", "0", ")"), 7))
  }
}
