/*
 * Produced as part of CS3270 coursework at Aston University
 */
package cs3270.pearsalm.tictactoe.controller;

import cs3270.pearsalm.tictactoe.exception.IllegalMoveException;
import cs3270.pearsalm.tictactoe.model.BoardState;
import cs3270.pearsalm.tictactoe.model.CPUPlayer;
import cs3270.pearsalm.tictactoe.model.CellState;
import cs3270.pearsalm.tictactoe.model.HumanPlayer;
import java.util.List;
import java.util.Random;
import org.junit.After;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

/**
 * Test the {@link Game} class
 *
 * @author M J Pearsall <pearsalm@aston.ac.uk>
 */
public class GameTest {

    @Rule
    public ExpectedException exception = ExpectedException.none();

    private Game game;

    public GameTest() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    @Test
    public void testPlayRandomOpp() throws IllegalMoveException {
        int wins = 0, draws = 0;
        for (int i = 0; i < 1000; i++) {
            CPUPlayer player1 = new CPUPlayer(CellState.X, true);
            HumanPlayer player2 = new HumanPlayer(CellState.O, false);
            game = new Game(player1, player2);

            Random random = new Random();
            while (game.getBoard().getBoardState() == BoardState.IN_PROGRESS) {
                game.playerMove();
                if (game.getBoard().getBoardState() == BoardState.IN_PROGRESS) {
                    List<int[]> availableMoves = game.getBoard().getAvailableMoves();
                    int index = random.nextInt(availableMoves.size());
                    player2.setNextMove(availableMoves.get(index)[0], availableMoves.get(index)[1]);
                    game.playerMove();
                }
            }

            BoardState state = game.getBoard().getBoardState();

            if (state == BoardState.X_WIN) {
                wins++;
            }

            if (state == BoardState.DRAW) {
                draws++;
            }

            boolean success = state == BoardState.X_WIN || state == BoardState.DRAW;
            assertTrue(success);
        }

        System.out.println("Wins: " + wins + " Draws: " + draws);
    }

    @Test
    public void testCPUVsCPU() throws IllegalMoveException {
        CPUPlayer player1 = new CPUPlayer(CellState.X, true);
        CPUPlayer player2 = new CPUPlayer(CellState.O, false);
        game = new Game(player1, player2);

        while (game.getBoard().getBoardState() == BoardState.IN_PROGRESS) {
            game.playerMove();
        }

        BoardState state = game.getBoard().getBoardState();

        boolean success = state == BoardState.DRAW;

        assertTrue(success);
    }

    @Test
    public void testNoGames() throws IllegalMoveException {
        CPUPlayer player1 = new CPUPlayer(CellState.X, true);
        CPUPlayer player2 = new CPUPlayer(CellState.O, false);
        game = new Game(player1, player2);

        game.playerMove();

        assertEquals(255168, game.getPossibleGames());
        System.out.println("Permutations: " + game.getPossibleGames());
    }

    @Test
    public void testHumanPlayerMove() throws IllegalMoveException {
        CPUPlayer player1 = new CPUPlayer(CellState.X, false);
        HumanPlayer player2 = new HumanPlayer(CellState.O, true);

        game = new Game(player1, player2);

        int row = 0, col = 0;
        player2.setNextMove(row, col);

        int[] move = game.playerMove();

        assertEquals(row, move[0]);
        assertEquals(col, move[1]);
    }

    @Test
    public void testMoveAfterGameOver() throws IllegalMoveException {
        testPlayRandomOpp();

        assertNull(game.playerMove());
    }

    @Test
    public void testBothPlayersGoFirst() {
        exception.expect(IllegalArgumentException.class);

        CPUPlayer player1 = new CPUPlayer(CellState.X, true);
        CPUPlayer player2 = new CPUPlayer(CellState.O, true);

        game = new Game(player1, player2);
    }

    @Test
    public void testNoPlayersGoFirst() {
        exception.expect(IllegalArgumentException.class);

        CPUPlayer player1 = new CPUPlayer(CellState.X, false);
        CPUPlayer player2 = new CPUPlayer(CellState.O, false);

        game = new Game(player1, player2);
    }

    @Test
    public void testBothPlayersSameRep() {
        exception.expect(IllegalArgumentException.class);

        CPUPlayer player1 = new CPUPlayer(CellState.X, true);
        CPUPlayer player2 = new CPUPlayer(CellState.X, false);

        game = new Game(player1, player2);
    }

}
