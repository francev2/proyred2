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
        boolean user = false;
        
        for (int i = 0; i < partidas.size(); i ++)
            if (partidas.get(i).getNombre().equalsIgnoreCase(sala)){
                partida = partidas.get(i);
                for (int j = 0; j < partidas.get(i).getJugadores().size(); j++){
                    if ( partidas.get(i).getJugadores().get(j).getUsername().equalsIgnoreCase(username) ){
                        user = true;
                        break;
                    }
                }
                break;
            }
                
                
        if (partida == null)
            return "La sala selecionada no esta creada";
        else{
            if ( user ){
                return "El username ya esta asignado";
            }
            
            partida.addJuagador(username, password, nombre, apellido);
            return "Jugador creado exitosamente";
        }
    }
    
    
    public String iniciarPartida(Partida p){
        if ( p.getJugadores().size() < 4 )
            return "La partida no tiene jugadores suficientes";
        else{
            String user = p.getJugadores().get(0).getUsername();
            p.setTurno(user);
            p.setInicioDePartida(true);
            p.setPartidaIniciada(true);
            
            
            Paquete paquete = new Paquete();
            paquete.setTipo(Tipo.INICIODEPARTIDA);
            paquete.setMensaje("Partida iniciada");
            paquete.setUsername(user);
            paquete.setError(false);
            enviarMensajePartida(p, paquete);
            return "Partida iniciada";
        }   
    }
    
    public String pausarPartida(Partida p){
        if (!p.isPartidaIniciada()){
            return "La partida no ha sido iniciada";
        }else if (p.getTurno() == null ){
            return " La partida ya esta pausada";
        }else{
            p.setPartidaIniciada(true);
            Paquete paquete = new Paquete();
            paquete.setTipo(Tipo.PAUSARPARTIDA);
            paquete.setError(false);
            enviarMensajePartida(p, paquete);
            return "Partida pausada";
        }   
    }
    
    public String reiniciarPartida(Partida p){
        if (!p.isPartidaIniciada()){
            return "La partida no ha sido iniciada";
        }else if(p.isPartidaIniciada()) {
            Paquete paquete = new Paquete();
            paquete.setTipo(Tipo.REINICIARPARTIDA);
            paquete.setUsername(p.getTurno());
            paquete.setError(false);
            enviarMensajePartida(p, paquete);
            return "Partida pausada";
        }else {
            return "La partida no esta pausada";
        }   
    }
    
    
    
//    Obtiene la partida 
    public Partida getPartidaByName(String name){
        for (int i = 0; i < partidas.size(); i++){
            if (partidas.get(i).getNombre().equalsIgnoreCase(name))
                return partidas.get(i);
        }
        
        return null;
    }
    
//    Obtiene la partida por el username del jugador
    public Partida getPartidaByUsername(String username){
        for (int i = 0; i < partidas.size(); i++){
            for (int j = 0; j < partidas.get(i).getJugadores().size(); j++)
                if (partidas.get(i).getJugadores().get(j).getUsername().equalsIgnoreCase(username))
                    return partidas.get(i);
        }
        
        return null;

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
    
    private boolean setDadoInicio(Partida partida, String username, String dado, ConexionUsuario c){
        for (int i = 0; i < 4 ; i++){
            if (partida.getJugadores().get(i).getUsername().equalsIgnoreCase(username)){
                partida.getInicio()[i][0] = username;
                partida.getInicio()[i][1] = dado;
                if (i == 3 ){
                    return true;
                } else {
                    String siguienteTurno = siguienteJugador(partida, username);
                    partida.setTurno(siguienteTurno);
                    enviarMensajePartida(partida, new Paquete(siguienteTurno, Tipo.INICIODEPARTIDA, false));
                    return false;
                }
                
            }
        }
        
        return false;
    }
    
    private String siguienteJugador(Partida partida, String currentUsername){
        for (int i = 0; i < 4 ; i++){
            if (partida.getJugadores().get(i).getUsername() == currentUsername){
                if (i == 3)
                {
                    return partida.getJugadores().get(0).getUsername();
                }else{
                    
                    return partida.getJugadores().get(i+1).getUsername();
                }        
            }
        }
        
        return null;
    }
//    Devuelve el jugador que saco el mayor numero en la primera ronda para empezar la partida
    private String primero(Partida partida){
        int num = 0;
        String username = "";
        for (int i = 0; i< 4 ; i++){
            int valor = Integer.parseInt(partida.getInicio()[i][1]);
            if ( valor > num){
                num = valor;
                username = partida.getInicio()[i][0];
            }
        }
        
        return username;
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
//        }else if (sol.getTipo() == Tipo.DADO){
            
            if (getPartidaByUsername(sol.getUsername()).isInicioDePartida()){
                if (getPartidaByUsername(sol.getUsername()).getInicio()[3][1] == null){
                    boolean fin = setDadoInicio(getPartidaByUsername(sol.getUsername()), sol.getUsername(), sol.getMensaje(), c);
                    if (fin) {
                        getPartidaByUsername(sol.getUsername()).setInicioDePartida(false);
                        String user = primero(getPartidaByUsername(sol.getUsername()));
                        enviarMensajePartida(getPartidaByUsername(sol.getUsername()), new Paquete(user, Tipo.TURNO, false));
                    }
                }
            } else if (getPartidaByUsername(sol.getUsername()).isPartidaPausada()) {
                enviarMensaje(c, new Paquete(sol.getUsername(), Tipo.MOVIMIENTO, true, "La partida esta pausada"));
            } else {
//                MOvimiento esperar mover logica etc
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
    
    private void enviarMensajePartida(Partida partida, Paquete p){
        try {
            for(int i = 0; i < partida.getJugadores().size(); i++){
                for (int j=0; j < conexiones.size(); j++ ){
                    if (conexiones.get(j).getUsername().equalsIgnoreCase(partida.getJugadores().get(i).getUsername())){
                        conexiones.get(j).getSalida().flush();
                        conexiones.get(j).getSalida().writeObject(p);
                    }
                }
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
