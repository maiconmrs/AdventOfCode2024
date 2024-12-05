package challenges;

import java.io.*;
import java.util.*;
import java.util.regex.*;

public class Day3MullItOver {

    // Part 1: Parse the input and compute the sum of valid mul results
    public static void solvePartOne(File inputFile) {
        try {
            // Read the entire file as a single string
            String corruptedMemory = readFileAsString(inputFile);

            // Regular expression to match valid mul(X,Y) instructions
            String regex = "mul\\((\\d{1,3}),(\\d{1,3})\\)";
            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(corruptedMemory);

            // Compute the sum of valid results
            int totalSum = 0;
            while (matcher.find()) {
                // Extract X and Y from the match
                int x = Integer.parseInt(matcher.group(1));
                int y = Integer.parseInt(matcher.group(2));

                // Add the product to the total sum
                totalSum += x * y;
            }

            // Output the result
            System.out.println("(Part 1) Total sum of valid mul results: " + totalSum);
        } catch (FileNotFoundException e) {
            System.err.println("Input file not found: " + inputFile.getAbsolutePath());
        } catch (Exception e) {
            System.err.println("An error occurred: " + e.getMessage());
        }
    }

    // Part 2: Handle do() and don't() instructions and compute the sum
    public static void solvePartTwo(File inputFile) {
        try {
            // Read the entire file as a single string
            String corruptedMemory = readFileAsString(inputFile);

            // Regular expressions for instructions
            String mulRegex = "mul\\((\\d{1,3}),(\\d{1,3})\\)";
            String doRegex = "do\\(\\)";
            String dontRegex = "don't\\(\\)";
            Pattern mulPattern = Pattern.compile(mulRegex);
            Pattern doPattern = Pattern.compile(doRegex);
            Pattern dontPattern = Pattern.compile(dontRegex);

            // Initialize variables
            int totalSum = 0;
            boolean isEnabled = true; // Start with mul enabled

            // Use a Matcher to process the corrupted memory
            int currentIndex = 0;
            while (currentIndex < corruptedMemory.length()) {
                // Check for mul(X,Y)
                Matcher mulMatcher = mulPattern.matcher(corruptedMemory.substring(currentIndex));
                if (mulMatcher.lookingAt()) {
                    if (isEnabled) {
                        int x = Integer.parseInt(mulMatcher.group(1));
                        int y = Integer.parseInt(mulMatcher.group(2));
                        totalSum += x * y;
                    }
                    currentIndex += mulMatcher.end();
                    continue;
                }

                // Check for do()
                Matcher doMatcher = doPattern.matcher(corruptedMemory.substring(currentIndex));
                if (doMatcher.lookingAt()) {
                    isEnabled = true;
                    currentIndex += doMatcher.end();
                    continue;
                }

                // Check for don't()
                Matcher dontMatcher = dontPattern.matcher(corruptedMemory.substring(currentIndex));
                if (dontMatcher.lookingAt()) {
                    isEnabled = false;
                    currentIndex += dontMatcher.end();
                    continue;
                }

                // If no match, move to the next character
                currentIndex++;
            }

            // Output the result
            System.out.println("(Part 2) Total sum of valid mul results with conditions: " + totalSum);
        } catch (FileNotFoundException e) {
            System.err.println("Input file not found: " + inputFile.getAbsolutePath());
        } catch (Exception e) {
            System.err.println("An error occurred: " + e.getMessage());
        }
    }

    // Helper method to read the input file as a single string
    private static String readFileAsString(File inputFile) throws IOException {
        StringBuilder content = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(inputFile))) {
            String line;
            while ((line = reader.readLine()) != null) {
                content.append(line);
            }
        }
        return content.toString();
    }

}
