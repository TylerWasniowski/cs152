val lst = List("Alpha", "Bravo", "Charlie", "Delta", "Echo", "Foxtrot", "Golf")

lst.filter(_.length() < 5)
lst.sortBy(_.length())
lst.reduce((a, b) => if (a.length() > b.length) a else b)

def makeMin[T](f: (T, T) => Boolean) : (T, T) => T = {
  (a: T, b: T) => if (f(a, b)) a else b
}

def makeMin2[T](f: (T, T) => Boolean)(a: T, b: T) = {
  if (f(a, b)) a else b
}

makeMin((a: String, b: String) => a.length() < b.length())("hi", "hello")
makeMin2((a: String, b: String) => a.length() < b.length())("test", "tester")


def max(lst : List[String], less : (String, String) => Boolean) =
  lst.reduce((x, y) => if (less(x, y)) y else x)

max(List("one", "two", "three"), _.length < _.length)

def max2[T](lst : List[T], less : (T, T) => Boolean) =
  lst.reduce((x, y) => if (less(x, y)) y else x)

//max2(lst, _ < _)


def max3[T](lst : List[T])(less : (T, T) => Boolean) =
  lst.reduce((x, y) => if (less(x, y)) y else x)

max3(lst)(_ < _)