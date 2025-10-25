package game;

import java.awt.Color;
import java.awt.Graphics;

public class Player {

    public int x, y;
    public int width, height;
    public int xSpeed, ySpeed;

    public Player() {
        this.width = 100;
        this.height = 20;
        this.x = (GamePanel.PANEL_WIDTH - this.width) / 2;
        this.y = GamePanel.PANEL_HEIGHT - this.height - 30;
    }

    public void update() {
        x += xSpeed;
        if (x < 0) x = 0;
        if (x > GamePanel.PANEL_WIDTH - this.width) {
            x = GamePanel.PANEL_WIDTH - this.width;
        }
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getWidth() {
        return width;
    }

    public void draw(Graphics g) {
        g.setColor(Color.GREEN);
        g.fillRect(x, y, width, height);
    }
}