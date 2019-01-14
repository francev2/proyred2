/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package core.Fichas;


/**
 *
 * @author Fabian Graterol
 */
public class Ficha {
    protected int casilla;
    protected int movimientos;
    protected int inicio;
    protected boolean meta;
    protected Boolean home;   //inidica si a salido o no de home
    protected Boolean estaEnMeta;   // indica si la pieza llego a la meta
    protected Colores color;
    
    public Ficha(Colores color){
        this.estaEnMeta = false;
        this.meta = false;
        this.home = true;
        this.movimientos=0;
        this.color = color;
        this.inicio=this.color.inicio();
        this.casilla = this.inicio;
    }

    public Colores getColor() {
        return color;
    }

    public void setColor(Colores color) {
        this.color = color;
    }
    
    public void setCasilla(int casilla){ this.casilla = casilla;}
    
    public int getCasilla(){ return this.casilla;}
    
    public void setMovimiento(int movimientos){ this.movimientos = movimientos;}
    
    public int getMovimiento(){ return this.movimientos;}
    
    public void setHome(Boolean home){ this.home = home;}
    
    public Boolean getHome(){ return this.home;}
    
    public void setEstaEnMeta(Boolean meta){ this.estaEnMeta = meta;}
    
    public Boolean getEstaEnMeta(){ return this.estaEnMeta;}

    public void moverFicha(int dado) {
        if (meta){
            this.moverAMeta(dado);
        } else{
            this.movimientos += dado;
            if (this.movimientos == 52){
                this.movimientos-=52;
                this.meta = true;
            }
            if (this.movimientos >= 52){
                this.movimientos-=52;
                this.meta = true;
                this.moverAMeta();
            }else{
                this.casilla += dado;
                if (this.casilla >= 52){
                    this.casilla-=52;
                }
            }
        }
        System.out.println("Movimientos: " + this.movimientos + "posicion: " + this.casilla);
    }
    
//  Devuelve la casilla donde va a caer la ficha
    public int casillaPosible(int dado) {
        
        int movimiento = this.movimientos + dado;
        
        if (estaEnMeta){
            return -1;
        } else if (meta) {
            int pasillo[] = color.pasillo();
            if (movimiento == 5){
                return 100;
            } else if (movimiento > 5){
                int aux = movimiento - 5;
                movimiento = 5 - aux;
                return pasillo[movimiento];
            }else{
                return pasillo[movimiento];
            }
        }else{
            
            if (movimiento <= 52 ){
                if (this.casilla + dado >= 52){
                    return this.casilla + dado - 52;
                }else{
                    return this.casilla + dado;
                }
                
            } else {
                movimiento -= 52 +1;
                int pasillo[] = color.pasillo();
                if (movimiento == 5){
                    return 100;
                } else if (movimiento > 5){
                    int aux = movimiento - 5;
                    movimiento = 5 - aux;
                    return pasillo[movimiento];
                }else{
                    return pasillo[movimiento];
                }
            }
        }
        
//        System.out.println("Movimientos: " + this.movimientos + "posicion: " + this.casilla);
    }
    
    public String getUrlIcon(){
        return this.color.getUrlIcon();
    }

    protected void moverAMeta(int dado){
        int pasillo[] = color.pasillo();
        this.movimientos+=dado;
        if (this.movimientos == 5){
            this.estaEnMeta = true;
        } else if (this.movimientos > 5){
            int aux = this.movimientos - 5;
            this.movimientos = 5 - aux;
        } 
        this.casilla = pasillo[this.movimientos];
    }

    protected void moverAMeta(){
        int pasillo[] = color.pasillo();
        this.movimientos = this.movimientos - 1;
        this.casilla = pasillo[this.movimientos];
    }

    public void moveToHome() {
        this.movimientos = 0;
        this.casilla = 0;
        this.casilla = this.inicio;
        this.home = true;
    }
}
