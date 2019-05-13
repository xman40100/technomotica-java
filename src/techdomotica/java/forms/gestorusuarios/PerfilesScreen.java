package techdomotica.java.forms.gestorusuarios;

import java.util.ArrayList;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableModel;
import techdomotica.objs.Conectar;
import techdomotica.objs.Perfil;

public class PerfilesScreen extends javax.swing.JFrame {

    private Conectar conx;
    private int admID = 0;
    private int selectedRow = 0;
    private ArrayList<Perfil> perfilList = new ArrayList();

    public PerfilesScreen(Conectar con, int admID) {
        conx = con;
        this.admID = admID;
        initComponents();
        setLocationRelativeTo(null);
        setIconImage(new ImageIcon(getClass().getResource("/resources/media/L4.png")).getImage());
        loadTable();
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
        String query = "SELECT * FROM perfil WHERE id_usuario = " + admID + " AND habilitado = 1;";
        if(conx.executeRS(query)) {
            tablePerfiles.getTableHeader().setReorderingAllowed(false);
            conx.setBeforeFirst();
            perfilList.clear();
            while(conx.nextRow()) {
                Perfil perfil = new Perfil(techdomotica.objs.Util.parseInteger(conx.getResultSetRow("id_perfil")), techdomotica.objs.Util.parseInteger(conx.getResultSetRow("temp1")), techdomotica.objs.Util.parseInteger(conx.getResultSetRow("temp2")), (techdomotica.objs.Util.parseInteger(conx.getResultSetRow("temp1_on")) == 1), (techdomotica.objs.Util.parseInteger(conx.getResultSetRow("temp2_on")) == 1), (techdomotica.objs.Util.parseInteger(conx.getResultSetRow("proyector_on")) == 1));
                perfilList.add(perfil);
                Object[] fila = {conx.getResultSetRow("id_perfil"), conx.getResultSetRow("temp1"), conx.getResultSetRow("temp2"), (techdomotica.objs.Util.parseInteger(conx.getResultSetRow("tem1_on")) == 1 ? "Si" : "No"), (techdomotica.objs.Util.parseInteger(conx.getResultSetRow("temp2_on")) == 1 ? "Si" : "No"), (techdomotica.objs.Util.parseInteger(conx.getResultSetRow("temp2")) == 1 ? "Si" : "No"), (techdomotica.objs.Util.parseInteger(conx.getResultSetRow("proyector_on")) == 1 ? "Si" : "No")};
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
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jButton3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(18, 18, 18)
                        .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
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

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, 391, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
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
            if(conx.execute("UPDATE usuario SET perfil_actual = " + perfilList.get(selectedRow).getPerfilID() + " WHERE id_usuario = " + admID + ";") == 1) {
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
                new PerfilesScreen(null, 0).setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tablePerfiles;
    // End of variables declaration//GEN-END:variables
}
