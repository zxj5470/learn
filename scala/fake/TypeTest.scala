import scala.reflect.runtime.universe.typeOf
class Outter{
    private var x:Int=0
    def print(i:Inner)=i

    class Inner{
        def test()=x
    }
}

object TypeTest extends App{
    val outter=new Outter
    val inner=new outter.Inner
    val outter2=new Outter
    val inner2=new outter2.Inner
    //下面的代码编译会失败
    //outter.print(inner2)
    //这是因为不同outter对象对应的内部类成员类型是不一样的
    //这就跟两个类成员的实例它们内存地址不一样类似

    //这也进一步说明了它们类型是不一样的
    println(typeOf[outter.Inner],typeOf[outter2.Inner])
}