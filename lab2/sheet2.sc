import scala.annotation.tailrec

def concat (list: List[String]): String = {
  @tailrec def helper (list: List[String], str: String): String = {
    if (list.isEmpty)
      str
    else {
      val delimiter = if (list.tail.nonEmpty) " " else ""
      helper(list.tail, str + list.head + delimiter)
    }
  }
  helper(list, "")
}

concat("hi"::"hello"::Nil)
concat(List("San", "José", "State", "University"))