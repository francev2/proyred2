/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ludo;

import conexion.Cliente;
import conexion.Paquete;
import conexion.Tipo;
import core.Tablero;
import java.awt.Color;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

/**
 *
 * @author Francisco
 */
public class Pantalla extends javax.swing.JFrame {

    private Cliente cliente;
    private static final String IMG_PATH = "/assets/tablero.png";
    private JLabel tableroFondo ;
    
    private JLabel userRojo ;
    private JLabel userAzul ;
    private JLabel userVerde ;
    private JLabel userAmarillo ;
    
    private JLabel user ;
    private Tablero tablero;
    private int tamañoTablero = 600;
    private JLabel dadoFondo;
    
    
    private JLabel[] fichas = new JLabel[16];
    /**
     * Creates new form Pantalla
     */
    public Pantalla(Cliente cliente) {
        initComponents();
        this.cliente = cliente;
        cliente.setPantalla(this);
        
        dadoPanel.setVisible(false);
        
        this.setSize(1256, 676);
        
        ImageIcon icon = new ImageIcon(getClass().getResource(IMG_PATH));
        Image image = icon.getImage();
        icon = new ImageIcon(image.getScaledInstance(tamañoTablero, tamañoTablero, Image.SCALE_DEFAULT));
        tableroFondo  = new JLabel(icon);
        tableroFondo.setSize(tamañoTablero, tamañoTablero);
        tableroFondo.setVisible(true);
         
        userAmarillo = new JLabel();
        userAmarillo.setText("userAmarillo");
        userAmarillo.setVisible(true);
        userAzul = new JLabel();
        userAzul.setText("userAzul");
        userAzul.setVisible(true);
        userRojo = new JLabel();
        userRojo.setText("userRojo");
        userRojo.setVisible(true);
        userVerde = new JLabel();
        userVerde.setText("userVerde");
        userVerde.setVisible(true);
        
        panelTablero.setSize(1000, 1000);
      
        
        inicializarFichas();
        panelTablero.add(userAmarillo);
        panelTablero.add(userAzul);
        panelTablero.add(userVerde);
        panelTablero.add(userRojo);
        panelTablero.add(tableroFondo);
        panelTablero.setVisible(true);
        
        
        icon = new ImageIcon(getClass().getResource("/assets/MoverPieza.png"));
        image = icon.getImage();
        icon = new ImageIcon(image.getScaledInstance(72, 65, Image.SCALE_DEFAULT));
        moverFicha.setIcon(icon);
        
        userAmarillo.setBounds(12, 558, 60, 30);
        userVerde.setBounds(12, 12, 60, 30);
        userRojo.setBounds(528, 12, 60, 30);
        userAzul.setBounds(528, 558, 60, 30);
        
        mensaje.setBackground(Color.WHITE);
        dadoButton.setBackground(Color.LIGHT_GRAY);
        
        dadoFondo  = new JLabel();
        dadoFondo.setSize(180, 180);
        dadoPanel.add(dadoFondo);
        
        
        habilitarDadoButton(false);
    }
    
    public void habilitarDadoButton(boolean habilitar){
            this.dadoButton.enableInputMethods(habilitar);
    }
    
    public void mostrarMensaje(String mensaje){
        this.mensaje.setText(mensaje);
    }
    
    public void moverFicha(int numFicha, int x, int y){
        this.fichas[numFicha].setBounds(x, y, 60, 30);
    }
    
