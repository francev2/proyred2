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
    
    public Jugador(String username, String pass, String nombre, String apellido){
        this.username = username;
        this.pass = pass;
        this.apellido = apellido;
        this.nombre = nombre;
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
}
