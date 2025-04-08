/**
 * The memoryCounter2 class provides a method for counting the number of elements in a Trie structure.

 * It traverses the Trie starting from a given root node and counts all elements (including children nodes).
 * The method also accounts for memory usage by adding a fixed value (96) for each child element to simulate additional memory used by each node.
 */
public class memoryCounter2 {

    /**
     * Recursively counts the number of elements in the Trie starting from the given root node.
     * The method traverses the Trie by visiting each child node starting from the root. For each child node,
     * it adds a fixed amount (96 bytes) to the memory count to simulate the memory consumption of each child element.
     *
     * @param root The root node of the Trie from which to start counting elements.
     * @return The total count of elements in the Trie, including additional memory for each child node.
     */
    public static long countElements2(Element2 root) {
        // Initialize the count with 1 to account for the current root node.
        long count[] = {1};

        // If the root is null, return 1 to indicate no elements.
        if (root == null) {
            return 1;
        }

        // Traverse each child node from 'a' to 'z' and accumulate the count.
        for (char c = 'a'; c <= 'z'; c++) {
            // Search for the child node corresponding to the current character.
        	if (root.value != null) {
            Element2 child = root.value.search2(c);
            if (child != null) {
                // Add the count of elements from the child node, including the fixed memory usage (96 bytes).
                count[0] += (countElements2(child) + 96);
            }
        	}
        }

        // Return the final count of elements, including the memory usage estimate.
        return count[0];
    }
}
