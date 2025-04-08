/**
 * The Element2 class represents a single node in the Trie data structure.
 * Each Element2 object contains a character key and a reference to a TrieNode2 object.
 * The key is used to identify the character, and the value points to the next TrieNode2 in the Trie structure.
 * The Element2 class is used within the Trie to represent individual elements (characters) in the stored words.
 */
public class Element2 {

    /** The character key representing the element's character. */
    public char key;

    /** A reference to the TrieNode2 object associated with this element, representing the next TrieNode in the Trie. */
    public TrieNode2 value;  // 96B (26 size array of Element2 of 1 bit each one points to a 6-byte TrieNode)

    /**
     * Default constructor initializes an empty Element2 with no key and no associated TrieNode2.
     */
    public Element2() {
        key = 0;
        value = new TrieNode2();
    }

    /**
     * Constructor to initialize the Element2 with a specified character key and associated TrieNode2.
     * 
     * @param key The character key representing the element's character.
     * @param value The TrieNode2 associated with this element, pointing to the next TrieNode in the Trie.
     */
    public Element2(Character key, TrieNode2 value) {
        this.key = key;
        this.value = value;
    }
}
