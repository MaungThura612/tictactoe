/*
 * Produced as part of CS3270 coursework at Aston University
 */
package cs3270.pearsalm.tictactoe.model;

import java.util.Objects;

/**
 * Abstract model of a player of the game
 *
 * @author M J Pearsall <pearsalm@aston.ac.uk>
 */
public abstract class Player {

    // this players representation on a game board, i.e: X, O
    private CellState playerRepresentation;
    // whether this player takes first move on the game board
    private boolean goesFirst;

    /**
     * Construct a Player
     *
     * @param playerRepresentation
     * @param goesFirst
     */
    public Player(CellState playerRepresentation, boolean goesFirst) {
        if (playerRepresentation == CellState.E) {
            throw new IllegalArgumentException("Player cannot be represented by the empty state");
        }
        this.playerRepresentation = playerRepresentation;
        this.goesFirst = goesFirst;
    }

    /**
     * Returns the players representation on the game board
     *
     * @return the playerRepresentation
     */
    public CellState getPlayerRepresentation() {
        return playerRepresentation;
    }

    /**
     * Sets the players representation on the game board
     *
     * @param playerRepresentation the playerRepresentation to set
     */
    public void setPlayerRepresentation(CellState playerRepresentation) {
        this.playerRepresentation = playerRepresentation;
    }

    /**
     * Returns the state of the board associated with a win for the player
     *
     * @return
     */
    public BoardState getWinningBoardState() {
        if (playerRepresentation == CellState.X) {
            return BoardState.X_WIN;
        } else {
            return BoardState.O_WIN;
        }
    }

    /**
     * Whether the player is first to move on the game board
     *
     * @return the goesFirst
     */
    public boolean isGoesFirst() {
        return goesFirst;
    }

    /**
     * Set whether the player is first to move on the game board
     *
     * @param goesFirst the goesFirst to set
     */
    public void setGoesFirst(boolean goesFirst) {
        this.goesFirst = goesFirst;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 47 * hash + Objects.hashCode(this.playerRepresentation);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Player other = (Player) obj;
        if (this.playerRepresentation != other.playerRepresentation) {
            return false;
        }
        return true;
    }

}
