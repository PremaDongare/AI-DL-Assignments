public class EightQueens {
    static final int N = 8;

    // Print the board
    static void printBoard(int board[][]) {
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                System.out.print((board[i][j] == 1 ? "Q " : ". "));
            }
            System.out.println();
        }
        System.out.println();
    }

    // Check if a queen can be placed at board[row][col]
    static boolean isSafe(int board[][], int row, int col) {
        // Check left side of row
        for (int i = 0; i < col; i++)
            if (board[row][i] == 1)
                return false;

        // Check upper diagonal (left)
        for (int i = row, j = col; i >= 0 && j >= 0; i--, j--)
            if (board[i][j] == 1)
                return false;

        // Check lower diagonal (left)
        for (int i = row, j = col; j >= 0 && i < N; i++, j--)
            if (board[i][j] == 1)
                return false;

        return true;
    }

    // Recursive function to solve N-Queens
    static boolean solveNQUtil(int board[][], int col) {
        if (col >= N) {
            printBoard(board); // Print one solution
            return true;       // To print only one solution, return true here
        }

        boolean res = false;
        for (int i = 0; i < N; i++) {
            if (isSafe(board, i, col)) {
                board[i][col] = 1;  // Place queen
                res = solveNQUtil(board, col + 1) || res; // Recurse
                board[i][col] = 0;  // Backtrack
            }
        }
        return res;
    }

    // Main solver
    static void solveNQ() {
        int board[][] = new int[N][N];

        if (!solveNQUtil(board, 0)) {
            System.out.println("No solution exists");
        }
    }

    public static void main(String[] args) {
        System.out.println("8-Queens Problem Solutions:");
        solveNQ();
    }
}
//javac EightQueens.java
//java EightQueens