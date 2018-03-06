import org.scalatest.FunSuite
import hw5part2._

class DefSuite extends FunSuite {
  test("Variable") {
    assert(42 == Var("x").eval(Map("x" -> 42)))
    assert("Hello" == Var("greeting").eval(Map("greeting" -> "Hello", "location" -> "World")))
  }

  test("Expression with variable") {
    assert(42 == Product(Const(6), Var("factor")).eval(Map("factor" -> 7)))

  }
}