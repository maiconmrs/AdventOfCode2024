package challenges;

import java.io.*;
import java.util.*;

public class Day5PrintQueue {

    // Helper method to parse the input file into rules and updates
    private static Map<String, List<String>> parseInput(File inputFile) throws FileNotFoundException {
        Scanner scanner = new Scanner(inputFile);
        List<String> rulesInput = new ArrayList<>();
        List<String> updatesInput = new ArrayList<>();
        boolean isRulesSection = true;

        try {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine().trim();
                if (line.isEmpty()) {
                    isRulesSection = false; // Switch to updates section
                    continue;
                }

                if (isRulesSection) {
                    rulesInput.add(line);
                } else {
                    updatesInput.add(line);
                }
            }
        } finally {
            scanner.close();
        }

        Map<String, List<String>> parsedData = new HashMap<>();
        parsedData.put("rules", rulesInput);
        parsedData.put("updates", updatesInput);

        return parsedData;
    }

    // Part 1: Calculate the sum of middle pages of valid updates
    public static void solvePartOne(File inputFile) {
        try {
            // Parse input
            Map<String, List<String>> parsedData = parseInput(inputFile);
            List<String> rulesInput = parsedData.get("rules");
            List<String> updatesInput = parsedData.get("updates");

            // Parse rules into a map
            Map<Integer, Set<Integer>> orderingRules = parseRules(rulesInput);

            // Parse updates into a list of lists
            List<List<Integer>> updates = parseUpdates(updatesInput);

            // Process updates
            int sumOfMiddlePages = 0;
            for (List<Integer> update : updates) {
                if (isValidUpdate(update, orderingRules)) {
                    int middlePage = update.get(update.size() / 2);
                    sumOfMiddlePages += middlePage;
                }
            }

            System.out.println("(Part 1) Sum of middle pages: " + sumOfMiddlePages);
        } catch (FileNotFoundException e) {
            System.err.println("Input file not found: " + inputFile.getAbsolutePath());
        } catch (Exception e) {
            System.err.println("An error occurred: " + e.getMessage());
        }
    }

    // Part 2: Reorder incorrect updates and calculate the sum of their middle pages
    public static void solvePartTwo(File inputFile) {
        try {
            // Parse input
            Map<String, List<String>> parsedData = parseInput(inputFile);
            List<String> rulesInput = parsedData.get("rules");
            List<String> updatesInput = parsedData.get("updates");

            // Parse rules into a map
            Map<Integer, Set<Integer>> orderingRules = parseRules(rulesInput);

            // Parse updates into a list of lists
            List<List<Integer>> updates = parseUpdates(updatesInput);

            // Process incorrect updates
            int sumOfMiddlePages = 0;
            for (List<Integer> update : updates) {
                if (!isValidUpdate(update, orderingRules)) {
                    // Reorder the incorrect update using topological sort
                    List<Integer> correctedUpdate = reorderUpdate(update, orderingRules);
                    int middlePage = correctedUpdate.get(correctedUpdate.size() / 2);
                    sumOfMiddlePages += middlePage;
                }
            }

            System.out.println("(Part 2) Sum of middle pages after reordering: " + sumOfMiddlePages);
        } catch (FileNotFoundException e) {
            System.err.println("Input file not found: " + inputFile.getAbsolutePath());
        } catch (Exception e) {
            System.err.println("An error occurred: " + e.getMessage());
        }
    }

    // Helper method to parse rules
    private static Map<Integer, Set<Integer>> parseRules(List<String> rulesInput) {
        Map<Integer, Set<Integer>> rules = new HashMap<>();
        for (String rule : rulesInput) {
            String[] parts = rule.split("\\|");
            int x = Integer.parseInt(parts[0]);
            int y = Integer.parseInt(parts[1]);
            rules.computeIfAbsent(x, k -> new HashSet<>()).add(y);
        }
        return rules;
    }

    // Helper method to parse updates
    private static List<List<Integer>> parseUpdates(List<String> updatesInput) {
        List<List<Integer>> updates = new ArrayList<>();
        for (String update : updatesInput) {
            List<Integer> pages = new ArrayList<>();
            for (String page : update.split(",")) {
                pages.add(Integer.parseInt(page));
            }
            updates.add(pages);
        }
        return updates;
    }

    // Helper method to validate an update based on ordering rules
    private static boolean isValidUpdate(List<Integer> update, Map<Integer, Set<Integer>> rules) {
        Map<Integer, Integer> positions = new HashMap<>();
        for (int i = 0; i < update.size(); i++) {
            positions.put(update.get(i), i);
        }

        for (Map.Entry<Integer, Set<Integer>> entry : rules.entrySet()) {
            int x = entry.getKey();
            if (!positions.containsKey(x)) continue;

            for (int y : entry.getValue()) {
                if (positions.containsKey(y) && positions.get(x) > positions.get(y)) {
                    return false;
                }
            }
        }

        return true;
    }

    // Helper method to reorder an update using topological sort
    private static List<Integer> reorderUpdate(List<Integer> update, Map<Integer, Set<Integer>> rules) {
        // Build a graph for the pages in this update
        Map<Integer, List<Integer>> graph = new HashMap<>();
        Map<Integer, Integer> inDegree = new HashMap<>();

        for (int page : update) {
            graph.put(page, new ArrayList<>());
            inDegree.put(page, 0);
        }

        for (int x : update) {
            if (rules.containsKey(x)) {
                for (int y : rules.get(x)) {
                    if (update.contains(y)) {
                        graph.get(x).add(y);
                        inDegree.put(y, inDegree.get(y) + 1);
                    }
                }
            }
        }

        // Perform topological sort
        Queue<Integer> queue = new LinkedList<>();
        for (int page : update) {
            if (inDegree.get(page) == 0) {
                queue.add(page);
            }
        }

        List<Integer> sorted = new ArrayList<>();
        while (!queue.isEmpty()) {
            int current = queue.poll();
            sorted.add(current);

            for (int neighbor : graph.get(current)) {
                inDegree.put(neighbor, inDegree.get(neighbor) - 1);
                if (inDegree.get(neighbor) == 0) {
                    queue.add(neighbor);
                }
            }
        }

        return sorted;
    }
}
