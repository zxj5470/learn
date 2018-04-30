import java.util.Collections
import java.util.HashMap

class Graph<N> {

	private val theNeighbors = HashMap<N, ArraySet<N>>()// Node -> adjacent nodes
	val nodeSet: MutableSet<N> = // Set view of all nodes
			(theNeighbors.keys)

	/**
	 * Add a node.  If node is already in graph then no change.
	 * @param node the node to add
	 */
	fun add(node: N) {
		if (theNeighbors.containsKey(node)) return
		theNeighbors[node] = ArraySet()
	}

	/**
	 * Add a link. If the link is already in graph then no change.
	 * @param nodeA one end of the link
	 * @param nodeB the other end of the link
	 * @throws NullPointerException if either endpoint is not in graph
	 */
	@Throws(NullPointerException::class)
	fun add(nodeA: N, nodeB: N) {
		theNeighbors[nodeA]?.add(nodeB)
		theNeighbors[nodeB]?.add(nodeA)
	}

	/**
	 * Remove node and any links that use node. If node not in graph, nothing
	 * happens.
	 * @param node the node to remove.
	 */
	fun remove(node: N) {
		val theNode = theNeighbors[node] ?: return
		theNode.forEach {
			theNeighbors[it]?.remove(node)
		}
		theNode.clear()
		theNeighbors.remove(node)
	}

	/**
	 * Report all the neighbors of node.
	 * @param node N the node
	 * @return Set<N> the neighbors of node
	 * @throws NullPointerException if node does not appear in graph
	 */
	@Throws(NullPointerException::class)
	fun neighbors(node: N): Set<N> {
		return Collections.unmodifiableSet(theNeighbors[node])
	}
}
