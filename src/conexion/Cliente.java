/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package conexion;

import core.Jugador;
import core.Movimiento;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import ludo.Inicio;
import ludo.Pantalla;

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
    boolean turno;
    int dado;
    int numFicha;
    int intentos = 0;
    boolean finalizo = false;
    private Pantalla pantalla;
    private Inicio inicio;
    private boolean inicioDePartida;
    
    

    public Cliente(String ipServer, int ipPort, Inicio inicio) throws IOException {
        this.ipServer = ipServer;
        this.inicio = inicio;
        
            this.socket = new Socket(ipServer, ipPort);
            
            salida = new ObjectOutputStream(socket.getOutputStream());
            entrada = new ObjectInputStream(socket.getInputStream());
            
            
            new Thread(new Listener(this)).start();
       
    }
    
    public void pasarTurno(){
        if (turno)
            this.enviarMensaje(new Paquete(this.username,Tipo.TURNO,false));
        else
            pantalla.mostrarMensaje("No es tu turno");
            
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
        
//        this.inicio.mostrarMensaje("Iniciando conexión ...");
        
        Paquete p = new Paquete();
        p.setError(false);
        p.setTipo(Tipo.LOGIN);
        p.setUsername(username);
        System.out.println(username);
        System.out.println(p.getUsername()+"5");
        p.setMensaje(password);
        enviarMensaje(p);
    }
    
    public boolean isTurno(){
        return this.turno;
    }
    

    public String procesarRespuesta(Paquete m) {
        try{
        Tipo  tipo= m.getTipo();
        if (tipo == Tipo.LOGIN){
            if(m.isError()){
                try {
                    System.out.println(m.getMensaje()+"2");
//                    this.pantalla.dispose();
                    cerrarConexion();
                    this.inicio.mostrarMensaje(m.getMensaje());
                    return m.getMensaje();
                } catch (Throwable ex) {
                    Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null, ex);
                }
            }else{
                this.username = m.getUsername();
                System.out.println(m.getMensaje()+"1");
                this.pantalla = new Pantalla(this);
                this.pantalla.setVisible(true);
                pantalla.mostrarMensaje("Esperando a todos los jugadores ...");
            }
            
            return m.getMensaje();  
        } else if (tipo == Tipo.TURNO || tipo == Tipo.INICIODEPARTIDA){
            if (m.isError()){
                pantalla.mostrarMensaje(m.getMensaje());
                return  m.getMensaje();
            }
            if (m.getUsername().equalsIgnoreCase(this.username)){
                turno = true;
                pantalla.habilitarDadoButton(turno);
                pantalla.mostrarMensaje("Es tu turno");
                return ("Es tu turno");
            }else{
                turno = false;
                pantalla.habilitarDadoButton(turno);
                pantalla.mostrarMensaje("Es el turno de "+m.getUsername());
                return ("Es el turno de "+m.getUsername());
            }
//        } if () {
//            if (m.getUsername().equalsIgnoreCase(this.username)){   
//                turno = true;
//                pantalla.mostrarMensaje("Es tu turno");
//                return "Es tu turno";
//            }else{
//                turno = false;
//                return "El turno es de "+ m.getUsername();
//            }
        } else if (tipo == Tipo.PAUSARPARTIDA) {
            turno = false;
            pantalla.habilitarDadoButton(false);
            pantalla.mostrarMensaje("Partida pausada");
            return "Partida pausada";
        
        } else if ( tipo == Tipo.MOVIMIENTO ) {
            if (m.isError()){
                if (m.getMovimiento().isError()){
                    this.dado = Integer.parseInt(m.getMensaje());
                    this.turno = true;
                    this.intentos = 1;
                    pantalla.mostrarMensaje(m.getMovimiento().getMensaje());
                }else
                    pantalla.mostrarMensaje(m.getMensaje());
                    
                return m.getMensaje();
            } else {
//                un jugador se movio
                if (m.getMovimiento().isError()){
                    pantalla.mostrarMensaje(m.getMovimiento().getMensaje());
                }else{
                    int ficha = m.getMovimiento().getNumFicha();
                    int x = m.getMovimiento().getCasilla().getX();
                    int y = m.getMovimiento().getCasilla().getY();
                    
                    pantalla.moverFicha(ficha, x, y);
                }
                return "Se movió el jugador";
            }
        } else if ( tipo == Tipo.COLOR ){
            String usernameLabel = m.getUsername();
            String message = "Se unió "+usernameLabel+" a la partida ";
            
            if (usernameLabel.equalsIgnoreCase(this.username)){
                usernameLabel = "Tú";
            }
            
            
            pantalla.setColorJugador(m.getMensaje(), usernameLabel);
            
            pantalla.mostrarMensaje(message);
            
            return message;
        } else if (tipo == Tipo.LLEGO){
            this.finalizo = true;
            pantalla.mostrarMensaje(m.getMensaje());
            return m.getMensaje();
        }else{
            return m.getMensaje();
        }
        
        
        }catch(Exception e){
            e.printStackTrace();
            String mess = "Ocurrió un error ene el servidor";
            pantalla.mostrarMensaje(mess);
            return mess;
        }
                       
    }
    
    public void enviarFicha(){
        if (!turno){
            pantalla.mostrarMensaje("No es tu turno");
        }else{
            if (numFicha == 0 || numFicha > 4)
                pantalla.mostrarMensaje("Selecione una ficha");
            else{
                
                enviarMensaje(new Paquete(this.username, Tipo.DADO, false, ""+this.dado, new Movimiento(false, null, this.numFicha)) );
                this.numFicha = 0;
                this.dado = 0;
                this.turno = false;
                this.intentos = 0;
            }
        }
    }
    
    public void seleccionarFicha(int numFicha){
        if (turno){
            if (this.dado == 0){
                pantalla.mostrarMensaje("¡Lanza el dado!");
            }
            else{
                this.numFicha = numFicha;
                pantalla.mostrarMensaje("Seleccionaste la ficha "+numFicha+"\n Presiona el botón de enviar ficha");
            }
        }else{
            pantalla.mostrarMensaje("No es tu turno");
        }
    }
    
    public void resetFicha(){
        this.numFicha = 0;
    }
    
    public void lanzarDado(){
        if (!turno){
            pantalla.mostrarMensaje("No es tu turno");
        } else if (!finalizo) {
            if (intentos == 0){
                this.intentos ++;
                this.dado = (int)(1+(Math.random()*6));
                pantalla.mostrarDado(this.dado);

                pantalla.mostrarMensaje("Selecciona una ficha");
            }
            else{
                pantalla.mostrarMensaje("Solo puedes lanzar el dado 1 vez");
            }
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

    public void setPantalla(Pantalla p) {
        this.pantalla = p;
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
