fun <T> T.whether(a:(T)->Boolean):Boolean=a(this)
fun Boolean.then(a:(Boolean)->Unit):Boolean{if(this)a(this);return this}
fun Boolean.ifelse(a:(Boolean)->Unit):Boolean{if(!this)a(this);return this}

fun main(args: Array<String>) {
    "emmmmm".whether{
        it.length>10
    }.then {
        println("gt 10")
    }.ifelse{
        println("lt 10")
    }
}