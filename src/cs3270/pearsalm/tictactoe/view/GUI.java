/*
 * Produced as part of CS3270 coursework at Aston University
 */
package cs3270.pearsalm.tictactoe.view;

import cs3270.pearsalm.tictactoe.controller.Game;
import cs3270.pearsalm.tictactoe.exception.IllegalMoveException;
import cs3270.pearsalm.tictactoe.model.BoardState;
import cs3270.pearsalm.tictactoe.model.CPUPlayer;
import cs3270.pearsalm.tictactoe.model.CellState;
import cs3270.pearsalm.tictactoe.model.HumanPlayer;
import cs3270.pearsalm.tictactoe.model.Player;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.WindowConstants;

/**
 * GUI for game
 *
 * @author M J Pearsall <pearsalm@aston.ac.uk>
 */
public class GUI {

    private final JFrame mainFrame;

    // rows and cols in board
    private static final int ROWS = 3;
    private static final int COLS = 3;

    // buttons representing board cells
    private final JButton topLeftBtn = new JButton();
    private final JButton topMidBtn = new JButton();
    private final JButton topRightBtn = new JButton();
    private final JButton midLeftBtn = new JButton();
    private final JButton midMidBtn = new JButton();
    private final JButton midRightBtn = new JButton();
    private final JButton botLeftBtn = new JButton();
    private final JButton botMidBtn = new JButton();
    private final JButton botRightBtn = new JButton();

    JButton[][] cellBtns = new JButton[ROWS][COLS];

    private Game game;
    private CPUPlayer cpuPlayer;
    private HumanPlayer humanPlayer;

    private boolean playerGoesFirst;

