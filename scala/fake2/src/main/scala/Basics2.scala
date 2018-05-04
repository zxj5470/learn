//	Java
//	import java.awt.*

import java.awt._

// import语句可以出现在任何地方，
// 而不是只能在文件顶部。
// import的效果从开始延伸到语句块的结束。
// 这可以大幅减少名称冲突的可能性。
import java.awt.{Color, Font}

//默认情况下，Scala 总会引入 java.lang._ 、 scala._ 和 Predef._
object Basics2 {
	def main(args: Array[String]): Unit = {
		// byte
		val a: Byte = 'a'

		// char	16位无符号Unicode字符, 区间值为 U+0000 到 U+FFFF
		val ca = 'a'
		println(a.getClass)
		println(ca.getClass)
		//AnyRef	AnyRef类是Scala里所有引用类(reference class)的基类

		val xmax, ymax = 100 // xmax, ymax都声明为100
		// 100
		// 100
		println(xmax)
		println(ymax)

		val pa: (Int, String) = (40, "Foo")
		// (40,Foo)
		println(pa)
	}
}