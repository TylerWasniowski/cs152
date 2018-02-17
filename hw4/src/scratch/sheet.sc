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

wordsForDigits("72252")
wordsForDigits("72253")
wordsForDigits("43556")
wordsForDigits("2")
wordsForDigits("")

val catsSpaces = (s: List[String], t: List[String]) =>
  s.flatMap(a => t.map(b => a + " " + b))

val wordsForDigitsSequence = (seq: List[String]) =>
  seq.map(e => wordsForDigits(e)).reduceLeft(catsSpaces)

wordsForDigitsSequence(List("72252", "47", "386"))

val grow1 = (c: String, substringLists: List[List[String]]) =>
  substringLists.map(c :: _)

val grow2 = (c: String, substringLists: List[List[String]]) =>
  substringLists.map(a => (c + a.head) :: a.tail)

val grow = (c: String, a: List[List[String]]) =>
  grow1(c, a) ++ grow2(c, a)

grow1("1", List(
  List("234"),
  List("23", "4"),
  List("2", "34"),
  List("2", "3", "4")
))

grow2("1", List(
  List("234"),
  List("23", "4"),
  List("2", "34"),
  List("2", "3", "4")
))

grow("1", grow("2", grow("3", List(List("4")))))


val substrings = (s: String) =>
  if (s.isEmpty)
    Nil
  else
    characters(s.substring(0, s.length - 1))
      .foldRight(List(List(s.substring(s.length - 1))))(grow)

substrings("1234")
substrings("2728")
substrings("")
substrings("72252")
substrings("72252").flatMap(wordsForDigitsSequence)

val phoneMnemonics = (digits: String) =>
  substrings(digits).flatMap(wordsForDigitsSequence)

phoneMnemonics("7225247386")