package Ahorcado;

import javax.swing.JFrame;

/**
 *
 * @author Juan Felipe López Hurtado
 */
public class Ahorcado extends JFrame {
    
    public Ahorcado() {
        add(new Panel());
    }
    
    public static void main(String[] args) {
        Ahorcado frame = new Ahorcado();
        frame.setTitle("Juego");
        frame.setSize(1120, 700);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
    
}
