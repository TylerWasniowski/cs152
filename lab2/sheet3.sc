import scala.annotation.tailrec

// Attempt 1
def listAllPairs(input: List[Any]): List[List[Any]] = {
  @tailrec def makeSomePairs(firstEl: Any, secondEls: List[Any], pairs: List[List[Any]]): List[List[Any]] = {
    if (secondEls.isEmpty)
      pairs
    else
      makeSomePairs(firstEl, secondEls.tail, List(firstEl, secondEls.head) :: pairs)
  }
  @tailrec def makeAllPairs(firstEls: List[Any], pairs: List[List[Any]]): List[List[Any]] = {
    if (firstEls.isEmpty)
      pairs
    else
      makeAllPairs(firstEls.tail, makeSomePairs(firstEls.head, input, Nil) ::: pairs)
  }
  makeAllPairs(input, Nil)
}

listAllPairs(List(1, 2, 3)).reverse

// Attempt 2
def listAllPairs2(input: List[Any]): List[List[Any]] = {
  val reversedInput = input.reverse
  @tailrec def makeAllPairs(firstEls: List[Any], secondEls: List[Any], pairs: List[List[Any]]): List[List[Any]] = {
    if (firstEls.isEmpty)
      pairs
    else if (secondEls.isEmpty)
      makeAllPairs(firstEls.tail, reversedInput, pairs)
    else
      makeAllPairs(firstEls, secondEls.tail,
        List(firstEls.head, secondEls.head) :: pairs)
  }
  makeAllPairs(reversedInput, reversedInput, Nil)
}

listAllPairs2(List(1, 2, 3, 4, 5))