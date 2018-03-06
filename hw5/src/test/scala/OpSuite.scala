import org.scalatest.FunSuite
import java.io._
import hw5part1._

class OpSuite extends FunSuite{
  test("simple expression") {
    assert(42 == Product(Const(6), Const(7)).value)
  }

  test("input expression") {
    System.setIn(new ByteArrayInputStream(
      "13".getBytes(java.nio.charset.StandardCharsets.UTF_8)))
    System.setOut(new PrintStream(new ByteArrayOutputStream()))
    assert(25 == Sum(Const(2), Const(10), Read).value)
  }

  test("nested expression") {
    val p : Op[Int] = Product(Const(2), Const(3))
    assert(7 == Sum(Const(1), p).value)
  }

  test("random expression") {
    val r: Expr[Int] = Rand
    assert(1029516620 == Sum(r, Product(r, r)).value)
  }

  test("string op expression") {
    val cat: Expr[String] = new Op[String](_.mkString(""), Const("He"), Const("l"), Const("lo"))
    assert("Hello" == cat.value)
  }
}