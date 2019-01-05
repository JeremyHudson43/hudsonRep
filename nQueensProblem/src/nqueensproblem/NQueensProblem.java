package nqueensproblem;

public class NQueensProblem {
    
    public static int[][] chessBoard = new int[8][8];

    /*
    method that checks rows and columns by continually calling 
    itself in a loop if (canBePlaced(chessBoard, row, column) returns true
    to see if a queen can be legally placed. Otherwise, it backtracks to the 
    last queen positioning that worked by replacing the queen at the 
    [row][column] square of the chessboard with a 0
     */
    public boolean backTrack(int column) {

        if (column == 8) {

            return true;
        }

        for (int row = 0; row < 8; row++) {

            if (canBePlaced(row, column)) {

                chessBoard[row][column] = 1;

                if (backTrack(column + 1) == true) {
                    return true;
                } 
                else {
                    chessBoard[row][column] = 0;
                }
            }
        }

        return false;
    }

    /*
    checks all diagonals from the given square and all squares in the current 
    row and column to see if a queen already exists within those squares
     */
    public boolean canBePlaced(int row, int column) {

        for (int i = row, j = column; j < 8 && i < 8; i++, j++) {
            if (chessBoard[i][j] == 1) {
                return false;
            }
        }

        for (int i = row, j = column; i >= 0 && j >= 0; i--, j--) {
            if (chessBoard[i][j] == 1) {
                return false;
            }
        }

        for (int i = row, j = column; j >= 0 && i < 8; i++, j--) {
            if (chessBoard[i][j] == 1) {
                return false;
            }
        }

        for (int i = row, j = column; j < 8 && i > 0; i--, j++) {
            if (chessBoard[i][j] == 1) {
                return false;
            }
        }

        for (int i = 0; i < 8; i++) {
            if (chessBoard[row][i] == 1) {
                return false;
            }

            for (int j = 0; j < 8; j++) {
                if (chessBoard[j][column] == 1) {
                    return false;
                }
            }

        }
        return true;
    }

    //simply prints the answer after the other methods have solved the problem
    public void printAnswer(int[][] chessBoard) {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                System.out.print(" " + chessBoard[i][j] + " ");
            }
            System.out.println("");
        }
    }

   

    /*driver method that calls backTrack beginning at the first column to solve
    the problem correctly then calls printAnswer to print the final result
     */

    public static void main(String args[]) {

        NQueensProblem NQueens = new NQueensProblem();
        NQueens.backTrack(0);
        NQueens.printAnswer(chessBoard);
    }
}
