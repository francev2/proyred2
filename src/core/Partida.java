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
    private String nombre;
    private Tablero tablero;
    private ArrayList<Jugador> jugadores = new ArrayList<Jugador>();
    private String turno;
    private boolean inicioDePartida;
    private boolean partidaIniciada;
    private boolean partidaPausada;
//    Representa el valor de los dados para saber que jugador empieza
    private String[][] inicio = new String[4][2];

    public String[][] getInicio() {
        return inicio;
    }

    public void setInicio(String[][] inicio) {
        this.inicio = inicio;
    }

    
    public boolean isPartidaIniciada() {
        return partidaIniciada;
    }

    public void setPartidaIniciada(boolean partidaIniciada) {
        this.partidaIniciada = partidaIniciada;
    }

    public boolean isInicioDePartida() {
        return inicioDePartida;
    }

    public void setInicioDePartida(boolean inicioDePartida) {
        this.inicioDePartida = inicioDePartida;
    }

    public Partida(String nombre){
        this.nombre = nombre;
    }

    public boolean isPartidaPausada() {
        return partidaPausada;
    }

    public void setPartidaPausada(boolean partidaPausada) {
        this.partidaPausada = partidaPausada;
    }

    public String getTurno() {
        return turno;
    }

    public void setTurno(String turno) {
        this.turno = turno;
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