    /**
     * Construct the GUI
     *
     * @param game
     */
    public GUI(Game game) {
        this.game = game;

        // get players from game
        Player player1 = game.getPlayer1();
        Player player2 = game.getPlayer2();

        // determine types of players
        if (player1 instanceof CPUPlayer) {
            // player1 == cpu
            cpuPlayer = (CPUPlayer) player1;
            if (player2 instanceof HumanPlayer) {
                // player 2 == human
                humanPlayer = (HumanPlayer) player2;
            } else {
                throw new IllegalArgumentException("One player must be human");
            }
        } else if (player1 instanceof HumanPlayer) {
            // player1 == human
            humanPlayer = (HumanPlayer) player1;
            if (player2 instanceof CPUPlayer) {
                // player2 == cpu
                cpuPlayer = (CPUPlayer) player2;
            } else {
                throw new IllegalArgumentException("One player must be CPU");
            }
        } else {
            throw new UnsupportedOperationException("Only CPU and Human Players supported by the GUI");
        }

        playerGoesFirst = humanPlayer.isGoesFirst();

        // Step 1: Create components
        cellBtns[0][0] = topLeftBtn;
        cellBtns[0][1] = topMidBtn;
        cellBtns[0][2] = topRightBtn;
        cellBtns[1][0] = midLeftBtn;
        cellBtns[1][1] = midMidBtn;
        cellBtns[1][2] = midRightBtn;
        cellBtns[2][0] = botLeftBtn;
        cellBtns[2][1] = botMidBtn;
        cellBtns[2][2] = botRightBtn;

        // menus
        JMenuBar menuBar = new JMenuBar();
        JMenu gameMenu = new JMenu("Game");
        JMenu settingsMenu = new JMenu("Settings");

        JMenuItem newGameItem = new JMenuItem("New Game...");

        ButtonGroup group = new ButtonGroup();
        JRadioButtonMenuItem playerX = new JRadioButtonMenuItem("Player is X");
        group.add(playerX);
        settingsMenu.add(playerX);
        JRadioButtonMenuItem playerO = new JRadioButtonMenuItem("Player is O");
        group.add(playerO);
        settingsMenu.add(playerO);

        if (humanPlayer.getPlayerRepresentation() == CellState.X) {
            playerX.setSelected(true);
        } else {
            playerO.setSelected(true);
        }

        settingsMenu.addSeparator();

        JCheckBoxMenuItem playerFirst = new JCheckBoxMenuItem("Player Goes First");

        playerFirst.setSelected(playerGoesFirst);
        settingsMenu.add(playerFirst);

        // Step 2: Set properties of components
        Font btnFont = new Font(Font.SANS_SERIF, Font.BOLD, 50);

        topLeftBtn.setFont(btnFont);
        topMidBtn.setFont(btnFont);
        topRightBtn.setFont(btnFont);
        midLeftBtn.setFont(btnFont);
        midMidBtn.setFont(btnFont);
        midRightBtn.setFont(btnFont);
        botLeftBtn.setFont(btnFont);
        botMidBtn.setFont(btnFont);
        botRightBtn.setFont(btnFont);

        gameMenu.add(newGameItem);

        menuBar.add(gameMenu);
        menuBar.add(settingsMenu);

        // Step 3: Create containers
        mainFrame = new JFrame("Tic Tac Toe by Matthew Pearsall");
        mainFrame.setMinimumSize(new Dimension(500, 500));
        mainFrame.setResizable(false);
        mainFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        // Step 4: Layout managers
        GridLayout gridLayout = new GridLayout(3, 3);

        mainFrame.setLayout(gridLayout);

        // Step 5: Add components to containers
        mainFrame.add(topLeftBtn);
        mainFrame.add(topMidBtn);
        mainFrame.add(topRightBtn);
        mainFrame.add(midLeftBtn);
        mainFrame.add(midMidBtn);
        mainFrame.add(midRightBtn);
        mainFrame.add(botLeftBtn);
        mainFrame.add(botMidBtn);
        mainFrame.add(botRightBtn);

        mainFrame.setJMenuBar(menuBar);

        // Step 6: Component listeners
        setUpButtons();

        newGameItem.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                resetGame();
            }
        });

        playerX.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                updatePlayerRepresentations(CellState.X);
            }
        });

        playerO.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                updatePlayerRepresentations(CellState.O);
            }
        });

        playerFirst.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                swapFirstGo();
            }
        });

    }

    /**
     * Make the GUI visible
     */
    public void lauch() {
        //Step 7: Display
        if (mainFrame != null) {
            mainFrame.pack();
            mainFrame.setVisible(true);
            if (!playerGoesFirst) {
                cpuMove();
            }
        }
    }

    /**
     * Performs a round in the game
     *
     * @param row row of players next move
     * @param col column of players next move
     */
    private void gameTick(int row, int col) {
        try {
            // set players next move
            humanPlayer.setNextMove(row, col);

            // players move
            game.playerMove();

            //computers move
            cpuMove();

            // check if game is over
            if (game.getBoard().getBoardState() != BoardState.IN_PROGRESS) {
                // disable all buttons
                for (int i = 0; i < ROWS; i++) {
                    for (int j = 0; j < COLS; j++) {
                        for (ActionListener act : cellBtns[i][j].getActionListeners()) {
                            cellBtns[i][j].removeActionListener(act);
                        }
                    }
                }
            }
        } catch (IllegalMoveException ex) {
            // shouldn't happen within confines of the system
            Logger.getLogger(GUI.class.getName()).log(Level.SEVERE, null, ex);
            System.exit(1);
        }
    }

    /**
     * Updates the representation of players
     *
     * @param cellState
     */
    private void updatePlayerRepresentations(CellState cellState) {
        if (cellState == CellState.X) {
            humanPlayer.setPlayerRepresentation(CellState.X);
            cpuPlayer.setPlayerRepresentation(CellState.O);
        } else {
            humanPlayer.setPlayerRepresentation(CellState.O);
            cpuPlayer.setPlayerRepresentation(CellState.X);
        }
        resetGame();
    }

    /**
     * Swaps which player goes first
     */
    private void swapFirstGo() {
        playerGoesFirst = !playerGoesFirst;
        humanPlayer.setGoesFirst(playerGoesFirst);
        cpuPlayer.setGoesFirst(!playerGoesFirst);
        resetGame();
    }

    /**
     * Performs a CPU move in the game
     */
    private void cpuMove() {
        try {
            // perform a move
            int[] move = game.playerMove();
            if (move != null) {
                // update representing button
                cellBtns[move[0]][move[1]].setText(cpuPlayer.getPlayerRepresentation().toString());
                // remove action listeners for button
                for (ActionListener act : cellBtns[move[0]][move[1]].getActionListeners()) {
                    cellBtns[move[0]][move[1]].removeActionListener(act);
                }
            }
        } catch (IllegalMoveException ex) {
            // should not happen within confines of the system
            Logger.getLogger(GUI.class.getName()).log(Level.SEVERE, null, ex);
            System.exit(1);
        }
    }

    /**
     * Creates a new game
     */
    private void resetGame() {
        // create new game with existing player configs
        game = new Game(cpuPlayer, humanPlayer);
        // instantiate cell buttons
        setUpButtons();
        // if the CPU goes first perform a move
        if (!playerGoesFirst) {
            cpuMove();
        }
    }

    /**
     * Remove any remaining action listeners and instantiate fresh ones
     */
    private void setUpButtons() {
        // remove all action listeners and re-add
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLS; j++) {
                cellBtns[i][j].setText("");
                for (ActionListener act : cellBtns[i][j].getActionListeners()) {
                    cellBtns[i][j].removeActionListener(act);
                }
            }
        }

        topLeftBtn.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                topLeftBtn.setText(humanPlayer.getPlayerRepresentation().toString());
                topLeftBtn.removeActionListener(this);
                gameTick(0, 0);
            }
        });

        topMidBtn.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                topMidBtn.setText(humanPlayer.getPlayerRepresentation().toString());
                topMidBtn.removeActionListener(this);
                gameTick(0, 1);
            }
        });

        topRightBtn.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                topRightBtn.setText(humanPlayer.getPlayerRepresentation().toString());
                topRightBtn.removeActionListener(this);
                gameTick(0, 2);
            }
        });

        midLeftBtn.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                midLeftBtn.setText(humanPlayer.getPlayerRepresentation().toString());
                midLeftBtn.removeActionListener(this);
                gameTick(1, 0);
            }
        });

        midMidBtn.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                midMidBtn.setText(humanPlayer.getPlayerRepresentation().toString());
                midMidBtn.removeActionListener(this);
                gameTick(1, 1);
            }
        });

        midRightBtn.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                midRightBtn.setText(humanPlayer.getPlayerRepresentation().toString());
                midRightBtn.removeActionListener(this);
                gameTick(1, 2);
            }
        });

        botLeftBtn.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                botLeftBtn.setText(humanPlayer.getPlayerRepresentation().toString());
                botLeftBtn.removeActionListener(this);
                gameTick(2, 0);
            }
        });

        botMidBtn.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                botMidBtn.setText(humanPlayer.getPlayerRepresentation().toString());
                botMidBtn.removeActionListener(this);
                gameTick(2, 1);
            }
        });

        botRightBtn.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                botRightBtn.setText(humanPlayer.getPlayerRepresentation().toString());
                botRightBtn.removeActionListener(this);
                gameTick(2, 2);
            }
        });
    }

}
