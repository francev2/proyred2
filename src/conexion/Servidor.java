/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package conexion;
import core.Casilla;
import core.Jugador;
import core.Movimiento;
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
        this.puerto = puerto;
    }
    
    public Servidor(int puerto) throws IOException{
        this.puerto = puerto;
        partidas = new ArrayList<>();
        conexiones = new ArrayList<>();
        
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
        if ( p.getJugadores().size() < 3  )
            return "La partida no tiene jugadores suficientes";
        else{
            String user = p.getJugadores().get(0).getUsername();
            p.setTurno(user);
            
            p.setPartidaIniciada(true);
            
            if (p.isPartidaPausada())
                p.setPartidaPausada(false);
            else
                p.setInicioDePartida(true);
            
            
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
            p.setPartidaPausada(true);
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
        p.setTipo(Tipo.LOGIN);
        
        for (int i = 0; i < partidas.size(); i ++){
            System.out.println(partidas.get(i).getNombre());
            for (int j = 0; j < partidas.get(i).getJugadores().size(); j++){
                System.out.println(partidas.get(i).getJugadores().get(j).getUsername() + " == " + username );
                
                if (partidas.get(i).getJugadores().get(j).getUsername().equalsIgnoreCase(username)){
                    p.setError(false);
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
            if (!partida.getJugadores().get(i).isFinalizo())
                if (partida.getJugadores().get(i).getUsername().equalsIgnoreCase(currentUsername)){
                    if (i == 3)
                    {
                        partida.setTurno(partida.getJugadores().get(0).getUsername());
                        return partida.getJugadores().get(0).getUsername();
                    }else{
                        partida.setTurno(partida.getJugadores().get(i+1).getUsername());
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
        
        partida.setTurno(username);
        return username;
    }

    private void procesarSolicitud(Paquete sol, ConexionUsuario c) {
        if (sol.getTipo() == Tipo.LOGIN){
            Paquete p = this.loginJugador(sol.getUsername(), sol.getMensaje());            
            enviarMensaje(c, p);
            
            if (p.isError()){
//                Borrar conexion
                System.out.println(sol.getUsername()+"10");
            }else{
                c.setUsername(sol.getUsername());
                Partida pa = getPartidaByUsername(sol.getUsername());
                
                    
                enviarMensajePartida(pa, new Paquete(sol.getUsername(), Tipo.COLOR, false, pa.getJugadorByUsername(sol.getUsername()).getColores().value()));
                
                for(int i = 0; i < conexiones.size(); i++){
                    if (!conexiones.get(i).getUsername().isEmpty())
                        if (pa.getJugadorByUsername(conexiones.get(i).getUsername()) == null) {
                        } else {
                            enviarMensaje(c, new Paquete(conexiones.get(i).getUsername(), Tipo.COLOR, false, pa.getJugadorByUsername(conexiones.get(i).getUsername()).getColores().value() ));
                        }
                }
                
                
                
                //mandar el estado del tablero al jugador que acaba de llegar
                if (pa.isPartidaIniciada()){
                    
                    for (int i = 0; i < pa.getJugadores().size(); i++){
                        for (int j = 0; j < 4; j++){
                            try {
                                Casilla casilla = pa.getTablero().getCasilla(pa.getJugadores().get(i).getFicha(j).getCasilla()-1);
                                enviarMensaje(c, new Paquete(sol.getUsername(), Tipo.MOVIMIENTO, false, "Estdo del tablero", new Movimiento(false, casilla, ((i-1)*4)+(j-1) ) ));
                            }catch(Exception e){
                                e.printStackTrace();
                            }
                        }
                    }
                    
                    enviarMensaje(c, new Paquete(pa.getTurno(), Tipo.TURNO, false));
                }
                
                
                
                if (pa.isPartidaTerminada()){
                    String ganador = getPartidaByUsername(sol.getUsername()).getFinalizaron().get(0).getUsername();
                    enviarMensajePartida(getPartidaByUsername(sol.getUsername()), new Paquete(sol.getUsername(), Tipo.MOVIMIENTO, true, "Partida finalizada. GANADOR " +ganador , new Movimiento(false, null)));
                }
                
                    
                System.out.println(sol.getUsername()+"6");
            }
            
            
            for (int i=0; i < conexiones.size(); i ++ ){
                System.out.println(conexiones.get(i).getUsername() + "3");
            }
        }else if (sol.getTipo() == Tipo.DADO){
            
            if (getPartidaByUsername(sol.getUsername()).isPartidaTerminada()){
                String ganador = getPartidaByUsername(sol.getUsername()).getFinalizaron().get(0).getUsername();
                enviarMensaje(c, new Paquete(sol.getUsername(), Tipo.MOVIMIENTO, true, "Partida finalizada. GANADOR " +ganador , new Movimiento(false, null)));
                return;
            }
            
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
//                Movimiento esperar mover logica etc
                    String username = sol.getUsername();
                    Partida pa = getPartidaByUsername(sol.getUsername());
                    int dado = Integer.parseInt(sol.getMensaje());
                    int numFicha = sol.getMovimiento().getNumFicha();
                    
//                  comprueba si alguna de las fichas del jugador esta fuera del home
                    if(pa.inHome(username)){
                        
//                      comprueba si la ficha esta en home y si el dado es 6 para poder salir
                        if (pa.fichaInHome(username, numFicha) && dado == 6){
//                          La ficha sale de home
                            getPartidaByUsername(sol.getUsername()).liberarFicha(username, numFicha);

//                          le toca turno otra vez
                            enviarMensaje(c, new Paquete(username, Tipo.TURNO, true, "Vuelve a lanzar"));
                            enviarMensaje(c, new Paquete(username, Tipo.TURNO, false));
                            
                            
//                      el dado no es 6 pero si tiene fichas para mover menos la que seleccionó 
                        }else if ( pa.fichaInHome(username, numFicha) && dado != 6 ) {
                            
//                           Se le pide que selecione otra ficha
                            enviarMensaje(c, new Paquete(username, Tipo.MOVIMIENTO, true, dado+"", new Movimiento(true, "Seleciona otra ficha")));
                            
                            
                            
//                      La ficha no esta en HOME y puede moverse
                        }else if (!pa.fichaInHome(username, numFicha)) {
                            
//                          Obtiene la casilla donde cae la ficha sin modificar ningún estado
                            int casilla = pa.obtenerCasillaMovimiento(username, numFicha, dado);
                            
                            if (casilla == -1){
//                              La casilla ya está en la meta
                                enviarMensaje(c, new Paquete(username, Tipo.MOVIMIENTO, true, dado+"", new Movimiento(true, "Esta ficha ya esta en meta, Seleciona otra ficha")));
                                
                            }else if (casilla == 100){
//                              La casilla llego a la meta
                                Casilla callo = getPartidaByUsername(sol.getUsername()).moverFicha(username, numFicha, dado);
                                enviarMensajePartida(pa, new Paquete(username, Tipo.MOVIMIENTO, false, pa.getPosicionJugador(username)+"",new Movimiento(false, callo, ((pa.getPosicionJugador(username)-1)*4)+(numFicha-1))));
                                
                                // comprobar si el jugador Tiene todas sus fchas en meta
                                if (pa.finalizo(sol.getUsername())){
                                    getPartidaByUsername(username).addGanador(pa.getJugadorByUsername(username));
                                    getPartidaByUsername(username).getJugadorByUsername(username).setFinalizo(true);
                                    enviarMensajePartida(pa, new Paquete(siguienteJugador(getPartidaByUsername(sol.getUsername()), username), Tipo.TURNO, false));
                                }else if (dado == 6){
                                    enviarMensaje(c, new Paquete(username, Tipo.TURNO, true, "Vuelve a lanzar"));
                                    enviarMensaje(c, new Paquete(username, Tipo.TURNO, false));
                                }else
//                                  Siguiente Jugador
                                    enviarMensajePartida(pa, new Paquete(siguienteJugador(getPartidaByUsername(sol.getUsername()), username), Tipo.TURNO, false));

//                          comprobar que la casilla esta ocupada
                            }else if (pa.getTablero().isCasillaOcupada(casilla-1)){
                                
//                              Comprobar si esta ocupada por una casilla propia
                                if(pa.getTablero().casillaPropia(pa.getPosicionJugador(username), casilla-1)){
//                                  Pedirle que seleccione otra ficha
                                    enviarMensaje(c, new Paquete(username, Tipo.MOVIMIENTO, true, dado+"", new Movimiento(true, "Seleciona otra ficha")));
                                
//                              Esta ocupada por una casilla contraria
                                }else{
//                                  Mover la ficha contraria a home
                                    int[] userDesalojado = getPartidaByUsername(sol.getUsername()).resetCasillaOcupada(casilla-1);
//                                  Encontrar la casilla de home que pertenece a esa pieza
                                    Casilla casillaHome = getPartidaByUsername(sol.getUsername()).getTablero().getCasillaFicha( ((userDesalojado[0]-1)*4)+(userDesalojado[1]-1) );
                                    enviarMensajePartida(pa, new Paquete(username, Tipo.MOVIMIENTO, false, userDesalojado[0]+"",new Movimiento(false, casillaHome, ((userDesalojado[0]-1)*4)+(userDesalojado[1]-1) )));

//                                  Mover la ficha a esa casilla
                                    Casilla callo = getPartidaByUsername(sol.getUsername()).moverFicha(username, numFicha, dado);
                                    enviarMensajePartida(pa, new Paquete(username, Tipo.MOVIMIENTO, false, pa.getPosicionJugador(username)+"",new Movimiento(false, callo, ((pa.getPosicionJugador(username)-1)*4)+(numFicha-1) )));
                                
                                    if (dado == 6){
                                        enviarMensaje(c, new Paquete(username, Tipo.TURNO, true, "Vuelve a lanzar"));
                                        enviarMensaje(c, new Paquete(username, Tipo.TURNO, false));
                                    }else
//                                      Siguiente Jugador
                                        enviarMensajePartida(pa, new Paquete(siguienteJugador(getPartidaByUsername(sol.getUsername()), username), Tipo.TURNO, false));

                                }
                                
//                          casilla no ocupada
                            }else{
                                Casilla callo = getPartidaByUsername(sol.getUsername()).moverFicha(username, numFicha, dado);
                                enviarMensajePartida(pa, new Paquete(username, Tipo.MOVIMIENTO, false, pa.getPosicionJugador(username)+"", new Movimiento(false, callo, ((pa.getPosicionJugador(username)-1)*4)+(numFicha-1))));
                                
                                if (dado == 6){
                                    enviarMensaje(c, new Paquete(username, Tipo.TURNO, true, "Vuelve a lanzar"));
                                    enviarMensaje(c, new Paquete(username, Tipo.TURNO, false));
                                }else
//                                  Siguiente Jugador
                                    enviarMensajePartida(pa, new Paquete(siguienteJugador(getPartidaByUsername(sol.getUsername()), username), Tipo.TURNO, false));
                            }
                            

                        }
                        
//                  Ninguna pieza esta fuera del home / todas las piezas estan en home
                    }else{
                        if (dado == 6){
//                            La ficha sale de home
                            getPartidaByUsername(sol.getUsername()).liberarFicha(username, numFicha);
//                            le toca turno otra vez
                            enviarMensaje(c, new Paquete(username, Tipo.TURNO, true, "Vuelve a lanzar"));
                            enviarMensaje(c, new Paquete(username, Tipo.TURNO, false));
                            
                            
//                      No sacó 6 y por lo tanto no puede moverse, turno siguiente jugador
                        }else{
                            enviarMensaje(c, new Paquete(sol.getUsername(), Tipo.MOVIMIENTO, true, "No puedes mover la ficha", new Movimiento(false, null)) );
                            enviarMensajePartida(pa, new Paquete(siguienteJugador(getPartidaByUsername(username), username), Tipo.TURNO, false));
                        }
                    }
                                       
                    
            }
            
            if (getPartidaByUsername(sol.getUsername()).partidaFinalizada()){
                String ganador = getPartidaByUsername(sol.getUsername()).getFinalizaron().get(0).getUsername();
                enviarMensajePartida(getPartidaByUsername(sol.getUsername()), new Paquete(sol.getUsername(), Tipo.MOVIMIENTO, true, "GANADOR " +ganador , new Movimiento(false, null)));
            }
            
            
        }else if (sol.getTipo() == Tipo.TURNO){
            
            if (getPartidaByUsername(sol.getUsername()).isPartidaTerminada()){
                String ganador = getPartidaByUsername(sol.getUsername()).getFinalizaron().get(0).getUsername();
                enviarMensaje(c, new Paquete(sol.getUsername(), Tipo.MOVIMIENTO, true, "Partida finalizada. GANADOR " +ganador , new Movimiento(false, null)));
                return;
            }
            
            enviarMensajePartida(getPartidaByUsername(sol.getUsername()), new Paquete(siguienteJugador(getPartidaByUsername(sol.getUsername()), sol.getUsername()), Tipo.TURNO, false));
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
                } catch (IOException | ClassNotFoundException ex) {
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
