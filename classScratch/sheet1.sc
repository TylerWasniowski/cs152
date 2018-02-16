import scala.annotation.tailrec

//def fac(n: BigInt) : BigInt = {
//  @tailrec def helper(n: BigInt, partialResult: BigInt) : BigInt = {
//    if (n <= BigInt(0))
//      partialResult
//    else
//      helper(n - BigInt(1), n * partialResult)
//  }
//
//  helper(n, BigInt(1))
//}
//
//fac(BigInt(10))
//fac(BigInt(6))
//fac(BigInt(3))
//fac(BigInt(13))

def fac2(n: BigInt) : Boolean = n < 5

fac2(BigInt(10))
fac2(BigInt(6))
fac2(BigInt(3))
fac2(BigInt(13))