import scala.annotation.tailrec

def fac(n: Long) : Long = {
  @tailrec def helper(n: Long, partialResult: Long) : Long = {
    if (n <= 0)
      partialResult
    else
      helper(n - 1, n * partialResult)
  }

  helper(n, 1)
}

fac(10)
fac(6)
fac(3)
fac(13)

def fac2(n: Long) : Long = if (n <= 0) 1 else n * fac(n-1)

fac2(10)
fac2(6)
fac2(3)
fac2(13)