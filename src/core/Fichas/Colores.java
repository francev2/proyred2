/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package core.Fichas;

import java.util.ArrayList;

/**
 *
 * @author Fabian Graterol
 */
public enum Colores {

    verde {
        @Override
        public String value() {
            return "verde";
        }

        @Override
        public int[] pasillo() {
            int[] pasillo = {53,54,55,56,57,58};
            return pasillo;
        }

        @Override
        public int inicio() {
            return 13;
        }

        @Override
        public String getUrlIcon() {
            return "fichaVerde.png";
        }
    },

    amarillo {    
        @Override
        public String value() {
            return "amarillo";
        }
        
        @Override
        public int[] pasillo() {
            int[] pasillo = {59,60,61,62,63,64};
            return pasillo;
        }
        
        @Override
        public int inicio() {
            return 0;
        }

        @Override
        public String getUrlIcon() {
            return "fichaAmarilla.png";
        }
    },

    azul {
        @Override
        public String value() {
            return "azul";
        }
        
        @Override
        public int[] pasillo() {
            int[] pasillo = {65,66,67,68,69,70};
            return pasillo;
        }

        @Override
        public int inicio() {
            return 39;
        }

        @Override
        public String getUrlIcon() {
            return "fichaAzul.png";
        }
    },
    rojo {
        @Override
        public String value() {
            return "rojo";
        }
        
        @Override
        public int[] pasillo() {
            int[] pasillo = {71,72,73,74,75,76};
            return pasillo;
        }
        
        @Override
        public int inicio() {
            return 26;
        }

        @Override
        public String getUrlIcon() {
            return "fichaRoja.png";
        }

    };
    
    public abstract String value();
    
    public abstract int inicio();

    public abstract int[] pasillo();
    
    public abstract String getUrlIcon();
}
