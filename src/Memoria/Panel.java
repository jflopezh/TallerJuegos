package Memoria;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

/**
 *
 * @author Juan Felipe López Hurtado
 */
public class Panel extends JPanel implements MouseListener {
    
    private Carta[][] cartas;
    private int[] dec1;
    private int[] dec2;
    private int intentos;
    private int record;
    private int parejasDestapadas;
    private boolean ganado;
    private boolean nuevoRecord;
    
    public Panel(int record) {
        this.addMouseListener(this);
        this.cartas = new Carta[4][6];
        this.dec1 = new int[2];
        this.dec1[0] = -1;
        this.dec2 = new int[2];
        this.dec2[0] = -1;
        this.record = record;
        
        int[] iconos = new int[50];
        int[] tablero = new int[24];
        int num, pos, cont = 0;
        
        for (int i = 0; i < 50; i++) {
            iconos[i] = i;
            if (i < 24) {
                tablero[i] = -1;
            }
        }
        for (int i = 0; i < 24; i++) {
            if (tablero[i] == -1) {
                num = (int) (Math.random() * 50);
                while (iconos[num] == -1) {
                    num = (int) (Math.random() * 50);
                }
                tablero[i] = num;
                iconos[num] = -1;
                pos = (int) (Math.random() * 24);
                while (pos == i || tablero[pos] != -1) {
                    pos = (int) (Math.random() * 24);
                }
                tablero[pos] = num;
            }
        }
        
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 6; j++) {
                cartas[i][j] = new Carta(tablero[cont], new Rectangle(63 + (98 * j), 135 + (98 * i), 83, 83));
                cont++;
            }
        }
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(loadImage("Fondo.png"), 0, 0, 700, 700, this);
        Rectangle r = new Rectangle(45, 120, 605, 500);
        if (ganado) {
            g.setColor(new Color(15, 24, 22));
            g.setFont(new Font("x", 1, 55));
            g.drawString("¡Felicitaciones,", 150, 185);
            g.drawString("ha ganado el juego!", 90, 245);
            g.drawImage(loadImage("Carta.png"), 250, 275, 200, 200, this);
            g.drawString("Intentos: " + intentos, 195, 535);
            if (nuevoRecord) {
                g.drawString("¡Nuevo record!", 155, 595);
            } else {
                g.drawString("Record: " + record, 210, 595);
            }
        } else {
            int coorx, coory;
            for (int i = 0; i < 4; i++) {
                for (int j = 0; j < 6; j++) {
                    if (cartas[i][j].estaAbierta()) {
                        coorx = (cartas[i][j].getCarta() % 10) * 74;
                        coory = ((int) Math.floor(cartas[i][j].getCarta() / 10)) * 74;
                        g.drawImage(loadImage("Cartas.png"), 63 + (98 * j), 135 + (98 * i), 145 + (98 * j),
                                    218 + (98 * i), coorx, coory, coorx + 82, coory + 83, this);
                    } else {
                        g.drawImage(loadImage("Carta.png"), 63 + (98 * j), 135 + (98 * i), 83, 83, this);
                    }
                }
            }
            g.setColor(new Color(15, 24, 22));
            g.setFont(new Font("x", 1, 30));
            g.drawString("Intentos: " + intentos, 270, 555);
            if (record == 0) {
                g.drawString("Record: No registrado", 185, 590);
            } else {
                g.drawString("Record: " + record, 270, 590);
            }
        }
    }
    
    private Image loadImage(String imageName) {
        ImageIcon ii = new ImageIcon(imageName);
        Image image = ii.getImage();
        return image;
    }
    
    private void evaluarVictoria() {
        if (intentos < record || record == 0) {
            nuevoRecord = true;
            File f = new File("Record.txt");
            try {
                PrintStream p = new PrintStream(f);
                f.delete();
                f.createNewFile();
                p.print(intentos);
            } catch (FileNotFoundException ex) {
                System.out.println("El archivo \"Record.txt\" no ha sido encontrado.");
            } catch (IOException ex) {}
        }
    }

    public void mouseClicked(MouseEvent me) {};

    public void mousePressed(MouseEvent me) {
        Point p = me.getPoint();
        
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 6; j++) {
                if  (cartas[i][j].getBoton().contains(p) && !cartas[i][j].estaAbierta()) {
                    cartas[i][j].setAbierta(true);
                    repaint();
                    if (dec1[0] == -1) {
                        dec1[0] = i;
                        dec1[1] = j;
                    } else if (!(dec1[0] == i && dec1[1] == j)){
                        dec2[0] = i;
                        dec2[1] = j;
                    }
                    break;
                }
            }
        }      
    }

    public void mouseReleased(MouseEvent me) {
        if ((dec1[0] != -1) && (dec2[0] != -1)) {
            intentos++;
            if (cartas[dec1[0]][dec1[1]].getCarta() == cartas[dec2[0]][dec2[1]].getCarta()) {
                parejasDestapadas++;
            } else {
                try {
                    Thread.sleep(700);
                } catch (InterruptedException ex) {}
                cartas[dec1[0]][dec1[1]].setAbierta(false);
                cartas[dec2[0]][dec2[1]].setAbierta(false);
            }
            repaint();
            dec1[0] = -1;
            dec2[0] = -1;
        }
        if (parejasDestapadas == 12) {
            ganado = true;
            evaluarVictoria();
            repaint();
        }
    }

    public void mouseEntered(MouseEvent me) {};

    public void mouseExited(MouseEvent me) {};
    
}
