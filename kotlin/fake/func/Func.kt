import java.util.*

fun main(args: Array<String>) {
    23333.print
    val str = "wjdghd"
    val count = str.countIndex("d")//2
    val list = str.indexToList("d")//{2,5}
    list.print
    println(count)// 2
    println(list)// 2
}

inline val Any.print: Unit
    get() = this.print()

fun Any.print() = println(this.toString())

fun String.countIndex(r: String): Int {
    var count = 0
    var temp = -1
    var b = -1
    this.indices.forEach {
        b = this.indexOf(r, it)
        if (b > temp) {
            temp = b
            count++
        }
    }
    return count
}

inline fun String.indexToList(r: String): List<Int> {
    val ll = LinkedList<Int>()
    var temp = -1
    var b = -1
    this.indices.forEach {
        b = this.indexOf(r, it)
        if (b > temp) {
            temp = b
            ll.add(temp)
        }
    }
    return ll.toList()
}

inline val String.numeric: Boolean
    get() = this.matches(Regex("\\d+"))

inline fun String.isNumeric() = this.matches(Regex("\\d+"))