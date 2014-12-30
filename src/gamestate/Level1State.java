package gamestate;

import java.awt.Color;
import java.awt.Graphics2D;

import Main.GamePanel;
import TileMap.TileMap;

public class Level1State extends GameState {

    private TileMap tileMap;

    public Level1State(final GameStateManager gsm) {
        this.gsm = gsm;
        init();

    }

    @Override
    public void init() {

        tileMap = new TileMap(30);
        tileMap.loadTiles("/Tilesets/grasstileset.gif");
        tileMap.loadMap("/Maps/level1-1.map");
        tileMap.setPosition(0, 0);
    }

    @Override
    public void update() {
    }

    @Override
    public void draw(final Graphics2D g) {

        // clear screen
		g.setColor(Color.WHITE);
        g.fillRect(0, 0, GamePanel.GAME_WIDTH, GamePanel.GAME_HEIGHT);
		
		// draw tilemap
		tileMap.draw(g);


    }

    @Override
    public void keyPressed(final int k) {
    }

    @Override
    public void keyReleased(final int k) {
    }

}
