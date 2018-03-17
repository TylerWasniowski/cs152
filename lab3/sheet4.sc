def reduce(lst: List[Int], op: (Int, Int) => Int): Int =
  if (lst.tail.isEmpty) lst.head else
    op(lst.head, reduce(lst.tail, op))

reduce(List(1,2,3,4,5), (x, y) => x - y)

(6 :: Nil) :+ 5
(1 to 5).reduce(_ - _)
(1 to 5).reduceLeft(_ - _)
(1 to 5).reduceRight(_ - _)

List(1, 7, 2, 9).reduceLeft(10 * _ + _)
