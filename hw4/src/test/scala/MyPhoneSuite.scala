import org.scalatest.FunSuite
import hw4._

class MyPhoneSuite extends FunSuite {
  test("cats function") {
    assert(cats(letters("2"), letters("3")).toSet
      == Set("AD", "BD", "CD", "AE", "BE", "CE", "AF", "BF", "CF"))
  }
}
