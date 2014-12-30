package TileMap;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

import Main.GamePanel;

public class Background {

    private BufferedImage image;

    private double x;
    private double y;
    private double dx;
    private double dy;

    private double moveScale;

    public Background(final String s, final double ms) {

        try {
            image = ImageIO.read(getClass().getResourceAsStream(s));
            moveScale = ms;
        } catch (final Exception ex) {
            ex.printStackTrace();
        }

    }

    public void setPosition(final double x, final double y) {
        this.x = (x * moveScale) % GamePanel.GAME_WIDTH;
        this.y = (y * moveScale) % GamePanel.GAME_HEIGHT;

    }

    public void setVector(final double dx, final double dy) {
        this.dx = dx;
        this.dy = dy;
    }

    public void update() {
        this.x += dx;
        this.y += dy;
        this.x = x % GamePanel.GAME_WIDTH;
        this.y = y % GamePanel.GAME_HEIGHT;
    }

    public void draw(final Graphics2D g) {
        g.drawImage(image, (int) x, (int) y, null);

        if (x < 0) {
            g.drawImage(image, (int) x + GamePanel.GAME_WIDTH, (int) y, null);
        } else if (x > 0) {
            g.drawImage(image, (int) x - GamePanel.GAME_WIDTH, (int) y, null);
        }
    }
}
