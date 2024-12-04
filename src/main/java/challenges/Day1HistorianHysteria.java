package challenges;
import java.io.*;
import java.util.*;

public class Day1HistorianHysteria {

    // Helper method to parse the input file into two lists
    private static Map<String, List<Long>> parseInput(File inputFile) throws FileNotFoundException {
        List<Long> leftValues = new ArrayList<Long>();
        List<Long> rightValues = new ArrayList<Long>();

        Scanner scanner = new Scanner(inputFile);
        try {
            while (scanner.hasNextLine()) {
                String[] parts = scanner.nextLine().split("\\s+");
                leftValues.add(Long.parseLong(parts[0]));
                rightValues.add(Long.parseLong(parts[1]));
            }
        } finally {
            scanner.close(); // Make sure to close the scanner
        }
        Map<String, List<Long>> result = new HashMap<String,List<Long>>();
        result.put("left", leftValues);
        result.put("right", rightValues);

        return result;
    }

    // Part 1: Calculate the total distance
    public static void solvePartOne(File inputFile) {
        try {
            // Parse input
            Map<String, List<Long>> parsedData = parseInput(inputFile);
            List<Long> leftList = parsedData.get("left");
            List<Long> rightList = parsedData.get("right");

            // Sort the lists
            Collections.sort(leftList);
            Collections.sort(rightList);

            // Calculate total distance
            long totalDistance = 0;
            for (int i = 0; i < leftList.size(); i++) {
                totalDistance += Math.abs(leftList.get(i) - rightList.get(i));
            }

            System.out.println("(Part 1) Total Distance: " + totalDistance);
        } catch (FileNotFoundException e) {
            System.err.println("Input file not found: " + inputFile.getAbsolutePath());
        } catch (Exception e) {
            System.err.println("An error occurred: " + e.getMessage());
        }
    }

    // Part 2: Calculate the similarity score
    public static void solvePartTwo(File inputFile) {
        try {
            // Parse input
            Map<String, List<Long>> parsedData = parseInput(inputFile);
            List<Long> leftList = parsedData.get("left");
            List<Long> rightList = parsedData.get("right");

            // Count occurrences in the right list
            Map<Long, Integer> rightCounts = new HashMap<>();
            for (Long num : rightList) {
                rightCounts.put(num, rightCounts.getOrDefault(num, 0) + 1);
            }

            // Calculate similarity score
            long similarityScore = 0;
            for (Long num : leftList) {
                int count = rightCounts.getOrDefault(num, 0);
                similarityScore += num * count;
            }

            System.out.println("(Part 2) Similarity Score: " + similarityScore);
        } catch (FileNotFoundException e) {
            System.err.println("Input file not found: " + inputFile.getAbsolutePath());
        } catch (Exception e) {
            System.err.println("An error occurred: " + e.getMessage());
        }
    }
}
