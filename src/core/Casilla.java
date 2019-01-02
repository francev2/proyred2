/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package core;

/**
 *
 * @author Francisco
 */
public class Casilla {
    private int x;
    private int y;
    private boolean ocupada;
    private TipoCasilla tipo;
    
    public Casilla (int x, int y, boolean ocupada,TipoCasilla tipo){
        this.x = x;
        this.y = y;
        this.ocupada = ocupada;
        this.tipo = tipo;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public boolean isOcupada() {
        return ocupada;
    }

    public void setOcupada(boolean ocupada) {
        this.ocupada = ocupada;
    }
    
    
}
