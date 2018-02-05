def greaterThan100(lst: List[Int]): List[Int] = {
  lst.filter(_ > 100)
}

greaterThan100(List(1, 232, 539, 35, 32, 75, 123))

val gen = new scala.util.Random

def randList(len: Int, n: Int) : List[Int] = {
  if (len == 0)
    Nil
  else
    gen.nextInt(n) :: randList(len - 1, n)
}

greaterThan100(randList(10, 200))

def greaterThan(n: Int, lst: List[Int]): List[Int] = {
  lst.filter(_ > n)
}

greaterThan(100, randList(10, 200))