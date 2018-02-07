object hw2test extends App {
  println(hw2.subs("Amy"))
  println(hw2.subs("Tyler"))
  println(hw2.lcs("permission", "cruise missile") + "| Expected: \"missi\"")
  println(hw2.lcs("emissions", "mississippi") + "| Expected: \"missi\"")
  println(hw2.lcs("Horstmann", "Horse") + "| Expected: \"Hors\"")
  println(hw2.lcs("Yogurt", "Television") + "| Expected: \"o\"")
  println(hw2.lcs("", "") + "| Expected: \"\"")
  println(hw2.lcs("Colon", "Zebra") + "| Expected: \"\"")
  println(hw2.lcs("Mary had a little lamb", "Its fleece was white as snow") + "| Expected: \"e\"")
  println(hw2.onebits(13) + "| Expected: List(0, 2, 3)")
  println(hw2.onebits(-5) + "| Expected: List(0, 2, 3)")
  // your other test cases
}