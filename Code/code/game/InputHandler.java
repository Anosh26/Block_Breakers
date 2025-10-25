package game;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class InputHandler implements KeyListener {

    private Player player;
    private Ball ball;
    private GamePanel gamePanel;

    private boolean leftPressed = false;
    private boolean rightPressed = false;

    public InputHandler(Player player, Ball ball, GamePanel gamePanel) {
        this.player = player;
        this.ball = ball;
        this.gamePanel = gamePanel;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();

        // One-time launch with UP key at the very start
        if (gamePanel.isFirstLaunch() && key == KeyEvent.VK_UP) {
            if (ball.ySpeed == 0) {
                ball.ySpeed = -5;
                ball.xSpeed = 2;
                gamePanel.setFirstLaunch(false);
            }
        }
        // Launch with SPACE for replays
        else if (key == KeyEvent.VK_SPACE) {
            if (ball.ySpeed == 0) {
                ball.ySpeed = -5;
                ball.xSpeed = 2;
            }
        }

        // Paddle control
        if (key == KeyEvent.VK_LEFT) leftPressed = true;
        if (key == KeyEvent.VK_RIGHT) rightPressed = true;
        updatePlayerSpeed();

        // Pause toggle
        if (key == KeyEvent.VK_P) {
            gamePanel.togglePause();
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int key = e.getKeyCode();
        if (key == KeyEvent.VK_LEFT) leftPressed = false;
        if (key == KeyEvent.VK_RIGHT) rightPressed = false;
        updatePlayerSpeed();
    }

    @Override
    public void keyTyped(KeyEvent e) {}

    private void updatePlayerSpeed() {
        if (leftPressed && !rightPressed) player.xSpeed = -5;
        else if (rightPressed && !leftPressed) player.xSpeed = 5;
        else player.xSpeed = 0;
    }
}
