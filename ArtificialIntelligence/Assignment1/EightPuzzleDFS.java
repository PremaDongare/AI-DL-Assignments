import java.util.*;

public class EightPuzzleDFS {

    static class Node {
        List<Integer> state; // Current state of puzzle
        Node parent;         // Parent node
        int blankIndex;      // Index of blank (0)

        Node(List<Integer> state, Node parent, int blankIndex) {
            this.state = state;
            this.parent = parent;
            this.blankIndex = blankIndex;
        }
    }

    // Main method to test the DFS algorithm
    public static void main(String[] args) {
        // Initial state
        List<Integer> initialState = Arrays.asList(
                1, 2, 3,
                4, 0, 6,
                7, 5, 8
        );

        // Goal state
        List<Integer> goalState = Arrays.asList(
                1, 2, 3,
                4, 5, 6,
                7, 8, 0
        );

        // Solve using DFS
        solveUsingDFS(initialState, goalState);
    }

    // Depth-First Search method
    static void solveUsingDFS(List<Integer> initialState, List<Integer> goalState) {
        Stack<Node> stack = new Stack<>();
        Set<List<Integer>> visited = new HashSet<>();

        int blankIndex = initialState.indexOf(0); // Position of blank
        stack.push(new Node(initialState, null, blankIndex));

        while (!stack.isEmpty()) {
            Node current = stack.pop();

            if (visited.contains(current.state)) continue;

            visited.add(current.state);

            if (current.state.equals(goalState)) {
                printSolutionPath(current);
                return;
            }

            for (int move : getValidMoves(current.blankIndex)) {
                List<Integer> newState = new ArrayList<>(current.state);
                Collections.swap(newState, current.blankIndex, move);
                Node child = new Node(newState, current, move);
                stack.push(child);
            }
        }

        System.out.println("No solution found.");
    }

    // Print the path from start to goal
    static void printSolutionPath(Node goalNode) {
        List<Node> path = new ArrayList<>();

        while (goalNode != null) {
            path.add(goalNode);
            goalNode = goalNode.parent;
        }

        Collections.reverse(path);
        System.out.println("Path to solution:");

        for (Node node : path) {
            printState(node.state);
        }
    }

    // Print the 3x3 puzzle state
    static void printState(List<Integer> state) {
        for (int i = 0; i < 9; i++) {
            System.out.print(state.get(i) + " ");
            if ((i + 1) % 3 == 0) System.out.println();
        }
        System.out.println();
    }

    // Return valid moves (indices where blank can move)
    static List<Integer> getValidMoves(int index) {
        List<Integer> validMoves = new ArrayList<>();
        int row = index / 3;
        int col = index % 3;

        if (row > 0) validMoves.add(index - 3); // Up
        if (row < 2) validMoves.add(index + 3); // Down
        if (col > 0) validMoves.add(index - 1); // Left
        if (col < 2) validMoves.add(index + 1); // Right

        return validMoves;
    }
}
