import main.scala.hw3
import org.scalatest.FunSuite

object MyTestSuite extends FunSuite {
  test("Composing +1 and *2") {
    assert(hw3.compose(_ + 1, _ * 2)(3) == 7)
  }
}

