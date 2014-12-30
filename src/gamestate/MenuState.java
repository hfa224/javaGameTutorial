package gamestate;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.KeyEvent;

import TileMap.Background;

public class MenuState extends GameState {

    private Background bg;

    private final String[] options = { "Start", "Help", "Quit" };
    private int currentChoice = 0;

    private Color titleColor;
    private Font titleFont;

    private Font font;

    public MenuState(final GameStateManager gsm) {
        this.gsm = gsm;

        try {
            bg = new Background("/Backgrounds/menubg.gif", 1);
            bg.setVector(-0.1, 0);

            titleColor = new Color(128, 0, 0);
            titleFont = new Font("CenturyGothic", Font.PLAIN, 28);
            font = new Font("Arial", Font.PLAIN, 12);
        } catch (final Exception ex) {
            ex.printStackTrace();
        }

    }

    @Override
    public void init() {
        // TODO Auto-generated method stub

    }

    @Override
    public void update() {
        bg.update();
    }

    @Override
    public void draw(final Graphics2D g) {

        bg.draw(g);
        g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
                RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        g.setColor(titleColor);
        g.setFont(titleFont);
        g.drawString("Someone else's game :(", 80, 70);

        g.setFont(font);
        for (int i = 0; i < options.length; i++) {
            if (i == currentChoice) {
                g.setColor(Color.BLACK);
            } else {
                g.setColor(Color.RED);
            }

            g.drawString(options[i], 145, 140 + (i * 15));
        }
    }

    @Override
    public void keyPressed(final int k) {
        if (k == KeyEvent.VK_ENTER) {
            select();
        }
        if (k == KeyEvent.VK_UP) {
            currentChoice--;
            if (currentChoice == -1) {
                currentChoice = options.length - 1;
            }
        }
        if (k == KeyEvent.VK_DOWN) {
            currentChoice++;
            if (currentChoice == options.length) {
                currentChoice = 0;
            }
        }
    }

    @Override
    public void keyReleased(final int k) {
        // TODO Auto-generated method stub

    }

    private void select() {
        switch (currentChoice) {
        case 0:
            gsm.setState(GameStateManager.LEVEL1STATE);
            break;
        case 1:
            // help
            break;
        case 2:
            System.exit(0);
        }
    }

}
