package Main;

import javax.swing.JFrame;

public class Game {

    public static void main(final String[] args) {

        final JFrame window = new JFrame("Thing");
        window.setContentPane(new GamePanel());

        window.pack();
        window.setResizable(false);
        window.setLocationRelativeTo(null);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setVisible(true);
    }

}
