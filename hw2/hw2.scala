// Tracing code for interview practice
// helper("Amy", "") = helper("my", "") + "|" + helper("my", "A")
// helper("my", "") = helper("y", "") + "|" + helper("y", "m") = "|y|m|my"

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
    ""
  }

  def onebits(n: Int) : List[Int] = {
    Nil
  }
}
