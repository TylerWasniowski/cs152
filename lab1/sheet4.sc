def countSevens (n: Int): Int =
  if (n == 0)
    0
  else
  if (n % 10 == 7)
    countSevens(n/10) + 1
  else
    countSevens(n/10)

countSevens(747)
countSevens(0)
countSevens(332953752)
countSevens(777)
countSevens(52137)
countSevens(7)
countSevens(71237)