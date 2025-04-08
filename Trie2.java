public class Trie2 {
    public Element2 root;
    public static int TrieNodeCounter;

    public Trie2() {
        root = new Element2();
        TrieNodeCounter = 0;
    }

    public void insert(String word) {
        Element2 current = root;
        TrieNode2 currentnode = root.value;

        for (int i = 0; i < word.length(); i++) {
            char ch = word.charAt(i);
            int index = Character.toLowerCase(ch) - 'a';

            if (currentnode==null||currentnode.children[index] == null) {
                currentnode.children[index] = new Element2();
                TrieNodeCounter++;
                currentnode.children[index].key=ch;
            }
            current = currentnode.children[index];
            currentnode = current.value;
    }
        // Mark end of word after insertion
        currentnode.isEndOfWord = true;
    }


    public Element2 search(String word) {
        Element2 current = root;
        TrieNode2 currentnode = root.value;

        for (int i = 0; i < word.length(); i++) {
            if (current == null) return null;
            current = currentnode.search2(word.charAt(i));
            if (current != null)
            	currentnode = current.value;
        }
        if (current == null || !currentnode.isEndOfWord) return null;
        return current;
    }

    public void updateImportance(String k) {
        Element2 searchNum = search(k);
        if (searchNum != null) {
            searchNum.value.importance++;
        }
    }
}

