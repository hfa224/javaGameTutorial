package TileMap;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.imageio.ImageIO;

import Main.GamePanel;

public class TileMap {

    private double x;
    private double y;

    private int xmin, ymin, xmax, ymax;

    // map

    private int[][] map;
    private final int tileSize;
    private int numRows;
    private int numColumns;
    private int width;
    private int height;

    // tileset

    private BufferedImage tileset;
    private int numTilesAcross;
    private Tile[][] tiles;

    // drawing
    private int rowOffset;
    private int columnOffset;
    private final int numRowsToDraw;
    private final int numColumnsToDraw;

    public TileMap(final int tileSize) {
        this.tileSize = tileSize;
        numRowsToDraw = (GamePanel.GAME_HEIGHT / tileSize) + 2;
        numColumnsToDraw = (GamePanel.GAME_WIDTH / tileSize) + 2;
        // tween = 0.7;
    }

    public void loadTiles(final String s) {
        try {
            tileset = ImageIO.read(getClass().getResourceAsStream(s));
            numTilesAcross = tileset.getWidth() / tileSize;
            tiles = new Tile[2][numTilesAcross];

            BufferedImage subImage;

            for (int col = 0; col < numTilesAcross; col++) {
                subImage = tileset.getSubimage(col * tileSize, 0, tileSize,
                        tileSize);

                tiles[0][col] = new Tile(subImage, Tile.NORMAL);

                subImage = tileset.getSubimage(col * tileSize, tileSize,
                        tileSize, tileSize);

                tiles[1][col] = new Tile(subImage, Tile.BLOCKED);
            }
        } catch (final Exception ex) {
            ex.printStackTrace();
        }

    }

    public void loadMap(final String s) {

        try {
            final InputStream in = getClass().getResourceAsStream(s);
            final BufferedReader br = new BufferedReader(new InputStreamReader(
                    in));

            numColumns = Integer.parseInt(br.readLine());
            numRows = Integer.parseInt(br.readLine());

            map = new int[numRows][numColumns];
            width = numColumns * tileSize;
            height = numRows * tileSize;

            final String delims = "\\s+";
            for (int row = 0; row < numRows; row++) {
                final String line = br.readLine();

                final String[] tokens = line.split(delims);

                for (int col = 0; col < numColumns; col++) {
                    map[row][col] = Integer.parseInt(tokens[col]);
                }

            }

        } catch (final Exception ex) {
            ex.printStackTrace();
        }
    }

    public int getX() {
        return (int) x;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getY() {
        return (int) y;
    }

    public int getTileSize() {
        return tileSize;
    }

    public int getType(final int row, final int col) {
        final int rc = map[row][col];
        final int r = rc / numTilesAcross;
        final int c = rc % numTilesAcross;

        return tiles[r][c].getType();
    }

    public void setPosition(final double x, final double y) {
        this.x += x;
        this.y += y;

        fixBounds();

        columnOffset = (int) -this.x / tileSize;
        rowOffset = (int) -this.y / tileSize;
    }

    private void fixBounds() {
        if (x < xmin) {
            x = xmin;
        }
        if (y < ymin) {
            y = ymin;
        }
        if (x > xmax) {
            x = xmax;
        }
        if (y > ymax) {
            y = ymax;
        }
    }

    public void draw(final Graphics2D g) {
        for (int row = rowOffset; row < (rowOffset + numRowsToDraw); row++) {

            if (row >= numRows) {
                break;
            }

            for (int col = columnOffset; col < (columnOffset + numColumnsToDraw); col++) {

                if (col >= numColumns) {
                    break;
                }

                if (map[row][col] == 0) {
                    continue;
                }

                final int rc = map[row][col];
                final int r = rc / numTilesAcross;
                final int c = rc % numTilesAcross;

                g.drawImage(tiles[r][c].getImage(), (int) x + (col * tileSize),
                        (int) y + (row * tileSize), null);

            }
        }
    }
}
