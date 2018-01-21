
class Outter {
    private var x: Int = 0
    fun print(i: Inner) = i
    inner class Inner {
        fun test() = x
    }
}

fun main(args: Array<String>) {
    val outter=Outter()
    val inner=outter.Inner()
    val outter2=Outter()
    val inner2=outter2.Inner()
    println(outter.Inner()::class.java)
    println(outter2.Inner()::class.java)
}