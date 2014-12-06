/*
 * Produced as part of CS3270 coursework at Aston University
 */
package cs3270.pearsalm.tictactoe.controller;

import cs3270.pearsalm.tictactoe.exception.IllegalMoveException;
import cs3270.pearsalm.tictactoe.model.Board;
import cs3270.pearsalm.tictactoe.model.BoardState;
import cs3270.pearsalm.tictactoe.model.CPUPlayer;
import cs3270.pearsalm.tictactoe.model.HumanPlayer;
import cs3270.pearsalm.tictactoe.model.Player;
import java.util.List;

/**
 * Performs set up of game, inputs and output
 *
 * @author M J Pearsall <pearsalm@aston.ac.uk>
 */
public class Game {

    // players
    private Player player1;
    private Player player2;

    // the game board
    private final Board board;

    // permutations
    private long possibleGames;

    // current best row and col for CPU move
    private int bestRow;
    private int bestCol;

    // player whose move is next
    private Player playerInMove;

    /**
     * Construct a new Game
     *
     * @param player1
     * @param player2
     */
    public Game(Player player1, Player player2) {
        if (player1.getPlayerRepresentation() == player2.getPlayerRepresentation()) {
            throw new IllegalArgumentException("Players must use different representations");
        }

        board = new Board();
        this.player1 = player1;
        this.player2 = player2;

        setUpPlayers();
    }

    /**
     * Construct a new game with an existing board
     *
     * @param player1
     * @param player2
     * @param board
     */
    public Game(Player player1, Player player2, Board board) {
        if (player1.getPlayerRepresentation() == player2.getPlayerRepresentation()) {
            throw new IllegalArgumentException("Players must use different representations");
        }
        this.board = board;
        this.player1 = player1;
        this.player2 = player2;

        setUpPlayers();
    }

    /**
     * Set up players in the context of the game
     */
    private void setUpPlayers() {
        if (player1.isGoesFirst() && player2.isGoesFirst()) {
            throw new IllegalArgumentException("Only one player can go first");
        } else if (player1.isGoesFirst()) {
            playerInMove = player1;
        } else if (player2.isGoesFirst()) {
            playerInMove = player2;
        } else {
            throw new IllegalArgumentException("One player must go first");
        }
    }

    /**
     * Return the permutation counter
     *
     * @return
     */
    public long getPossibleGames() {
        return possibleGames;
    }

    /**
     * Return player 1
     *
     * @return
     */
    public Player getPlayer1() {
        return player1;
    }

    /**
     * Return player 2
     *
     * @return
     */
    public Player getPlayer2() {
        return player2;
    }

    /**
     * Return the board this game holds
     *
     * @return
     */
    public Board getBoard() {
        return board;
    }

    /**
     * Performs a move on the board for the player currently in move
     *
     * @return
     * @throws IllegalMoveException
     */
    public int[] playerMove() throws IllegalMoveException {
        int[] move;

        // if the games not over
        if (board.getBoardState() == BoardState.IN_PROGRESS) {
            if (playerInMove instanceof CPUPlayer) {
                // perform CPU move
                minimax(board, playerInMove, 0);
                board.move(bestRow, bestCol, playerInMove.getPlayerRepresentation());
                move = new int[]{bestRow, bestCol};
            } else if (playerInMove instanceof HumanPlayer) {
                // perform human move
                HumanPlayer player = (HumanPlayer) playerInMove;
                board.move(player.getNextMoveRow(), player.getNextMoveCol(), player.getPlayerRepresentation());
                move = new int[]{player.getNextMoveRow(), player.getNextMoveCol()};
            } else {
                move = null;
            }

//          System.out.println(board.toString() + "\n");
            
            // change player in move
            if (playerInMove.equals(player1)) {
                playerInMove = player2;
            } else {
                playerInMove = player1;
            }
        } else {
            move = null;
        }

        return move;
    }

    /**
     * Minimax algorithm used to calculate the best move on the board for the
     * CPU player
     *
     * @param board
     * @param player
     * @param depth
     * @return
     * @throws IllegalMoveException
     */
    private int minimax(Board board, Player player, int depth) throws IllegalMoveException {
        // determine if player is the active player
        boolean turn = player.equals(playerInMove);

        // determine the other player
        Player otherPlayer;
        if (player.equals(player1)) {
            otherPlayer = player2;
        } else {
            otherPlayer = player1;
        }

        BoardState boardState = board.getBoardState();

        // check if game over 
        if (boardState != BoardState.IN_PROGRESS) {
            possibleGames++;
            if (boardState == playerInMove.getWinningBoardState()) {
                return Integer.MAX_VALUE;
            } else if (board.getBoardState() == BoardState.DRAW) {
                return 0;
            } else {
                return Integer.MIN_VALUE;
            }
        }

        // board value at this level
        int alpha;
        // board value in sub level
        int subalpha;

        // if in turn try to max, else try to min
        if (turn) {
            alpha = Integer.MIN_VALUE;
        } else {
            alpha = Integer.MAX_VALUE;
        }

        // get available moves
        List<int[]> nextMoves = board.getAvailableMoves();

        // loop through available moves
        for (int[] move : nextMoves) {
            // perform the move
            board.move(move[0], move[1], player.getPlayerRepresentation());

            // calculate value of sub levels
            subalpha = minimax(board, otherPlayer, depth + 1);

            // if at the top level and value of sub levels is greater or equal
            // record the move
            if (depth == 0 && alpha <= subalpha) {
                bestRow = move[0];
                bestCol = move[1];
            }

            // set alpha
            if (turn) {
                alpha = Math.max(alpha, subalpha);
            } else {
                alpha = Math.min(alpha, subalpha);
            }

            // undo this move on the board
            board.undoLastMove();

        }

        return alpha;
    }

}
