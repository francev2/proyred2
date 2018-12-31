/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package core;


/**
 *
 * @author Fabian Graterol
 */
public class Ficha {
    private int casilla;
    private Boolean home;   //inidica si a salido o no de home
    private Boolean meta;   // indica si la pieza llego a la meta
    private String color;
    
    public Ficha(String color){
        this.casilla = -1;
        this.color = color;
        this.meta = false;
        this.home = true;
    }
    
    public void setCasilla(int casilla){ this.casilla = casilla;}
    
    public int getCasilla(){ return this.casilla;}
    
    public void setHome(Boolean home){ this.home = home;}
    
    public Boolean getHome(){ return this.home;}
    
    public void setMeta(Boolean meta){ this.meta = meta;}
    
    public Boolean getMeta(){ return this.meta;}
    
    public void setColor(String color){ this.color = color; }
    
    public String getColor(){ return this.color;}

    void moverFicha(int dado) {
        this.casilla += dado;
        if (this.casilla == 76){
            this.casilla-=76;
        }
        System.out.println("posicion: " + this.casilla);
    }
}
