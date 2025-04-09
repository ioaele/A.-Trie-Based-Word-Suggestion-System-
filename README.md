EPL231 Assignment : A Trie-Based Word Suggestion System with Importance Ranking (most-search)

**Authors:** Eleni Ioannou & Georgos Georgiadis 
**Last Updated:** 28/11/2024 

The Trie-Based Word Suggestion System implements a powerful word suggestion engine using a combination of a Trie data structure and Robin Hood Hashing to store dictionary words and track their importance based on frequency in additional text files. It also provides smart word suggestions based on prefix and similarity, ranking them by usage frequency.

**Features**

- Efficient Trie for word storage and prefix search
- Each Trie node tracks word importance (frequency of appearance)
- Robin Hood Hashing for managing and accessing words quickly
- Parses a dictionary file and a text file to assign importance
- Suggests alternative words based on:
  - Prefix match
  - Similar length with ≤2 character differences
  - Shorter or slightly longer words with loose character inclusion
- Uses a min-heap to return the `k` most important suggestions


**Structure Overview**

1. **TrieNode**: Contains child nodes, an `importance` counter, and whether the node completes a word.
2. **Robin Hood Hash Table**: Stores words with efficient collision resolution.
3. **Min-Heap**: Stores the top `k` suggestions based on importance.

**Important**
In order to see how the Trie nodes implementation using a Robin Hood hashing table has a better approach than using a Static array, we implemented the same project using a static array (all the ...2 classes).
  > Further explanation using a static array:
Each Trie node contains a static array where each position corresponds to a character, without relying on the ASCII table.Each entry in the array stores the character and a pointer to the next Trie node. Additionally, each node holds the importance value of the word that ends at that node.

**Run** 
Run the program with two input files:
java mainGame <dictionary_file> <importance_text_file>
The text file is parsed to count how often dictionary words appear (ignoring punctuation), increasing their importance each time they're found.

After preprocessing, the program enters an interactive mode. It takes as an input: 1) a word to be searches 2) an integer k (number of suggestions)
The system will return up to k relevant word suggestions, chosen using:
Similarity Criteria:
  - Prefix match: Suggested word starts with the input (e.g., input: plan → plant, plane).
  - Same length, ≤2 character differences: e.g., plan → clan, plum, span.
  - Different length (−1 to +2 characters): Words that loosely match (input characters may appear in order with gaps), e.g., plan → planet, plains, planner.
The results are ranked by importance using a min-heap to ensure the top k are shown.



