/*
 * Produced as part of CS3270 coursework at Aston University
 */
package cs3270.pearsalm.tictactoe.model;

/**
 * Represents a human player of the game
 *
 * @author M J Pearsall <pearsalm@aston.ac.uk>
 */
public class HumanPlayer extends Player {

    private int nextMoveRow;
    private int nextMoveCol;

    /**
     * Construct a new HumanPlayer
     * 
     * @param playerRepresentation
     * @param goesFirst 
     */
    public HumanPlayer(CellState playerRepresentation, boolean goesFirst) {
        super(playerRepresentation, goesFirst);
    }

    /**
     * Returns the row of the players next move
     * 
     * @return row of next move
     */
    public int getNextMoveRow() {
        return nextMoveRow;
    }

    /**
     * Returns the column of the players next move
     * 
     * @return column of next move
     */
    public int getNextMoveCol() {
        return nextMoveCol;
    }

    /**
     * Sets the row and column of the players next move
     * 
     * @param row
     * @param col 
     */
    public void setNextMove(int row, int col) {
        nextMoveRow = row;
        nextMoveCol = col;
    }
}
