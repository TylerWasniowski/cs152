def altSum(list: List[Int]): Int = {
  def helper(list: List[Int], sign: Int): Int = {
    if (list.isEmpty)
      0
    else
      sign * list.head + helper(list.tail, -sign)
  }

  helper(list, 1)
}

def altSumFold(list: List[Int]) = list.foldLeft((0, 1))((a, b) => (a._1 + a._2*b, -a._2))._1

val list = List(4, 2, 232, 1, 239, 43, 2, 23, 435, 32, 324, 234, 3424, 3)

altSum(list)
altSumFold(list)

def allDifferences(list1: List[Int], list2: List[Int]): List[Int] = {
  list1.flatMap(a => list2.map(a - _))
}

allDifferences(List(1, 5), List(2, 3, 4)) // List(-1, -2, -3, 3, 2, 1)

def swapFold[T](list: List[T]): List[T] = {
  val pair = list.foldLeft((Nil, None): (List[T], Option[T]))(
    (pair, value) => pair._2 match {
      case Some(otherValue) => (pair._1 :+ value :+ otherValue, None)
      case None => (pair._1, Some(value))
    }
  )

  pair._2 match {
    case Some(value) => pair._1 :+ value
    case None => pair._1
  }
}

swapFold(List(1))