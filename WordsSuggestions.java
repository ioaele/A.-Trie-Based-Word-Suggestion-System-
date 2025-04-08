/**
 * Class for suggesting words from the input dictionary based on a given word.
 * The suggested words are either have the given word as a prefix, have the same length as the given word with maximum of 2 different letters or have either 2 more letters
 *  or 1 less with the word in it (as it is or scrabled with max 2 more different letters)
 */
public class WordsSuggestions {
    public Trie trie; // Trie structure containing words and their importance.
    public HeapElement Minheap[]; // Min-heap for storing top suggestions with the most importance.
    public int size; // Current size of the heap.

    /**
     * Constructor to initialize the WordsSuggestions object.
     *
     * @param trie The Trie containing the words to suggest from.
     * @param k    The number of top suggestions to store in the min-heap.
     */
    WordsSuggestions(Trie trie, int k) {
        this.trie = trie;
        Minheap = new HeapElement[k + 1];
        for (int i = 0; i < k; i++)
            Minheap[i] = new HeapElement();
        size = 0;
    }

    /**
     * Finds and suggests words that start with a given prefix.
     *
     * @param prefix The prefix to search for.
     */
    public void Prefix(String prefix) {
        String st = "";
        boolean PrefixIn = true;
        Element NodeIn = null;

        TrieNode current = trie.tnode;
        for (int i = 0; i < prefix.length(); i++) {
            NodeIn = current.search(prefix.charAt(i)); // διατρεχω καθε χαρακτηρα της ζητουμενησ λεξεις και χτιζω την λεξη μεσα στα trieNodes
            // μεχρι να φτασω στο node που περιεχεται το τελευταιο γραμμα τησ λεξεισ.
            if (NodeIn == null) {
                PrefixIn = false;
                break;
            } // αν δεν περιεχεται τουλαχιστον ενα γραμμα τισ λεξεισ στο λεξικο, σημαινει δεν υπαρχουν λεξεισ που ξεκινουν (εχουν ωσ prefix) την ζητουμενη λεξη
            st += prefix.charAt(i);
            current = NodeIn.value;
        }

        if (PrefixIn) {
            collectWordsWithPrefix(current, st, prefix); // συλλεγω τισ λεξεισ που ξεκινουν με την ζητουμενη
        }
    }

    /**
     * Collects all words in the Trie that match a given prefix.
     *
     * @param node The current TrieNode being explored.
     * @param st   The prefix being built.
     * @param word The original prefix word.
     */
    private void collectWordsWithPrefix(TrieNode node, String st, String word) {
        if (node.isEndOfWord) {
            HeapElement helement = new HeapElement(st, node.importance);
            if (!alreadyIn(helement) && st.length() != word.length()) // αν δεν υπαρχει ηδη και δεν ανηκει και στην κατηγορια SameLength.
                insertHeap(helement);
        }

        for (char ch = 'a'; ch <= 'z'; ch++) { // διατρεχω απο ολο το πινακα και πηγαινω node-node αναδρομικα μεχρι να φτασω στο τελοσ καποιασ λεξεισ και την προθετω στο heap μου.
            Element element = node.search(ch);
            if (element != null) {
                TrieNode childNode = element.value;
                collectWordsWithPrefix(childNode, st + ch, word);
            }
        }
    }

    
    
     /// k=μεγεθοσ ζητουμενησ λεξησ.
    /**
     * Suggests words of the same length as the target word with limited mismatches.
     *
     * @param node         The current TrieNode being explored.
     * @param st           The prefix being built.
     * @param word         The target word for suggestions.
     * @param lengthCount  Current length of the built word.
     * @param notSame      Number of mismatches allowed.
     */
    
    public void SameLength(TrieNode node, String st, String word, int lengthCount, int notSame) {
        if (node.isEndOfWord && lengthCount == word.length() && notSame <= 2) { // οσεσ λεξεισ υπαρχουν στο k node και διαφερουν μεχρι 2 γραμματα απο την ζητουμενη λεξη, προστιθονται στο heap.
            HeapElement helement = new HeapElement(st, node.importance);
            if (!alreadyIn(helement))
                insertHeap(helement);
            return;
        }

        if (lengthCount == word.length()) {
            return;
        }

        for (char ch = 'a'; ch <= 'z'; ch++) { // διατρεχω αναδρομικα απο καθε node και χτιζω λεξη-λεξη μεχρι το k node οπου ειναι το μεγεθοσ τησ ζητουμενησ λεξησ. 
       
            Element element = node.search(ch);
            if (element != null) {
                TrieNode childNode = element.value;
                int updatedNotSame = notSame;
                if (lengthCount < word.length() && ch != word.charAt(lengthCount)) {
                    updatedNotSame++;
                }

                if (updatedNotSame <= 2) {
                    SameLength(childNode, st + ch, word, lengthCount + 1, updatedNotSame);
                }
            }
        }
    }

