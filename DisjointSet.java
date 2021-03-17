/* Academic Honesty Certification
* Written sources used: Introduction to Algorithms by Cormen et. al
* (Include textbook(s), complete citations for web or other written sources.
* Note that you are not allowed to use the web for this assignment.
* Write "none" if no sources used.) *
*
*
* Help obtained: None
* (Include names of anyone other than the instructor.)
*
*
*
* My written or typed signature below confirms that the above list * of sources is complete.
* Signature: Rexford Essilfie
*/

/**
 * A DisjointNode is an element within a member set of a disjoint set
 * 
 * @param <T> T, any object
 */
class DisjointNode<T> {
    private T element;

    /**
     * Parent of this DisjointNode. Must explicitly be set by user via setParent if
     * necessary
     */
    private DisjointNode<T> parent;

    /** The rank of this DisjointNode. i.e the height of this node. */
    private int rank = 0;

    /**
     * Initializes element to the provided value and parent to null
     * 
     * @param element, any object
     */
    DisjointNode(T element) {
        this.element = element;
        this.parent = null;
    }

    /**
     * Gets the rank or height of the node
     * 
     * @return rank, an integer
     */
    public int getRank() {
        return this.rank;
    }

    /**
     * Sets the rank of the node to provided number
     * 
     * @param num, an integer
     */
    public void setRank(int num) {
        this.rank = num;
    }

    /**
     * Sets the parent of the node
     * 
     * @param someNode, a DisjointNode<T>, where T is the same type as the node's
     *                  element
     */
    public void setParent(DisjointNode<T> someNode) {
        this.parent = someNode;
    }

    /**
     * Returns the parent of the node
     * 
     * @return parent which is a DisjointNode<T>, where T is the same type as the
     *         node's element
     */
    public DisjointNode<T> getParent() {
        return this.parent;
    }

    /**
     * Gets the element of the node
     * 
     * @return the element of type T stored within the node
     */
    public T getElement() {
        return this.element;
    }

    @Override
    public String toString() {
        String parentString = this.parent == null ? "" : this.parent.getElement().toString();
        return "<#Element: " + this.element + " Parent: " + parentString + " Rank: " + this.rank + ">";
    }

}

/** DisjointSet class implements disjoint set operations as static methods */
public class DisjointSet {

    /**
     * makeSet creates a new set whose only member is node.
     * 
     * @param <T>
     * @param node, a DisjointNode
     */
    public static <T> void makeSet(DisjointNode<T> node) {
        node.setParent(node);
        node.setRank(0);
    }

    /**
     * findSet returns a pointer to the representative of the (unique) set
     * containing x (applying path compression after retrieval)
     * 
     * @param node, a DisjointNode (should not be null)
     * @return a pointer to the representative node of the set containing x
     * @throws NullPointerException if node is null
     * 
     */
    public static <T> DisjointNode<T> findSet(DisjointNode<T> x) {
        DisjointNode<T> current = x;

        if (current != current.getParent()) {
            current.setParent(findSet(current.getParent()));
        }

        return current.getParent();
    }

    /**
     * linkTrees combines two DisjointNodes into one using union by rank heuristic
     * 
     * @param x, a DisjointNode
     * @param y, a DisjointNode
     * @throws NullPointerException if either x or y is null
     */
    private static <T> void linkTrees(DisjointNode<T> x, DisjointNode<T> y) {
        if (x.getRank() > y.getRank()) {
            y.setParent(x);
        } else {
            x.setParent(y);

            if (x.getRank() == y.getRank()) {
                y.setRank(y.getRank() + 1);
            }
        }
    }

    /**
     * union unites the dynamic sets that contain x and y, say Sx and Sy, into a new
     * set that is the union of these two sets
     * 
     * @param x, a DisjointNode
     * @param y, a DisjointNode
     * @throws NullPointerException if x or y is null
     */
    public static <T> void union(DisjointNode<T> x, DisjointNode<T> y) {
        linkTrees(findSet(x), findSet(y));
    }
}
