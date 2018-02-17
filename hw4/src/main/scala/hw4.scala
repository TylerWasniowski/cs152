import scala.annotation.tailrec
import scala.util.matching.Regex

object hw4 {
  def firstMatch(input: String, patterns: List[Regex]) : String = {
    if (patterns.isEmpty)
      ""
    else
      patterns.head.findPrefixOf(input)
        .getOrElse(firstMatch(input, patterns.tail))
  }

  def tokens(input: String, reported: List[Regex], ignored: List[Regex]) : (List[String], Int) = {
    @tailrec def helper(input: String, partialResult: (List[String], Int)) : (List[String], Int) = {
      val firstReportedMatch = firstMatch(input, reported)

      if (firstReportedMatch.isEmpty) {
        val firstIgnoredMatch = firstMatch(input, ignored)

        if (firstIgnoredMatch.isEmpty)
          if (input.isEmpty)
            (partialResult._1, -1)
          else
            partialResult
        else
          helper(input.substring(firstIgnoredMatch.length),
            (partialResult._1, partialResult._2 + firstIgnoredMatch.length))

      } else {
        helper(input.substring(firstReportedMatch.length),
          (partialResult._1 :+ firstReportedMatch , partialResult._2 + firstReportedMatch.length))
      }
    }

    helper(input, (Nil, 0))
  }

  // Phone problem
  val numToLetters = Map("2" -> "ABC", "3" -> "DEF", "4" -> "GHI", "5" -> "JKL", "6" -> "MNO", "7" -> "PRS", "8" -> "TUV", "9" -> "WXY")
  val words = io.Source.fromURL("http://horstmann.com/sjsu/spring2018/cs152/words")
    .getLines.filter(w => Character.isLowerCase(w(0)) && w.length > 1)
    .map(_.toUpperCase).toSet + "SCALA"

  val characters = (s: String) => s.toList.map("" + _)
  val letters = numToLetters.map(e => (e._1, characters(e._2)))

  val cats = (s: List[String], t: List[String]) => s.flatMap(a => t.map(b => a + b))

  val wordsForDigits = (digits: String) =>
    characters(digits)
      .map(letters)
      .reduceLeft(cats)
      .filter(words.contains)

  val catsSpaces = (s: List[String], t: List[String]) =>
    s.flatMap(a => t.map(b => a + " " + b))

  val wordsForDigitsSequence = (seq: List[String]) =>
    seq.map(e => wordsForDigits(e)).reduceLeft(catsSpaces)

  val grow1 = (c: String, substringLists: List[List[String]]) =>
    substringLists.map(c :: _)

  val grow2 = (c: String, substringLists: List[List[String]]) =>
    substringLists.map(a => (c + a.head) :: a.tail)

  val grow = (c: String, a: List[List[String]]) =>
    grow1(c, a) ++ grow2(c, a)

  val substrings = (s: String) =>
    if (s.isEmpty)
      Nil
    else
      characters(s.substring(0, s.length - 1))
        .foldRight(List(List(s.substring(s.length - 1))))(grow)

  val phoneMnemonics = (digits: String) =>
    substrings(digits).flatMap(wordsForDigitsSequence)
}
