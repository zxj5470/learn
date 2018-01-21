object HelloWorld {
    def main(args: Array[String]): Unit = {
        1000 ice2333 444 //这也能中缀23333。Scala大法好
        println("afsfdadfsfD" firstIndexOfAny Seq[Char]('f'))
    }
    implicit class AnyExt(self:Object){
        def println()=Predef.println(self.toString)
    }
    implicit class IntExt(self: Int) {
        def pr()=System.out.println(self.toString)
        System.out.println(this)
        def ice1() = s"${self}ice23333"
        def ice2() = f"${self}ice23333"
        def ice3() = raw"${self}ice23333"
        def ice2333(param2: Int): Int = self + param2
    }

    implicit class ExtStr(self: String) {
        def firstIndexOfAny(searchChars: Seq[Char]): Option[Int] = {
            def indexInput = (0 until self.length).zip(self)
            indexInput println()
            val result =
                for (pair <- indexInput; char <- searchChars; if char == pair._2)
                    yield pair._1
            result.headOption
        }
    }

    def firstIndexOfAny(self: String, searchChars: Seq[Char]): Option[Int] = {
        def indexInput = (0 until self.length).zip(self)
        val result =
            for (pair <- indexInput; char <- searchChars; if char == pair._2)
                yield pair._1
        result.headOption
    }
}
