package main.scala

/**
  * Created by rooke on 2/9/2018.
  */
import scala.annotation.tailrec;

object hw3 {
  def compose(f: (Int => Int), g: (Int => Int))(n: Int) {
    f(g(n))
  }
}