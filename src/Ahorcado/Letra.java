package Ahorcado;

import java.awt.Rectangle;

/**
 *
 * @author Juan Felipe López Hurtado
 */
public class Letra {
    
    private boolean usada;
    private char letra;
    private Rectangle boton;

    public Letra(char letra, Rectangle boton) {
        this.usada = false;
        this.letra = letra;
        this.boton = boton;
    }

    public boolean estaUsada() {
        return usada;
    }

    public char getLetra() {
        return letra;
    }

    public Rectangle getBoton() {
        return boton;
    }
    
    public void usar() {
        usada = true;
    }
    
}
