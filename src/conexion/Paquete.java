/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package conexion;

import java.io.Serializable;

/**
 *
 * @author Francisco
 */
public class Paquete implements Serializable {
    private String username;
    private String mensaje;
    private Tipo tipo;
    private boolean error; 

    public Paquete() {
    }
    
    

    public Paquete(String username, Tipo tipo, boolean error) {
        this.username = username;
        this.tipo = tipo;
        this.error = error;
    }

    public Paquete(String username, Tipo tipo, boolean error, String mensaje) {
        this.username = username;
        this.mensaje = mensaje;
        this.tipo = tipo;
        this.error = error;
    }
    
    

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public Tipo getTipo() {
        return tipo;
    }

    public void setTipo(Tipo tipo) {
        this.tipo = tipo;
    }
    
    
}
