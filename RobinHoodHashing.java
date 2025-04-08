/**
 * This class implements Robin Hood hashing, a variation of open addressing hash table.
 * In Robin Hood hashing, when a collision occurs, the element with the shorter probe length is reinserted,
 * while the one with the longer probe length stays in place. This improves distribution in some cases.
 */
public class RobinHoodHashing {
    
    /** The array of elements stored in the hash table. */
    Element table[];
    
    /** The capacity (size) of the hash table. */
    int capacity; // 4B
    
    /** The current number of elements in the hash table. */
    int size;     // 4B
    
    /** The maximum probe length encountered during insertions. */
    int maxProbeLength; // 4B

    /**
     * Constructor to initialize the Robin Hood hash table with a default capacity of 5.
     */
    RobinHoodHashing() {
        table = new Element[5];

        // Initialize each slot in the table with an empty Element.
        for (int i = 0; i < 5; i++)
            table[i] = new Element();

        capacity = 5;
        size = 0;
        maxProbeLength = 0;
    }

    /**
     * Inserts an element into the hash table. If the table is more than 90% full, it triggers a rehash.
     * It handles collisions by using the Robin Hood hashing principle.
     *
     * @param k The element to be inserted into the table.
     */
    public void insert(Element k) {
        // Only insert if the element is not already present in the table.
        if (search(k) == -1) {
            int in = Character.toLowerCase(k.key) % capacity; //ΟΛΟΙ ΟΙ ΧΑΡΑΚΤΗΡΕΣ ΕΙΝΑΙ ΤΟΠΟΘΕΤΗΜΕΝΟΙ ΣΤΟ HASHTABLE ΒΑΣΗ ΤΗΝ ΤΙΜΗ ΤΟΥ ΜΙΚΡΟΥ ΓΡΑΜΜΑΤΟΣ ΤΟΥ

            // Rehash if the table is more than 90% full.
            if (((size + 1) * 100 / capacity) >= 90)
                this.rehash();

            // If the slot is empty, insert the element.
            if (table[in].key == 0 || table[in] == null) {
                table[in] = k;
            } else {
                // Handle collisions using the Robin Hood principle.
                while (table[in] != null && table[in].key != 0) {
                    // If the current element has a higher probe length, swap it with the new element.
                    if (table[in].probeLength < k.probeLength) {
                        Element temp = table[in];
                        table[in] = k;
                        k = temp;
                    }

                    k.probeLength++;

                    // Move to the next slot, wrapping around if necessary.
                    if (in + 1 >= capacity)
                        in = 0;
                    in++;
                }
                // Insert the element in the found position.
                table[in] = k;
            }

            // Update the maximum probe length encountered.
            for (int i = 0; i < capacity; i++)
                if (table[i].probeLength > maxProbeLength)
                    maxProbeLength = table[i].probeLength;

            size++;
        }
    }

    /**
     * Resizes the hash table and rehashes all elements when the table reaches its capacity limit.
     * The capacity is increased according to predefined values (5, 11, 19, 29).
     */
    public void rehash() {
        // Save the current table to rehash the elements.
        Element[] oldTable = table;

        // Increase the capacity following the predefined sequence.
        if (capacity == 5)
            capacity = 11;
        else if (capacity == 11)
            capacity = 19;
        else if (capacity == 19)
            capacity = 29;

        // Create a new table with the updated capacity.
        table = new Element[capacity];

        // Initialize each slot with an empty Element.
        for (int i = 0; i < capacity; i++) {
            table[i] = new Element();
        }

        size = 0;

        // Rehash all existing elements into the new table.
        for (int i = 0; i < oldTable.length; i++) {
            if (oldTable[i] != null && oldTable[i].key != 0)
                insert(oldTable[i]);
        }
    }

    /**
     * Searches for an element in the hash table. Returns the index of the element if found, or -1 if not found.
     * It performs collision resolution by probing according to the Robin Hood principle.
     *
     * @param k The element to be searched for in the table.
     * @return The index of the element if found, or -1 if not found.
     */
    public int search(Element k) {
        int in = Character.toLowerCase(k.key) % capacity; //ΟΛΟΙ ΟΙ ΧΑΡΑΚΤΗΡΕΣ ΕΙΝΑΙ ΤΟΠΟΘΕΤΗΜΕΝΟΙ ΣΤΟ HASHTABLE ΒΑΣΗ ΤΗΝ ΤΙΜΗ ΤΟΥ ΜΙΚΡΟΥ ΓΡΑΜΜΑΤΟΣ ΤΟΥ
        int KprobeLength = k.probeLength;

        // 	διατρεχω τον πινακα μου
        while (table[in] != null && table[in].key != 0 && KprobeLength <= maxProbeLength) {
            // If the element is found, return the index.
            if (table[in].key != 0 && Character.toLowerCase(table[in].key) == Character.toLowerCase(k.key))
                return in;

            // Move to the next slot, wrapping around if necessary.
            if (in + 1 >= capacity)
                in = 0;
            KprobeLength++;
            in++;
        }

        return -1; // Return -1 if the element is not found.
    }
}
