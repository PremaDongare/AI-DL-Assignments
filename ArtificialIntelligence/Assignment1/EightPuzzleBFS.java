import java.util.*;

// Main class
public class EightPuzzleBFS {

    // Method to perform BFS search
    static void solvePuzzle(String start) {
       
        Queue<String> queue = new LinkedList<>();

        // Set to keep track of visited puzzle 
        Set<String> visited = new HashSet<>();

        // Map  (for backtracking)
        Map<String, String> parentMap = new HashMap<>();

        // Add the starting state to queue and visited set
        queue.add(start);
        visited.add(start);
        parentMap.put(start, null);

        // Goal state 
        String goal = "123456780";

        while (!queue.isEmpty()) {
            String current = queue.poll(); // Remove front element from queue

            if (current.equals(goal)) {
                // Goal reached
                printPath(current, parentMap);
                return;
            }

            int zeroIndex = current.indexOf('0'); // Position of empty space

            for (int move : getValidMoves(zeroIndex)) {
                String next = swap(current, zeroIndex, move); // Get next puzzle state

                if (!visited.contains(next)) {
                    queue.add(next);           // Add new state to queue
                    visited.add(next);         // Mark it as visited
                    parentMap.put(next, current); // Store parent
                }
            }
        }

        System.out.println("No solution found.");
    }

    // Method to get valid moves based on empty space (0) position
    static List<Integer> getValidMoves(int index) {
        List<Integer> validMoves = new ArrayList<>();
        int row = index / 3; 
        int col = index % 3; 

        if (row > 0) validMoves.add(index - 3); // Move Up
        if (row < 2) validMoves.add(index + 3); // Move Down
        if (col > 0) validMoves.add(index - 1); // Move Left
        if (col < 2) validMoves.add(index + 1); // Move Right

        return validMoves;
    }

    // Method to swap characters 
    static String swap(String str, int i, int j) {
        char[] arr = str.toCharArray();
        char temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
        return new String(arr);
    }

    // Method to print the path from start to goal
    static void printPath(String state, Map<String, String> parentMap) {
        List<String> path = new ArrayList<>();
        while (state != null) {
            path.add(state); // Add current state to path
            state = parentMap.get(state); // Move to parent
        }

        Collections.reverse(path); // Reverse to start from initial state

        System.out.println("Solution found in " + (path.size() - 1) + " steps:");
        for (String s : path) {
            printGrid(s);
            System.out.println();
        }
    }

    // Method to print 3x3 grid from state string
    static void printGrid(String state) {
        for (int i = 0; i < 9; i++) {
            System.out.print(state.charAt(i) + " ");
            if ((i + 1) % 3 == 0) System.out.println(); // New line after each row
        }
    }

    // Main method to take input and trigger the puzzle solver
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the 8 puzzle (e.g., 123456780):");
        String start = scanner.nextLine().trim();

        // Validate input: should contain digits 0-8 exactly once
        if (start.length() != 9 || !start.matches("[0-8]+")) {
            System.out.println("Invalid input. Enter exactly 9 digits from 0 to 8.");
            return;
        }

        Set<Character> uniqueDigits = new HashSet<>();
        for (char c : start.toCharArray()) {
            uniqueDigits.add(c);
        }

        if (uniqueDigits.size() != 9) {
            System.out.println("Invalid input. Digits must be unique.");
            return;
        }

        
        solvePuzzle(start);
    }
}
