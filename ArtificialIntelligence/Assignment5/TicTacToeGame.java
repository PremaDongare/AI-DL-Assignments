import java.util.Scanner;

public class TicTacToeGame {
    static char player = 'X';     // AI
    static char opponent = 'O';   // Human

    // Function to check if there are moves left
    static boolean isMovesLeft(char board[][]) {
        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 3; j++)
                if (board[i][j] == '_')
                    return true;
        return false;
    }

    // Evaluate board
    static int evaluate(char b[][]) {
        // Rows
        for (int row = 0; row < 3; row++) {
            if (b[row][0] == b[row][1] && b[row][1] == b[row][2]) {
                if (b[row][0] == player)
                    return +10;
                else if (b[row][0] == opponent)
                    return -10;
            }
        }

        // Columns
        for (int col = 0; col < 3; col++) {
            if (b[0][col] == b[1][col] && b[1][col] == b[2][col]) {
                if (b[0][col] == player)
                    return +10;
                else if (b[0][col] == opponent)
                    return -10;
            }
        }

        // Diagonals
        if (b[0][0] == b[1][1] && b[1][1] == b[2][2]) {
            if (b[0][0] == player)
                return +10;
            else if (b[0][0] == opponent)
                return -10;
        }

        if (b[0][2] == b[1][1] && b[1][1] == b[2][0]) {
            if (b[0][2] == player)
                return +10;
            else if (b[0][2] == opponent)
                return -10;
        }

        return 0;
    }

    // Minimax algorithm
    static int minimax(char board[][], int depth, boolean isMax) {
        int score = evaluate(board);

        if (score == 10) return score - depth;
        if (score == -10) return score + depth;
        if (!isMovesLeft(board)) return 0;

        if (isMax) {
            int best = Integer.MIN_VALUE;
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    if (board[i][j] == '_') {
                        board[i][j] = player;
                        best = Math.max(best, minimax(board, depth + 1, !isMax));
                        board[i][j] = '_';
                    }
                }
            }
            return best;
        } else {
            int best = Integer.MAX_VALUE;
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    if (board[i][j] == '_') {
                        board[i][j] = opponent;
                        best = Math.min(best, minimax(board, depth + 1, !isMax));
                        board[i][j] = '_';
                    }
                }
            }
            return best;
        }
    }

    // Find best move for AI
    static int[] findBestMove(char board[][]) {
        int bestVal = Integer.MIN_VALUE;
        int[] bestMove = {-1, -1};

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j] == '_') {
                    board[i][j] = player;
                    int moveVal = minimax(board, 0, false);
                    board[i][j] = '_';

                    if (moveVal > bestVal) {
                        bestMove[0] = i;
                        bestMove[1] = j;
                        bestVal = moveVal;
                    }
                }
            }
        }
        return bestMove;
    }

    // Print the board
    static void printBoard(char board[][]) {
        System.out.println();
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                System.out.print(board[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println();
    }

    // Check if someone won
    static char checkWinner(char board[][]) {
        int score = evaluate(board);
        if (score == 10) return player;
        if (score == -10) return opponent;
        if (!isMovesLeft(board)) return 'D'; // Draw
        return '_'; // Game still going
    }

    // Main game loop
    public static void main(String[] args) {
        char board[][] = {
            { '_', '_', '_' },
            { '_', '_', '_' },
            { '_', '_', '_' }
        };

        Scanner sc = new Scanner(System.in);
        System.out.println("Tic Tac Toe! You are O, AI is X.");
        printBoard(board);

        while (true) {
            // Human move
            System.out.print("Enter your move (row and col: 0-2): ");
            int r = sc.nextInt();
            int c = sc.nextInt();

            if (board[r][c] != '_') {
                System.out.println("Invalid move! Try again.");
                continue;
            }
            board[r][c] = opponent;
            printBoard(board);

            if (checkWinner(board) == opponent) {
                System.out.println("You win!");
                break;
            }
            if (checkWinner(board) == 'D') {
                System.out.println("It's a draw!");
                break;
            }

            // AI move
            int[] bestMove = findBestMove(board);
            board[bestMove[0]][bestMove[1]] = player;
            System.out.println("AI played at: " + bestMove[0] + ", " + bestMove[1]);
            printBoard(board);

            if (checkWinner(board) == player) {
                System.out.println("AI wins!");
                break;
            }
            if (checkWinner(board) == 'D') {
                System.out.println("It's a draw!");
                break;
            }
        }
        sc.close();
    }
}
// Javac TicTacToeGame.java
// Java TicTacToeGame