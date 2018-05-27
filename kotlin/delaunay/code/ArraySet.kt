import java.util.*

open class ArraySet<E> : AbstractSet<E> {

	private var items: ArrayList<E>            // Items of the set

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
		items = collection.distinct().mapTo(ArrayList()) { it }
	}

	/**
	 * Get the item at the specified index.
	 * @param index where the item is located in the ListSet
	 * @return E the item at the specified index
	 * @throws IndexOutOfBoundsException if the index is out of bounds
	 */
	@Throws(IndexOutOfBoundsException::class)
	operator fun get(index: Int): E {
		return items[index]
	}

	/**
	 * `in`
	 * @usage `if(a in b){xxxxx}`
	 * @param element E
	 * @return Boolean
	 */
	override operator fun contains(element: E):Boolean{
		return items.contains(element)
	}

	override fun add(element: E): Boolean {
		return if (items.contains(element)) false else items.add(element)
	}

	override fun iterator(): MutableIterator<E> {
		return items.iterator()
	}

	override val size
		get() = items.size
}