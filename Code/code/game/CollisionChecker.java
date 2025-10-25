package game;

public class CollisionChecker {

    /**
     * Checks if the rectangular boundaries of the player and ball are colliding.
     * @param player The player object.
     * @param ball The ball object.
     * @return true if they are colliding, false otherwise.
     */
    public static boolean checkCollision(Player player, Ball ball) {
        int playerLeft = player.x;
        int playerRight = player.x + player.width;
        int playerTop = player.y;
        int playerBottom = player.y + player.height;

        int ballLeft = ball.x;
        int ballRight = ball.x + ball.width;
        int ballTop = ball.y;
        int ballBottom = ball.y + ball.height;

        // Check for collision using the AABB (Axis-Aligned Bounding Box) algorithm
        return playerRight > ballLeft &&
               playerLeft < ballRight &&
               playerBottom > ballTop &&
               playerTop < ballBottom;
    }
}