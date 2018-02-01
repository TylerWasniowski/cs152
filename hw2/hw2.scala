import scala.annotation.tailrec

object hw2 {
  def subs(s: String) : String = {
    def helper(str: String, ret: String) : String = {
      if (str.isEmpty)
        ret
      else {
        val notUsingFirstChar = helper(str.substring(1), ret)
        val usingFirstChar = helper(str.substring(1), ret + str.charAt(0))
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

    val shorterString = if (a.length() < b.length()) a else b
    val longerString = if (a != shorterString) a else b

    if (shorterString.isEmpty)
      shorterString
    else {
      val usingFirstChar = helper(shorterString, longerString)
      val notUsingFirstChar = lcs(shorterString.substring(1), longerString)
      if (usingFirstChar.length() > notUsingFirstChar.length()) usingFirstChar else notUsingFirstChar
    }
  }

  def onebits(n: Int) : List[Int] = {
    Nil
  }
}
