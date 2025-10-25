package game;

import javax.swing.JFrame;

/**
 * The main entry point for the application.
 * This class is responsible for creating the main window (JFrame)
 * and launching the GamePanel.
 */
public class Main {

    public static void main(String[] args) {
        // Create the main window frame
        JFrame frame = new JFrame("Simple Pong Game");

        // Create an instance of the GamePanel
        GamePanel gamePanel = new GamePanel();

        // Add the panel to the frame
        frame.add(gamePanel);

        // Configure the frame's properties
        frame.pack(); // Sizes the window to fit the GamePanel's preferred size
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Closes the app when window is closed
        frame.setResizable(false); // Prevents the user from resizing the window
        frame.setLocationRelativeTo(null); // Centers the window on the screen
        frame.setVisible(true); // Makes the window visible

        // Start the game loop
        gamePanel.startGame();
    }
}