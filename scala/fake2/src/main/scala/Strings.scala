object Strings {
	def main(args: Array[String]): Unit = {
		val palindrome = "www.runoob.com"
		val len = palindrome.length
		println("String Length is : " + len)

		var floatVar = 12.456
		var intVar = 2000
		var stringVar = "阿里云大学!"
		var fs = printf("浮点型变量为 " +
			"%f, 整型变量为 %d, 字符串为 " +
			" %s", floatVar, intVar, stringVar)
		println(fs)

	}
}
