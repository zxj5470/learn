object Trans {
    def main(args: Array[String]): Unit = {
        val employees = List("neal", "s", "stu", "j", "rich", "bob", "aiden")
        val result = employees
            .filter(_.length > 1)
            .map(_.capitalize) //首字母大写
            .reduce(_ + "," + _)
        println(result)
        //Neal,Stu,Rich,Bob,Aiden
        val setOf1=Set("233","wre23333",123)
        val pl=(false,"1lkjfasd")
        println(setOf1)
        println(pl)
        val x:Option[Int] = Some(5)
        println(x)
    }
}