import java.util.*

open class ArraySet<E> : AbstractSet<E> {

	private var items: ArrayList<E>? = null            // Items of the set

	/**
	 * Create an empty set with the specified initial capacity.
	 * @param initialCapacity the initial capacity
	 */
	@JvmOverloads
	constructor(initialCapacity: Int = 3) {
		items = ArrayList(initialCapacity)
	}

	/**
	 * Create a set containing the items of the collection.  Any duplicate
	 * items are discarded.
	 * @param collection the source for the items of the small set
	 */
	constructor(collection: Collection<E>) {
		items = ArrayList(collection.size)
		collection
				.filterNot { items!!.contains(it) }
				.forEach { items!!.add(it) }
	}

	/**
	 * Get the item at the specified index.
	 * @param index where the item is located in the ListSet
	 * @return E the item at the specified index
	 * @throws IndexOutOfBoundsException if the index is out of bounds
	 */
	@Throws(IndexOutOfBoundsException::class)
	operator fun get(index: Int): E {
		return items!![index]
	}

	/**
	 * True iff any member of the collection is also in the ArraySet.
	 * @param collection the Collection to check
	 * @return true iff any member of collection appears in this ArraySet
	 */
	fun containsAny(collection: Collection<*>): Boolean {
		return collection.any { this.contains(it) }
	}

	override fun add(element: E): Boolean {
		return if (items!!.contains(element)) false else items!!.add(element)
	}

	override fun iterator(): MutableIterator<E> {
		return items!!.iterator()
	}


	override val size
		get() = items!!.size

}