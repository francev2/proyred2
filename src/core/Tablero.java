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
public class Tablero {
    Casilla[] casillas = new Casilla[76];
    Casilla[] casillaFichas = new Casilla[16];
    int[][] ocupadas = new int[16][3];
    private int turno;

    public int getTurno() {
        return turno;
    }

    public void setTurno(int turno) {
        this.turno = turno;
    }
    
    
//    public int getIdJugador(int casilla){
//        for (int i = 0; i < 16; i++){
//            if (ocupadas[i][1] == casilla)
//                return ocupadas[i][1];
//        }
//        
//        return -1;
//    }
    
    public Tablero(){
        inicializarCasillas();
    }
    
    public int lanzarDado(){
        return (int) (Math.random() * 6) + 1;
    }
    
//  devuelve true si la casilla esta ocupada por una casilla propia, y false si la casilla esta ocupada por otro jugador 
    public boolean casillaPropia(int idJugador, int numCasilla){
        boolean propia = false;
        
        for (int i = 0; i < 16; i++)
            if (this.ocupadas[i][0] == idJugador && this.ocupadas[i][1] == numCasilla)
                propia = true;
        
        return propia;
    } 
    
    public boolean isCasillaOcupada(int numCasilla){
        return this.casillas[numCasilla].isOcupada();
    }
    
//    idJugador es la posicion en el arraylist de jugadores de la partida
    public boolean setCasilla(int numCasilla, int idJugador, int numFicha){
        int ocupada = ((idJugador-1)*4)+numFicha-1;
        this.casillas[numCasilla].setOcupada(true);
        this.ocupadas[ocupada][0] = idJugador;
        this.ocupadas[ocupada][1] = numCasilla;
        this.ocupadas[ocupada][2] = numFicha;
        return true;   
    }
    
//    Devuelve un arra 
//    1: idJugador(Posicion en el array de jugadores de partida)
//    2: el numero de la ficha que estaba en la casilla
    public int[] resetCasillaOcupada(int numCasilla){
        int[] id = new int[2];
        for (int[] ocupada : ocupadas) {
            if (ocupada[1] == numCasilla) {
                this.casillas[numCasilla].setOcupada(true);
                id[0] = ocupada[0];
                id[1] = ocupada[2];
            
                ocupada[0] = -1;
                ocupada[1] = -1;
                ocupada[2] = -1;
                
                return id;
            }
        }
        
        return id;
    }
    
