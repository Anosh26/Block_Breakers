package game;

import java.awt.Graphics;
import java.awt.Rectangle;

public class BrickManager {

    public Brick[][] bricks;
    private int brickWidth;
    private int brickHeight;
    private int totalBricks;
    private int destroyedCount;

    public BrickManager(int rows, int cols) {
        bricks = new Brick[rows][cols];
        totalBricks = rows * cols;
        destroyedCount = 0;

        int padding = 10;
        brickWidth = (GamePanel.PANEL_WIDTH - (padding * (cols + 1))) / cols;
        brickHeight = 30;

        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                int brickX = c * (brickWidth + padding) + padding;
                int brickY = r * (brickHeight + padding) + 50;
                bricks[r][c] = new Brick(brickX, brickY, brickWidth, brickHeight);
            }
        }
    }

    public void draw(Graphics g) {
        for (Brick[] row : bricks) {
            for (Brick brick : row) {
                brick.draw(g);
            }
        }
    }

    public int checkCollisions(Ball ball) {
        int brokenBricksThisFrame = 0;

        for (Brick[] row : bricks) {
            for (Brick brick : row) {
                if (!brick.isVisible) continue;

                Rectangle ballRect = new Rectangle(ball.x, ball.y, ball.width, ball.height);
                Rectangle brickRect = new Rectangle(brick.x, brick.y, brick.width, brick.height);

                if (ballRect.intersects(brickRect)) {
                    // Determine collision side
                    int overlapLeft = ballRect.x + ballRect.width - brickRect.x;
                    int overlapRight = brickRect.x + brickRect.width - ballRect.x;
                    int overlapTop = ballRect.y + ballRect.height - brickRect.y;
                    int overlapBottom = brickRect.y + brickRect.height - ballRect.y;

                    boolean ballFromLeft = overlapLeft < overlapRight && overlapLeft < overlapTop && overlapLeft < overlapBottom;
                    boolean ballFromRight = overlapRight < overlapLeft && overlapRight < overlapTop && overlapRight < overlapBottom;
                    boolean ballFromTop = overlapTop < overlapBottom && overlapTop < overlapLeft && overlapTop < overlapRight;
                    boolean ballFromBottom = overlapBottom < overlapTop && overlapBottom < overlapLeft && overlapBottom < overlapRight;

                    if (ballFromLeft || ballFromRight) {
                        ball.xSpeed = -ball.xSpeed;
                    } else {
                        ball.ySpeed = -ball.ySpeed;
                    }

                    brick.isVisible = false;
                    destroyedCount++;
                    brokenBricksThisFrame++;
                }
            }
        }
        return brokenBricksThisFrame;
    }

    public boolean allBricksDestroyed() {
        return destroyedCount >= totalBricks;
    }

    public int getDestroyedCount() {
        return destroyedCount;
    }
}