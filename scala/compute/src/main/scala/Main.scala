//import com.thoughtworks.compute.gpu._
import com.thoughtworks.compute.cpu._

object Main {
  def main(args: Array[String]): Unit = {
    val my2DArray: Tensor = Tensor(Array(Seq(1.0f, 2.0f, 3.0f), Seq(4.0f, 5.0f, 6.0f)))
    println(my2DArray)
  }
}
