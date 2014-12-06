/*
 * Produced as part of CS3270 coursework at Aston University
 */

package cs3270.pearsalm.tictactoe.model;

/**
 * Represents a past move performed on a board
 *
 * @author M J Pearsall <pearsalm@aston.ac.uk>
 */
public class Move {
    // move
    private int[] move;
    // state of board before move
    private BoardState previousState;
    
    public Move(int[] move, BoardState previousState){
        this.move = move;
        this.previousState = previousState;
    }

    /**
     * @return the move
     */
    public int[] getMove() {
        return move;
    }

    /**
     * @param move the move to set
     */
    public void setMove(int[] move) {
        this.move = move;
    }

    /**
     * @return the previousState
     */
    public BoardState getPreviousState() {
        return previousState;
    }

    /**
     * @param previousState the previousState to set
     */
    public void setPreviousState(BoardState previousState) {
        this.previousState = previousState;
    }
    
    
}
