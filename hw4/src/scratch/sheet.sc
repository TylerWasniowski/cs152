val numToLetters = Map("2" -> "ABC", "3" -> "DEF", "4" -> "GHI", "5" -> "JKL", "6" -> "MNO", "7" -> "PRS", "8" -> "TUV", "9" -> "WXY")

val characters = (s: String) => s.toList.map("" + _)
val letters = numToLetters.map(e => (e._1, characters(e._2)))
letters("2")

val cats = (s: List[String], t: List[String]) =>
  s.flatMap(a => t.map(b => a + b))

cats(letters("2"), letters("3"))

val words = io.Source.fromURL("http://horstmann.com/sjsu/spring2018/cs152/words")
  .getLines.filter(w => Character.isLowerCase(w(0)) && w.length > 1)
  .map(_.toUpperCase).toSet + "SCALA"

val wordsForDigits = (digits: String) =>
  characters(digits)
    .map(letters(_))
    .foldLeft(List(""))(cats(_, _))
    .filter(words.contains)

val wordsForDigitsSequence = (seq: List[String]) =>
  seq.map(e => wordsForDigits(e)).reduceLeft(cats)

wordsForDigits("72252")
wordsForDigits("72253")
wordsForDigits("43556")
wordsForDigits("2")
wordsForDigits("")

val catsSpaces = (s: List[String], t: List[String]) =>
  s.flatMap(a => t.map(b => a + b))

wordsForDigitsSequence(List("72252", "47", "386"))

