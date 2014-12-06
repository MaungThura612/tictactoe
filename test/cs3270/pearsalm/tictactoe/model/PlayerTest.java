/*
 * Produced as part of CS3270 coursework at Aston University
 */
package cs3270.pearsalm.tictactoe.model;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.Rule;
import org.junit.rules.ExpectedException;

/**
 *
 * @author M J Pearsall <pearsalm@aston.ac.uk>
 */
public class PlayerTest {

    @Rule
    public ExpectedException exception = ExpectedException.none();

    private Player player;

    public PlayerTest() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    @Test
    public void testEmptyRepresentation() {
        exception.expect(IllegalArgumentException.class);
        
        player = new PlayerImp(CellState.E, true);
    }

    public class PlayerImp extends Player {

        public PlayerImp(CellState playerRepresentation, boolean goesFirst) {
            super(playerRepresentation, goesFirst);
        }

    }

}
