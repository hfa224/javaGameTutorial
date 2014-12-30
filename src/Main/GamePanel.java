package Main;

import gamestate.GameStateManager;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

public class GamePanel extends JPanel implements Runnable, KeyListener {

    // dimensions
    public static final int SCALE = 2;
    public static final int GAME_WIDTH = 320 * SCALE;
    public static final int GAME_HEIGHT = 240 * SCALE;

    private final int DOUBLE_BUFFER = 2;

    // game thread
    private Thread thread;
    private boolean isRunning;
    private int FPS = 60;
    private final long targetTime = 1000 / FPS;

    private Graphics2D buffer;
    private BufferedImage image;

    // game state manager
    private GameStateManager gameStateManger;

    // private Graphics2D graphics;

    public GamePanel() {
        super();
        setPreferredSize(new Dimension(GAME_WIDTH, GAME_HEIGHT));
        setFocusable(true);
        requestFocus();
    }

    @Override
    public void addNotify() {
        super.addNotify();
        if (thread == null) {
            thread = new Thread(this);
            addKeyListener(this);
            thread.start();
        }
    }

    private void init() {

        image = new BufferedImage(GAME_WIDTH, GAME_HEIGHT,
                BufferedImage.TYPE_INT_RGB);

        // buffer = getBufferStrategy();
        // if (buffer == null) {
        // createBufferStrategy(DOUBLE_BUFFER);
        // return;
        // }

        // Graphics g = buffer.getDrawGraphics();

        // image = createImage(getSize().width, getSize().height);
        buffer = (Graphics2D) image.getGraphics();
        // graphics = (Graphics2D) image.getGraphics();

        isRunning = true;

        gameStateManger = new GameStateManager();
    }

    @Override
    public void run() {
        init();

        // long start;
        // long elapsed;
        // long wait;

        // game loop
        // while(isRunning) {
        //
        // start = System.nanoTime();
        //
        // update();
        // render();
        // drawToScreen();
        // elapsed = System.nanoTime() - start;
        // wait = targetTime - elapsed /1000000;
        // if (wait<0) {
        // wait = 5;
        // }
        //
        // try {
        // Thread.sleep(wait);
        // } catch(Exception e) {
        // e.printStackTrace();
        // }
        //
        // }

        /*
         * Using nano time to get a precise elapsed time and then dividing it by
         * TARGET_TIME which is 0.016 Nano seconds Everytime 0.016 adds up to 1
         * (Assuming 1 Second) we do an update.
         */

        long lastMilliSec = System.currentTimeMillis(); // Used to displayUPS
                                                        // and FPS every second
        long pastTime = System.nanoTime(); // NanoTime for a more precise time
                                           // compared to Milliseconds.
        final double UPS = 60.0;
        final double NANOSEC = 1000000000.0; // 1 Billionth of a Second
        final double TARGET_TIME = NANOSEC / UPS; // 0.016 Nanoseconds

        double elapsedTime = 0;
        int frames = 0;

        while (isRunning) {
            final long currentTime = System.nanoTime(); // Get current nano
                                                        // second time
            elapsedTime += (currentTime - pastTime) / TARGET_TIME; // Calculates
                                                                   // how much
                                                                   // time has
                                                                   // passed
                                                                   // since past
                                                                   // nano time.
            pastTime = currentTime;

            while (elapsedTime >= 1) { // When elapsedTime becomes 1 or greater
                                       // then update
                update();
                elapsedTime--;
            }

            // update();
            render();
            drawToScreen();// Render graphics
            frames++;

            if ((System.currentTimeMillis() - lastMilliSec) > 1000) { // If a
                                                                      // second
                                                                      // has
                                                                      // passed
                                                                      // since
                                                                      // last
                                                                      // millisecond
                                                                      // then
                                                                      // display
                                                                      // UPS and
                                                                      // FPS and
                                                                      // reset
                lastMilliSec += 1000;
                FPS = frames;
                frames = 0;
            }
        }
    }

    private void update() {
        gameStateManger.update();
    }

    // drawing background
    private void render() {

        // buffer.clearRect(0, 0, getSize().width, getSize().height);
        // draw stuff to buffer

        gameStateManger.draw(buffer);
        // final Graphics2D graphics = (Graphics2D) getGraphics();
        // graphics.drawImage(image, 0, 0, this);
        // graphics.dispose();
    }

    // drawing background
    private void drawToScreen() {

        // buffer.clearRect(0, 0, getSize().width, getSize().height);
        // draw stuff to buffer

        // gameStateManger.update();
        final Graphics2D graphics = (Graphics2D) getGraphics();
        graphics.drawImage(image, 0, 0, GAME_WIDTH, GAME_HEIGHT, this);
        graphics.dispose();
    }

    @Override
    public void keyPressed(final KeyEvent event) {
        gameStateManger.keyPressed(event.getKeyCode());
    }

    @Override
    public void keyReleased(final KeyEvent event) {
        gameStateManger.keyReleased(event.getKeyCode());
    }

    @Override
    public void keyTyped(final KeyEvent arg0) {
        // TODO Auto-generated method stub

    }

}
