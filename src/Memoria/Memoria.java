package Memoria;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import javax.swing.JFrame;

/**
 *
 * @author Juan Felipe López Hurtado
 */
public class Memoria extends JFrame {

    public Memoria() {
        File f = new File("Record.txt");
        try {
            Scanner sc = new Scanner(f);
            add(new Panel(sc.nextInt()));
        } catch (FileNotFoundException ex) {
            System.out.println("El archivo \"Record.txt\" no ha sido encontrado.");
        }
    }
    
    public static void main(String[] args) {
        Memoria frame = new Memoria();
        frame.setTitle("Juego");
        frame.setSize(700, 700);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
    
}
