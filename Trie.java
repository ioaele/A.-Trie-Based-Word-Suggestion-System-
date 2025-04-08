/**
 * Represents a Trie data structure for efficient prefix-based word searching and insertion.
 * Each Trie instance contains a root TrieNode (tnode) which serves as the starting point for all operations.
 */
public class Trie {
    
    /** The root node of the Trie, which serves as the entry point for all Trie operations. */
    public TrieNode tnode;

    /**
     * Constructor to initialize the Trie. Creates a root TrieNode to serve as the starting point.
     */
    Trie() {
        tnode = new TrieNode();  // Initialize the root TrieNode.
    }

    /**
     * Adds a string (word) to the Trie. Each character in the word is inserted as a node, 
     * and the last node marks the end of the word.
     * 
     * @param st The string (word) to be added to the Trie.
     */
    public void add(String st) {
        TrieNode current = tnode;  // Start at the root node.
        TrieNode Tnode;

        // διατρεχω απο ολο το string για τα εισαξω χαρακτηρα-χαρακτηρα στο trie μου.
        for (int i = 0; i < st.length(); i++) {
            // Search for the character in the current node's children.
            Element ch = current.search(st.charAt(i));

            // If the character is found, move to the next node.
            if (ch != null)
                Tnode = ch.value;
            else {
                // If the character is not found, create a new TrieNode and insert it.
                Tnode = new TrieNode();
                current.insert(st.charAt(i), Tnode);
            }

            current = Tnode;  // Move to the next TrieNode.
        }

        // Mark the last node as the end of the word.
        current.isEndOfWord = true;
    }

    /**
     * Searches for a string (word) in the Trie. Returns the TrieNode corresponding to the 
     * last character of the word, or null if the word is not found.
     * 
     * @param st The string (word) to search for in the Trie.
     * @return The TrieNode corresponding to the last character of the word, or null if the word is not found.
     */
    public TrieNode search(String st) {
        TrieNode current = tnode;  // Start at the root node.

     // διατρεχω απο ολο το string για τα ελεγξω χαρακτηρα-χαρακτηρα εαν ειναι στο trie μου.
        for (int i = 0; i < st.length(); i++) {
            // Search for the character in the current node's children.
            Element ch = current.search(st.charAt(i));

            // If the character is found, move to the next node.
            if (ch != null)
                current = ch.value;
            else
                return null;  // Return null if the character is not found.
        }

        return current;  // Return the TrieNode corresponding to the last character.
    }

    /**
     * Updates the importance of a word (string) in the Trie by incrementing its importance 
     * value if the word exists in the Trie.
     * 
     * @param k The string (word) whose importance value is to be updated.
     */
    public void updateImportance(String k) {
        // Search for the word in the Trie.
        TrieNode searchNum = search(k);

        // If the word exists in the Trie, increment its importance value.
        if (searchNum != null)
            searchNum.importance++;
    }
}
