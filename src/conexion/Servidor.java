/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package conexion;
import core.Partida;
import java.net.*;
import java.io.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;



/**
 *
 * @author Francisco
 */
public class Servidor {
    ServerSocket server;
    ArrayList<Partida> partidas;
    ArrayList<ConexionUsuario> conexiones;
    int puerto = 9000;
    ObjectInputStream entrada;
    ObjectOutputStream salida;

    public ServerSocket getServer() {
        return server;
    }

    public ArrayList<Partida> getPartidas() {
        return partidas;
    }

    public void setPartidas(ArrayList<Partida> partidas) {
        this.partidas = partidas;
    }

    public ObjectInputStream getEntrada() {
        return entrada;
    }

    public void setEntrada(ObjectInputStream entrada) {
        this.entrada = entrada;
    }

    public ObjectOutputStream getSalida() {
        return salida;
    }

    public void setSalida(ObjectOutputStream salida) {
        this.salida = salida;
    }

    public void setPuerto(int puerto) {
        this.puerto = this.puerto;
    }
    
    public Servidor(int puerto) throws IOException{
        this.puerto = puerto;
        partidas = new ArrayList<Partida>();
        conexiones = new ArrayList<ConexionUsuario>();
        
        server = new ServerSocket(puerto);
        
        new Thread(new ListenerAccepts(this) ).start();
    }
    
    
    public boolean crearPartida(String nombrePartida){
        
        if (!isPartidaCreada(nombrePartida)){
            Partida p = new Partida(nombrePartida);
            partidas.add(p);
            return true;
        }
        return false;
    }
    
    public boolean isPartidaCreada(String nombrePartida){
        
        for (int i = 0; i < partidas.size(); i ++)
            if (partidas.get(i).getNombre().equalsIgnoreCase(nombrePartida))
                return true;
        
        return false;
    }
    
    public String registrarJugador(String sala, String nombre, String apellido, String username, String password ){
        
        Partida partida = null; 
        for (int i = 0; i < partidas.size(); i ++)
            if (partidas.get(i).getNombre().equalsIgnoreCase(sala))
                partida = partidas.get(i);
                
        if (partida == null)
            return "La sala selecionada no esta creada";
        
        partida.addJuagador(username, password, nombre, apellido);
        
        return "Jugador creado exitosamente";
    }
    
    
    public Paquete loginJugador(String username, String pass){
        Paquete p = new Paquete();
        
        
        for (int i = 0; i < partidas.size(); i ++){
            System.out.println(partidas.get(i).getNombre());
            for (int j = 0; j < partidas.get(i).getJugadores().size(); j++){
                System.out.println(partidas.get(i).getJugadores().get(j).getUsername() + " == " + username );
                
                if (partidas.get(i).getJugadores().get(j).getUsername().equalsIgnoreCase(username)){
                    p.setError(false);
                    p.setTipo(Tipo.LOGIN);
                    p.setMensaje(partidas.get(i).getNombre());
                    p.setUsername(partidas.get(i).getJugadores().get(j).getUsername());
                    return p;
                }
            }
        }
                    
        System.out.println("usuario no registrado");
        p.setError(true);
        p.setMensaje("Usuario no registrado");
        return p;
    }
    
    
    public String getIpAndPort() throws UnknownHostException{
        
        return Inet4Address.getLocalHost().getHostAddress()+':'+this.server.getLocalPort();
    }

    private void procesarSolicitud(Paquete sol, ConexionUsuario c) {
        if (sol.getTipo() == Tipo.LOGIN){
//            conexiones.get(conexiones.indexOf(c)).setUsername(sol.getUsername());
            c.setUsername(sol.getUsername());
            System.out.println(sol.getUsername()+"6");
            enviarMensaje(c, loginJugador(sol.getUsername(), sol.getMensaje()));
            for (int i=0; i < conexiones.size(); i ++ ){
                System.out.println(conexiones.get(i).getUsername() + "3");
            }
        }
    }
    
    private void enviarMensaje(ConexionUsuario c, Paquete p){
        try {
            if (c.getSocket().isConnected()){
                c.getSalida().flush();
                c.getSalida().writeObject(p);
            }else{
                conexiones.remove(c);
                c.getSalida().close();
                c.getEntrada().close();
                c.getSocket().close();
            }
        } catch (IOException ex) {
            Logger.getLogger(Servidor.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    public class Listener implements Runnable{
        private Servidor s;
        ConexionUsuario c;

        public Listener(Servidor s, ConexionUsuario c) {
            this.s = s;
            this.c = c;
        }
        @Override
        public void run() {
            while(true){
                try {
                    Paquete sol = (Paquete) c.getEntrada().readObject();
                    s.procesarSolicitud(sol,this.c);
                } catch (IOException ex) {
                    Logger.getLogger(ConexionUsuario.class.getName()).log(Level.SEVERE, null, ex);
                } catch (ClassNotFoundException ex) {
                    Logger.getLogger(ConexionUsuario.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }
    
    public void addConexion(ConexionUsuario conexion){
        conexion.setIndex(conexiones.size()+1);
        conexiones.add(conexion);
    }
    
    public class ListenerAccepts implements Runnable{
        private Servidor s;

        public ListenerAccepts(Servidor s) {
            this.s = s;
        }
        @Override
        public void run() {
            while(true){
                
                try {
                    Socket socket = s.getServer().accept();
                    ConexionUsuario conn = new ConexionUsuario(socket);
                    s.addConexion(conn);
                    new Thread( new Listener(s, conn) ).start();
                } catch (IOException ex) {
                    Logger.getLogger(Servidor.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }
    
    
}
