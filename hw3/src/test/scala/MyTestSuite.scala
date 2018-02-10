package test.scala

import main.scala.hw3
import org.scalatest.FunSuite

class MyTestSuite extends FunSuite {
  test("Composing +1 and *2") {
    assert(hw3.compose(_ + 1, _ * 2)(3) == 7)
  }

  test("Flipping args in -") {
    assert(hw3.flip(_ - _)(3, 4) == 1)
    assert(hw3.flip(_ - _)(4, 3) == -1)
  }

  test("First list longer") {
    assert(hw3.zip(List(1, 2, 3, 4), List(4, 5, 6), _ + _) == List(5, 7, 9, 4))
  }

  test("Multiplying neighbors, odd length") {
    assert(hw3.combineNeighbors(List(1, 2, 3, 4, 5), _ * _) == List(2, 12, 5))
  }

  test("Iterating * 2 five times") {
    assert(hw3.iterateN(1, _ * 2, 5) == List(1, 2, 4, 8, 16))
  }

  test("Iterating + 1 while less than 10") {
    assert(hw3.iterateWhile(0, _ + 1, _ < 10) == List(0, 1, 2, 3, 4, 5, 6, 7, 8, 9))
  }

  test("computing sqrt(2)") {
    assert(hw3.iterateUntil(2, x => (x + 2 / x) / 2, (x, y) => math.abs(x - y) < 1E-8) == List(2.0, 1.5, 1.4166666666666665, 1.4142156862745097, 1.4142135623746899))
  }

  test("Reducing sum with default 0") {
    assert(hw3.reduceWithDefault(0, (1 to 100).toList, _ + _) == 5050)
  }

  test("Reducing sum with empty list") {
    assert(hw3.reduceWithDefault(5, Nil, _ + _) == 5)
  }

  test("Reducing sum with list of length 1") {
    assert(hw3.reduceWithDefault(1, List(3), _ + _) == 4)
  }

  test("Reducing sum with list of length 2") {
    assert(hw3.reduceWithDefault(1, List(3, 2), _ + _) == 6)
  }

  test("Reducing difference with default -1") {
    assert(hw3.otherReduceWithDefault(-1, (1 to 5).toList, _ - _) == 4)
  }
}

