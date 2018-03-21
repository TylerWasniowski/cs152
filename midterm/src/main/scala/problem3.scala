object problem3 extends App {
  def allCombinations(as: List[Int], bs: List[Int]): List[Int] = {
    as.flatMap((a) => bs.map(a*10 + _))
  }
  
  println(allCombinations(List(1, 2), List(3, 4, 5)))
}
