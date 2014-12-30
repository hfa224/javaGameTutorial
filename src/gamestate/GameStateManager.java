package gamestate;

import java.awt.Graphics2D;
import java.util.ArrayList;

public class GameStateManager {

    private final ArrayList<GameState> gameStates;
    private int currentState;

    public static final int MENUSTATE = 0;
    public static final int LEVEL1STATE = 1;

    public GameStateManager() {
        gameStates = new ArrayList<GameState>();

        currentState = MENUSTATE;
        gameStates.add(new MenuState(this));
        gameStates.add(new Level1State(this));
    }

    public void setState(final int state) {
        currentState = state;
        gameStates.get(currentState).init();
    }

    public void update() {
        gameStates.get(currentState).update();
    }

    public void draw(final Graphics2D g) {
        gameStates.get(currentState).draw(g);
    }

    public void keyPressed(final int k) {
        gameStates.get(currentState).keyPressed(k);
    }

    public void keyReleased(final int k) {
        gameStates.get(currentState).keyReleased(k);
    }
}
