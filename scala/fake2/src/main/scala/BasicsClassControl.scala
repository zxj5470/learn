class BasicsClassControl {
	class Outer {

		class Inner {
			private def f() {
				println("f")
			}

			class InnerMost {
				f() // 正确
			}

		}

		//		(new Inner).f() //错误}
	}

}


//在 scala 中，对保护（Protected）成员的访问比 java 更严格一些。
// 因为它只允许保护成员在定义了该成员的的类的子类中被访问。
// 而在java中，用protected关键字修饰的成员，
// 除了定义了该成员的类的子类可以访问，同一个包里的其他类也可以进行访问。
package p{class Super{
	protected def f() {println("f")}
}
	class Sub extends Super{
		f()
	}
	class Other{
//		(new Super).f() //错误
	}}