    private void inicializarCasillas(){
        casillas[0] = new Casilla(246, 507, false, TipoCasilla.NORMAL);
        casillas[1] = new Casilla(246, 469, false, TipoCasilla.NORMAL);
        casillas[2] = new Casilla(246, 433, false, TipoCasilla.NORMAL);
        casillas[3] = new Casilla(246, 393, false, TipoCasilla.NORMAL);
        casillas[4] = new Casilla(246, 358, false, TipoCasilla.NORMAL);
        casillas[5] = new Casilla(207, 319, false, TipoCasilla.NORMAL);
        casillas[6] = new Casilla(171, 319, false, TipoCasilla.NORMAL);
        casillas[7] = new Casilla(133, 319, false, TipoCasilla.NORMAL);
        casillas[8] = new Casilla(98, 319, false, TipoCasilla.NORMAL);
        casillas[9] = new Casilla(57, 319, false, TipoCasilla.NORMAL);
        casillas[10] = new Casilla(21, 319, false, TipoCasilla.NORMAL);
        casillas[11] = new Casilla(21, 283, false, TipoCasilla.NORMAL);
        casillas[12] = new Casilla(21, 245, false, TipoCasilla.NORMAL);
        casillas[13] = new Casilla(59, 245, false, TipoCasilla.NORMAL);
        casillas[14] = new Casilla(98, 245, false, TipoCasilla.NORMAL);
        casillas[15] = new Casilla(133, 245, false, TipoCasilla.NORMAL);
        casillas[16] = new Casilla(171, 245, false, TipoCasilla.NORMAL);
        casillas[17] = new Casilla(207, 245, false, TipoCasilla.NORMAL);
        casillas[18] = new Casilla(246, 207, false, TipoCasilla.NORMAL);
        casillas[19] = new Casilla(246, 172, false, TipoCasilla.NORMAL);
        casillas[20] = new Casilla(246, 134, false, TipoCasilla.NORMAL);
        casillas[21] = new Casilla(246, 96, false, TipoCasilla.NORMAL);
        casillas[22] = new Casilla(246, 57, false, TipoCasilla.NORMAL);
        casillas[23] = new Casilla(246, 22, false, TipoCasilla.NORMAL);
        casillas[24] = new Casilla(284, 22, false, TipoCasilla.NORMAL);
        casillas[25] = new Casilla(322, 22, false, TipoCasilla.NORMAL);
        casillas[26] = new Casilla(322, 57, false, TipoCasilla.NORMAL);
        casillas[27] = new Casilla(322, 97, false, TipoCasilla.NORMAL);
        casillas[28] = new Casilla(322, 132, false, TipoCasilla.NORMAL);
        casillas[29] = new Casilla(322, 171, false, TipoCasilla.NORMAL);
        casillas[30] = new Casilla(322, 209, false, TipoCasilla.NORMAL);
        casillas[31] = new Casilla(359, 246, false, TipoCasilla.NORMAL);
        casillas[32] = new Casilla(396, 246, false, TipoCasilla.NORMAL);
        casillas[33] = new Casilla(434, 246, false, TipoCasilla.NORMAL);
        casillas[34] = new Casilla(472, 246, false, TipoCasilla.NORMAL);
        casillas[35] = new Casilla(509, 246, false, TipoCasilla.NORMAL);
        casillas[36] = new Casilla(547, 246, false, TipoCasilla.NORMAL);
        casillas[37] = new Casilla(547, 284, false, TipoCasilla.NORMAL);
        casillas[38] = new Casilla(547, 321, false, TipoCasilla.NORMAL);
        casillas[39] = new Casilla(510, 321, false, TipoCasilla.NORMAL);
        casillas[40] = new Casilla(472, 321, false, TipoCasilla.NORMAL);
        casillas[41] = new Casilla(435, 321, false, TipoCasilla.NORMAL);
        casillas[42] = new Casilla(396, 321, false, TipoCasilla.NORMAL);
        casillas[43] = new Casilla(359, 321, false, TipoCasilla.NORMAL);
        casillas[44] = new Casilla(322, 359, false, TipoCasilla.NORMAL);
        casillas[45] = new Casilla(322, 396, false, TipoCasilla.NORMAL);
        casillas[46] = new Casilla(322, 434, false, TipoCasilla.NORMAL);
        casillas[47] = new Casilla(322, 472, false, TipoCasilla.NORMAL);
        casillas[48] = new Casilla(322, 509, false, TipoCasilla.NORMAL);
        casillas[49] = new Casilla(322, 546, false, TipoCasilla.NORMAL);
        casillas[50] = new Casilla(284, 546, false, TipoCasilla.NORMAL);
        casillas[51] = new Casilla(246, 546, false, TipoCasilla.NORMAL);
        
        casillas[52] = new Casilla(58, 284, false, TipoCasilla.PASILLO);
        casillas[53] = new Casilla(98, 284, false, TipoCasilla.PASILLO);
        casillas[54] = new Casilla(135, 284, false, TipoCasilla.PASILLO);
        casillas[55] = new Casilla(175, 284, false, TipoCasilla.PASILLO);
        casillas[56] = new Casilla(212, 284, false, TipoCasilla.PASILLO);
        casillas[57] = new Casilla(248, 284, false, TipoCasilla.META);
        
        casillas[58] = new Casilla(286, 508, false, TipoCasilla.PASILLO);
        casillas[59] = new Casilla(286, 472, false, TipoCasilla.PASILLO);
        casillas[60] = new Casilla(286, 436, false, TipoCasilla.PASILLO);
        casillas[61] = new Casilla(286, 396, false, TipoCasilla.PASILLO);
        casillas[62] = new Casilla(286, 358, false, TipoCasilla.PASILLO);
        casillas[63] = new Casilla(286, 321, false, TipoCasilla.META);
        
        casillas[64] = new Casilla(513, 284, false, TipoCasilla.PASILLO);
        casillas[65] = new Casilla(476, 284, false, TipoCasilla.PASILLO);
        casillas[66] = new Casilla(438, 284, false, TipoCasilla.PASILLO);
        casillas[67] = new Casilla(400, 284, false, TipoCasilla.PASILLO);
        casillas[68] = new Casilla(365, 284, false, TipoCasilla.PASILLO);
        casillas[69] = new Casilla(323, 284, false, TipoCasilla.META);
        
        casillas[70] = new Casilla(286, 57, false, TipoCasilla.PASILLO);
        casillas[71] = new Casilla(286, 94, false, TipoCasilla.PASILLO);
        casillas[72] = new Casilla(286, 133, false, TipoCasilla.PASILLO);
        casillas[73] = new Casilla(286, 170, false, TipoCasilla.PASILLO);
        casillas[74] = new Casilla(286, 208, false, TipoCasilla.PASILLO);
        casillas[75] = new Casilla(286, 246, false, TipoCasilla.META);
        
        
        
        casillaFichas[0] = new Casilla(77, 452, false, TipoCasilla.HOME);
        casillaFichas[1] = new Casilla(116, 414, false, TipoCasilla.HOME);
        casillaFichas[2] = new Casilla(155, 452, false, TipoCasilla.HOME);
        casillaFichas[3] = new Casilla(116, 489, false, TipoCasilla.HOME);
        casillaFichas[4] = new Casilla(78, 112, false, TipoCasilla.HOME);
        casillaFichas[5] = new Casilla(118, 75, false, TipoCasilla.HOME);
        casillaFichas[6] = new Casilla(156, 112, false, TipoCasilla.HOME);
        casillaFichas[7] = new Casilla(118, 149, false, TipoCasilla.HOME);
        casillaFichas[8] = new Casilla(416, 111, false, TipoCasilla.HOME);
        casillaFichas[9] = new Casilla(452, 75, false, TipoCasilla.HOME);
        casillaFichas[10] = new Casilla(491, 111, false, TipoCasilla.HOME);
        casillaFichas[11] = new Casilla(452, 151, false, TipoCasilla.HOME);
        casillaFichas[12] = new Casilla(416, 450, false, TipoCasilla.HOME);
        casillaFichas[13] = new Casilla(457, 413, false, TipoCasilla.HOME);
        casillaFichas[14] = new Casilla(491, 450, false, TipoCasilla.HOME);
        casillaFichas[15] = new Casilla(452, 488, false, TipoCasilla.HOME);
    }

    public Casilla getCasilla(int numCasilla) {
        return this.casillas[numCasilla];
    }
    
    
    public Casilla getCasillaFicha(int num) {
        return this.casillaFichas[num];
    }
}


