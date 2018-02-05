import scala.annotation.tailrec

object hw2 {
  def subs(s: String) : String = {
    def helper(str: String, wordsPrefix: String) : String = {
      if (str.isEmpty)
        wordsPrefix
      else {
        val notUsingFirstChar = helper(str.substring(1), wordsPrefix)
        val usingFirstChar = helper(str.substring(1), wordsPrefix + str.charAt(0))
        notUsingFirstChar + "|" + usingFirstChar
      }
    }
    helper(s, "")
  }

  def lcs(a: String, b: String) : String = {
    @tailrec def helper(shorterString: String, longerString: String) : String = {
      if (longerString.contains(shorterString))
        shorterString
      else
        helper(shorterString.substring(0, shorterString.length() - 1), longerString)
    }

    if (a.isEmpty)
      a
    else {
      val usingFirstChar = helper(
        a.substring(0, if (a.length() < b.length) a.length() else b.length()),
        b
      )
      val notUsingFirstChar = lcs(a.substring(1), b)
      if (usingFirstChar.length() > notUsingFirstChar.length()) usingFirstChar else notUsingFirstChar
    }
  }

  def onebits(n: Int) : List[Int] = {
    if (n == 0)
      Nil
    else if (n % 2 == 1)
      0 :: onebits(n >> 1).map(m => m + 1)
    else
      onebits(n >> 1).map(m => m + 1)
  }
}
