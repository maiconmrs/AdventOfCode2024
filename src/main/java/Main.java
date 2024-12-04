import challenges.Day1HistorianHysteria;

import java.io.File;

public class Main {
    public static void main(String[] args) {
        try {
            System.out.println("■■■■■■■■■■■■■[DAY 1]■■■■■■■■■■■■■");
                    
            // Example:
            // File inputFile = new File("src/main/java/inputs/ExampleDay1.txt");
                      
            File inputFile = new File("src/main/java/inputs/Day1.txt");

            //Part 1
            Day1HistorianHysteria.solvePartOne(inputFile);

            //Part 2
            Day1HistorianHysteria.solvePartTwo(inputFile);

        } catch (Exception e) {
            System.err.println("An error occurred: " + e.getMessage());
        }
    }
}
