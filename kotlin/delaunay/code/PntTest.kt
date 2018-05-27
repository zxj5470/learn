import Pnt.Companion.circumscribedCenter
import Pnt.Companion.determinant

fun main(args: Array<String>) {
	val matrix1 = arrayOf(Pnt(1, 2), Pnt(3, 4))
	val matrix2 = arrayOf(Pnt(7, 0, 5), Pnt(2, 4, 6), Pnt(3, 8, 1))
	print("Results should be -2 and -288: ")
	println("${determinant(matrix1)} ${determinant(matrix2)}")
	val p1 = Pnt(1, 1)
	val p2 = Pnt(-1, 1)
	println("Angle between $p1 and $p2: ${p1.angle(p2)}")
	println("$p1 subtract $p2: ${p1 - p2}")
	val v0 = Pnt(0, 0)
	val v1 = Pnt(1, 1)
	val v2 = Pnt(2, 2)
	val vs = arrayOf(v0, Pnt(0, 1), Pnt(1, 0))
	val vp = Pnt(.1, .1)
	println("$vp 是否在 三角形${vs.str()} 中: ${vp.isInside(vs)}")
	println("$v1 是否在 三角形${vs.str()} 中: ${v1.isInside(vs)}")
	println("$vp vsCircumcircle ${vs.str()}: ${vp.vsCircumcircle(vs)}")
	println("$v1 vsCircumcircle ${vs.str()}: ${v1.vsCircumcircle(vs)}")
	println("$v2 vsCircumcircle ${vs.str()}: ${v2.vsCircumcircle(vs)}")
	println("Circumcenter of ${vs.str()} is ${circumscribedCenter(vs)}")
}