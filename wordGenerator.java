import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

public class wordGenerator {
    public static void main(String[] args) {
        String inputFile = "words.txt";   // Input file
        String outputFile = "100000dl.txt"; // Output file
        int wordCount = 100000;              // Number of words to generate
        int minLength = 1;                // Minimum word length
        int maxLength = 20;               // Maximum word length
        double avgLength = 5.0;           // Average word length
        double stdDev =8.0;              // Standard deviation

        generateWordList(inputFile, outputFile, wordCount, minLength, maxLength, avgLength, stdDev);
    }

    public static void generateWordList(String inputFile, String outputFile, int wordCount,
                                        int minLength, int maxLength, double avgLength, double stdDev) {
        try {
            // Read the input as a continuous string
            String text = readInputAsContinuousText(inputFile);

            // Extract words ignoring delimiters and removing any numbers
            List<String> words = extractWordsFromText(text, minLength, maxLength);

            // Shuffle the words for randomness
            Collections.shuffle(words);

            // Filter by deviation and limit to word count
            List<String> selectedWords = words.stream()
                    .filter(word -> Math.abs(word.length() - avgLength) <= stdDev)
                    .limit(wordCount)
                    .collect(Collectors.toList());

            // Write the output to a file
            writeWordsToFile(outputFile, selectedWords);

            System.out.println("Generated " + selectedWords.size() + " words.");
        } catch (IOException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }

    private static String readInputAsContinuousText(String filename) throws IOException {
        StringBuilder content = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                content.append(line); // Append lines without splitting them
            }
        }
        return content.toString();
    }

    private static List<String> extractWordsFromText(String text, int minLength, int maxLength) {
        List<String> words = new ArrayList<>();
        int start = 0;

        // Process the text character by character
        for (int i = 0; i <= text.length(); i++) {
            // Define word boundaries
            if (i == text.length() || !Character.isLetterOrDigit(text.charAt(i))) {
                int length = i - start;
                String word = text.substring(start, i);

                // Check if word is valid (contains only letters, no numbers)
                if (length >= minLength && length <= maxLength && word.matches("[a-zA-Z]+")) {
                    words.add(word);
                }
                start = i + 1;
            }
        }
        return words;
    }

    private static void writeWordsToFile(String filename, List<String> words) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            for (String word : words) {
                writer.write(word);
                writer.newLine();
            }
        }
    }
}