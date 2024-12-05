package challenges;

import java.io.*;
import java.util.*;

public class Day2RedNosedReports {

    // Part 1: Count the number of safe reports
    public static void solvePartOne(File inputFile) {
        try {
            // Parse the input file into a list of reports
            List<List<Integer>> reports = parseInput(inputFile);

            // Count the number of safe reports
            int safeCount = 0;
            for (List<Integer> report : reports) {
                if (isSafeReport(report)) {
                    safeCount++;
                }
            }

            // Output the result
            System.out.println("(Part 1) Number of safe reports: " + safeCount);
        } catch (FileNotFoundException e) {
            System.err.println("Input file not found: " + inputFile.getAbsolutePath());
        } catch (Exception e) {
            System.err.println("An error occurred: " + e.getMessage());
        }
    }

    // Part 2: Count the number of safe reports considering the Problem Dampener
    public static void solvePartTwo(File inputFile) {
        try {
            // Parse the input file into a list of reports
            List<List<Integer>> reports = parseInput(inputFile);

            // Count the number of safe reports
            int safeCount = 0;
            for (List<Integer> report : reports) {
                if (isSafeReport(report) || canBeMadeSafe(report)) {
                    safeCount++;
                }
            }

            // Output the result
            System.out.println("(Part 2) Number of safe reports with Problem Dampener: " + safeCount);
        } catch (FileNotFoundException e) {
            System.err.println("Input file not found: " + inputFile.getAbsolutePath());
        } catch (Exception e) {
            System.err.println("An error occurred: " + e.getMessage());
        }
    }

    // Helper method to parse the input file into a list of reports
    private static List<List<Integer>> parseInput(File inputFile) throws IOException {
        List<List<Integer>> reports = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(inputFile))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.trim().split("\\s+");
                List<Integer> report = new ArrayList<>();
                for (String part : parts) {
                    report.add(Integer.parseInt(part));
                }
                reports.add(report);
            }
        }
        return reports;
    }

    // Helper method to determine if a report is safe
    private static boolean isSafeReport(List<Integer> report) {
        if (report.size() < 2) {
            return true; // A report with fewer than 2 levels is trivially safe
        }

        // Determine the trend (increasing or decreasing) and validate differences
        boolean isIncreasing = report.get(1) > report.get(0);
        for (int i = 1; i < report.size(); i++) {
            int diff = report.get(i) - report.get(i - 1);

            // Check if the difference is valid
            if (Math.abs(diff) < 1 || Math.abs(diff) > 3) {
                return false;
            }

            // Check if the trend is consistent
            if ((diff > 0 && !isIncreasing) || (diff < 0 && isIncreasing)) {
                return false;
            }
        }

        return true;
    }

    // Helper method to determine if a report can be made safe by removing one level
    private static boolean canBeMadeSafe(List<Integer> report) {
        for (int i = 0; i < report.size(); i++) {
            // Create a modified report with the i-th level removed
            List<Integer> modifiedReport = new ArrayList<>(report);
            modifiedReport.remove(i);

            // Check if the modified report is safe
            if (isSafeReport(modifiedReport)) {
                return true;
            }
        }

        return false;
    }
}
