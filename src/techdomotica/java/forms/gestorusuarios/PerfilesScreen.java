package techdomotica.java.forms.gestorusuarios;

import java.util.ArrayList;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableModel;
import techdomotica.objs.Ambiente;
import techdomotica.objs.Conectar;
import techdomotica.objs.Perfil;

public class PerfilesScreen extends javax.swing.JFrame {

    private Ambiente ambiente;
    private Conectar conx;
    private int admID = 0;
    private int selectedRow = 0;
    private ArrayList<Perfil> perfilList = new ArrayList();

    public PerfilesScreen(Ambiente amb) {
        
        ambiente = amb;
        conx = amb.getConnection();
        admID = Integer.parseInt(amb.getAdminEncargado().getID());
        initComponents();
        setLocationRelativeTo(null);
        setIconImage(new ImageIcon(getClass().getResource("/resources/media/L4.png")).getImage());
        loadTable();
        loadAvailableFields();
    }

    private void loadAvailableFields() {
        int amountDisabled = 0;
        javax.swing.JTextField fieldsAC[] = {tempaire1, tempaire2};
        javax.swing.JComboBox combo[][] = {{comboAire1, comboAire2}, {comboProyector}, {comboSensor1, comboSensor2}};
        techdomotica.objs.comps.ACondicionado arrayAC[] = ambiente.getACondicionadoAsArray();
        techdomotica.objs.comps.Sensor arraySensores[] = ambiente.getSensorAsArray();
        techdomotica.objs.comps.Televisor proyector = ambiente.getTelevisor();
        
        for(int i = 0, j = arrayAC.length ; i < j ; i++) {
            if(arrayAC[i] == null) {
                combo[0][i].setEnabled(false);
                combo[0][i].setToolTipText("Este aire acondicionado no está instalado.");
                fieldsAC[i].setEnabled(false);
                fieldsAC[i].setToolTipText("Este aire acondicionado no está instalado.");
                amountDisabled++;
            }
        }
        
        if(proyector == null) {
            combo[1][0].setEnabled(false);
            combo[1][0].setToolTipText("Este proyector no está instalado");
            amountDisabled++;
        }
        
        for(int i = 0, j = arraySensores.length ; i < j ; i++) {
            if(arraySensores[i] == null) {
                combo[2][i].setEnabled(false);
                combo[2][i].setToolTipText("Este sensor no está instalado.");
                amountDisabled++;
            }
        }
        System.out.println("amountdisabled: " + amountDisabled);
        if(amountDisabled == 5) {
            addDefault.setEnabled(false);
            justAdd.setEnabled(false);
            addDefault.setToolTipText("No se puede guardar un perfi: no hay dispositivos que configurar");
            justAdd.setToolTipText("No se puede guardar un perfi: no hay dispositivos que configurar");
        }
    }
    
