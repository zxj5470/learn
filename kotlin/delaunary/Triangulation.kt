/*
 * Copyright (c) 2005, 2007 by L. Paul Chew.
 *
 * Permission is hereby granted, without written agreement and without
 * license or royalty fees, to use, copy, modify, and distribute this
 * software and its documentation for any purpose, subject to the following
 * conditions:
 *
 * The above copyright notice and this permission notice shall be included
 * in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS
 * OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING
 * FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER
 * DEALINGS IN THE SOFTWARE.
 */

import java.util.*

/**
 * A 2D Delaunay Triangulation (DT) with incremental site insertion.
 *
 * This is not the fastest way to build a DT, but it's a reasonable way to build
 * a DT incrementally and it makes a nice interactive display. There are several
 * O(n log n) methods, but they require that the sites are all known initially.
 *
 * A Triangulation is a Set of Triangles. A Triangulation is unmodifiable as a
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
internal class Triangulation(triangle: Triangle) : ArraySet<Triangle>() {

	private var mostRecent: Triangle? = null      // Most recently "active" triangle
	private val triGraph: Graph<Triangle>        // Holds triangles for navigation

	init {
		triGraph = Graph()
		triGraph.add(triangle)
		mostRecent = triangle
	}

	/* The following two methods are required by AbstractSet */

	override fun iterator(): MutableIterator<Triangle> {
		return triGraph.nodeSet()!!.iterator()
	}

//	override fun size(): Int {
//		return triGraph.nodeSet().size
//	}

	override fun toString(): String {
		return "Triangulation with $size triangles"
	}

//	/**
//	 * True iff triangle is a member of this triangulation.
//	 * This method isn't required by AbstractSet, but it improves efficiency.
//	 * @param triangle the object to check for membership
//	 */
//	override operator fun contains(triangle: Triangulation?): Boolean {
//		return triGraph.nodeSet().contains(triangle)
//	}

	/**
	 * Report neighbor opposite the given vertex of triangle.
	 * @param site a vertex of triangle
	 * @param triangle we want the neighbor of this triangle
	 * @return the neighbor opposite site in triangle; null if none
	 * @throws IllegalArgumentException if site is not in this triangle
	 */
	fun neighborOpposite(site: Pnt, triangle: Triangle): Triangle? {
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
	 * Report triangles surrounding site in order (cw or ccw).
	 * @param site we want the surrounding triangles for this site
	 * @param triangle a "starting" triangle that has site as a vertex
	 * @return all triangles surrounding site in order (cw or ccw)
	 * @throws IllegalArgumentException if site is not in triangle
	 */
	fun surroundingTriangles(site: Pnt, triangle: Triangle): List<Triangle> {
		var tri = triangle
		if (!tri.contains(site))
			throw IllegalArgumentException("Site not in triangle")
		val list = ArrayList<Triangle>()
		val start = tri
		var guide = tri.getVertexButNot(site)        // Affects cw or ccw
		while (true) {
			list.add(tri)
			val previous = tri
			tri = this.neighborOpposite(guide, tri)!! // Next triangle
			guide = previous.getVertexButNot(site, guide)     // Update guide
			if (tri === start) break
		}
		return list
	}

	/**
	 * Locate the triangle with point inside it or on its boundary.
	 * @param point the point to locate
	 * @return the triangle that holds point; null if no such triangle
	 */
	fun locate(point: Pnt): Triangle? {
		var triangle = mostRecent
		if (!this.contains(triangle)) triangle = null

		// Try a directed walk (this works fine in 2D, but can fail in 3D)
		val visited = HashSet<Triangle>()
		while (triangle != null) {
			if (visited.contains(triangle)) { // This should never happen
				println("Warning: Caught in a locate loop")
				break
			}
			visited.add(triangle)
			// Corner opposite point
			val corner = point.isOutside(triangle.toTypedArray()) ?: return triangle
			triangle = this.neighborOpposite(corner, triangle)
		}
		// No luck; try brute force
		println("Warning: Checking all triangles for " + point)
		for (tri in this) {
			if (point.isOutside(tri.toTypedArray()) == null) return tri
		}
		// No such triangle
		println("Warning: No triangle holds " + point)
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
		if (triangle.contains(site)) return

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
		for (vertices in boundary) {
			vertices.add(site)
			val tri = Triangle(vertices)
			triGraph.add(tri)
			newTriangles.add(tri)
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

		/**
		 * Main program; used for testing.
		 */
		@JvmStatic
		fun main(args: Array<String>) {
			val tri = Triangle(Pnt(-10, 10), Pnt(10, 10), Pnt(0, -10))
			println("Triangle created: $tri")
			val dt = Triangulation(tri)
			println("DelaunayTriangulation created: $dt")
			dt.delaunayPlace(Pnt(0, 0))
			dt.delaunayPlace(Pnt(1, 0))
			dt.delaunayPlace(Pnt(0, 1))
			println("After adding 3 points, we have a $dt")
			Triangle.moreInfo = true
			println("Triangles: ${dt.triGraph.nodeSet()}")
		}
	}
}