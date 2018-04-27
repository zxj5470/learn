/**
 *
 * @property coordinates DoubleArray
 * @property x Double
 * @property y Double
 * @constructor
 */
class Pnt(vararg coords: Double) {

	private val coordinates: DoubleArray = DoubleArray(coords.size)

	init {
		/**
		 * from coords to `coordinates`
		 */
		System.arraycopy(coords, 0, coordinates, 0, coords.size)
	}

	constructor(vararg coordsInt: Int) : this(coords = *coordsInt.map(Int::toDouble).toDoubleArray())

	override fun toString(): String {
		if (coordinates.isEmpty()) return "Pnt()"
		return buildString {
			append("Pnt(")
			append(coordinates.joinToString(","))
			append(")")
		}
	}

	override fun equals(other: Any?): Boolean {
		if (other !is Pnt) return false
		if (this.coordinates.size != other.coordinates.size) return false
		return this.coordinates.indices.none { this.coordinates[it] != other.coordinates[it] }
	}

	override fun hashCode(): Int {
		var hash = 0
		for (c in this.coordinates) {
			val bits = java.lang.Double.doubleToLongBits(c)
			hash = 31 * hash xor (bits xor (bits shr 32)).toInt()
		}
		return hash
	}

	/**
	 * @return the specified coordinate of this Pnt
	 * @throws ArrayIndexOutOfBoundsException for bad coordinate
	 */
	operator fun get(i: Int): Double = this.coordinates[i]

	/**
	 * @return this Pnt's dimension.
	 */
	fun dimension(): Int {
		return coordinates.size
	}

	/**
	 * Check that dimensions match.
	 * @param p the Pnt to check (against this Pnt)
	 * @return the dimension of the Pnts
	 * @throws IllegalArgumentException if dimension fail to match
	 */
	fun dimCheck(p: Pnt): Int {
		val len = this.coordinates.size
		if (len != p.coordinates.size)
			throw IllegalArgumentException("Dimension mismatch")
		return len
	}

	/**
	 * Create a new Pnt by adding additional coordinates to this Pnt.
	 * @param coords the new coordinates (added on the right end)
	 * @return a new Pnt with the additional coordinates
	 */
	fun extend(vararg coords: Double): Pnt {
		val result = DoubleArray(coordinates.size + coords.size)
		System.arraycopy(coordinates, 0, result, 0, coordinates.size)
		System.arraycopy(coords, 0, result, coordinates.size, coords.size)
		return Pnt(*result)
	}

	/**
	 * Dot product.
	 * @param p the other Pnt
	 * @return dot product of this Pnt and p
	 */
	private fun dot(p: Pnt): Double {
		val len = dimCheck(p)
		return (0 until len).sumByDouble { coordinates[it] * p.coordinates[it] }
	}

	/**
	 * @return the Euclidean length of this vector
	 */
	fun distanceEuclidean(): Double {
		return Math.sqrt(this.dot(this))
	}

	/**
	 * Subtract.
	 * @param p the other Pnt
	 * @return a new Pnt = this - p
	 */
	fun subtract(p: Pnt): Pnt {
		dimCheck(p)
		return coordinates.mapIndexed { index, d ->
			d - p.coordinates[index]
		}.toDoubleArray().let(::Pnt)
	}

	/**
	 * Add.
	 * @param p the other Pnt
	 * @return a new Pnt = this + p
	 */
	private fun add(p: Pnt): Pnt {
		val len = dimCheck(p)
		return (0 until len).map {
			this.coordinates[it] + p.coordinates[it]
		}.toDoubleArray().let(::Pnt)
	}

	/**
	 * Angle (in radians) between two Pnts (treated as vectors).
	 * @param p the other Pnt
	 * @return the angle (in radians) between the two Pnts
	 */
	fun angle(p: Pnt): Double {
		return Math.acos(this.dot(p) / (this.distanceEuclidean() * p.distanceEuclidean()))
	}

	/**
	 * Perpendicular bisector of two Pnts.
	 * Works in any dimension.  The coefficients are returned as a Pnt of one
	 * higher dimension (e.g., (A,B,C,D) for an equation of the form
	 * Ax + By + Cz + D = 0).
	 * @param point the other point
	 * @return the coefficients of the perpendicular bisector
	 */
	fun bisector(point: Pnt): Pnt {
		dimCheck(point)
		val diff = this.subtract(point)
		val sum = this.add(point)
		val dot = diff.dot(sum)
		return diff.extend(-dot / 2)
	}