    /**
     * Suggests words with lengths differing from the target word by up to 2 characters.
     *
     * @param node         The current TrieNode being explored.
     * @param st           The prefix being built.
     * @param word         The target word for suggestions.
     * @param lengthCount  Current length of the built word.
     * @param notSame      Number of mismatches allowed.
     */
    public void DifferentLength(TrieNode node, String st, String word, int lengthCount, int notSame) {
        if ((lengthCount > word.length() + 2)) {
            return;
        }

        if (node.isEndOfWord && lengthCount == word.length() - 1 && notSame == 0) { // οσεσ λεξεισ υπαρχουν στο k-1 node (εχουν μεγεθοσ k-1) και δεν εχουν κανενα διαφορετκο γραμμα, προσττιθονται στο heap.
            HeapElement helement = new HeapElement(st, node.importance);
            if (!alreadyIn(helement)) {
                insertHeap(helement);
            }
        } else if (node.isEndOfWord && lengthCount == word.length() + 1 && notSame <= 1) { // οσεσ λεξεισ υπαρχουν στο k+1 node (εχουν μεγεθοσ k+1) και εχουν μεχρι και 1 διαφορετκο γραμμα, προστιθονται στο heap.
            HeapElement helement = new HeapElement(st, node.importance);
            if (!alreadyIn(helement)) {
                insertHeap(helement);
            }
        } else if (node.isEndOfWord && lengthCount == word.length() + 2 && notSame <= 2) { // οσεσ λεξεισ υπαρχουν στο k+2 node (εχουν μεγεθοσ k+2) και εχουν μεχρι και 2 διαφορετκο γραμμα, προστιθονται στο heap.
            HeapElement helement = new HeapElement(st, node.importance);
            if (!alreadyIn(helement)) {
                insertHeap(helement);
            }
        }

        for (char ch = 'a'; ch <= 'z'; ch++) { // διατρεχω αναδρομικα απο καθε node και χτιζω λεξη-λεξη μεχρι να περασω απο ολεσ τισ θεσεισ των trieNodes.
            Element element = node.search(ch);
            if (element != null) {
                TrieNode childNode = element.value;
                int updatedNotSame = notSame;
                boolean same = false;

                for (int i = 0; i < word.length(); i++)
                    if (ch == word.charAt(i))
                        same = true;

                if (!same)
                    updatedNotSame++;

                DifferentLength(childNode, st + ch, word, lengthCount + 1, updatedNotSame);
            }
        }
    }

    /**
     * Deletes the minimum element in the heap and rebalances it.
     */
    public void deleteMin() {
        HeapElement last = new HeapElement();
        int x = 1, child = 0;
        if (size != 0) {
            last = Minheap[this.size];
            this.size--;

            while ((x * 2) <= this.size) {
                child = x * 2;
                if (child != this.size && Minheap[child + 1].elemImportance < Minheap[child].elemImportance)
                    child++;
                if (last.elemImportance > Minheap[child].elemImportance) {
                    Minheap[x].key = Minheap[child].key;
                    Minheap[x].elemImportance = Minheap[child].elemImportance;
                    x = child;
                } else
                    break;
            }
            Minheap[x].key = last.key;
            Minheap[x].elemImportance = last.elemImportance;
        }
    }

    /**
     * Inserts an element into the heap while maintaining the heap properties.
     *
     * @param element The element to insert.
     */
    public void insertHeap(HeapElement element) {
        if (!alreadyIn(element)) {
            if (size == Minheap.length - 1) {
                if (Minheap[1].elemImportance < element.elemImportance) {
                    Minheap[1] = element;
                    PercolateDown(1);
                }
            } else {
                size++;
                int index = size;
                while (index > 1 && Minheap[index / 2].elemImportance > element.elemImportance) {
                    Minheap[index] = Minheap[index / 2];
                    index = index / 2;
                }
                Minheap[index] = element;
            }
        }
    }

    /**
     * Rebalances the heap after an element is moved downward.
     *
     * @param i The index to start percolating down from.
     */
    public void PercolateDown(int i) {
        HeapElement k = new HeapElement();
        k.key = Minheap[i].key;
        k.elemImportance = Minheap[i].elemImportance;

        int j;
        while (2 * i <= size) {
            j = 2 * i;
            if (j < size && Minheap[j + 1].elemImportance < Minheap[j].elemImportance)
                j++;
            if (k.elemImportance > Minheap[j].elemImportance) {
                Minheap[i].elemImportance = Minheap[j].elemImportance;
                Minheap[i].key = Minheap[j].key;
                i = j;
            } else
                break;
        }
        Minheap[i] = k;
    }

    /**
     * Prints the contents of the heap.
     */
    public void printHeap() {
        for (int i = 1; i <= size; i++)
            System.out.println(Minheap[i].key + " " + Minheap[i].elemImportance);
    }

    /**
     * Checks if a given element is already in the heap.
     *
     * @param element The element to check for.
     * @return True if the element exists in the heap, false otherwise.
     */
    public boolean alreadyIn(HeapElement element) {
        for (int i = 1; i < Minheap.length; i++) {
            if ((Minheap[i] != null) && (Minheap[i].key != "") && (element.key.equals(Minheap[i].key))) {
                return true;
            }
        }
        return false;
    }
}