    private void loadTable() {
        selectedRow = 0;
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("#");
        model.addColumn("Temperatura aire 1");
        model.addColumn("Temperatura aire 2");
        model.addColumn("¿Aire 1 encendido?");
        model.addColumn("¿Aire 2 encendido?");
        model.addColumn("¿Aire 2 encendido?");
        model.addColumn("¿Proyector encendido?");
        model.addColumn("¿Sensor puerta encendido?");
        model.addColumn("¿Sensor movimiento encendido?");
        model.addColumn("¿Perfil por defecto?");
        String query = "SELECT * FROM perfil WHERE id_usuario = " + admID + " AND habilitado >= 1;";
        if(conx.executeRS(query)) {
            tablePerfiles.getTableHeader().setReorderingAllowed(false);
            conx.setBeforeFirst();
            perfilList.clear();
            while(conx.nextRow()) {
                Perfil perfil = new Perfil(techdomotica.objs.Util.parseInteger(conx.getResultSetRow("id_perfil")), techdomotica.objs.Util.parseInteger(conx.getResultSetRow("temp1")), techdomotica.objs.Util.parseInteger(conx.getResultSetRow("temp2")), (techdomotica.objs.Util.parseInteger(conx.getResultSetRow("temp1_on")) == 1), (techdomotica.objs.Util.parseInteger(conx.getResultSetRow("temp2_on")) == 1), (techdomotica.objs.Util.parseInteger(conx.getResultSetRow("proyector_on")) == 1), (techdomotica.objs.Util.parseInteger(conx.getResultSetRow("sensor1_on")) == 1), (techdomotica.objs.Util.parseInteger(conx.getResultSetRow("sensor2_on")) == 1));
                perfilList.add(perfil);
                Object[] fila = {conx.getResultSetRow("id_perfil"), conx.getResultSetRow("temp1"), conx.getResultSetRow("temp2"), (techdomotica.objs.Util.parseInteger(conx.getResultSetRow("tem1_on")) == 1 ? "Si" : "No"), (techdomotica.objs.Util.parseInteger(conx.getResultSetRow("temp2_on")) == 1 ? "Si" : "No"), (techdomotica.objs.Util.parseInteger(conx.getResultSetRow("temp2")) == 1 ? "Si" : "No"), (techdomotica.objs.Util.parseInteger(conx.getResultSetRow("proyector_on")) == 1 ? "Si" : "No"), (techdomotica.objs.Util.parseInteger(conx.getResultSetRow("sensor1_on")) == 1 ? "Si" : "No"), (techdomotica.objs.Util.parseInteger(conx.getResultSetRow("sensor2_on")) == 1 ? "Si" : "No"), (techdomotica.objs.Util.parseInteger(conx.getResultSetRow("habilitado")) == 2 ? "Si" : "No")};
                model.addRow(fila);
            }
            
        }
        else {
            Object[] lol = {"No hay datos"};
            model.addRow(lol);
        }
        if(conx.getResultSet() != null) conx.destroyResultSet();
        if(perfilList.isEmpty()) {
            jButton1.setEnabled(false);
            jButton3.setEnabled(false);
        }
        else {
            jButton1.setEnabled(true);
            jButton2.setEnabled(true);
        }
        tablePerfiles.setModel(model);
        tablePerfiles.setRowSelectionInterval(0, 0);
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tablePerfiles = new javax.swing.JTable() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        jLabel2 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        tempaire1 = new javax.swing.JTextField();
        tempaire2 = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        comboAire1 = new javax.swing.JComboBox<>();
        comboAire2 = new javax.swing.JComboBox<>();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        comboSensor1 = new javax.swing.JComboBox<>();
        jLabel10 = new javax.swing.JLabel();
        comboSensor2 = new javax.swing.JComboBox<>();
        jLabel11 = new javax.swing.JLabel();
        comboProyector = new javax.swing.JComboBox<>();
        justAdd = new javax.swing.JButton();
        addDefault = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Perfil - Tech Domótica");
        setResizable(false);
        setSize(new java.awt.Dimension(900, 250));

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Mis perfiles");

        tablePerfiles.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        tablePerfiles.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tablePerfilesMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tablePerfiles);

        jLabel2.setText("Presiona doble click para ver detalles.");

        jButton1.setText("Eliminar");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setText("Modificar");

