
package Main;

import javax.swing.JFrame;

public class Game {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        JFrame gameF = new JFrame("Dodge The Rocks Ladies.");
        gameF.setContentPane(new GamePanel());
        gameF.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        gameF.setResizable(false);
        gameF.pack();
        gameF.setVisible(true);
    }
    
}
