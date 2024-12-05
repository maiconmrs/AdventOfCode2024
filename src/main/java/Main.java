import java.io.File;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;


public class Main {
    // Map of solved days to descriptive class names
    private static final Map<Integer, Class<?>> challengeClassMap = new HashMap<Integer, Class<?>>() {
        {
            put(1, challenges.Day1HistorianHysteria.class);
            put(2, challenges.Day2RedNosedReports.class);
            
            // Add more entries as needed
        }
    };

    public static void main(String[] args) {
        for (Map.Entry<Integer, Class<?>> entry : challengeClassMap.entrySet()) {
            int day = entry.getKey();
            Class<?> challengeClass = entry.getValue();

            try {
                System.out.println("■■■■■■■■■■■■■[DAY " + day + "]■■■■■■■■■■■■■");

                // Load input file dynamically
                File inputFile = loadInputFile(day);
                if (inputFile == null) {
                    System.out.println("Input file for Day " + day + " not found. Skipping...");
                    continue;
                }

                // Solve Part 1
                Method solvePartOne = challengeClass.getMethod("solvePartOne", File.class);  //check abstraction to create interface
                solvePartOne.invoke(null, inputFile);

                // Solve Part 2
                Method solvePartTwo = challengeClass.getMethod("solvePartTwo", File.class);
                solvePartTwo.invoke(null, inputFile);

            } catch (Exception e) {
                System.err.println("An error occurred for Day " + day + ": " + e.getMessage());
            }
        }
    }

    // Helper method to load input files dynamically
    private static File loadInputFile(int day) {
        String inputFilePath = "src/main/java/inputs/Day" + day + ".txt";
        File inputFile = new File(inputFilePath);
        return inputFile.exists() ? inputFile : null;
    }
}
