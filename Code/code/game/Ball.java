package game;

import java.awt.Color;
import java.awt.Graphics;

public class Ball {

    public int x, y;
    public int width, height;
    public int xSpeed, ySpeed;

    public Ball() {
        this.width = 20;
        this.height = 20;
    }

    public void update(Player player, BrickManager brickManager, GamePanel panel) {
        x += xSpeed;
        y += ySpeed;

        // Wall bouncing (left and right)
        if (x <= 0 || x >= GamePanel.PANEL_WIDTH - this.width) {
            xSpeed = -xSpeed;
        }
        // Ceiling bounce
        if (y <= 0) {
            ySpeed = -ySpeed;
        }

        // Check collision with player paddle
        if (CollisionChecker.checkCollision(player, this)) {
            ySpeed = -Math.abs(ySpeed); // Always bounce upward
            
            // Add horizontal speed variation based on where ball hits paddle
            int paddleCenter = player.x + player.width / 2;
            int ballCenter = x + width / 2;
            int offset = ballCenter - paddleCenter;
            xSpeed = offset / 10; // Adjust angle based on hit position
        }

        // Check collision with bricks
        brickManager.checkCollisions(this);

        // If ball is stuck to paddle, move with it
        if (ySpeed == 0) {
            x = player.x + player.width / 2 - width / 2;
        }
    }

    public boolean isOutOfBounds() {
        return y > GamePanel.PANEL_HEIGHT;
    }

    public void setPosition(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getDiameter() {
        return width; // assuming square ball
    }

    public void draw(Graphics g) {
        g.setColor(Color.WHITE);
        g.fillOval(x, y, width, height);
    }
}