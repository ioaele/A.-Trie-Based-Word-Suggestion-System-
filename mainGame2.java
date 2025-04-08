import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * The main class for handling the game logic. It processes input files to build a Trie, 
 * updates word importance, and suggests words based on a prefix.
 * 
 * The class performs the following tasks:


 *     Reads words from the first input file and inserts them into a Trie structure.
 *     Processes the second input file to update the importance of words in the Trie.
 *     Generates word suggestions based on a prefix, matching word length, and different length conditions.
 *     Displays the word suggestions and memory usage.
 */
public class mainGame2 {

    /**
     * Main method that serves as the entry point for the program.
     * <p>
     * It reads two input files specified by command-line arguments, inserts words into a Trie, 
     * updates word importance based on the second file, and generates word suggestions 
     * based on the prefix "call". The results are then printed to the console.
     * </p>
     *
     * @param args Command-line arguments containing the file names for input files.
     */
    public static void main(String args[]) {
        // Check if the number of command-line arguments is correct.
        if (args.length != 2) {
            System.out.println("Wrong input arguments. Insert the two input file names.");
            return;
        }

        // Get the file names from the command-line arguments.
        String file1 = args[0];
        String file2 = args[1];

        // Create a new Trie instance for storing words.
        Trie2 trie = new Trie2();

        // Read the first input file and insert the words into the Trie.
        try {
            File myObj = new File(file1);
            Scanner scanner = new Scanner(myObj);
            while (scanner.hasNextLine()) {
                String word = scanner.nextLine();
                trie.insert(word);
            }
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

        // Read the second input file and update the importance of words in the Trie.
        try {
            File file = new File(file2);
            Scanner scanner = new Scanner(file);

            while (scanner.hasNext()) {
                String word = scanner.next();
                boolean flag = true;

                // Check if the word contains a delimiter in the middle.
                for (int i = 0; i < word.length(); i++) {
                    if (isDelimiter(word.charAt(i)) && word.length() - 1 != i) {
                        flag = false;
                        break;
                    }
                }

                // If the word has an invalid delimiter, skip it.
                if (flag == false)
                    continue;

                // If the last character is a delimiter, remove it and update the word importance.
                if (isDelimiter(word.charAt(word.length() - 1))) {
                    trie.updateImportance(word.substring(0, word.length() - 1));
                } else {
                    // Otherwise, update the word's importance normally.
                    trie.updateImportance(word);
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

        //Προτεινω λεξεις βαση τησ ζητουμενησ st και των αντιστοιχων κριτηριων. 
        WordSuggestions2 ws = new WordSuggestions2(trie, 4);
        String st = "call";

        ws.Prefix(st); //Α
        ws.SameLength(trie.root, "", st, 0, 0); //Β 
        ws.DifferentLength(trie.root, "", st, 0, 0); //Γ
        ws.printHeap(); // τυπωνω το heap με τις n(n=ποσες προτεινομενεσ λεξεισ θελω) ποιο σημαντικεσ λεξεισ (λεξεισ που εμφανιζονται περισσοτερο στο κειμενο (from file2).
        System.out.println(memoryCounter2.countElements2(trie.root)); // τυπονω την καταναλωση μνημησ για την υλοποηση τησ εργασιασ 
    }
    /**
     * Determines if a given character is a delimiter.
     * <p>
     * The following characters are considered delimiters: ",.!?;:'\"()[]{}\\".
     * </p>
     *
     * @param c The character to check.
     * @return True if the character is a delimiter, false otherwise.
     */
    public static boolean isDelimiter(char c) {
        // List of delimiter characters.
        String delimiters = ",.!?;:'\"()[]{}\\";
        
        // Return true if the character is in the delimiters string.
        return delimiters.indexOf(c) >= 0;
    }
}
