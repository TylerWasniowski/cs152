val gen = new scala.util.Random
gen.nextInt(10)
gen.nextInt(10)

def randList(len: Int, n: Int) : List[Int] = {
  if (len == 0)
    Nil
  else
    gen.nextInt(n) :: randList(len - 1, n)
}

randList(5, 10)

randList(5, 1000)
randList(1000, 5)