/*
 * Produced as part of CS3270 coursework at Aston University
 */
package cs3270.pearsalm.tictactoe.model;

import cs3270.pearsalm.tictactoe.exception.IllegalMoveException;
import org.junit.After;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

/**
 * Tests the {@link Board} class
 *
 * @author M J Pearsall <pearsalm@aston.ac.uk>
 */
public class BoardTest {

    private final Board board;

    @Rule
    public ExpectedException exception = ExpectedException.none();

    public BoardTest() {
        board = new Board();
    }

    @Before
    public void setUp() {
        board.clearBoard();
    }

    @After
    public void tearDown() {
    }

    @Test
    public void testRowWin() throws IllegalMoveException {
        assertEquals(BoardState.IN_PROGRESS, board.getBoardState());

        board.move(0, 0, CellState.X);
        board.move(0, 1, CellState.X);
        board.move(0, 2, CellState.X);

        System.out.println(board.toString());
        assertEquals(BoardState.X_WIN, board.getBoardState());

        board.clearBoard();

        board.move(0, 0, CellState.O);
        board.move(0, 1, CellState.O);
        board.move(0, 2, CellState.O);

        System.out.println(board.toString());
        assertEquals(BoardState.O_WIN, board.getBoardState());
    }

    @Test
    public void testColWin() throws IllegalMoveException {
        assertEquals(BoardState.IN_PROGRESS, board.getBoardState());

        board.move(0, 0, CellState.X);
        board.move(1, 0, CellState.X);
        board.move(2, 0, CellState.X);

        System.out.println(board.toString());
        assertEquals(BoardState.X_WIN, board.getBoardState());

        board.clearBoard();

        board.move(0, 0, CellState.O);
        board.move(1, 0, CellState.O);
        board.move(2, 0, CellState.O);

        System.out.println(board.toString());
        assertEquals(BoardState.O_WIN, board.getBoardState());
    }

    @Test
    public void testPosDiagWin() throws IllegalMoveException {
        assertEquals(BoardState.IN_PROGRESS, board.getBoardState());

        board.move(0, 0, CellState.X);
        board.move(1, 1, CellState.X);
        board.move(2, 2, CellState.X);

        System.out.println(board.toString());
        assertEquals(BoardState.X_WIN, board.getBoardState());

        board.clearBoard();

        board.move(0, 0, CellState.O);
        board.move(1, 1, CellState.O);
        board.move(2, 2, CellState.O);

        System.out.println(board.toString());
        assertEquals(BoardState.O_WIN, board.getBoardState());
    }

    @Test
    public void testNegDiagWin() throws IllegalMoveException {
        assertEquals(BoardState.IN_PROGRESS, board.getBoardState());

        board.move(0, 2, CellState.X);
        board.move(1, 1, CellState.X);
        board.move(2, 0, CellState.X);

        System.out.println(board.toString());
        assertEquals(BoardState.X_WIN, board.getBoardState());

        board.clearBoard();

        board.move(0, 2, CellState.O);
        board.move(1, 1, CellState.O);
        board.move(2, 0, CellState.O);

        System.out.println(board.toString());
        assertEquals(BoardState.O_WIN, board.getBoardState());
    }

    @Test
    public void testDraw() throws IllegalMoveException {
        assertEquals(BoardState.IN_PROGRESS, board.getBoardState());

        board.move(0, 0, CellState.O);
        board.move(0, 1, CellState.X);
        board.move(1, 1, CellState.O);
        board.move(0, 2, CellState.X);
        board.move(1, 2, CellState.O);
        board.move(1, 0, CellState.X);
        board.move(2, 0, CellState.O);
        board.move(2, 2, CellState.X);
        board.move(2, 1, CellState.O);

        System.out.println(board.toString());

        assertEquals(BoardState.DRAW, board.getBoardState());
    }

    @Test
    public void testGameOver() throws IllegalMoveException {
        assertEquals(BoardState.IN_PROGRESS, board.getBoardState());

        board.move(0, 2, CellState.X);
        board.move(1, 1, CellState.X);
        board.move(2, 0, CellState.X);

        exception.expect(IllegalMoveException.class);
        board.move(0, 0, CellState.O);
    }

    @Test
    public void testMoveWithEmpty() throws IllegalMoveException {
        int row = 0;
        int col = 0;
        CellState state = CellState.E;

        exception.expect(IllegalMoveException.class);

        board.move(row, col, state);
        System.out.println(board.toString());
    }

    @Test
    public void testCellPopulated() throws IllegalMoveException {
        int row = 0;
        int col = 0;
        CellState state = CellState.X;

        board.move(row, col, state);

        state = CellState.O;

        exception.expect(IllegalMoveException.class);

        board.move(row, col, state);
    }

    @Test
    public void testValidMovesBVA() throws IllegalMoveException {
        CellState state = CellState.X;

        // test rows
        // test 1
        int row = 0;
        int col = 1;

        board.move(row, col, state);

        assertEquals(CellState.X, board.getCellState(row, col));

        board.clearBoard();

        // test 2
        row = 1;

        board.move(row, col, state);

        assertEquals(CellState.X, board.getCellState(row, col));

        board.clearBoard();

        // test 3
        row = 2;

        board.move(row, col, state);

        assertEquals(CellState.X, board.getCellState(row, col));

        board.clearBoard();

        // test cols
        // test 4
        row = 1;
        col = 0;

        board.move(row, col, state);

        assertEquals(CellState.X, board.getCellState(row, col));

        board.clearBoard();

        // test 5
        col = 2;

        board.move(row, col, state);

        assertEquals(CellState.X, board.getCellState(row, col));
    }

    @Test
    public void testMoveWithInvalidRow() {
        // min - 1
        int row = -1;
        int col = 1;

        CellState state = CellState.X;

        boolean success = false;
        try {
            board.move(row, col, state);
        } catch (IllegalMoveException e) {
            success = true;
        }

        assertTrue(success);

        // max + 1
        row = 3;

        success = false;

        try {
            board.move(row, col, state);
        } catch (IllegalMoveException e) {
            success = true;
        }

        assertTrue(success);
    }

    @Test
    public void testMoveWithInvalidCol() {
        int row = 1;
        // min -1
        int col = -1;

        CellState state = CellState.X;

        boolean success = false;

        try {
            board.move(row, col, state);
        } catch (IllegalMoveException e) {
            success = true;
        }

        assertTrue(success);

        // max + 1
        col = 3;

        success = false;

        try {
            board.move(row, col, state);
        } catch (IllegalMoveException e) {
            success = true;
        }

        assertTrue(success);
    }

    @Test
    public void testUndoLastMove() throws IllegalMoveException {
        int row = 0;
        int col = 0;
        CellState state = CellState.X;

        board.move(row, col, state);

        int row2 = 1;
        int col2 = 1;
        board.move(row2, col2, state);

        board.undoLastMove();

        assertEquals(CellState.X, board.getCellState(row, col));
        assertEquals(CellState.E, board.getCellState(row2, col2));

        board.undoLastMove();

        assertEquals(CellState.E, board.getCellState(row, col));
        System.out.println(board.toString());
    }

    @Test
    public void testClearBoard() {
        board.clearBoard();
        assertTrue(board.isBoardClear());
        System.out.println(board.toString());
    }

}
