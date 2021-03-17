import java.util.HashMap;
import java.util.Map;

public class DisjointSetTests {
    /**
     * MAX_TEST_NODES_SUITE_1 should be a multiple of 4 given nature of first set of
     * tests. eg. unions on every other 4 nodes
     */
    static int MAX_TEST_NODES_SUITE_1 = 16;
    static int MAX_TEST_NODES_SUITE_2 = 100;

    static Map<Integer, DisjointNode<Integer>> testNodesSuite1 = null;
    static Map<Integer, DisjointNode<Integer>> testNodesSuite2 = null;

    /**
     * Helper function for easily creating new DisjointNodes
     * 
     * @param <T>      T, any object
     * @param element, an object of type T
     * @return a new DisjointNode<T>
     */
    private static <T> DisjointNode<T> createDisjointNode(T element) {
        return new DisjointNode<T>(element);
    }

    /**
     * Simple test for makeSet on a newly created node
     * 
     * @return true if all tests pass (no Exception is thrown)
     * @throws Exception if any assertions fail
     * @apiNote Assertions: 1. The test nodes parent should be set to itself after
     *          the makeSet operation. 2. The element that was originally stored in
     *          the test node should remain intact after the makeSet operation
     */
    private static boolean testMakeSetThenFindSetOnSingleNode() throws Exception {
        System.out.println("\tRunning [testMakeSetThenFindSetOnSingleNode]");
        int element = 10;
        DisjointNode<Integer> newNode = createDisjointNode(element);
        DisjointSet.makeSet(newNode);

        if (newNode.getParent() != newNode) {
            throw new Exception("New node's parent was not set to itself");
        }

        if (newNode.getElement() != element) {
            throw new Exception("New node's element lost after DisjointSet.makeSet operation");
        }

        if (DisjointSet.findSet(newNode) != newNode) {
            throw new Exception("DisjointSet.findSet of new node is not equal to itself");
        }

        System.out.println("\tPassed [testMakeSetThenFindSetOnSingleNode] ✅");
        System.out.println();
        return true;
    }

    /**
     * Batch tests makeSet operation on all of the nodes within a collection
     * (HashMap)
     * 
     * @return true, if all tests pass
     * @throws Exception if any assertions fail
     * @apiNote Assertions: 1. All nodes in the collection should have null parents,
     *          2. Parents of each node should be set to the node itself after the
     *          makeSet operation
     */
    private static boolean testMakeSetOnAllNodes() throws Exception {
        System.out.println("\tRunning [testMakeSetOnAllNodes]");
        int maxLength = testNodesSuite1.size();
        for (int i = 0; i < maxLength; i++) {
            DisjointNode<Integer> current = testNodesSuite1.get(i);
            if (current.getParent() != null) {
                throw new Exception("Aborted: Parent was not previously initialized to null: " + current.toString());
            }

            DisjointSet.makeSet(current);

            if (current.getParent() != current) {
                throw new Exception("Failed: Current node's parent was not set to itself: " + current.toString());
            }
        }
        System.out.println("\tPassed [testMakeSetOnAllNodes] ✅");
        System.out.println();
        return true;
    }

    /**
     * Performs batch findSet operations on a collection of nodes for which makeSet
     * had previously been called
     * 
     * @return true if no assertions fail
     * @throws Exception if any assertions fail
     * @apiNote Assertions: 1. For any node, in the collection, it's parent should
     *          already be set to itself, 2. The representative of each node (i.e
     *          the result of the findSet operation) should be equal to the node
     *          within each iteration
     */
    private static boolean testFindSetOnAllNodesAfterMakeSet() throws Exception {
        System.out.println("\tRunning [testFindSetOnAllNodesAfterMakeSet]");
        int maxLength = testNodesSuite1.size();
        for (int i = 0; i < maxLength; i++) {
            DisjointNode<Integer> current = testNodesSuite1.get(i);

            if (current.getParent() != current) {
                throw new Exception("Aborted: Current node's parent was not set to itself: " + current.toString());
            }

            DisjointNode<Integer> representative = DisjointSet.findSet(current);

            if (representative != current) {
                throw new Exception("Failed: DisjointSet.findSet() does not yield parent pointing to itself");
            }
        }
        System.out.println("\tPassed [testFindSetOnAllNodesAfterMakeSet] ✅");
        System.out.println();
        return true;
    }

    /**
     * Performs a batch union for every other two nodes in a collection
     * 
     * @return true if no assertions fail
     * @throws Exception if any assertions fail
     * @apiNote Assertions: 1. For any given node in the collection, makeSet should
     *          have previously been called (i.e the node is not null and its parent
     *          is equal to itself), 2. Following the union, the result of a findSet
     *          operation on the two union-ed nodes should be equal
     */
    private static boolean testUnionOnEveryOtherTwoNodesAfterMakeSet() throws Exception {
        System.out.println("\tRunning [testUnionOnEveryOtherTwoNodesAfterMakeSet]");
        int maxLength = testNodesSuite1.size();
        for (int i = 0; i < maxLength; i += 2) {
            DisjointNode<Integer> current = testNodesSuite1.get(i);
            DisjointNode<Integer> next = testNodesSuite1.get(i + 1);

            if (current.getParent() != current || next.getParent() != next || current == null || next == null) {
                throw new Exception("Aborted: Either current/next is null, or does not point to itself: " + "Current: "
                        + current.toString() + " Next: " + next.toString());
            }

            DisjointSet.union(current, next);

            if (DisjointSet.findSet(current) != DisjointSet.findSet(next)) {
                throw new Exception("Failed: Current and next do not have the same representative");
            }
        }
        System.out.println("\tPassed [testUnionOnEveryOtherTwoNodesAfterMakeSet] ✅");
        System.out.println();
        return true;
    }

