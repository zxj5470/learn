import java.util.Arrays

class Triangle(collection: Collection<Pnt>) : ArraySet<Pnt>(collection) {

	private val idNumber: Int                   // The id number
	private var circumCenter: Pnt? = null        // The triangle's circumCenter

	constructor(vararg vertices: Pnt) : this(Arrays.asList<Pnt>(*vertices))

	init {
		idNumber = idGenerator++
		if (this.size != 3)
			throw IllegalArgumentException("Triangle must have 3 vertices")
	}

	override fun toString(): String {
		return if (!moreInfo) "Triangle$idNumber" else "Triangle" + idNumber + super.toString()
	}

	/**
	 * True if triangles are neighbors. Two triangles are neighbors if they
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
	 * @return the triangle's circumCenter
	 */
	fun getCircumCenter(): Pnt {
		return circumCenter?: Pnt.circumcenter(this.toTypedArray())
	}


	override fun iterator(): MutableIterator<Pnt> {
		return object : MutableIterator<Pnt> {
			private val it = super@Triangle.iterator()
			override fun hasNext() = it.hasNext()
			override fun next() = it.next()
			override fun remove() {
				throw UnsupportedOperationException()
			}
		}
	}

	override fun hashCode(): Int {
		return idNumber xor idNumber.ushr(32)
	}

	override fun equals(other: Any?): Boolean {
		return this === other
	}

	companion object {
		private var idGenerator = 0     // Used to create id numbers
		var moreInfo = false // True iff more info in toString
	}

}