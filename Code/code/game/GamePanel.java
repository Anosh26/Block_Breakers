package game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class GamePanel extends JPanel implements Runnable {
    public static final int PANEL_WIDTH = 800;
    public static final int PANEL_HEIGHT = 600;
    private Thread gameThread;
    private Player player;
    private Ball ball;
    private BrickManager brickManager;
    private InputHandler inputHandler;
    private boolean isRunning = false;
    private boolean isFirstLaunch = true;
    private boolean isPaused = false;
    private int score = 0;

    public GamePanel() {
        setPreferredSize(new Dimension(800, 600));
        setBackground(Color.BLACK);
        setFocusable(true);

        player = new Player();
        ball = new Ball();
        brickManager = new BrickManager(4, 8);

        inputHandler = new InputHandler(player, ball, this);
        addKeyListener(inputHandler);

        // --- Reliable replay and focus handling ---
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                // Ensure focus after click, even on Linux/Mac
                SwingUtilities.invokeLater(() -> requestFocusInWindow());

                if (!isRunning) {
                    resetGame();
                    startGame();
                }
            }
        });

        setBallOnPaddle();
    }

    // --- Core game loop ---
    @Override
    public void run() {
        double fps = 60.0;
        double timePerTick = 1_000_000_000 / fps;
        double delta = 0;
        long lastTime = System.nanoTime();

        while (isRunning) {
            long now = System.nanoTime();
            delta += (now - lastTime) / timePerTick;
            lastTime = now;

            if (delta >= 1) {
                if (!isPaused) {
                    update();
                }
                repaint();
                delta--;
            }
        }
    }

    private void update() {
        player.update();
        ball.update(player, brickManager, this);

        if (ball.isOutOfBounds()) {
            isRunning = false;
        }

        if (brickManager.allBricksDestroyed()) {
            isRunning = false;
        }

        score = brickManager.getDestroyedCount() * 10;
    }

    public void startGame() {
        isRunning = true;
        isPaused = false;
        gameThread = new Thread(this);
        gameThread.start();
    }

    private void resetGame() {
        // Create new game objects
        player = new Player();
        ball = new Ball();
        brickManager = new BrickManager(4, 8);
        score = 0;
        isFirstLaunch = false;
        isPaused = false;

        // Remove old listener referencing outdated player/ball
        removeKeyListener(inputHandler);

        // Add new InputHandler bound to the new objects
        inputHandler = new InputHandler(player, ball, this);
        addKeyListener(inputHandler);

        // Reset position and regain focus
        setBallOnPaddle();
        SwingUtilities.invokeLater(() -> requestFocusInWindow());
    }

    public void setBallOnPaddle() {
        ball.setPosition(
                player.getX() + player.getWidth() / 2 - ball.getDiameter() / 2,
                player.getY() - ball.getDiameter()
        );
        ball.xSpeed = 0;
        ball.ySpeed = 0;
    }

    public boolean isFirstLaunch() {
        return isFirstLaunch;
    }

    public void setFirstLaunch(boolean firstLaunch) {
        this.isFirstLaunch = firstLaunch;
    }

    public void togglePause() {
        isPaused = !isPaused;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        player.draw(g);
        ball.draw(g);
        brickManager.draw(g);

        // --- Draw score ---
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 20));
        g.drawString("Score: " + score, 20, 30);

        // --- Pause indicator ---
        if (isPaused && isRunning) {
            g.setColor(Color.YELLOW);
            g.setFont(new Font("Arial", Font.BOLD, 36));
            g.drawString("PAUSED", 320, 300);
        }

        // --- Game over / Replay text ---
        if (!isRunning && !isFirstLaunch) {
            g.setColor(Color.YELLOW);
            g.setFont(new Font("Arial", Font.BOLD, 36));
            
            if (brickManager.allBricksDestroyed()) {
                g.drawString("YOU WIN!", 300, 250);
            } else {
                g.drawString("GAME OVER", 280, 250);
            }
            
            g.setFont(new Font("Arial", Font.BOLD, 24));
            g.drawString("Click to Replay", 300, 320);
        }

        // --- Instructions at start ---
        if (isFirstLaunch) {
            g.setColor(Color.YELLOW);
            g.setFont(new Font("Arial", Font.BOLD, 24));
            g.drawString("Press UP ARROW to launch ball", 220, 350);
            g.drawString("Use LEFT/RIGHT ARROWS to move", 210, 380);
        }
    }
}