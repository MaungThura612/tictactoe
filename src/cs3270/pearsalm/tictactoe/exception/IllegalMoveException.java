/*
 * Produced as part of CS3270 coursework at Aston University
 */
package cs3270.pearsalm.tictactoe.exception;

/**
 * Represents an illegal move on a board
 *
 * @author M J Pearsall <pearsalm@aston.ac.uk>
 */
public class IllegalMoveException extends Throwable {
    
    public IllegalMoveException(String message){
        super(message);
    }

}