	/**
	 * Relation between this Pnt and a simplex (represented as an array of
	 * Pnts). Result is an array of signs, one for each vertex of the simplex,
	 * indicating the relation between the vertex, the vertex's opposite facet,
	 * and this Pnt.
	 *
	 * <pre>
	 * -1 means Pnt is on same side of facet
	 * 0 means Pnt is on the facet
	 * +1 means Pnt is on opposite side of facet
	</pre> *
	 *
	 * @param simplex an array of Pnts representing a simplex
	 * @return an array of signs showing relation between this Pnt and simplex
	 * @throws IllegalArgumentException if the simplex is degenerate
	 */
	private fun relation(simplex: Array<Pnt>): IntArray {
		/* In 2D, we compute the cross of this matrix:
         *    1   1   1   1
         *    p0  a0  b0  c0
         *    getP1  a1  b1  c1
         * where (a, b, c) is the simplex and p is this Pnt. The result is a
         * vector in which the first coordinate is the signed area (all signed
         * areas are off by the same constant factor) of the simplex and the
         * remaining coordinates are the *negated* signed areas for the
         * simplices in which p is substituted for each of the vertices.
         * Analogous results occur in higher dimensions.
         */
		val dim = simplex.size - 1
		if (this.dimension() != dim)
			throw IllegalArgumentException("Dimension mismatch")

		/* Create and load the matrix */
		val matrix = arrayOfNulls<Pnt>(dim + 1)
		/* First row */
		val coords = DoubleArray(simplex.size + 1) { 1.0 }
		matrix[0] = Pnt(*coords)
		/* Other rows */
		(0 until dim).forEach { i ->
			// [0,dim-1]
			coords[0] = this.coordinates[i]
			simplex.forEachIndexed { j, it ->
				coords[j + 1] = it.coordinates[i]
			}
			matrix[i + 1] = Pnt(*coords)
		}

		/* Compute and analyze the vector of areas/volumes/contents */
		val vector = cross(matrix as Array<Pnt>)
		val content = vector.coordinates[0]
		val result = IntArray(dim + 1)
		for (i in result.indices) {
			val value = vector.coordinates[i + 1]
			if (Math.abs(value) <= 1.0e-6 * Math.abs(content))
				result[i] = 0
			else if (value < 0)
				result[i] = -1
			else
				result[i] = 1
		}
		when {
			content < 0 -> return result.map { -it }.toIntArray()
			content == 0.0 -> return result.map(Math::abs).toIntArray()
			else -> return result
		}
	}

	/**
	 * Test if this Pnt is outside of simplex.
	 * @param simplex the simplex (an array of Pnts)
	 * @return simplex Pnt that "witnesses" outsideness (or null if not outside)
	 */
	fun isOutside(simplex: Array<Pnt>): Pnt? {
		val result = this.relation(simplex)
		val index = result.find { it > 0 } ?: return null
		return simplex[index]
	}

	/**
	 * Test if this Pnt is inside a simplex.
	 * @param simplex the simplex (an arary of Pnts)
	 * @return true iff this Pnt is inside simplex.
	 */
	fun isInside(simplex: Array<Pnt>): Boolean {
		val result = this.relation(simplex)
		for (r in result) if (r >= 0) return false
		return true
	}

	/**
	 * Test relation between this Pnt and circumcircle of a simplex.
	 * @param simplex the simplex (as an array of Pnts)
	 * @return -1, 0, or +1 for inside, on, or outside of circumcircle
	 */
	fun vsCircumcircle(simplex: Array<Pnt>): Int {
		val matrix = simplex.mapTo(ArrayList()) {
			it.extend(1.0, it.dot(it))
		}
		matrix.add(this.extend(1.0, this.dot(this)))
		val result = determinant(matrix.toTypedArray()).let { d ->
			when {
				d > 0 -> 1
				d < 0 -> -1
				else -> 0
			}
		}
		return if (content(simplex) < 0) -result else result
	}

