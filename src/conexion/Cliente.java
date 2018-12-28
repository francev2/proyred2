/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package conexion;

import core.Jugador;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Francisco
 */
public class Cliente {
    Socket socket;
    String ipServer;
    private ObjectOutputStream salida;
    private ObjectInputStream entrada;
    String username;

    public Cliente(String ipServer, int ipPort) throws IOException {
        this.ipServer = ipServer;
        
        
            this.socket = new Socket(ipServer, ipPort);
            
            salida = new ObjectOutputStream(socket.getOutputStream());
            entrada = new ObjectInputStream(socket.getInputStream());
            
            
            new Thread(new Listener(this)).start();
       
    }
    
    private boolean enviarMensaje(Paquete p){
        try {
            this.salida.flush();
            this.salida.writeObject(p);
            return true;
        } catch (IOException ex) {
            Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return false;
    }
    
    public void login(String username, String password){
        Paquete p = new Paquete();
        p.setError(false);
        p.setTipo(Tipo.LOGIN);
        p.setUsername(username);
        System.out.println(username);
        System.out.println(p.getUsername()+"5");
        p.setMensaje(password);
        enviarMensaje(p);
    }
    
    
    

    public String procesarRespuesta(Paquete m) {
            
        if (m.getTipo() == Tipo.LOGIN){
            if(m.isError()){
                try {
                    System.out.println(m.getMensaje()+"2");
                    cerrarConexion();
                    return m.getMensaje();
                } catch (Throwable ex) {
                    Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null, ex);
                }
            }else{
                this.username = m.getUsername();
                System.out.println(m.getMensaje()+"1");
            }
            
            return m.getMensaje();  
        } 
        else{
            return m.getMensaje();
        }
        
        
                
                
    }
    
    private void cerrarConexion(){
        try {
            this.entrada.close();
            this.salida.close();
            this.socket.close();
        } catch (IOException ex) {
            Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null, ex);
        }
    }


   public class Listener implements Runnable {
        private Cliente c;

        public Listener(Cliente c) {
            this.c = c;
        }
        @Override
        public void run() {
            while(true){
                try {
                    System.out.println("Esperando el Tablero");
                    Paquete m = (Paquete) this.c.entrada.readObject();
                    this.c.procesarRespuesta(m);
                    System.out.println("Tablero Recibido");
                } catch (IOException ex) {
                    Logger.getLogger(ConexionUsuario.class.getName()).log(Level.SEVERE, null, ex);
                } catch (ClassNotFoundException ex) {
                    Logger.getLogger(ConexionUsuario.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
   }
    
    
}
