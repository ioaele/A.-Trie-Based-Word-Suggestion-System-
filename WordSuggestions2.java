public class WordSuggestions2 {

	public Trie2 trie;
	public HeapElement Minheap[];
	public int size;

	public WordSuggestions2(Trie2 trie, int k) {

		this.trie = trie;
		Minheap = new HeapElement[k + 1];
		for (int i = 0; i < k; i++)
			Minheap[i] = new HeapElement();
		size = 0;
	}

	public void Prefix(String prefix) {
		String st = "";
		boolean PrefixIn = true;

		Element2 NodeIn;
		Element2 current = trie.root;

		NodeIn = trie.search(prefix);

		if (NodeIn == null) {
			PrefixIn = false;

		}
		st = prefix;

		current = NodeIn;

		if (PrefixIn) {
			collectWordsWithPrefix(current, st, prefix);
		}
	}

	private void collectWordsWithPrefix(Element2 element, String st, String word) {

		TrieNode2 node=element.value;
		if (node.isEndOfWord) {
			HeapElement helement = new HeapElement(st, node.importance);
			if (!alreadyIn(helement) && st.length() != word.length())
				insertHeap(helement);
		}


		for (int i = 0; i < node.children.length; i++) {

			if (node.children[i] != null) {
				Element2 elementchild=node.children[i];
				collectWordsWithPrefix(elementchild, st + node.children[i].key, word);
			}
		}
	}

	public void SameLength(Element2 element, String st, String word, int lengthCount, int notSame) {

		TrieNode2 node=element.value;
		if (node.isEndOfWord && lengthCount == word.length() && notSame <= 2) {

			HeapElement helement = new HeapElement(st, node.importance);
			if (!alreadyIn(helement))
				insertHeap(helement);
			return;
		}

		if (lengthCount == word.length()) {
			return;
		}

		for (int i = 0; i < node.children.length; i++) {

			if (node.children[i] != null) {
				Element2 childelement = node.children[i];

				int updatedNotSame = notSame;
				if (lengthCount < word.length() && node.children[i].key != word.charAt(lengthCount)) {
					updatedNotSame++;
				}

				if (updatedNotSame <= 2) {
					SameLength(childelement, st + node.children[i].key, word, lengthCount + 1, updatedNotSame);
				}
			}
		}
	}

	public void DifferentLength(Element2 element, String st, String word, int lengthCount, int notSame) {

		TrieNode2 node= element.value;
		if ((lengthCount > word.length() + 2)) {
			return;
		}

		if (node.isEndOfWord && lengthCount == word.length() - 1 && notSame == 0) {
			HeapElement helement = new HeapElement(st, node.importance);
			if (!alreadyIn(helement)) {
				insertHeap(helement);
			}

		} else if (node.isEndOfWord && lengthCount == word.length() + 1 && notSame <= 1) {

			HeapElement helement = new HeapElement(st, node.importance);
			if (!alreadyIn(helement)) {
				insertHeap(helement);
			}
		}

		else if (node.isEndOfWord && lengthCount == word.length() + 2 && notSame <= 2) {

			HeapElement helement = new HeapElement(st, node.importance);
			if (!alreadyIn(helement)) {
				insertHeap(helement);
			}
		}

		for (int i = 0; i < node.children.length; i++) {

			if (node.children[i] != null) {
				Element2 childelement = node.children[i];

				int updatedNotSame = notSame;
				boolean same = false;

				for (int j = 0; j < word.length(); j++)
					if (node.children[i].key == word.charAt(j))
						same = true;

				if (!same)
					updatedNotSame++;

				DifferentLength(childelement, st + node.children[i].key, word, lengthCount + 1, updatedNotSame);

			}
		}
	}



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

	public void printHeap() {
		for (int i = 1; i <= size; i++)
			System.out.println(Minheap[i].key + " " + Minheap[i].elemImportance);

	}

	public boolean alreadyIn(HeapElement element) {

		for (int i = 1; i < Minheap.length; i++) {

			if ((Minheap[i] != null) && (Minheap[i].key != "") && (element.key.equals(Minheap[i].key))) {
				return true;

			}
		}
		return false;

	}

}