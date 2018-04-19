class Pnt(vararg coords: Double) {
	private val coordinates: DoubleArray = DoubleArray(coords.size)

	init {
		// Copying is done here to ensure that Pnt's coords cannot be altered.
		// This is necessary because the double... notation actually creates a
		// constructor with double[] as its argument.
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
		val p = other as Pnt?
		if (this.coordinates.size != p!!.coordinates.size) return false
		return this.coordinates.indices.none { this.coordinates[it] != p.coordinates[it] }
	}

	override fun hashCode(): Int {
		var hash = 0
		for (c in this.coordinates) {
			val bits = java.lang.Double.doubleToLongBits(c)
			hash = 31 * hash xor (bits xor (bits shr 32)).toInt()
		}
		return hash
	}

	/* Pnts as vectors */

	/**
	 * @return the specified coordinate of this Pnt
	 * @throws ArrayIndexOutOfBoundsException for bad coordinate
	 */
	fun coord(i: Int): Double {
		return this.coordinates[i]
	}

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
	fun dot(p: Pnt): Double {
		val len = dimCheck(p)
		val sum = (0 until len).sumByDouble { this.coordinates[it] * p.coordinates[it] }
		return sum
	}

	/**
	 * Magnitude (as a vector).
	 * @return the Euclidean length of this vector
	 */
	fun magnitude(): Double {
		return Math.sqrt(this.dot(this))
	}

	/**
	 * Subtract.
	 * @param p the other Pnt
	 * @return a new Pnt = this - p
	 */
	fun subtract(p: Pnt): Pnt {
		val len = dimCheck(p)
		val coords = DoubleArray(len)
		(0 until len).forEach { i -> coords[i] = this.coordinates[i] - p.coordinates[i] }
		return Pnt(*coords)
	}

	/**
	 * Add.
	 * @param p the other Pnt
	 * @return a new Pnt = this + p
	 */
	fun add(p: Pnt): Pnt {
		val len = dimCheck(p)
		val coords = DoubleArray(len)
		for (i in 0 until len)
			coords[i] = this.coordinates[i] + p.coordinates[i]
		return Pnt(*coords)
	}

	/**
	 * Angle (in radians) between two Pnts (treated as vectors).
	 * @param p the other Pnt
	 * @return the angle (in radians) between the two Pnts
	 */
	fun angle(p: Pnt): Double {
		return Math.acos(this.dot(p) / (this.magnitude() * p.magnitude()))
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
	fun relation(simplex: Array<Pnt>): IntArray {
		/* In 2D, we compute the cross of this matrix:
         *    1   1   1   1
         *    p0  a0  b0  c0
         *    p1  a1  b1  c1
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
		val coords = DoubleArray(dim + 2)
		for (j in coords.indices) coords[j] = 1.0
		matrix[0] = Pnt(*coords)
		/* Other rows */
		for (i in 0 until dim) {
			coords[0] = this.coordinates[i]
			for (j in simplex.indices)
				coords[j + 1] = simplex[j].coordinates[i]
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
		if (content < 0) {
			for (i in result.indices)
				result[i] = -result[i]
		}
		if (content == 0.0) {
			for (i in result.indices)
				result[i] = Math.abs(result[i])
		}
		return result
	}

	/**
	 * Test if this Pnt is outside of simplex.
	 * @param simplex the simplex (an array of Pnts)
	 * @return simplex Pnt that "witnesses" outsideness (or null if not outside)
	 */
	fun isOutside(simplex: Array<Pnt>): Pnt? {
		val result = this.relation(simplex)
		for (i in result.indices) {
			if (result[i] > 0) return simplex[i]
		}
		return null
	}

	/**
	 * Test if this Pnt is on a simplex.
	 * @param simplex the simplex (an array of Pnts)
	 * @return the simplex Pnt that "witnesses" on-ness (or null if not on)
	 */
	fun isOn(simplex: Array<Pnt>): Pnt? {
		val result = this.relation(simplex)
		var witness: Pnt? = null
		for (i in result.indices) {
			if (result[i] == 0)
				witness = simplex[i]
			else if (result[i] > 0) return null
		}
		return witness
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
		val matrix = arrayOfNulls<Pnt>(simplex.size + 1)
		for (i in simplex.indices)
			matrix[i] = simplex[i].extend(1.0, simplex[i].dot(simplex[i]))
		matrix[simplex.size] = this.extend(1.0, this.dot(this))
		val d = determinant(matrix as Array<Pnt>)
		var result = if (d < 0) -1 else if (d > 0) +1 else 0
		if (content(simplex) < 0) result = -result
		return result
	}

	companion object {

		/* Pnts as matrices */

		/**
		 * Create a String for a matrix.
		 * @param matrix the matrix (an array of Pnts)
		 * @return a String represenation of the matrix
		 */
		fun toString(matrix: Array<Pnt>): String {
			val buf = StringBuilder("{")
			for (row in matrix) buf.append(" " + row)
			buf.append(" }")
			return buf.toString()
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
			val columns = BooleanArray(matrix.size)
			for (i in matrix.indices) columns[i] = true
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

		/* Pnts as simplices */

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
			val matrix = arrayOfNulls<Pnt>(dim)
			for (i in 0 until dim)
				matrix[i] = simplex[i].bisector(simplex[i + 1])
			val hCenter = cross(matrix as Array<Pnt>)      // Center in homogeneous coordinates
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
			println("Pnt created: " + p)
			val matrix1 = arrayOf(Pnt(1, 2), Pnt(3, 4))
			val matrix2 = arrayOf(Pnt(7, 0, 5), Pnt(2, 4, 6), Pnt(3, 8, 1))
			print("Results should be -2 and -288: ")
			println("${determinant(matrix1)} ${determinant(matrix2)}")
			val p1 = Pnt(1, 1)
			val p2 = Pnt(-1, 1)
			println("Angle between " + p1 + " and " +
					p2 + ": " + p1.angle(p2))
			println("$p1 subtract $p2: ${p1.subtract(p2)}")
			val v0 = Pnt(0, 0)
			val v1 = Pnt(1, 1)
			val v2 = Pnt(2, 2)
			val vs = arrayOf(v0, Pnt(0, 1), Pnt(1, 0))
			val vp = Pnt(.1, .1)
			println("$vp isInside ${toString(vs)}: ${vp.isInside(vs)}")
			println("$v1 isInside ${toString(vs)}: ${v1.isInside(vs)}")
			println("$vp vsCircumcircle ${toString(vs)}: ${vp.vsCircumcircle(vs)}")
			println("$v1 vsCircumcircle ${toString(vs)}: ${v1.vsCircumcircle(vs)}")
			println("$v2 vsCircumcircle ${toString(vs)}: ${v2.vsCircumcircle(vs)}")
			println("Circumcenter of ${toString(vs)} is ${circumcenter(vs)}")
		}
	}
}