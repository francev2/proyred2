/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package core;

import java.io.Serializable;

/**
 *
 * @author Francisco
 */
public class Movimiento implements Serializable {
    boolean error;
    Casilla casilla;
    int numFicha;
    String mensaje;

    public Movimiento(boolean error, Casilla casilla, int numFicha) {
        this.error = error;
        this.casilla = casilla;
        this.numFicha = numFicha;
    }

    public Movimiento(boolean error, String mensaje) {
        this.error = error;
        this.mensaje = mensaje;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }
    
    

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public Casilla getCasilla() {
        return casilla;
    }

    public void setCasilla(Casilla casilla) {
        this.casilla = casilla;
    }

    public int getNumFicha() {
        return numFicha;
    }

    public void setNumFicha(int numFicha) {
        this.numFicha = numFicha;
    }
}
