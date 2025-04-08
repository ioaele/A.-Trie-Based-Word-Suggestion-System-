import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * The mainGame class serves as the entry point for the program, 
 * which processes two input files: one to build a Trie structure 
 * and the other to update the Trie with word importances and perform suggestions.
 */
public class mainGame {

    /**
     * The main method executes the program's logic, including reading input files,
     * building a Trie structure, updating word importances, and generating word suggestions.
     * 
     * @param args command-line arguments, where args[0] is the first input file 
     *             and args[1] is the second input file.
     */
    public static void main(String args[]) {
        if (args.length != 2) {
            System.out.println("Wrong input arguments. Insert the two input file names.");
            return;
        }

        String file1 = args[0];
        String file2 = args[1];
        Trie trie = new Trie();

        // Reading the first file and adding words to the Trie.
        try {
            File myObj = new File(file1);
            Scanner scanner = new Scanner(myObj);
            while (scanner.hasNextLine()) {
                String word = scanner.nextLine();
                trie.add(word);
            }
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

        // Reading the second file and updating word importance in the Trie.
        try {
            File file = new File(file2);
            Scanner scanner = new Scanner(file);

            while (scanner.hasNext()) {
                String word = scanner.next();
                boolean flag = true;

                for (int i = 0; i < word.length(); i++) {
                    if (isDelimiter(word.charAt(i)) && (word.length() - 1 != i)) {
                        flag = false;
                        break;
                    }
                }
                if (!flag) {
                    continue;
                }

                if (isDelimiter(word.charAt(word.length() - 1))) {
                    trie.updateImportance(word.substring(0, word.length() - 1));
                } else {
                    trie.updateImportance(word);
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

        //Προτεινω λεξεις βαση τησ ζητουμενησ st και των αντιστοιχων κριτηριων. 
        WordsSuggestions ws = new WordsSuggestions(trie, 4);
        String st = "call";

        ws.Prefix(st); //Α
        ws.SameLength(trie.tnode, "", st, 0, 0); //Β 
        ws.DifferentLength(trie.tnode, "", st, 0, 0); //Γ
        ws.printHeap(); // τυπωνω το heap με τις n(n=ποσες προτεινομενεσ λεξεισ θελω) ποιο σημαντικεσ λεξεισ (λεξεισ που εμφανιζονται περισσοτερο στο κειμενο (from file2).
        System.out.println(memoryCounter.countTrieNodes(trie.tnode)); // τυπονω την καταναλωση μνημησ για την υλοποηση τησ εργασιασ 
    }

    /**
     * Determines whether a character is a delimiter.
     * 
     * @param c the character to check.
     * @return true if the character is a delimiter, false otherwise.
     */
    public static boolean isDelimiter(char c) {
        String delimiters = ",.!?;:'\"()[]{}\\";
        return delimiters.indexOf(c) >= 0;
    }
}
