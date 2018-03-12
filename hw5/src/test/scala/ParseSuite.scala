import org.scalatest.FunSuite
import hw5part3._

class ParseSuite extends  FunSuite {
  val parser = new SimpleLanguageParser

  test("No variable") {
    assert(parser.eval(parser.parse(parser.expr, "3 - 4 * 5").get, Map()) == -17)
  }

  test("One variable") {
    assert(parser.eval(parser.parse(parser.expr, "4 * x + 2").get, Map("x" -> 10)) == 42)
  }

  test("One variable twice") {
    assert(parser.eval(parser.parse(parser.expr, "4 * x + x").get, Map("x" -> 10)) == 50)
  }

  test("Two variables") {
    assert(parser.eval(parser.parse(parser.expr, "(x + 1) * (y - 1)").get, Map("x" -> 2, "y" -> 9)) == 24)
  }

  test("Two variables twice") {
    assert(parser.eval(parser.parse(parser.expr, "(x + y) * (x - y)").get, Map("x" -> 10, "y" -> 2)) == 96)
  }
}