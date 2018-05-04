object Basics {
	def main(args: Array[String]): Unit = {
		def addOne(m: Int): Int = m + 1

		val two = 1 + 1
		println(two)
		var name = "steve"
		println(name)
		name = "marius"
		println(name)

		val three = addOne(2)

		def thr() = addOne(2)

		println(three)
		println(thr)
		val lambda = (x: Int) => x + 1
		println(lambda.getClass)
	}
}
