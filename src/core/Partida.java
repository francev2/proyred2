/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package core;

import java.util.ArrayList;

/**
 *
 * @author Francisco
 */
public class Partida {
    String nombre;
    Tablero tablero;
    ArrayList<Jugador> jugadores = new ArrayList<Jugador>();

    public Partida(String nombre){
        this.nombre = nombre;
    }
    
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Tablero getTablero() {
        return tablero;
    }

    public void setTablero(Tablero tablero) {
        this.tablero = tablero;
    }

    public ArrayList<Jugador> getJugadores() {
        return jugadores;
    }

    public void setJugadores(ArrayList<Jugador> jugadores) {
        this.jugadores = jugadores;
    }
    
    
    public boolean addJuagador (String username, String pass, String nombre, String apellido ){
        if (jugadores.size() < 4){
            Jugador jugador = new Jugador( username, pass, nombre, apellido);
            jugadores.add(jugador);
            return true;
        }
        else{
            return false;
        }
    }
}
