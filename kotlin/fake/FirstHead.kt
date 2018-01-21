fun printSum(a: Int, b: Int) {//return void
    println("sum of $a and $b is ${a + b}")
}

fun main(args: Array<String>) {
    val a: Int = 10
    val b: Int = 10
    //Use method in Java Class `Main`
    val c = 200

    //learn to use 'when' instead of 'switch'
    when (c) {
        in a..Integer.MAX_VALUE -> println("1")
        250, 200 -> println("2")
        else -> println("default")
    }
    A.S1()
    A.double(2.0)
    B.S2()

    val s=2
    println(s ext 2)
}

class A {
    companion object {
        fun S1() = println(23333)
        fun double(d: Double) = d * 2
    }
}

object B {
    @JvmStatic
    fun S2() = println(23333)
}

infix fun Int.ext(i:Int)=this*i