    public void setColorJugador(String color, String username){
        ImageIcon icon ;
        Image image ;
        if (color.equalsIgnoreCase("verde")){
            
            if (username.equalsIgnoreCase("Tú")){
                icon = new ImageIcon(getClass().getResource("/assets/fichaVerde.png"));
                image = icon.getImage();
                icon = new ImageIcon(image.getScaledInstance(30, 60, Image.SCALE_DEFAULT));
                ficha1.setIcon(icon);
                ficha2.setIcon(icon);
                ficha3.setIcon(icon);
                ficha4.setIcon(icon);
            }
            userVerde.setText(username);
            
        }else if (color.equalsIgnoreCase("amarillo")){
            
            if (username.equalsIgnoreCase("Tú")){
                icon = new ImageIcon(getClass().getResource("/assets/fichaAmarilla.png"));
                image = icon.getImage();
                icon = new ImageIcon(image.getScaledInstance(30, 60, Image.SCALE_DEFAULT));
                ficha1.setIcon(icon);
                ficha2.setIcon(icon);
                ficha3.setIcon(icon);
                ficha4.setIcon(icon);
            }
            
            userAmarillo.setText(username);
            
        }else if (color.equalsIgnoreCase("rojo")){
            
            if (username.equalsIgnoreCase("Tú")){
                icon = new ImageIcon(getClass().getResource("/assets/fichaRojo.png"));
                image = icon.getImage();
                icon = new ImageIcon(image.getScaledInstance(30, 60, Image.SCALE_DEFAULT));
                ficha1.setIcon(icon);
                ficha2.setIcon(icon);
                ficha3.setIcon(icon);
                ficha4.setIcon(icon);
            }
            
            userRojo.setText(username);
            
        }else if (color.equalsIgnoreCase("azul")){
            
            if (username.equalsIgnoreCase("Tú")){
                icon = new ImageIcon(getClass().getResource("/assets/fichaAzul.png"));
                image = icon.getImage();
                icon = new ImageIcon(image.getScaledInstance(30, 60, Image.SCALE_DEFAULT));
                ficha1.setIcon(icon);
                ficha2.setIcon(icon);
                ficha3.setIcon(icon);
                ficha4.setIcon(icon);
            }
            userAzul.setText(username);
        }
    }
    
    public void mostrarDado(int num){
        
        String dadoTexturaURL = "/assets/"+num+".png";
        ImageIcon icon = new ImageIcon(getClass().getResource(dadoTexturaURL));
        Image image = icon.getImage();
        icon = new ImageIcon(image.getScaledInstance(180, 180, Image.SCALE_DEFAULT));
        
        dadoFondo.setIcon(icon);
        
        dadoPanel.setVisible(true);
        
        
    }
    

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panelTablero = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        dadoButton = new javax.swing.JLabel();
        mensaje = new java.awt.Label();
        dadoPanel = new javax.swing.JPanel();
        ficha4 = new javax.swing.JLabel();
        ficha3 = new javax.swing.JLabel();
        ficha2 = new javax.swing.JLabel();
        ficha1 = new javax.swing.JLabel();
        pasarTurno = new javax.swing.JButton();
        moverFicha = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setPreferredSize(new java.awt.Dimension(1100, 700));

