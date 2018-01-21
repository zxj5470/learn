# LearnToUseKotlin
first head for Kotlin
:joy: :joy: :joy:

# In Kotlin
````kotlin
package cn.wjdghd

fun add(a: Int, b: Int): String {
    return "${a + b}"
}
fun printSum(a: Int, b: Int) {//return void
    println("sum of $a and $b is ${a + b}")
}
//learn to use 'when' instead of 'switch'

fun main(args: Array<String>) {
    val a: Int = 10
    val b: Int = 10
    println(add(a, b))
    printSum(233, 3)
    System.out.println(Main.javaAdd(2, 3))
    val c=200
    when (c) {
            100 -> println("1")
            250, 200 -> println("2")
            else -> println("fvck")
    }
}
    
    
````

# In Java
````java
package cn.wjdghd;
public class Main {
    
    static int javaAdd(int a,int b){
        return a+b;
    }
    
    public static void main(String[] args) {
        System.out.println(javaAdd(1,2));
        //Kotlin file(class)name is 'MainKt' for 'Main.kt'
        MainKt.printSum(1,2);
        System.out.println("Kotlin is as follow");
        MainKt.main(new String[]{});
    }
}

````
# Kotlin function extension
```kotlin
fun String.parseToPoint(): Point {
    val index = this.indexOf(',')
    return Point(
            this.substring(0, index).toInt(),
            this.substring(index + 1, this.length).toInt()
    )
}
```
# Learn to use GSON and OkHttp
```kotlin

fun testURL(paramsMap: HashMap<String, String>): String {
    val tempParams = StringBuilder()
    var pos = 0
    for ((key, value) in paramsMap) {
        if (pos > 0) {
            tempParams.append("&")
        }
        tempParams.append("$key=${URLEncoder.encode(value, "utf-8")}")
        pos++
    }
    return tempParams.toString()
}



data class BDLocation(val lng:String,val lat:String)
data class BDResult(val location: BDLocation,val precise:String,val confidence:String,val level:String)
data class BDMap(val status:String,val result:BDResult)

val paramsMap = HashMap<String, String>()
val callback = getHttp(requestURL + testURL(paramsMap))
val json=callback.subSequence(callback.indexOf("(") + 1, callback.indexOf(")")).toString()
val results = Gson().fromJson(json, BDMap::class.java)
// println(results)
```
# Learn to use Thread with lambda expression
```kotlin
fun f(){
    Thread{println(23333)}.start()
}
```
```java
public class ThreadTest {
    static void n(){
        new Thread(()->System.out.println(23333)).start();
    }
}
```
# Learn to use infix expression
```kotlin
class My(var ii: Int) {
    infix fun s(a: Int): Int {
        return ii + a * 10
    }
}
fun main(args: Array<String>) {
    val ss = My(10)
    //both are OK.
    val result1=ss.s(2)
    val result2=ss s 2
    println("$result1,$result2")
    //30,30
}
```

RxJava（RxKotlin）
```kotlin
import io.reactivex.Flowable
import io.reactivex.schedulers.Schedulers
fun main(args: Array<String>) {
    Flowable.just("Hello Kt").subscribe { println(it) }         //Kt大法好
    Flowable.fromCallable {
        Thread.sleep(1000) //  imitate expensive computation
        "Done"
    }
            .subscribeOn(Schedulers.io())
            .observeOn(Schedulers.single())
            .subscribe({ println(it) }, { it.printStackTrace() })
    Thread.sleep(2000)
}
```