	companion object {
		/**
		 * Create a String for a matrix.
		 * @param matrix the matrix (an array of Pnts)
		 * @return a String representation of the matrix
		 */
		private fun str(matrix: Array<Pnt>): String {
			return buildString {
				append("{ ")
				matrix.joinToString(" ") { "$it" }.let(::append)
				append(" }")
			}
		}

		/**
		 * Compute the determinant of a matrix (array of Pnts).
		 * This is not an efficient implementation, but should be adequate
		 * for low dimension.
		 * @param matrix the matrix as an array of Pnts
		 * @return the determinnant of the input matrix
		 * @throws IllegalArgumentException if dimensions are wrong
		 */
		fun determinant(matrix: Array<Pnt>): Double {
			if (matrix.size != matrix[0].dimension())
				throw IllegalArgumentException("Matrix is not square")
			val columns = BooleanArray(matrix.size) { true }

			try {
				return determinant(matrix, 0, columns)
			} catch (e: ArrayIndexOutOfBoundsException) {
				throw IllegalArgumentException("Matrix is wrong shape")
			}

		}

		/**
		 * Compute the determinant of a submatrix specified by starting row
		 * and by "active" columns.
		 * @param matrix the matrix as an array of Pnts
		 * @param row the starting row
		 * @param columns a boolean array indicating the "active" columns
		 * @return the determinant of the specified submatrix
		 * @throws ArrayIndexOutOfBoundsException if dimensions are wrong
		 */
		private fun determinant(matrix: Array<Pnt>, row: Int, columns: BooleanArray): Double {
			if (row == matrix.size) return 1.0
			var sum = 0.0
			var sign = 1
			for (col in columns.indices) {
				if (!columns[col]) continue
				columns[col] = false
				sum += sign.toDouble() * matrix[row].coordinates[col] *
						determinant(matrix, row + 1, columns)
				columns[col] = true
				sign = -sign
			}
			return sum
		}

		/**
		 * Compute generalized cross-product of the rows of a matrix.
		 * The result is a Pnt perpendicular (as a vector) to each row of
		 * the matrix.  This is not an efficient implementation, but should
		 * be adequate for low dimension.
		 * @param matrix the matrix of Pnts (one less row than the Pnt dimension)
		 * @return a Pnt perpendicular to each row Pnt
		 * @throws IllegalArgumentException if matrix is wrong shape
		 */
		fun cross(matrix: Array<Pnt>): Pnt {
			val len = matrix.size + 1
			if (len != matrix[0].dimension())
				throw IllegalArgumentException("Dimension mismatch")
			val columns = BooleanArray(len)
			for (i in 0 until len) columns[i] = true
			val result = DoubleArray(len)
			var sign = 1
			try {
				for (i in 0 until len) {
					columns[i] = false
					result[i] = sign * determinant(matrix, 0, columns)
					columns[i] = true
					sign = -sign
				}
			} catch (e: ArrayIndexOutOfBoundsException) {
				throw IllegalArgumentException("Matrix is wrong shape")
			}

			return Pnt(*result)
		}

		/**
		 * Determine the signed content (i.e., area or volume, etc.) of a simplex.
		 * @param simplex the simplex (as an array of Pnts)
		 * @return the signed content of the simplex
		 */
		fun content(simplex: Array<Pnt>): Double {
			val matrix = simplex.map { it.extend(1.0) }.toTypedArray()
			var fact = 1
			for (i in 1 until matrix.size) fact *= i
			return determinant(matrix) / fact
		}

		/**
		 * Circumcenter of a simplex.
		 * @param simplex the simplex (as an array of Pnts)
		 * @return the circumcenter (a Pnt) of simplex
		 */
		fun circumcenter(simplex: Array<Pnt>): Pnt {
			val dim = simplex[0].dimension()
			if (simplex.size - 1 != dim)
				throw IllegalArgumentException("Dimension mismatch")
			val matrix = simplex.mapIndexedNotNull { i, v ->
				if (i < simplex.lastIndex) v.bisector(simplex[i + 1])
				else null
			}.toTypedArray()
			val hCenter = cross(matrix)      // Center in homogeneous coordinates
			val last = hCenter.coordinates[dim]
			val result = DoubleArray(dim)
			for (i in 0 until dim) result[i] = hCenter.coordinates[i] / last
			return Pnt(*result)
		}

		/**
		 * Main program (used for testing).
		 */
		@JvmStatic
		fun main(args: Array<String>) {
			val p = Pnt(1, 2, 3)
			println("Pnt created: $p")
			val matrix1 = arrayOf(Pnt(1, 2), Pnt(3, 4))
			val matrix2 = arrayOf(Pnt(7, 0, 5), Pnt(2, 4, 6), Pnt(3, 8, 1))
			print("Results should be -2 and -288: ")
			println("${determinant(matrix1)} ${determinant(matrix2)}")
			val p1 = Pnt(1, 1)
			val p2 = Pnt(-1, 1)
			println("Angle between $p1 and $p2: ${p1.angle(p2)}")
			println("$p1 subtract $p2: ${p1.subtract(p2)}")
			val v0 = Pnt(0, 0)
			val v1 = Pnt(1, 1)
			val v2 = Pnt(2, 2)
			val vs = arrayOf(v0, Pnt(0, 1), Pnt(1, 0))
			val vp = Pnt(.1, .1)
			println("$vp isInside ${str(vs)}: ${vp.isInside(vs)}")
			println("$v1 isInside ${str(vs)}: ${v1.isInside(vs)}")
			println("$vp vsCircumcircle ${str(vs)}: ${vp.vsCircumcircle(vs)}")
			println("$v1 vsCircumcircle ${str(vs)}: ${v1.vsCircumcircle(vs)}")
			println("$v2 vsCircumcircle ${str(vs)}: ${v2.vsCircumcircle(vs)}")
			println("Circumcenter of ${str(vs)} is ${circumcenter(vs)}")
		}
	}
}