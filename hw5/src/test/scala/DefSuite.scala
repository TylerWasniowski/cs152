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

  test("Expression with multiple variables") {
    assert(50 == Sum(Var("summand"), Product(Const(6), Var("factor"))).
      eval(Map("factor" -> 7, "summand" -> 8)))
  }

  test("Expression with multiple occurrences of the same variable") {
    assert(49 == Product(Var("factor"), Var("factor")).eval(Map("factor" -> 7)))
  }

  test("Definition") {
    assert(Map("x" -> 42) == eval(Def("x", Const(42)), Map()))
  }

  test("Shadowing") {
    assert(Map("x" -> 42) == eval(Def("x", Const(42)), Map("x" -> 1729)))
  }
}