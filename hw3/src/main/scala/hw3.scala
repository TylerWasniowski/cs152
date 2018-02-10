package main.scala

import org.scalactic.Bool

import scala.annotation.tailrec;

object hw3 {
  def compose(f: (Int => Int), g: (Int => Int))(n: Int): Int = f(g(n))

  def flip(f: ((Int, Int) => Int))(a: Int, b: Int): Int = f(b, a)

  def zip(list1: List[Int], list2: List[Int], f: (Int, Int) => Int): List[Int] = {
    if (list1.isEmpty)
      list2
    else if (list2.isEmpty)
      list1
    else
      f(list1.head, list2.head) :: zip(list1.tail, list2.tail, f)

  }

  def combineNeighbors(list: List[Int], f: (Int, Int) => Int): List[Int] = {
    if (list.isEmpty || list.tail.isEmpty)
      list
    else
      f(list.head, list.tail.head) :: combineNeighbors(list.tail.tail, f)
  }

  def iterateN(x: Int, f: (Int => Int), n: Int): List[Int]  = {
    if (n <= 0)
      Nil
    else
      x :: iterateN(f(x), f, n - 1)
  }

  def iterateWhile(x: Int, f: (Int => Int), p: (Int => Boolean)): List[Int] = {
    if (!p(x))
      Nil
    else
      x :: iterateWhile(f(x), f, p)
  }

  def iterateUntil(x: Double, f: (Double => Double), p: ((Double, Double) => Boolean)): List[Double] = {
    if (p(x, f(x)))
      x :: Nil
    else
      x :: iterateUntil(f(x), f, p)
  }

  def reduceWithDefault(default: Int, list: List[Int], f: ((Int, Int) => Int)): Int = {
    if (list.isEmpty)
      default
    else
      f(default, reduceWithDefault(list.head, list.tail, f))
  }

  def otherReduceWithDefault(default: Int, list: List[Int], op: (Int, Int) => Int): Int = {
    @tailrec def helper(list: List[Int], partialResult: Int): Int = {
      if (list.isEmpty)
        partialResult
      else
        helper(list.tail, op(list.head, partialResult))
    }

    if (list.isEmpty)
      default
    else
      op(helper(list.tail, list.head), default)
  }

}