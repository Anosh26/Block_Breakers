package game;

import java.awt.Color;
import java.awt.Graphics;

/**
 * Represents a single brick in the game.
 */
public class Brick {

    public int x, y;
    public int width, height;
    public boolean isVisible;

    public Brick(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.isVisible = true; // Bricks start as visible
    }

    /**
     * Draws the brick on the screen if it is visible.
     * @param g The Graphics context to draw with.
     */
    public void draw(Graphics g) {
        if (isVisible) {
            g.setColor(Color.CYAN);
            g.fillRect(x, y, width, height);
            
            // Add a border to make bricks stand out
            g.setColor(Color.BLACK);
            g.drawRect(x, y, width, height);
        }
    }
}