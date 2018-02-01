def squares(n: Int): List[Int] =
  (1 to n).toList.map(x => x * x).reverse

List(List(1, 2), List(3, 4, 5)).flatten

def listAllPairs(input: List[Any]): List[List[Any]] =
  input.flatMap(n => input.map(m => List(n, m)))

listAllPairs(List(1, 2, 3))
listAllPairs(List("Tyler", "Wasniowski", "Tyler Wasniowski"))
