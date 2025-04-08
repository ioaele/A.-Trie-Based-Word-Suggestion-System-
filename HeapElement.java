/**
 * The HeapElement class represents an element to be stored in a heap.
 * Each element consists of a key (a word or string) and its associated importance (an integer).
 * This class is used to manage elements in a min-heap, which is primarily utilized for storing and ordering words based on their importance.
 */
public class HeapElement {
    
    /** The key (word) associated with the element. */
    public String key;
    
    /** The importance of the word associated with the key. */
    public int elemImportance;

    /**
     * Default constructor initializes a HeapElement with an empty string as the key 
     * and zero as the importance.
     */
    public HeapElement () {
        key = "";
        elemImportance = 0;
    }

    /**
     * Parameterized constructor initializes a HeapElement with the given key and importance value.
     *
     * @param key The key (word) associated with this element.
     * @param importance The importance value of the word.
     */
    public HeapElement (String key, int importance) {
        this.key = key;
        this.elemImportance = importance;
    }
}
