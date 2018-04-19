/*
 * Copyright (c) 2007 by L. Paul Chew.
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

import java.util.Arrays
import java.util.NoSuchElementException

/**
 * A Triangle is an immutable Set of exactly three Pnts.
 *
 * All Set operations are available. Individual vertices can be accessed via
 * iterator() and also via triangle.get(index).
 *
 * Note that, even if two triangles have the same vertex set, they are
 * *different* triangles. Methods equals() and hashCode() are consistent with
 * this rule.
 *
 * @author Paul Chew
 *
 * Created December 2007. Replaced general simplices with geometric triangle.
 */
internal class Triangle
/**
 * @param collection a Collection holding the Simplex vertices
 * @throws IllegalArgumentException if there are not three distinct vertices
 */
(collection: Collection<Pnt>) : ArraySet<Pnt>(collection) {

	private val idNumber: Int                   // The id number
	private var circumcenter: Pnt? = null        // The triangle's circumcenter

	/**
	 * @param vertices the vertices of the Triangle.
	 * @throws IllegalArgumentException if there are not three distinct vertices
	 */
	constructor(vararg vertices: Pnt) : this(Arrays.asList<Pnt>(*vertices)) {}

	init {
		idNumber = idGenerator++
		if (this.size != 3)
			throw IllegalArgumentException("Triangle must have 3 vertices")
	}

	override fun toString(): String {
		return if (!moreInfo) "Triangle" + idNumber else "Triangle" + idNumber + super.toString()
	}

	/**
	 * Get arbitrary vertex of this triangle, but not any of the bad vertices.
	 * @param badVertices one or more bad vertices
	 * @return a vertex of this triangle, but not one of the bad vertices
	 * @throws NoSuchElementException if no vertex found
	 */
	fun getVertexButNot(vararg badVertices: Pnt): Pnt {
		val bad = Arrays.asList(*badVertices)
		for (v in this) if (!bad.contains(v)) return v
		throw NoSuchElementException("No vertex found")
	}

	/**
	 * True iff triangles are neighbors. Two triangles are neighbors if they
	 * share a facet.
	 * @param triangle the other Triangle
	 * @return true iff this Triangle is a neighbor of triangle
	 */
	fun isNeighbor(triangle: Triangle): Boolean {
		var count = 0
		for (vertex in this)
			if (!triangle.contains(vertex)) count++
		return count == 1
	}

	/**
	 * Report the facet opposite vertex.
	 * @param vertex a vertex of this Triangle
	 * @return the facet opposite vertex
	 * @throws IllegalArgumentException if the vertex is not in triangle
	 */
	fun facetOpposite(vertex: Pnt): ArraySet<Pnt> {
		val facet = ArraySet(this)
		if (!facet.remove(vertex))
			throw IllegalArgumentException("Vertex not in triangle")
		return facet
	}

	/**
	 * @return the triangle's circumcenter
	 */
	fun getCircumcenter(): Pnt {
		return circumcenter?: Pnt.circumcenter(this.toTypedArray())
	}

	/* The following two methods ensure that a Triangle is immutable */

	fun add(vertex: Pnt?): Boolean {
		throw UnsupportedOperationException()
	}

	override fun iterator(): MutableIterator<Pnt> {
		return object : MutableIterator<Pnt> {
			private val it = super@Triangle.iterator()
			override fun hasNext(): Boolean {
				return it.hasNext()
			}

			override fun next(): Pnt {
				return it.next()
			}

			override fun remove() {
				throw UnsupportedOperationException()
			}
		}
	}

	/* The following two methods ensure that all triangles are different. */

	override fun hashCode(): Int {
		return idNumber xor idNumber.ushr(32)
	}

	override fun equals(o: Any?): Boolean {
		return this === o
	}

	companion object {

		private var idGenerator = 0     // Used to create id numbers
		var moreInfo = false // True iff more info in toString
	}

}