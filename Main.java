/**
 * AsogaOluwaseyiA1Q2
 * COMP 2140 SECTION D01
 * ASSIGNMENT: Assignment 1, question 2
 * @author Oluwaseyi Asoga, 007913224
 * @version 20th May 2023
 *
 * PURPOSE: Sudoku solver using recursive backtracking
 */
import java.util.Scanner;
public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the Sudoku board (use '-' for empty cells):");
        String input = scanner.nextLine();

        Sudoku solver = new Sudoku(input);
        if (solver.solve()) {
            System.out.println("Solution:");
            solver.printBoard();
        } else {
            System.out.println("No solution exists for the provided board.");
        }
    }
}

class Sudoku {
    private int[][] board; // 2D Array to contain board
    private int boardSize;

    private int boardSizesqrt;

    public Sudoku(String sudokuBoard) {
        String[] rows = sudokuBoard.trim().split("\\s+"); // Split by whitespace
        boardSize = (int) Math.sqrt(rows.length);
        board = new int[boardSize][boardSize];
        boardSizesqrt = (int) Math.sqrt(boardSize);

        // Fill the board
        int index = 0; // Track the index of the split string
        for (int row = 0; row < boardSize; row++) {
            for (int col = 0; col < boardSize; col++) {
                if (!rows[index].equals("-")) {
                    board[row][col] = Integer.parseInt(rows[index]);
                }
                index++; // Move to the next index
            }
        }
    }


    public boolean solve() {
        return solveRecursive(0, 0);
    }

    private boolean solveRecursive(int row, int col) {
        if (row == boardSize) {
            return true;
        }
        if (col == boardSize) {
            return solveRecursive(row + 1, 0); // Move to the next row
        }
        if (board[row][col] != 0) {
            return solveRecursive(row, col + 1); // Cell is already filled, move to the next cell
        }
        for (int num = 1; num <= boardSize; num++) {
            if (isValid(row, col, num)) {
                board[row][col] = num;
                if (solveRecursive(row, col + 1)) {
                    return true;
                }
                board[row][col] = 0; // Backtrack
            }
        }
        return false;
    }



    private boolean isValid(int row, int column, int num){
        // Check if the number already exists in the row or column
        for (int i = 0; i < boardSize; i++) {
            if (board[row][i] == num || board[i][column] == num) {
                return false;
            }
        }

        // Check if the number already exists in the boardSizesqrt x boardSizesqrt block
        int blockStartRow = (row / boardSizesqrt) * boardSizesqrt;
        int blockStartCol = (column / boardSizesqrt) * boardSizesqrt;
        for (int i = 0; i < boardSizesqrt; i++) {
            for (int j = 0; j < boardSizesqrt; j++) {
                int currRow = blockStartRow + i;
                int currCol = blockStartCol + j;
                if (board[currRow][currCol] == num) {
                    return false;
                }
            }
        }

        return true; // Number is valid in this position
    }

    public void printBoard() {
        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                System.out.print(board[i][j] + " ");
            }
            System.out.println();
        }
    }
}

/*  1. I chose to store the grid in a 2D Array of ints because I felt that
           although the 2D array may use more space, I thought accessing the cells in the grid would be easier
           and the result would look better if printed in the 2D array format. I chose to represent the board in
           an int array rather than char because I thought the calculations would be easier to do with ints
           instead of chars.


        2. When beginning to develop this program I considered using a 1D array, but I considered that approach would
           make calculations harder and the cells harder to access thus making the program a bit slower and less efficient.
           I believe the approach with a 2D int array is the most efficient solution.
    * */