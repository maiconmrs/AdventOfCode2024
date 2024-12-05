package challenges;

import java.io.*;
import java.util.*;

public class Day4CeresSearch {

    // Helper method to parse the input file into a grid of characters
    private static char[][] parseInput(File inputFile) throws FileNotFoundException {
        List<char[]> grid = new ArrayList<>();
        Scanner scanner = new Scanner(inputFile);

        try {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine().trim();
                grid.add(line.toCharArray());
            }
        } finally {
            scanner.close();
        }

        return grid.toArray(new char[0][]);
    }

    // Part 1: Count occurrences of "XMAS" in all directions
    public static void solvePartOne(File inputFile) {
        try {
            // Parse the input grid
            char[][] grid = parseInput(inputFile);

            // Count occurrences of "XMAS"
            String targetWord = "XMAS";
            int count = countOccurrences(grid, targetWord);

            System.out.println("(Part 1) Total occurrences of \"" + targetWord + "\": " + count);
        } catch (FileNotFoundException e) {
            System.err.println("Input file not found: " + inputFile.getAbsolutePath());
        } catch (Exception e) {
            System.err.println("An error occurred: " + e.getMessage());
        }
    }

    // Helper method to count occurrences of the target word in the grid
    private static int countOccurrences(char[][] grid, String targetWord) {
        int count = 0;
        int rows = grid.length;
        int cols = grid[0].length;
        int len = targetWord.length();

        // Direction vectors for 8 directions
        int[][] directions = {
            {0, 1},   // Right
            {0, -1},  // Left
            {1, 0},   // Down
            {-1, 0},  // Up
            {1, 1},   // Down-Right
            {1, -1},  // Down-Left
            {-1, 1},  // Up-Right
            {-1, -1}  // Up-Left
        };

        // Check all positions in the grid
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                // Check each direction from this position
                for (int[] dir : directions) {
                    int dx = dir[0], dy = dir[1];
                    if (canFindWord(grid, targetWord, row, col, dx, dy)) {
                        count++;
                    }
                }
            }
        }

        return count;
    }

    // Helper method to check if the target word exists starting from (row, col) in direction (dx, dy)
    private static boolean canFindWord(char[][] grid, String targetWord, int row, int col, int dx, int dy) {
        int len = targetWord.length();
        int rows = grid.length;
        int cols = grid[0].length;

        for (int i = 0; i < len; i++) {
            int newRow = row + i * dx;
            int newCol = col + i * dy;

            // Check bounds
            if (newRow < 0 || newRow >= rows || newCol < 0 || newCol >= cols) {
                return false;
            }

            // Check character match
            if (grid[newRow][newCol] != targetWord.charAt(i)) {
                return false;
            }
        }

        return true;
    }

    // Part 2: Count occurrences of "X-MAS" patterns
    public static void solvePartTwo(File inputFile) {
        try {
            // Parse the input grid
            char[][] grid = parseInput(inputFile);

            // Count X-MAS patterns
            int count = countXMasPatterns(grid);

            System.out.println("(Part 2) Total occurrences of X-MAS patterns: " + count);
        } catch (FileNotFoundException e) {
            System.err.println("Input file not found: " + inputFile.getAbsolutePath());
        } catch (Exception e) {
            System.err.println("An error occurred: " + e.getMessage());
        }
    }

    // Helper method to count occurrences of X-MAS patterns
    private static int countXMasPatterns(char[][] grid) {
        int count = 0;
        int rows = grid.length;
        int cols = grid[0].length;

        // Iterate through all possible "center" positions for the pattern
        for (int row = 1; row < rows - 1; row++) { // Avoid boundary rows
            for (int col = 1; col < cols - 1; col++) { // Avoid boundary columns
                // Check if the center is 'A' and if the X-MAS pattern exists
                if (grid[row][col] == 'A' && isXMasPattern(grid, row, col)) {
                    count++;
                }
            }
        }

        return count;
    }

    // Helper method to check if X-MAS pattern exists centered at (row, col)
    private static boolean isXMasPattern(char[][] grid, int row, int col) {
        // Check the top-left to bottom-right diagonal
        boolean diagonal1 = matchesMAS(grid[row - 1][col - 1], grid[row][col], grid[row + 1][col + 1]);

        // Check the top-right to bottom-left diagonal
        boolean diagonal2 = matchesMAS(grid[row - 1][col + 1], grid[row][col], grid[row + 1][col - 1]);

        return diagonal1 && diagonal2;
    }

    // Helper method to check if three characters form MAS or SAM
    private static boolean matchesMAS(char left, char middle, char right) {
        return (left == 'M' && middle == 'A' && right == 'S') || (left == 'S' && middle == 'A' && right == 'M');
    }



}