        panelTablero.setBackground(new java.awt.Color(255, 0, 102));
        panelTablero.setRequestFocusEnabled(false);
        panelTablero.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                panelTableroMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout panelTableroLayout = new javax.swing.GroupLayout(panelTablero);
        panelTablero.setLayout(panelTableroLayout);
        panelTableroLayout.setHorizontalGroup(
            panelTableroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 600, Short.MAX_VALUE)
        );
        panelTableroLayout.setVerticalGroup(
            panelTableroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        dadoButton.setBackground(new java.awt.Color(102, 255, 255));
        dadoButton.setFont(new java.awt.Font("Tahoma", 0, 22)); // NOI18N
        dadoButton.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        dadoButton.setText("LANZAR");
        dadoButton.setToolTipText("");
        dadoButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                dadoButtonMouseClicked(evt);
            }
        });

        mensaje.setBackground(new java.awt.Color(255, 255, 255));
        mensaje.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        mensaje.setFont(new java.awt.Font("DialogInput", 1, 18)); // NOI18N

        javax.swing.GroupLayout dadoPanelLayout = new javax.swing.GroupLayout(dadoPanel);
        dadoPanel.setLayout(dadoPanelLayout);
        dadoPanelLayout.setHorizontalGroup(
            dadoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 180, Short.MAX_VALUE)
        );
        dadoPanelLayout.setVerticalGroup(
            dadoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 180, Short.MAX_VALUE)
        );

        ficha4.setText("ficha4");
        ficha4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                ficha4MouseClicked(evt);
            }
        });

        ficha3.setText("ficha3");
        ficha3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                ficha3MouseClicked(evt);
            }
        });

        ficha2.setText("ficha2");
        ficha2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                ficha2MouseClicked(evt);
            }
        });

        ficha1.setText("ficha1");
        ficha1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                ficha1MouseClicked(evt);
            }
        });

        pasarTurno.setText("Terminar turno");
        pasarTurno.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                pasarTurnoActionPerformed(evt);
            }
        });

        moverFicha.setText("Mover Ficha");
        moverFicha.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                moverFichaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(mensaje, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(dadoButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(208, Short.MAX_VALUE)
                .addComponent(dadoPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(202, 202, 202))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(54, 54, 54)
                .addComponent(ficha1, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(28, 28, 28)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(ficha2, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(ficha4)
                        .addGap(30, 30, 30)
                        .addComponent(ficha3, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(pasarTurno, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(moverFicha, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(90, 90, 90))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(mensaje, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(135, 135, 135)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(ficha3, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(ficha1, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(108, 108, 108)
                                .addComponent(moverFicha)
                                .addGap(64, 64, 64)
                                .addComponent(pasarTurno)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 68, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(ficha2, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(34, 34, 34)
                        .addComponent(ficha4, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(36, 36, 36)))
                .addComponent(dadoPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(dadoButton, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(panelTablero, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(46, 46, 46)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(panelTablero, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 14, Short.MAX_VALUE)))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void panelTableroMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panelTableroMouseClicked
        System.out.println("X: " + evt.getX() + " -- Y: " + evt.getY());
    }//GEN-LAST:event_panelTableroMouseClicked

    private void dadoButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_dadoButtonMouseClicked
        cliente.lanzarDado();

//        int i = (int)(1+(Math.random()*6));
//        System.out.println(i);
//        mostrarDado(i);
    }//GEN-LAST:event_dadoButtonMouseClicked

    private void ficha1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_ficha1MouseClicked
        cliente.seleccionarFicha(1);
    }//GEN-LAST:event_ficha1MouseClicked

    private void ficha2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_ficha2MouseClicked
        // TODO add your handling code here:
        cliente.seleccionarFicha(2);
    }//GEN-LAST:event_ficha2MouseClicked

    private void ficha3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_ficha3MouseClicked
        // TODO add your handling code here:
        cliente.seleccionarFicha(3);
    }//GEN-LAST:event_ficha3MouseClicked

    private void ficha4MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_ficha4MouseClicked
        // TODO add your handling code here:
        cliente.seleccionarFicha(4);
    }//GEN-LAST:event_ficha4MouseClicked

    private void pasarTurnoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_pasarTurnoActionPerformed
        // TODO add your handling code here:
        cliente.pasarTurno();
    }//GEN-LAST:event_pasarTurnoActionPerformed

    private void moverFichaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_moverFichaActionPerformed
        // TODO add your handling code here:
        cliente.enviarFicha();
    }//GEN-LAST:event_moverFichaActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel dadoButton;
    private javax.swing.JPanel dadoPanel;
    private javax.swing.JLabel ficha1;
    private javax.swing.JLabel ficha2;
    private javax.swing.JLabel ficha3;
    private javax.swing.JLabel ficha4;
    private javax.swing.JPanel jPanel1;
    private java.awt.Label mensaje;
    private javax.swing.JButton moverFicha;
    private javax.swing.JPanel panelTablero;
    private javax.swing.JButton pasarTurno;
    // End of variables declaration//GEN-END:variables

    private void inicializarFichas() {
        ImageIcon icon ;
        Image image;
        
        icon = new ImageIcon(getClass().getResource("/assets/fichaAmarilla.png"));
        image = icon.getImage();
        icon = new ImageIcon(image.getScaledInstance(20, 38, Image.SCALE_DEFAULT));
        fichas[0] = new JLabel();
        fichas[0].setSize(20, 38);
        fichas[0].setIcon(icon);
        fichas[0].setBounds(77, 452, 20, 38);
        fichas[0].setVisible(true);
        fichas[1] = new JLabel();
        fichas[1].setSize(20, 38);
        fichas[1].setIcon(icon);
        fichas[1].setBounds(116, 414, 20, 38);
        fichas[1].setVisible(true);
        fichas[2] = new JLabel();
        fichas[2].setSize(20, 38);
        fichas[2].setIcon(icon);
        fichas[2].setBounds(155, 452, 20, 38);
        fichas[2].setVisible(true);
        fichas[3] = new JLabel();
        fichas[3].setSize(20, 38);
        fichas[3].setIcon(icon);
        fichas[3].setBounds(116, 489, 20, 38);
        fichas[3].setVisible(true);
        
        icon = new ImageIcon(getClass().getResource("/assets/fichaVerde.png"));
        image = icon.getImage();
        icon = new ImageIcon(image.getScaledInstance(20, 38, Image.SCALE_DEFAULT));
        fichas[4] = new JLabel();
        fichas[4].setBounds(78, 112, 20, 38);
        fichas[4].setIcon(icon);
        fichas[4].setVisible(true);
        fichas[5] = new JLabel();
        fichas[5].setSize(20, 38);
        fichas[5].setIcon(icon);
        fichas[5].setBounds(118, 75, 20, 38);
        fichas[5].setVisible(true);
        fichas[6] = new JLabel();
        fichas[6].setSize(20, 38);
        fichas[6].setIcon(icon);
        fichas[6].setBounds(156, 112, 20, 38);
        fichas[6].setVisible(true);
        fichas[7] = new JLabel();
        fichas[7].setSize(20, 38);
        fichas[7].setIcon(icon);
        fichas[7].setBounds(118, 149, 20, 38);
        fichas[7].setVisible(true);
        
        icon = new ImageIcon(getClass().getResource("/assets/fichaRoja.png"));
        image = icon.getImage();
        icon = new ImageIcon(image.getScaledInstance(20, 38, Image.SCALE_DEFAULT));
        fichas[8] = new JLabel();
        fichas[8].setSize(20, 38);
        fichas[8].setIcon(icon);
        fichas[8].setBounds(416, 111, 20, 38);
        fichas[8].setVisible(true);
        fichas[9] = new JLabel();
        fichas[9].setSize(20, 38);
        fichas[9].setIcon(icon);
        fichas[9].setBounds(452, 75, 20, 38);
        fichas[9].setVisible(true);
        fichas[10] = new JLabel();
        fichas[10].setSize(20, 38);
        fichas[10].setIcon(icon);
        fichas[10].setBounds(491, 111, 20, 38);
        fichas[10].setVisible(true);
        fichas[11] = new JLabel();
        fichas[11].setSize(20, 38);
        fichas[11].setIcon(icon);
        fichas[11].setBounds(452, 151, 20, 38);
        fichas[11].setVisible(true);
        
        
        icon = new ImageIcon(getClass().getResource("/assets/fichaAzul.png"));
        image = icon.getImage();
        icon = new ImageIcon(image.getScaledInstance(20, 38, Image.SCALE_DEFAULT));
        fichas[12] = new JLabel();
        fichas[12].setSize(20, 38);
        fichas[12].setIcon(icon);
        fichas[12].setBounds(416, 450, 20, 38);
        fichas[12].setVisible(true);
        fichas[13] = new JLabel();
        fichas[13].setSize(20, 38);
        fichas[13].setIcon(icon);
        fichas[13].setBounds(457, 413, 20, 38);
        fichas[13].setVisible(true);
        fichas[14] = new JLabel();
        fichas[14].setSize(20, 38);
        fichas[14].setIcon(icon);
        fichas[14].setBounds(491, 450, 20, 38);
        fichas[14].setVisible(true);
        fichas[15] = new JLabel();
        fichas[15].setSize(20, 38);
        fichas[15].setIcon(icon);
        fichas[15].setBounds(452, 488, 20, 38);
        fichas[15].setVisible(true);
        
        
        panelTablero.add(fichas[0]);
        panelTablero.add(fichas[1]);
        panelTablero.add(fichas[2]);
        panelTablero.add(fichas[3]);
        panelTablero.add(fichas[4]);
        panelTablero.add(fichas[5]);
        panelTablero.add(fichas[6]);
        panelTablero.add(fichas[7]);
        panelTablero.add(fichas[8]);
        panelTablero.add(fichas[9]);
        panelTablero.add(fichas[10]);
        panelTablero.add(fichas[11]);
        panelTablero.add(fichas[12]);
        panelTablero.add(fichas[13]);
        panelTablero.add(fichas[14]);
        panelTablero.add(fichas[15]);
    }
}
