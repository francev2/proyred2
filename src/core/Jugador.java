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
public class Jugador {
    int id;
    String sala;
    String username;
    String pass;
    String nombre;
    String apellido;
    String color;
    Ficha[] fichas = new Ficha[4];
    
    public Jugador(String username, String pass, String nombre, String apellido){
        this.username = username;
        this.pass = pass;
        this.apellido = apellido;
        this.nombre = nombre;
    }
    public Jugador(String username, String pass, String nombre, String apellido,String color){
        this.username = username;
        this.pass = pass;
        this.apellido = apellido;
        this.nombre = nombre;
        this.color = color;
        inicializarPiezas();
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

    public String getColor(){ return this.color;}
    
    public void setColor(String color){ this.color = color;}
    
    private void inicializarPiezas() {
        for (int i = 0 ;i < this.fichas.length ;i++){
            this.fichas[i] = new Ficha(this.color);
        }
    }
    
    public int lanzarDado(){
        return (int) (Math.random() * 6) + 1;
    }
    
    
    public int liberarFicha(int i){
        this.fichas[i].setHome(false);
        int dado = lanzarDado();
        this.fichas[i].moverFicha(dado);
        return this.fichas[i].getCasilla();
    }
    
    public int moverFicha(int i,int movimientos){
        this.fichas[i].moverFicha(movimientos);
        return this.fichas[i].getCasilla();
    }
    
    public boolean getFichaInHome(int i){
        return this.fichas[i].getHome();
    }
}
