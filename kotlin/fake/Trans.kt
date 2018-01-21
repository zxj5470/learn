fun main(args: Array<String>) {
    val employees = listOf("neal", "s", "stu", "j", "rich", "bob", "aiden")
    val result = employees
            .filter { it.length > 1 }
            .map { it.capitalize() }
            .reduce { acc, s -> acc + "," + s }
    println(result)
    //Neal,Stu,Rich,Bob,Aiden
//    println("afdsgf"["1:"])
//    println("afdsgf"[":3"])
//    println("afdsgf"["2:"])
//    println("afdsgf"[":1"])
//    println("afdsgf"[":"])
}
val String.isNotEmpty:Boolean
    get() = this.isNotEmpty()

operator fun String.get(i: String): String {

    fun String.toIndex()=if(this.isNotEmpty)this.toInt();else 0

    val splt = i.split(':')
    println(splt.size)
    val begin = if(splt[0].isNotEmpty)splt[0].toInt();else 0
    val end = if(splt[1].isNotEmpty)splt[1].toInt();else this.length
    return this.substring(begin,end)
}
