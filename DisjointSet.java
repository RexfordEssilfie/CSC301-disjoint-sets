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
 * A DisjointNode is a disjoint tree within a disjoint forest
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

    public static <T> void makeSet(DisjointNode<T> node) {
        node.setParent(node);
        node.setRank(0);
    }

    /**
     * findSet finds the root element of a given disjoint set, and uses path
     * compression
     * 
     * @param node, a DisjointNode (should not be null)
     * @return the root or representative node of the tree
     * @throws NullPointerException if node is null
     * 
     */
    public static <T> DisjointNode<T> findSet(DisjointNode<T> node) {
        DisjointNode<T> current = node;

        if (current != current.getParent()) {
            current.setParent(findSet(current.getParent()));
        }

        return current.getParent();
    }

    /**
     * linkTrees combines two DisjointNodes into one
     * 
     * @param nodeA, a DisjointNode
     * @param nodeB, a DisjointNode
     * @throws NullPointerException if either nodeA or nodeB is null
     */
    private static <T> void linkTrees(DisjointNode<T> nodeA, DisjointNode<T> nodeB) {
        if (nodeA.getRank() > nodeB.getRank()) {
            nodeB.setParent(nodeA);
        } else {
            nodeA.setParent(nodeB);

            if (nodeA.getRank() == nodeB.getRank()) {
                nodeB.setRank(nodeB.getRank() + 1);
            }
        }
    }

    /**
     * union combines two disjoint sets into one
     * 
     * @param nodeA, a DisjointNode
     * @param nodeB, a DisjointNode
     * @throws NullPointerException if nodeA or nodeB is null
     */
    public static <T> void union(DisjointNode<T> nodeA, DisjointNode<T> nodeB) {
        linkTrees(findSet(nodeA), findSet(nodeB));
    }
}
