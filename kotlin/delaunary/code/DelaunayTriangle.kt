import java.util.*

/**
 * A 2D Delaunay DelaunayTriangle (DT) with incremental site insertion.
 *
 * This is not the fastest way to build a DT, but it's a reasonable way to build
 * a DT incrementally and it makes a nice interactive display. There are several
 * O(n log n) methods, but they require that the sites are all known initially.
 *
 * A DelaunayTriangle is a Set of Triangles. A DelaunayTriangle is unmodifiable as a
 * Set; the only way to change it is to add sites (via delaunayPlace).
 *
 * @author Paul Chew
 *
 * Created July 2005. Derived from an earlier, messier version.
 *
 * Modified November 2007. Rewrote to use AbstractSet as parent class and to use
 * the Graph class internally. Tried to make the DT algorithm clearer by
 * explicitly creating a cavity.  Added code needed to find a Voronoi cell.
 */
class DelaunayTriangle(triangle: Triangle) : ArraySet<Triangle>() {

	private var mostRecent: Triangle? = null      // Most recently "active" triangle
	private val triGraph: Graph<Triangle> = Graph()        // Holds triangles for navigation

	init {
		triGraph.add(triangle)
		mostRecent = triangle
	}

	/* The following two methods are required by AbstractSet */

	override fun iterator(): MutableIterator<Triangle> {
		return triGraph.nodeSet.iterator()
	}

	override val size
		get() = triGraph.nodeSet.size

	override fun toString(): String {
		return "DelaunayTriangle with $size triangles"
	}

	/**
	 * Report neighbor opposite the given vertex of triangle.
	 * @param site a vertex of triangle
	 * @param triangle we want the neighbor of this triangle
	 * @return the neighbor opposite site in triangle; null if none
	 * @throws IllegalArgumentException if site is not in this triangle
	 */
	private fun neighborOpposite(site: Pnt, triangle: Triangle): Triangle? {
		if (!triangle.contains(site))
			throw IllegalArgumentException("Bad vertex; not in triangle")
		for (neighbor in triGraph.neighbors(triangle)) {
			if (!neighbor.contains(site)) return neighbor
		}
		return null
	}

	/**
	 * Return the set of triangles adjacent to triangle.
	 * @param triangle the triangle to check
	 * @return the neighbors of triangle
	 */
	fun neighbors(triangle: Triangle): Set<Triangle> {
		return triGraph.neighbors(triangle)
	}


	/**
	 * Locate the triangle with point inside it or on its boundary.
	 * @param point the point to locate
	 * @return the triangle that holds point; null if no such triangle
	 */
	private fun locate(point: Pnt): Triangle? {
		var triangle = mostRecent
		if (!this.contains(triangle)) triangle = null

		val visited = HashSet<Triangle>()
		while (triangle != null) {
			if (visited.contains(triangle)) { // This should never happen
				println("Warning: Caught in a locate loop")
				break
			}
			visited += triangle
			val corner = point.isOutside(triangle.toTypedArray()) ?: return triangle
			triangle = this.neighborOpposite(corner, triangle)
		}
		println("Warning: Checking all triangles for $point")
		for (tri in this) {
			if (point.isOutside(tri.toTypedArray()) == null) return tri
		}
		println("Warning: No triangle holds $point")
		return null
	}

	/**
	 * Place a new site into the DT.
	 * Nothing happens if the site matches an existing DT vertex.
	 * @param site the new Pnt
	 * @throws IllegalArgumentException if site does not lie in any triangle
	 */
	fun delaunayPlace(site: Pnt) {
		// Uses straightforward scheme rather than best asymptotic time

		// Locate containing triangle
		val triangle = locate(site) ?: throw IllegalArgumentException("No containing triangle")
		// Give up if no containing triangle or if site is already in DT
		if (site in triangle) return

		// Determine the cavity and update the triangulation
		val cavity = getCavity(site, triangle)
		mostRecent = update(site, cavity)
	}

	/**
	 * Determine the cavity caused by site.
	 * @param site the site causing the cavity
	 * @param triangle the triangle containing site
	 * @return set of all triangles that have site in their circumcircle
	 */
	private fun getCavity(site: Pnt, triangle: Triangle): Set<Triangle> {
		var tri = triangle
		val encroached = HashSet<Triangle>()
		val toBeChecked = LinkedList<Triangle>()
		val marked = HashSet<Triangle>()
		toBeChecked.add(tri)
		marked.add(tri)
		while (!toBeChecked.isEmpty()) {
			tri = toBeChecked.remove()
			if (site.vsCircumcircle(tri.toTypedArray()) == 1)
				continue // Site outside triangle => triangle not in cavity
			encroached.add(tri)
			// Check the neighbors
			for (neighbor in triGraph.neighbors(tri)) {
				if (marked.contains(neighbor)) continue
				marked.add(neighbor)
				toBeChecked.add(neighbor)
			}
		}
		return encroached
	}

	/**
	 * Update the triangulation by removing the cavity triangles and then
	 * filling the cavity with new triangles.
	 * @param site the site that created the cavity
	 * @param cavity the triangles with site in their circumcircle
	 * @return one of the new triangles
	 */
	private fun update(site: Pnt, cavity: Set<Triangle>): Triangle {
		val boundary = HashSet<ArraySet<Pnt>>()
		val theTriangles = HashSet<Triangle>()

		// Find boundary facets and adjacent triangles
		for (triangle in cavity) {
			theTriangles.addAll(neighbors(triangle))
			triangle
					.map { triangle.facetOpposite(it) }
					.forEach {
						if (boundary.contains(it))
							boundary.remove(it)
						else
							boundary.add(it)
					}
		}
		theTriangles.removeAll(cavity)        // Adj triangles only

		// Remove the cavity triangles from the triangulation
		for (triangle in cavity) triGraph.remove(triangle)

		// Build each new triangle and add it to the triangulation
		val newTriangles = HashSet<Triangle>()
		boundary.forEach {
			it.add(site)
			Triangle(it).apply {
				triGraph.add(this)
				newTriangles.add(this)
			}
		}

		// Update the graph links for each new triangle
		theTriangles.addAll(newTriangles)    // Adj triangle + new triangles
		newTriangles.forEach { triangle ->
			theTriangles
					.filter { triangle.isNeighbor(it) }
					.forEach { triGraph.add(triangle, it) }
		}

		// Return one of the new triangles
		return newTriangles.iterator().next()
	}

	companion object {
		@JvmStatic
		fun main(args: Array<String>) {
			val tri = Triangle(Pnt(-10, 10), Pnt(10, 10), Pnt(0, -10))
			println("Triangle created: $tri")
			val dt = DelaunayTriangle(tri)
			println("DelaunayTriangulation created: $dt")
			dt.delaunayPlace(Pnt(0, 0))
			dt.delaunayPlace(Pnt(1, 0))
			dt.delaunayPlace(Pnt(0, 1))
			println("After adding 3 points, we have a $dt")
			Triangle.moreInfo = true
			println("Triangles: ${dt.triGraph.nodeSet}")
		}
	}
}