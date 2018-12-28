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
public class Tablero {
    Casilla[] casillas = new Casilla[76];
    int[][] ocupadas = new int[16][3];
    private int turno;

    public int getTurno() {
        return turno;
    }

    public void setTurno(int turno) {
        this.turno = turno;
    }
    
    
    public int getIdJugador(int casilla){
        for (int i = 0; i < 16; i++){
            if (ocupadas[i][1] == casilla)
                return ocupadas[i][1];
        }
        
        return -1;
    }
    
    public Tablero(){
        
    }
    
    public int lanzarDado(){
        return (int) (Math.random() * 6) + 1;
    }
    
    private void inicializarCasillas(){
        
    }
}


