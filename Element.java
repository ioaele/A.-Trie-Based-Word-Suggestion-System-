/**
 * Represents an element in the Trie structure. Each element contains a character (key),
 * a reference to the corresponding TrieNode (value), and a probe length used in hash table operations.
 */
public class Element {
    
    /** The character key that this element represents. */
    public char key;

    /** The probe length for hash table operations. Indicates how many probes were made during insertion. */
    public int probeLength;

    /** The TrieNode associated with this element, representing the next node in the Trie for the given key. */
    public TrieNode value;

    /**
     * Default constructor to initialize an empty element with a key of 0, no associated TrieNode, 
     * and a probe length of 0.
     */
    public Element() {
        key = 0;            // Default key is set to 0 (empty character).
        value = null;       // Default value is set to null (no TrieNode).
        probeLength = 0;    // Default probe length is 0.
    }

    /**
     * Constructor to initialize an element with a specific key and associated TrieNode.
     * The probe length is set to 0 by default.
     *
     * @param key   The character key to associate with this element.
     * @param value The TrieNode associated with this element.
     */
    public Element(Character key, TrieNode value) {
        this.key = key;      // Set the key to the provided character.
        this.value = value;  // Set the value to the provided TrieNode.
        this.probeLength = 0; // Initialize probe length to 0.
    }
}
