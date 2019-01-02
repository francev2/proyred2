/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ludo;
import conexion.Cliente;
import conexion.Servidor;
/**
 *
 * @author Francisco
 */
public class Ludo {
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
       //Servidor servidor = new Servidor(9000);
       //Cliente cliente = new Cliente("192.168.1.4");
       System.out.println("'continuará ...'");
       
       Inicio i = new Inicio();
       i.setVisible(true);

//        Pantalla p = new Pantalla();
//        p.setVisible(true);
    }
    
}