        jButton3.setText("Seleccionar como perfil actual");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane1)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel2)
                                .addGap(0, 0, Short.MAX_VALUE)))
                        .addContainerGap())
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(57, 57, 57)
                        .addComponent(jButton3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 60, Short.MAX_VALUE)
                        .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(40, 40, 40))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1)
                    .addComponent(jButton2)
                    .addComponent(jButton3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 284, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText("Agregar un nuevo perfil");

        jLabel4.setText("<html>Explicación: Un perfil son los valores por defecto que adquieren los dispositivos al iniciar sesión. Estos valores pueden modificarse a tu gusto, a no ser que el perfil sea sobreescrito por un evento, en cuyo caso, se activará el perfil seleccionado del evento.</html>");

        jLabel5.setText("Temperatura del 1er aire acondicionado:");

        jLabel6.setText("Temperatura del 2do aire acondicionado:");

        jLabel7.setText("Aire acondicionado 1:");

        comboAire1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Inactivo", "Activo" }));

        comboAire2.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Inactivo", "Activo" }));

        jLabel8.setText("Aire acondicionado 2:");

        jLabel9.setText("Sensor 1:");

        comboSensor1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Inactivo", "Activo" }));

        jLabel10.setText("Sensor 2:");

        comboSensor2.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Inactivo", "Activo" }));

        jLabel11.setText("Proyector:");

        comboProyector.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Inactivo", "Activo" }));

        justAdd.setText("Crear perfil");

        addDefault.setText("Crear perfil y escoger como por defecto");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel6)
                            .addComponent(jLabel5))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(tempaire1)
                            .addComponent(tempaire2, javax.swing.GroupLayout.DEFAULT_SIZE, 64, Short.MAX_VALUE)))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(addDefault)
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(jPanel2Layout.createSequentialGroup()
                                    .addComponent(jLabel7)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                    .addComponent(comboAire1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(jPanel2Layout.createSequentialGroup()
                                    .addComponent(jLabel8)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                    .addComponent(comboAire2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(jPanel2Layout.createSequentialGroup()
                                    .addComponent(jLabel11)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(comboProyector, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(18, 18, 18)
                                    .addComponent(jLabel9)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                    .addComponent(comboSensor1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(jPanel2Layout.createSequentialGroup()
                                    .addComponent(jLabel10)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                    .addComponent(comboSensor2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(justAdd, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(85, 85, 85))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(tempaire1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(tempaire2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(comboAire1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(comboAire2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(11, 11, 11)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11)
                    .addComponent(comboProyector, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel9)
                    .addComponent(comboSensor1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(comboSensor2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(addDefault)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(justAdd)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void tablePerfilesMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tablePerfilesMouseClicked
        if(evt.getClickCount() == 2) {
            
        }
        else {
            selectedRow = tablePerfiles.getSelectedRow();
        }
    }//GEN-LAST:event_tablePerfilesMouseClicked

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        int conf = JOptionPane.showConfirmDialog(null, "¿Estás seguro de eliminar el perfil con ID " + tablePerfiles.getValueAt(selectedRow, 0) + "?\nEsta acción no se puede deshacer.", "Confirmación", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        if(conf == JOptionPane.YES_OPTION) {
            if(conx.execute("UPDATE perfil SET habilitado = 0 WHERE id_perfil = " + perfilList.get(selectedRow).getPerfilID() + ";") == 1) {
                loadTable();
                JOptionPane.showMessageDialog(null, "Perfil eliminado correctamente.", "Operación exitosa", JOptionPane.INFORMATION_MESSAGE);
            }
            else JOptionPane.showMessageDialog(null, "Ha ocurrido un error durante la eliminación. Intentalo de nuevo más tarde.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        int conf = JOptionPane.showConfirmDialog(null, "¿Quieres seleccionar el perfil " + perfilList.get(selectedRow).getPerfilID() + " como perfil actual?\nLos dispositivos de la sala obtendrán los valores especificados cuando inicies sesión.", "Selección de perfil", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        if(conf == JOptionPane.YES_OPTION) {
            if(conx.execute("UPDATE perfil SET habilitado = 2 WHERE id_perfil = " + perfilList.get(selectedRow).getPerfilID() + ";") == 1) {
                JOptionPane.showMessageDialog(null, "Se ha seleccionado este perfil como el perfil por defecto. Cuando inicies sesión,\nlos dispositivos obtendrán los valores que hayas asignado.", "Configuración exitosa", JOptionPane.INFORMATION_MESSAGE);
            }
            else JOptionPane.showMessageDialog(null, "Ha ocurrido un error al intentar asignarte un perfil. Por favor, intenalo de nuevo.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_jButton3ActionPerformed

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
            java.util.logging.Logger.getLogger(PerfilesScreen.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(PerfilesScreen.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(PerfilesScreen.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(PerfilesScreen.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new PerfilesScreen(null).setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton addDefault;
    private javax.swing.JComboBox<String> comboAire1;
    private javax.swing.JComboBox<String> comboAire2;
    private javax.swing.JComboBox<String> comboProyector;
    private javax.swing.JComboBox<String> comboSensor1;
    private javax.swing.JComboBox<String> comboSensor2;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JButton justAdd;
    private javax.swing.JTable tablePerfiles;
    private javax.swing.JTextField tempaire1;
    private javax.swing.JTextField tempaire2;
    // End of variables declaration//GEN-END:variables
}
