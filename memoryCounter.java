/**
 * The memoryCounter class provides a utility method to calculate the memory usage 
 * of a Trie data structure by counting its nodes and accounting for the storage overhead.
 */
public class memoryCounter {

    /**
     * Counts the total memory used by the nodes in the Trie.
     * The memory is calculated based on the structure of the nodes and the 
     * capacity of the `node` field in each `TrieNode`.
     *
     * @param root the root node of the Trie for which memory is to be counted.
     * @return the total memory consumed by the Trie nodes in bytes.
     */
    public static long countTrieNodes(TrieNode root) {
        long count[] = {1}; // Initialize count to include the root node.
        if (root == null) {
            return 1; // Base case: If the root is null, count the root only.
        }

        // διατρεχω απο ολα τα nodes και τα παιδια τους 
        for (char c = 'a'; c <= 'z'; c++) {
            Element child = root.search(c); // Search for the child node corresponding to character 'c'.
            if (child != null) {
                // Add memory for the child node and recursively count its memory.
                count[0] += countTrieNodes(child.value) + (root.node.capacity * 12) + 11; 
            }
        }

        return count[0]; // Return the total memory count.
    }
}
