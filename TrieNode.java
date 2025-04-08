	/*
	 * Represents a node in a Trie data structure. Each node can store a reference to its children
	 * using a RobinHood hashing scheme and contains metadata about the node's word status and importance.
	 */
	public class TrieNode {

	    /** 
	     * A RobinHoodHashing object used to store child elements (i.e., child TrieNodes).
	     * The RobinHood hashing technique allows for efficient lookup and insertion.
	     * Each TrieNode points to a RobinHoodHashing, which contains a table of elements and associated TrieNodes.
	     */
	    public RobinHoodHashing node; // 12B + capacity*11B (each element is 5B + 6B for TrieNode)
	 // an array of elements of 'capacity' size each one of 5B plus a 6B TrieNode )
	    /** 
	     * Flag to indicate whether this node marks the end of a valid word in the Trie.
	     * If true, it signifies that the path from the root to this node forms a complete word.
	     */
	    public boolean isEndOfWord; // 2B

	    /** 
	     * Importance score of the word ending at this TrieNode.
	     * Higher values represent more important words, typically used for ranking or suggestions.
	     */
	    public int importance; // 4B

	    /**
	     * Default constructor to initialize a TrieNode with a RobinHood hashing object.
	     * The node is initially marked as not the end of a word and has zero importance.
	     */
	    TrieNode() {
	        node = new RobinHoodHashing(); // Initialize the RobinHoodHashing table for storing child nodes.
	        isEndOfWord = false;           // Node is not the end of a word by default.
	        importance = 0;                // Default importance is set to 0.
	    }

	    /**
	     * Searches for a child element in this TrieNode corresponding to the given character.
	     * The search is performed using the RobinHoodHashing technique.
	     *
	     * @param ch The character to search for.
	     * @return The corresponding Element if found, or null if the character does not exist in the node.
	     */
	    public Element search(char ch) {
	        Element element = new Element(ch, this); // Create an element with the character to search.
	        int index = node.search(element);        // Use RobinHoodHashing's search method.
	        
	        // If element is found, return the corresponding Element from the table.
	        if (index != -1) {
	            return node.table[index];
	        }

	        return null; // Return null if the character is not found.
	    }

	    /**
	     * Inserts a new child TrieNode corresponding to the given character into this TrieNode.
	     * The insertion is done using the RobinHoodHashing technique.
	     *
	     * @param ch    The character to insert.
	     * @param Tnode The TrieNode to associate with the given character.
	     */
	    public void insert(char ch, TrieNode Tnode) {
	        Element element = new Element(ch, Tnode); // Create a new element with the character and TrieNode.
	        node.insert(element);                      // Insert the element into the RobinHood hashing structure.
	    }
	}
