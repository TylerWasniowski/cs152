object problem1 extends App {
  def diffs(lst: List[Int]): List[Int] = {
    def helper(input: List[Int], output: List[Int]): List[Int] = {
      if (input == Nil || input.tail == Nil)
        output
      else
        input.head - input.tail.head :: helper(input.tail, output)
    }

    helper(lst, Nil)
  }

  println(diffs(List(3, 1, 4, 1, 5, 9, 2, 6)))
}
