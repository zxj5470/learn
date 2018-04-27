import java.awt.Graphics2D
import java.awt.RenderingHints
import javax.swing.UIManager

val Pnt.x
	get() = this[0]

val Pnt.y
	get() = this[1]

fun Graphics2D.antiAliasing() = this.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON)

val Triangle.p1: Pnt
	get() = this[0]

val Triangle.p2: Pnt
	get() = this[1]

val Triangle.p3: Pnt
	get() = this[2]

fun useSystemUI() = UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName())

fun DoubleArray.toPnt() = Pnt(*this)

/**
 *
 * @receiver Array<Pnt> matrix the matrix (an array of Pnts)
 * @return String representation of the matrix
 */
fun Array<Pnt>.str(): String {
	return buildString {
		append("{ ")
		this@str.joinToString(" ") { "$it" }.let(::append)
		append(" }")
	}
}

fun Array<Pnt>.get(i:Int,j:Int) = this[i].coordinates[j]