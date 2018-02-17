import org.scalatest.FunSuite
import hw4._

class MyPhoneSuite extends FunSuite {
  test("cats and letters function") {
    assert(cats(letters("2"), letters("3")).toSet
      == Set("AD", "BD", "CD", "AE", "BE", "CE", "AF", "BF", "CF"))
  }

  test("wordsForDigits") {
    assert(wordsForDigits("72252") == List("SCALA"))
  }

  test("catsSpaces") {
    assert(catsSpaces(letters("2"), letters("3")).toSet
      == Set("A D", "B D", "C D", "A E", "B E", "C E", "A F", "B F", "C F"))
    assert(catsSpaces(letters("2"), List("Hi", "Yo")).toSet
      == Set("A Hi", "B Hi", "C Hi", "A Yo", "B Yo", "C Yo"))
  }

  test("wordsForDigitsSequence") {
    assert(wordsForDigitsSequence(List("72252", "47", "386"))
      == List("SCALA GS DUN", "SCALA GS DUO", "SCALA GS FUN", "SCALA IS DUN", "SCALA IS DUO", "SCALA IS FUN"))
  }

  test("grow1") {
    assert(grow1("1", List(
      List("234"),
      List("23", "4"),
      List("2", "34"),
      List("2", "3", "4")
    )) == List(
      List("1", "234"),
      List("1", "23", "4"),
      List("1", "2", "34"),
      List("1", "2", "3", "4")
    ))
  }

  test("grow2") {
    assert(grow2("1", List(
      List("234"),
      List("23", "4"),
      List("2", "34"),
      List("2", "3", "4")
    )) == List(
      List("1234"),
      List("123", "4"),
      List("12", "34"),
      List("12", "3", "4")
    ))
  }

  test("grow") {
    assert(grow("1", List(
      List("234"),
      List("23", "4"),
      List("2", "34"),
      List("2", "3", "4")
    )) == List(
      List("1", "234"),
      List("1", "23", "4"),
      List("1", "2", "34"),
      List("1", "2", "3", "4"),
      List("1234"),
      List("123", "4"),
      List("12", "34"),
      List("12", "3", "4")
    ))
  }

  test("substrings") {
    assert(substrings("1234") == List(
      List("1", "2", "3", "4"),
      List("1", "2", "34"),
      List("1", "23", "4"),
      List("1", "234"),
      List("12", "3", "4"),
      List("12", "34"),
      List("123", "4"),
      List("1234")
    ))
  }

  test("phoneMnemonics") {
    assert(phoneMnemonics("7225247386").contains("SCALA IS FUN"))
    assert(phoneMnemonics("4355696753").contains("HELLO WORLD"))
  }


}
