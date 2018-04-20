import java.awt.Graphics2D
import java.awt.RenderingHints

val Pnt.x
	get() = this[0]

val Pnt.y
	get() = this[1]

fun Graphics2D.antiAliasing() = this.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON)

val Triangle.p1
	get() = this[0]

val Triangle.p2
	get() = this[1]

val Triangle.p3
	get() = this[2]