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

/**
 *
 * @receiver Iterable<T>
 * @param predicate Function1<T, Boolean>
 * @return T?
 */
inline fun <T> Iterable<T>.anyThis(predicate: (T) -> Boolean): T? {
	if (this is Collection && isEmpty()) return null
	for (element in this) if (predicate(element)) return element
	return null
}