    /**
     * Performs a batch union on operation on every other four nodes in a collection
     * 
     * @return tru if no assertions fail
     * @throws Exception if any assertions fail
     * @apiNote Assertions: 1. None of the nodes being union-ed should be null, 2.
     *          For every consecutive group of 4 nodes in the collection, a union
     *          must have been performed between the first set of two nodes, and a
     *          separate union between the second set of two nodes, such that a
     *          findSet operation on all the four nodes would yield the same
     *          representative after a union on the 1st and 3rd elements out of the
     *          four.
     */
    private static boolean testUnionOnEveryOtherFourNodesAfterUnionOnEveryOtherTwo() throws Exception {
        System.out.println("\tRunning [testUnionOnEveryOtherFourNodesAfterUnionOnEveryOtherTwo]");
        int maxLength = testNodesSuite1.size();
        for (int i = 0; i < maxLength; i += 4) {
            DisjointNode<Integer> current = testNodesSuite1.get(i);
            DisjointNode<Integer> next = testNodesSuite1.get(i + 2);

            if (current == null || next == null) {
                throw new Exception("Aborted: Either current/next is null: " + "Current: " + current.toString()
                        + " Next: " + next.toString());
            }

            DisjointSet.union(current, next);

            if (DisjointSet.findSet(current) != DisjointSet.findSet(next)) {
                throw new Exception("Failed: Current and next do not have the same representative");
            }

            DisjointNode<Integer> representative = DisjointSet.findSet(current);

            // Test all nodes inside of union to make sure they have the same representative
            for (int j = i; j < i + 4; j++) {
                DisjointNode<Integer> nodeInsideOfUnion = testNodesSuite1.get(j);
                if (DisjointSet.findSet(nodeInsideOfUnion) != representative) {
                    throw new Exception(
                            "Failed: Some node inside of the union does not have the expected representative: "
                                    + "Node: " + nodeInsideOfUnion.toString() + "Representative: "
                                    + representative.toString());
                }
            }

        }
        System.out.println("\tPassed [testUnionOnEveryOtherFourNodesAfterUnionOnEveryOtherTwo] ✅");
        System.out.println();
        return true;
    }

    /**
     * Performs a batch union between consecutive nodes in a collection
     * 
     * @return true if no assertions fail
     * @throws Exception if any assertions fail
     * @apiNote Assertions: 1. No nodes in the collection should be null following
     *          the initial makeSet operation on all the nodes in the collection 2.
     *          All nodes in the collection should have the same representative
     *          after the batch union operations since all elements have been
     *          union-ed with each other
     */
    private static boolean testUnionOnEverySingleNode() throws Exception {
        System.out.println("\tRunning [testUnionOnEverySingleNode]");
        int maxLength = testNodesSuite2.size();

        // Make Set on all Nodes First
        for (int i = 0; i < maxLength; i++) {
            DisjointSet.makeSet(testNodesSuite2.get(i));
        }

        for (int i = 0; i < maxLength - 1; i++) {
            DisjointNode<Integer> current = testNodesSuite2.get(i);
            DisjointNode<Integer> next = testNodesSuite2.get(i + 1);

            if (current == null || next == null) {
                throw new Exception("Aborted: Either current/next is null: " + "Current: " + current.toString()
                        + " Next: " + next.toString());
            }

            DisjointSet.union(current, next);
        }

        DisjointNode<Integer> previousRepresentative = DisjointSet.findSet(testNodesSuite2.get(0));
        DisjointNode<Integer> currentRepresentative = null;

        for (int i = 1; i < maxLength; i++) {
            currentRepresentative = DisjointSet.findSet(testNodesSuite2.get(i));

            if (previousRepresentative != currentRepresentative) {
                throw new Exception("Failed: Two nodes have different representatives");
            }

            previousRepresentative = currentRepresentative;
        }

        System.out.println("\tPassed [testUnionOnEverySingleNode] ✅");
        System.out.println();
        return true;
    }

    /** Initializes the static datasets to be used in the testing procedures */
    private static void initializeDataSets() {
        System.out.println("Initializing Datasets...");

        testNodesSuite1 = new HashMap<>();
        for (int i = 0; i < MAX_TEST_NODES_SUITE_1; i++) {
            testNodesSuite1.put(i, createDisjointNode(i));
        }

        testNodesSuite2 = new HashMap<>();
        for (int i = 0; i < MAX_TEST_NODES_SUITE_2; i++) {
            testNodesSuite2.put(i, createDisjointNode(i));
        }
    }

    public static void main(String[] args) {

        try {
            initializeDataSets();

            System.out.println("Starting Tests...");

            // Suite 0
            testMakeSetThenFindSetOnSingleNode();

            // Suite 1 - Statements must be run in this order since shared dataset
            testMakeSetOnAllNodes();
            testFindSetOnAllNodesAfterMakeSet();
            testUnionOnEveryOtherTwoNodesAfterMakeSet();
            testUnionOnEveryOtherFourNodesAfterUnionOnEveryOtherTwo();

            // Suite 2
            testUnionOnEverySingleNode();

            System.out.println("Finished Tests!");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
