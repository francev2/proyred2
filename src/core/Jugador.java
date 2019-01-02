/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package core;

import core.Fichas.Colores;
import core.Fichas.Ficha;

/**
 *
 * @author Francisco
 */
public class Jugador {
    int id;
    String sala;
    String username;
    String pass;
    String nombre;
    String apellido;
    Ficha[] fichas = new Ficha[4];
    Colores color;
    
    public Jugador(String username, String pass, String nombre, String apellido){
        this.username = username;
        this.pass = pass;
        this.apellido = apellido;
        this.nombre = nombre;
    }
    public Jugador(String username, String pass, String nombre, String apellido,Colores color){
        this.username = username;
        this.pass = pass;
        this.apellido = apellido;
        this.nombre = nombre;
        inicializarPiezas(color);
    }
    
    public Jugador(String username){
        this.username = username;
//        this.sala = sala;
    }
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSala() {
        return sala;
    }

    public void setSala(String ip) {
        this.sala = sala;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getColor(){ return this.fichas[0].getUrlIcon();}
    
    private void inicializarPiezas(Colores color) {
        for (int i = 0 ;i < this.fichas.length ;i++){
            this.fichas[i] = new Ficha(color);
        }
    }
    
    public int lanzarDado(){
        return (int) (Math.random() * 6) + 1;
    }
    
    
    public void liberarFicha(int i){
        this.fichas[i].setHome(false);
    }
    
    public int moverFicha(int i,int movimientos){
        this.fichas[i].moverFicha(movimientos);
        return this.fichas[i].getCasilla();
    }
    
    public boolean getFichaInHome(int i){
        return this.fichas[i].getHome();
    }
    
    public boolean puedeMoverFicha(){
        boolean flag = false;
        for (int i = 0; i < this.fichas.length; i++){
            flag = flag || !(this.getFichaInHome(i));
        }
        return flag;
    }
    
    public boolean getFichaInMeta(int i){
        return this.fichas[i].getEstaEnMeta();
    }
}
