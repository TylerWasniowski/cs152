// Tracing code for interview practice
// helper("Amy", "") = helper("my", "") + "|" + helper("my", "A")
// helper("my", "") = helper("y", "") + "|" + helper("y", "m") = "|y|m|my"

object hw2 {
  def subs(s: String) : String = {
    def helper(s: String, ret: String) : String = {
      if (s.isEmpty)
        ret
      else {
        val notUsingFirstChar = helper(s.substring(1), ret)
        val usingFirstChar = helper(s.substring(1), ret + s.substring(0))
        usingFirstChar + "|" + notUsingFirstChar
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
