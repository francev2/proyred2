/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package core;

import core.Fichas.Colores;
import core.Fichas.*;
import java.util.ArrayList;

/**
 *
 * @author Francisco
 */
public class Partida {
    private String nombre;
    private Tablero tablero;
    private ArrayList<Jugador> jugadores = new ArrayList<Jugador>();
    private ArrayList<Jugador> finalizaron = new ArrayList<Jugador>();
    private String turno;
    private boolean inicioDePartida;
    private boolean partidaIniciada;
    private boolean partidaPausada;
    private Colores[] colores;
    private boolean partidaTerminada = false;
    
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
        this.colores = new Colores[]{Colores.amarillo, Colores.verde, Colores.azul, Colores.rojo,  };
        this.nombre = nombre;
        this.tablero = new Tablero();
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
        System.out.println("jugador num: => " + jugadores.size());
        if (jugadores.size() < 4){
            //Jugador jugador = new Jugador( username, pass, nombre, apellido);
            Ficha ficha;
            Jugador jugador = new Jugador( username, pass, nombre, apellido, colores[jugadores.size()]);
            jugadores.add(jugador);
            return true;
        }
        else{
            return false;
        }
    }
    
    public Jugador getJugadorByUsername(String username){
        for(int i =0; i < jugadores.size(); i++){
            Jugador j = jugadores.get(i);
            if (j.getUsername().equalsIgnoreCase(username))
                return j;
        }
        
        return null;
    }
    
    // devuelve true si el jugador tiene alguna ficha fuera de home
    public boolean inHome(String username){
        return getJugadorByUsername(username).puedeMoverFicha();
    }
    
    
    // devuelve true si el jugador tiene ESA ficha dentro del home
    public boolean fichaInHome(String username, int numFicha){
        return getJugadorByUsername(username).getFichaInHome(numFicha-1);
    }
    
//    Devuelve la casilla donde va a caer la ficha sin afectar el tablero
    public int obtenerCasillaMovimiento (String username, int numFicha, int dado) {
        return getJugadorByUsername(username).getFicha(numFicha-1).casillaPosible(dado);
    }
    
//    devuelve la casilla donde calló la ficha
    public Casilla moverFicha(String username, int numFicha, int dado){
        int casillaActual = getJugadorByUsername(username).getFicha(numFicha-1).getCasilla() -1;
        
        int casilla = getJugadorByUsername(username).moverFicha(numFicha-1, dado)-1;
        System.out.println("casilla => " +casilla);
        
        try{
            this.tablero.setCasilla(casillaActual, getPosicionJugador(username), numFicha, false);
        }catch(Exception e){
            e.printStackTrace();
        }
        
        this.tablero.setCasilla(casilla, getPosicionJugador(username), numFicha);
        
        return this.tablero.getCasilla(casilla);
    }

    public void liberarFicha(String username, int numFicha) {
        getJugadorByUsername(username).liberarFicha(numFicha-1);
    }

//    Devuelve el username del usuario que fue desalojado y la ficha 
    public int[] resetCasillaOcupada(int numCasilla){
        int[] id = this.tablero.resetCasillaOcupada(numCasilla);
        this.jugadores.get(id[0]-1).returnToHome(id[1]-1);
        return id;
//        String[] r = new String[2]; 
//        try{
//            r[0] = getUsernameJugador(posJugador[0]);
//            r[1] = posJugador[1]+"";
//            return r;
//        }catch(Exception e){
//            e.printStackTrace();
//            return r;
//        }
        
    }
    
    public int getPosicionJugador(String username) {
        for (int i = 0; i < jugadores.size(); i++){
            if (jugadores.get(i).getUsername().equalsIgnoreCase(username))
                return i+1;
        }
        
        return -1;
    }
    
    public String getUsernameJugador(int posicion) {
        return jugadores.get(posicion).getUsername();
    }

    public ArrayList<Jugador> getFinalizaron() {
        return finalizaron;
    }

    public void setFinalizaron(ArrayList<Jugador> finalizaron) {
        this.finalizaron = finalizaron;
    }
    
    
    
    public void addGanador(Jugador jugador) {
        this.finalizaron.add(jugador);
    }
    
    public boolean finalizo(String username) {
        return this.getJugadorByUsername(username).comprobarFichasEnMeta();
    }

    public boolean isPartidaTerminada() {
        return partidaTerminada;
    }

    public void setPartidaTerminada(boolean partidaTerminada) {
        this.partidaTerminada = partidaTerminada;
    }
    
    
    public boolean partidaFinalizada() {
        boolean flag = true;
        for (int i = 0; i< jugadores.size(); i++){
            flag = flag && this.jugadores.get(i).comprobarFichasEnMeta();
        }
        
        this.partidaTerminada = flag;
        return flag;
    }
}
