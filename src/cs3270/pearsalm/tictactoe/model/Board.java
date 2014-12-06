/*
 * Produced as part of CS3270 coursework at Aston University
 */
package cs3270.pearsalm.tictactoe.model;

import cs3270.pearsalm.tictactoe.exception.IllegalMoveException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Stack;

/**
 * Models a tic-tac-toe board
 *
 * @author M J Pearsall <pearsalm@aston.ac.uk>
 */
public final class Board {

    // rows on the board
    public static final int ROWS = 3;
    // columns on the board
    public static final int COLS = 3;

    // multi-dimensional array representing rows and column cells on the board
    private final CellState[][] board;

    // current state of this board
    private BoardState boardState;

    // moves performed on this board
    private int moveCount;

    // stack of all moves
    private final Stack<Move> moveStack;

    /**
     * Construct a new board
     */
    public Board() {
        board = new CellState[ROWS][COLS];
        boardState = BoardState.IN_PROGRESS;
        moveStack = new Stack<>();
        clearBoard();
    }

    /**
     * Return the state of a board cell
     * 
     * @param row
     * @param col
     * @return 
     */
    public CellState getCellState(int row, int col) {
        return board[row][col];
    }

    /**
     * Returns the current state of this board
     *
     * @return state of current board
     */
    public BoardState getBoardState() {
        return boardState;
    }

    /**
     * Perform a move on the board which changes a cell state
     *
     * @param row row on the board between 0 and (ROWS - 1)
     * @param col column on the board between 0 and (COLS - 1)
     * @param cellState the state the cell will be changed to
     * @return state of the board after the move is performed
     * @throws IllegalMoveException invalid move
     */
    public BoardState move(int row, int col, CellState cellState) throws IllegalMoveException {
        if (boardState != BoardState.IN_PROGRESS) {
            throw new IllegalMoveException("Current game over");
        }

        if (cellState == CellState.E) {
            throw new IllegalMoveException("Cannot set cell to empty");
        }

        if (row > (ROWS - 1) || (col > (COLS - 1) || row < 0 || col < 0)) {
            throw new IllegalMoveException("Cell does not exist");
        }

        if (board[row][col] != CellState.E) {
            throw new IllegalMoveException("Cell already populated");
        } else {
            // update board cell
            board[row][col] = cellState;
            // update move count
            moveCount++;
            
            // record move and add to stack
            Move move = new Move(new int[]{row, col}, boardState);
            moveStack.push(move);

        }
        
        // game over due to winning move
        boolean gameOver = false;

        // check rows
        for (int i = 0; i < ROWS; i++) {
            if (board[row][i] != cellState) {
                break;
            }
            if (i == ROWS - 1) {
                // win
                gameOver = true;
            }
        }

        // check columns
        for (int i = 0; i < COLS; i++) {
            if (board[i][col] != cellState) {
                break;
            }
            if (i == COLS - 1) {
                // win
                gameOver = true;
            }
        }

        // diagnal attempted
        if (row == col) {
            // check positive diag
            for (int i = 0; i < COLS; i++) {
                if (board[i][i] != cellState) {
                    break;
                }
                if (i == COLS - 1) {
                    // win
                    gameOver = true;
                }
            }
        }

        // check negative diag
        for (int i = 0; i < COLS; i++) {
            if (board[i][(COLS - 1) - i] != cellState) {
                break;
            }
            if (i == COLS - 1) {
                // win
                gameOver = true;
            }
        }

        // check if game is over
        if (gameOver) {
            // check winning state
            if (cellState == CellState.X) {
                boardState = BoardState.X_WIN;
            } else {
                boardState = BoardState.O_WIN;
            }
        } else if (moveCount == (ROWS * COLS)) {
            boardState = BoardState.DRAW;
        }

        return boardState;
    }

    /**
     * Reverts the boards state to before the previous move
     */
    public void undoLastMove() {
        // remove from stack
        Move move = moveStack.pop();
        int[] lastMove = move.getMove();
        BoardState prevBoardState = move.getPreviousState();
        // set cell to empty
        board[lastMove[0]][lastMove[1]] = CellState.E;
        // revert back to previous state
        boardState = prevBoardState;
        // decrease move count
        moveCount--;
    }

    /**
     * Resets the state of all cells on the board to empty
     */
    public void clearBoard() {
        for (CellState[] cellState : board) {
            Arrays.fill(cellState, CellState.E);
        }
        boardState = BoardState.IN_PROGRESS;
    }

    /**
     * Loops through board data and checks all cells are in empty state
     *
     * @return
     */
    public boolean isBoardClear() {
        boolean clear = true;

        for (CellState[] boardCell : board) {
            for (CellState state : boardCell) {
                if (state != CellState.E) {
                    clear = false;
                }
            }
        }

        return clear;
    }

    /**
     * Returns a list of all empty cells on the board
     * 
     * @return all empty cells on the board
     */
    public List<int[]> getAvailableMoves() {
        List<int[]> moves = new ArrayList<>();
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLS; j++) {
                if (board[i][j] == CellState.E) {
                    moves.add(new int[]{i, j});
                }
            }
        }
        return moves;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (CellState[] cellStates : board) {
            sb.append("[");
            for (CellState state : cellStates) {
                sb.append(state.toString());
                sb.append(", ");
            }
            sb.replace(sb.length() - 2, sb.length(), "");
            sb.append("]");
            sb.append("\n");
        }
        sb.replace(sb.length() - 1, sb.length(), "");

        return sb.toString();
    }

}
