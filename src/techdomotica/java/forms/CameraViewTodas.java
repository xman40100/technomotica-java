/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package techdomotica.java.forms;

import java.awt.Image;
import java.time.format.DateTimeFormatter;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import techdomotica.objs.Ambiente;

/**
 *
 * @author Andres
 */
public class CameraViewTodas extends javax.swing.JFrame {

    private Ambiente ambiente;
    
    private boolean continueIt = true;
    private Thread changeUI;
    
    public CameraViewTodas(Ambiente ambient) {
        initComponents();
        ambiente = ambient;
        setIconImage(new ImageIcon(getClass().getResource("/resources/media/L4.png")).getImage());
        
        changeUI = new Thread(new Runnable() {
            @Override
            public void run() {
                while(continueIt) {
                    try {
                        Thread.sleep(1000);
                        SwingUtilities.invokeLater(new Runnable() {
                            @Override
                            public void run() {
                                dateTime.setText(java.time.LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-YYYY - hh:mm:ss a")));
                            }
                        });
                    } 
                    catch (InterruptedException ex) {
                        ex.printStackTrace();
                    }

                }
            }
        });
        changeUI.start();
        
        loadCameraFeed();
        loadCamaraRadioButtons();
        
        setLocationRelativeTo(null);
    }

    public void loadCameraFeed() {
        JLabel cameras[] = {camera1, camera2, camera3, camera4};
        for(int i = 0 ; i < 4 ; i++) {
            if(ambiente.getCamara(i) != null) {
                if(ambiente.getCamara(i).getComponenteEncendidoState()) cameras[i].setIcon(new ImageIcon(new ImageIcon(getClass().getResource("/resources/media/simulator/camara" + (i + 1) +".png")).getImage().getScaledInstance(340, 250, Image.SCALE_SMOOTH)));
                else cameras[i].setIcon(new ImageIcon(new ImageIcon(getClass().getResource("/resources/media/simulator/nosignal.png")).getImage().getScaledInstance(340, 250, Image.SCALE_SMOOTH)));
            }
        }
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        btnGroup = new javax.swing.ButtonGroup();
        camera1 = new javax.swing.JLabel();
        camera2 = new javax.swing.JLabel();
        camera3 = new javax.swing.JLabel();
        camera4 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        cameraon = new javax.swing.JRadioButton();
        cameraoff = new javax.swing.JRadioButton();
        comboCamara = new javax.swing.JComboBox<>();
        jLabel1 = new javax.swing.JLabel();
        dateTime = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setTitle("Vista de todas las cámaras - Tech Domótica");
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        getContentPane().add(camera1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 340, 250));
        getContentPane().add(camera2, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 0, 340, 250));
        getContentPane().add(camera3, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 250, 340, 250));
        getContentPane().add(camera4, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 250, 340, 250));

        btnGroup.add(cameraon);
        cameraon.setText("Activado");
        cameraon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cameraonActionPerformed(evt);
            }
        });

        btnGroup.add(cameraoff);
        cameraoff.setText("Desactivado");
        cameraoff.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cameraoffActionPerformed(evt);
            }
        });

        comboCamara.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Cámara 1", "Cámara 2", "Cámara 3", "Cámara 4" }));
        comboCamara.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboCamaraActionPerformed(evt);
            }
        });

        jLabel1.setText("Seleccionar cámara:");

        dateTime.setText("-");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(comboCamara, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(cameraon)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(cameraoff)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 73, Short.MAX_VALUE)
                .addComponent(dateTime, javax.swing.GroupLayout.PREFERRED_SIZE, 213, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(4, 4, 4)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(comboCamara, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cameraoff)
                    .addComponent(cameraon)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(dateTime, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 500, 680, 30));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void comboCamaraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboCamaraActionPerformed
        loadCamaraRadioButtons();
    }//GEN-LAST:event_comboCamaraActionPerformed

    private void cameraonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cameraonActionPerformed
        progressDialog dialogo = new progressDialog(this, true, 10){
            @Override
            public void progressBarFilled() {
                super.progressBarFilled();
                ambiente.getCamara(comboCamara.getSelectedIndex()).toggleComponenteEncendido(true);
                try {
                    Thread.sleep(500);
                    loadCameraFeed();
                } 
                catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
            }
        };
        dialogo.setTitle("Encendiendo cámara");
        dialogo.textVar.setText("Encendiendo la cámara " + ambiente.getCamara(comboCamara.getSelectedIndex()).getComponenteFullName() + ". Por favor espere...");
        dialogo.setVisible(true);
    }//GEN-LAST:event_cameraonActionPerformed

    private void cameraoffActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cameraoffActionPerformed
        progressDialog dialogo = new progressDialog(this, true, 10){
            @Override
            public void progressBarFilled() {
                super.progressBarFilled();
                ambiente.getCamara(comboCamara.getSelectedIndex()).toggleComponenteEncendido(false);
                try {
                    Thread.sleep(500);
                    loadCameraFeed();
                } 
                catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
            }
        };
        dialogo.setTitle("Apagando cámara");
        dialogo.textVar.setText("Apagando la cámara " + ambiente.getCamara(comboCamara.getSelectedIndex()).getComponenteFullName() + ". Por favor espere...");
        dialogo.setVisible(true);
    }//GEN-LAST:event_cameraoffActionPerformed

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        handleClose();
    }//GEN-LAST:event_formWindowClosing

    public void handleClose() {
        continueIt = false;
        changeUI.interrupt();
        this.dispose();
    }
    
    public void loadCamaraRadioButtons() {
        if(ambiente.getCamara(comboCamara.getSelectedIndex()) != null) {
            if(ambiente.getCamara(comboCamara.getSelectedIndex()).getComponenteEncendidoState()) cameraon.setSelected(true);
            else cameraoff.setSelected(true);
        }
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(CameraViewTodas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(CameraViewTodas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(CameraViewTodas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(CameraViewTodas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new CameraViewTodas(null).setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup btnGroup;
    private javax.swing.JLabel camera1;
    private javax.swing.JLabel camera2;
    private javax.swing.JLabel camera3;
    private javax.swing.JLabel camera4;
    private javax.swing.JRadioButton cameraoff;
    private javax.swing.JRadioButton cameraon;
    private javax.swing.JComboBox<String> comboCamara;
    private javax.swing.JLabel dateTime;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    // End of variables declaration//GEN-END:variables
}
