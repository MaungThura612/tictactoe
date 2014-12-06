package cs3270.pearsalm.tictactoe;

import cs3270.pearsalm.tictactoe.view.GUI;
import cs3270.pearsalm.tictactoe.controller.Game;
import cs3270.pearsalm.tictactoe.model.CPUPlayer;
import cs3270.pearsalm.tictactoe.model.CellState;
import cs3270.pearsalm.tictactoe.model.HumanPlayer;

/**
 * Main class used to instantiate game and launch GUI
 *
 * @author M J Pearsall <pearsalm@aston.ac.uk>
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        CPUPlayer cpuPlayer = new CPUPlayer(CellState.O, true);
        HumanPlayer humanPlayer = new HumanPlayer(CellState.X, false);
        
        Game game = new Game(cpuPlayer, humanPlayer);
        
        GUI gui = new GUI(game);
        gui.lauch();
    }
    
}
