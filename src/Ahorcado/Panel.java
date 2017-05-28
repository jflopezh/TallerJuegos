package Ahorcado;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

/**
 *
 * @author Juan Felipe López Hurtado
 */
public class Panel extends JPanel implements MouseListener {
    
    private Letra[][] letras;
    private String[] palabras;
    private String palabra;
    private char[] juego;
    private String fallos;
    private int acabado;

    public Panel() {
        this.addMouseListener(this);
        letras = new Letra[4][7];
        palabras = new String[8];
        palabras[0] = "IGUANA";
        palabras[1] = "CABEZA";
        palabras[2] = "GORILA";
        palabras[3] = "ARBOLEDA";
        palabras[4] = "BANANA";
        palabras[5] = "AVION";
        palabras[6] = "TENEDOR";
        palabras[7] = "ALCANCIA";
        palabra = palabras[(int) (Math.random() * 8)];
        juego = new char[palabra.length()];
        fallos = "";
        
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 7; j++) {
                if (i == 4 && j == 5) {
                    break;
                }
                letras[i][j] = new Letra((char) (65 + (i * 7) + j), new Rectangle
                                   (472 + (j * 75), 255 + (i * 77), 60, 60));
            }
        }
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(loadImage("FondoA.png"), 0, 0, 1120, 700, this);
        if (fallos.length() != 0) {
            g.drawImage(loadImage("Ahorcado.png"), 270, 220, 420, 577, (fallos.length() - 1)
                        * 192, 0, fallos.length() * 192, 457, this);
        }
        
        g.setColor(new Color(243, 156, 17));
        g.setFont(new Font("x", 1, 40));
        for (int i = 0; i < fallos.length(); i++) {
            g.drawString("" + fallos.charAt(i), 493 + (74 * i), 212);
        }
        
        switch (acabado) {
            case 0:
                g.setColor(Color.BLACK);
                for (int i = 0; i < palabra.length(); i++) {
                    g.drawImage(loadImage("Palabra.png"), 476 + (75 * i), 620, 60, 10, this);
                    if (juego[i] > 64 && juego[i] < 91) {
                        g.drawString("" + juego[i], 493 + (74 * i), 610);
                    }
                }   break;
            case 1:
                g.setColor(new Color(123, 150, 72));
                g.setFont(new Font("x", 1, 50));
                g.drawString("¡Ganaste!", 610, 620);
                break;
            default:
                g.setColor(Color.BLACK);
                g.setFont(new Font("x", 1, 50));
                g.drawString("Perdiste", 630, 620);
                break;
        }
        
    }
    
    private Image loadImage(String imageName) {
        ImageIcon ii = new ImageIcon(imageName);
        Image image = ii.getImage();
        return image;
    }
    
    private void analizarLetra(char l) {
        boolean valida = false;
        for (int i = 0; i < palabra.length(); i++) {
            if (palabra.charAt(i) == l) {
                valida = true;
                juego[i] = palabra.charAt(i);
            }
        }
        if (!valida && fallos.length() < 7) {
            fallos += l;
        }
        repaint();
    }
    
    public void mouseClicked(MouseEvent me) {};

    public void mousePressed(MouseEvent me) {
        if (acabado == 0) {
            Point p = me.getPoint();
            for (int i = 0; i < 4; i++) {
                for (int j = 0; j < 7; j++) {
                    if (letras[i][j].getBoton().contains(p) && !letras[i][j].estaUsada()) {
                        letras[i][j].usar();
                        analizarLetra(letras[i][j].getLetra());
                        break;
                    }
                }
            }
        }
    }

    public void mouseReleased(MouseEvent me) {
        boolean igual = true;
        for (int i = 0; i < palabra.length(); i++) {
            if (palabra.charAt(i) != juego[i]) {
                igual = false;
            }
        }
        if (igual) {
            try {
                Thread.sleep(1500);
            } catch (InterruptedException ex) {}
            acabado = 1;
            repaint();
        } else if (fallos.length() == 7) {
            acabado = 2;
            repaint();
        }
    }

    public void mouseEntered(MouseEvent me) {};

    public void mouseExited(MouseEvent me) {};
    
}
