package Memoria;

import java.awt.Rectangle;

/**
 *
 * @author Juan Felipe López Hurtado
 */
public class Carta {
    
    public boolean abierta;
    private int carta;
    private Rectangle boton;

    public Carta(int carta, Rectangle boton) {
        this.carta = carta;
        this.boton = boton;
    }

    public boolean estaAbierta() {
        return abierta;
    }

    public int getCarta() {
        return carta;
    }

    public Rectangle getBoton() {
        return boton;
    }

    public void setAbierta(boolean x) {
        this.abierta = x;
    }
    
}
