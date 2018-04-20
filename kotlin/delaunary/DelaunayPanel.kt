import java.awt.*
import javax.swing.JPanel

/**
 * Graphics Panel for DelaunayMain.
 */
class DelaunayPanel(private val parent: DelaunayMain) : JPanel() {
	private var all: DelaunayTriangle
	private lateinit var g: Graphics2D
	private var initialTriangle = Triangle(
			Pnt(-initCoord, -initCoord),
			Pnt(initCoord, -initCoord),
			Pnt(0, initCoord))

	init {
		all = DelaunayTriangle(initialTriangle)
	}

	/**
	 * Add a new site to the DT.
	 * @param p the site to add
	 */
	fun addPointToGraph(p: Pnt) {
		all.delaunayPlace(p)
	}

	/**
	 * Re-initialize the DT.
	 */
	fun clear() {
		all = DelaunayTriangle(initialTriangle)
	}

	/* Basic Drawing Methods */

	/**
	 * Draw a point.
	 * @param point the Pnt to drawPoint
	 */
	private fun drawPoint(point: Pnt) {
		val r = pointRadius
		val x = point.x.toInt()
		val y = point.y.toInt()
		g.fillOval(x - r, y - r, r + r, r + r)
	}

	/**
	 * Draw a polygon.
	 * @param polygon an array of polygon vertices
	 */
	private fun drawSingleDelaunary(polygon: Array<Pnt>) {
		// filter initCoord by Y is not initCoord
		polygon.filterNot {
			it.y == initCoordD || it.y == -initCoordD
		}.let { poly ->
			val xInts = poly.map { it.x.toInt() }.toIntArray()
			val yInts = poly.map { it.y.toInt() }.toIntArray()
			g.color = Color.white
			// drawSingleDelaunary vertex points
			poly.forEach(this::drawPoint)
			// drawSingleDelaunary polygons
			g.drawPolygon(xInts, yInts, poly.size)
		}
	}

	private fun drawAllCircumscribedCircles() {
		val stroke = g.stroke
		val color = g.color
		g.color = Color.black
		// dotted line
		g.stroke = BasicStroke(1.0f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 1.0f, floatArrayOf(2f, 0f, 2f), 2f)
		// filter INF value
		all.filter {
			!it.any { it.y == initCoordD || it.y == -initCoordD }
		}.forEach {
			val center = it.getCircumCenter()
			val radius = center.subtract(it[0]).distanceEuclidean()
			drawCircle(center, radius)
		}
		g.stroke = stroke
		g.color = color
	}

	private inline fun drawCircle(center: Pnt, radius: Double) {
		val x = center.x.toInt()
		val y = center.y.toInt()
		val r = radius.toInt()
		g.drawOval(x - r, y - r, r + r, r + r)
	}

	public override fun paintComponent(g: Graphics) {
		super.paintComponent(g)
		this.g = g as Graphics2D
		g.antiAliasing()

		// draw background
		drawBackground()

		// draw triangles
		drawAllDelaunay()

		// whether to Circumscribed Circles
		if (parent.showCircle) {
			drawAllCircumscribedCircles()
		}
	}

	private fun drawBackground() {
		val color = g.color
		g.color = tianyiBlue
		g.fillRect(0, 0, this.width, this.height)
		g.color = color
	}

	private fun drawAllDelaunay() {
		val color = g.color
		g.color = Color.white
		all.forEach {
			drawSingleDelaunary(it.toTypedArray())
		}
		g.color = color
	}

	companion object {
		val tianyiBlue = Color(0x66, 0xcc, 0xff)
		private const val initCoord: Int = 0xcafe
		private const val initCoordD = 0xcafe.toDouble()
		var pointRadius = 3
	}

}
