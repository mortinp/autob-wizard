/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * WelcomeScreen.java
 *
 * Created on 09-mar-2012, 22:22:40
 */
package nbautobuild.wizard.pages;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import javax.swing.JFileChooser;
import nbautobuild.core.dirutils.DirectoriesUtility;
import nbautobuild.core.ResourcesManager;

/**
 *
 * @author Martin
 */
public class LocalResourcesConfig extends javax.swing.JPanel {
    
    JFileChooser pathChooser = new JFileChooser();

    /** Creates new form WelcomeScreen */
    public LocalResourcesConfig() {
        initComponents();
        initialize();
    }
    
    private void initialize() {
        //txtLinkPath.setText(DirectoriesUtility.getDesktopPath());
        
        /*chkCustomLinkPath.addItemListener(new ItemListener() {

            @Override
            public void itemStateChanged(ItemEvent e) {
                boolean currentCustomLinkPathEnabled = chkCustomLinkPath.isSelected();
                jLabel1.setEnabled(currentCustomLinkPathEnabled);
                txtLinkPath.setEnabled(currentCustomLinkPathEnabled);
                btnSelectLinkPath.setEnabled(currentCustomLinkPathEnabled);
            }
        });*/
        btnSelectLinkPath.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                pathChooser.setCurrentDirectory(new java.io.File("."));
                pathChooser.setDialogTitle("Select Link Copy Directory");
                pathChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                pathChooser.setAcceptAllFileFilterUsed(false);
                if (pathChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
                    txtLinkPath.setText(DirectoriesUtility.formatPath(pathChooser.getSelectedFile().toString()));
                }
            }
        });
        
        chkNBInstalled.addItemListener(new ItemListener() {

            @Override
            public void itemStateChanged(ItemEvent e) {
                boolean currentNBInstalledEnabled = chkNBInstalled.isSelected();
                jLabel2.setEnabled(currentNBInstalledEnabled);
                txtNBInstallPath.setEnabled(currentNBInstalledEnabled);
                btnSelectNBInstalPath.setEnabled(currentNBInstalledEnabled);
                
                ResourcesManager rm = ResourcesManager.instance();
                rm.setNBInstalled(currentNBInstalledEnabled);
            }
        });
        btnSelectNBInstalPath.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                pathChooser.setCurrentDirectory(new java.io.File("."));
                pathChooser.setDialogTitle("Select NB Installation Directory");
                pathChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                pathChooser.setAcceptAllFileFilterUsed(false);
                if (pathChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
                    txtNBInstallPath.setText(DirectoriesUtility.formatPath(pathChooser.getSelectedFile().toString()));
                }
            }
        });
    }
    
    public void aboutToDisplay() {
        chkCustomLinkPath.requestFocusInWindow();
    }
    
    public void doGetData() {
        ResourcesManager rm = ResourcesManager.instance();
        rm.setProperty(ResourcesManager.COPY_LINK_PATH, txtLinkPath.getText());
        rm.setNBInstallationPath(txtNBInstallPath.getText());
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        chkCustomLinkPath = new javax.swing.JCheckBox();
        txtLinkPath = new javax.swing.JTextField();
        btnSelectLinkPath = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        chkNBInstalled = new javax.swing.JCheckBox();
        jLabel2 = new javax.swing.JLabel();
        txtNBInstallPath = new javax.swing.JTextField();
        btnSelectNBInstalPath = new javax.swing.JButton();

        setMaximumSize(new java.awt.Dimension(550, 430));
        setMinimumSize(new java.awt.Dimension(550, 430));
        setPreferredSize(new java.awt.Dimension(550, 430));

        chkCustomLinkPath.setSelected(true);
        chkCustomLinkPath.setText("Copy script link to custom folder");
        chkCustomLinkPath.setEnabled(false);

        btnSelectLinkPath.setText("Browse");

        jLabel1.setText("Where:");

        chkNBInstalled.setText("Netbeans is installed in this machine");

        jLabel2.setText("Where:");
        jLabel2.setEnabled(false);

        txtNBInstallPath.setEnabled(false);

        btnSelectNBInstalPath.setText("Browse");
        btnSelectNBInstalPath.setEnabled(false);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addGap(18, 18, 18)
                        .addComponent(txtNBInstallPath, javax.swing.GroupLayout.PREFERRED_SIZE, 318, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnSelectNBInstalPath))
                    .addComponent(chkCustomLinkPath)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(18, 18, 18)
                        .addComponent(txtLinkPath, javax.swing.GroupLayout.PREFERRED_SIZE, 318, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnSelectLinkPath))
                    .addComponent(chkNBInstalled))
                .addContainerGap(69, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(38, 38, 38)
                .addComponent(chkNBInstalled)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txtNBInstallPath, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnSelectNBInstalPath))
                .addGap(46, 46, 46)
                .addComponent(chkCustomLinkPath)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(txtLinkPath, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnSelectLinkPath))
                .addContainerGap(240, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnSelectLinkPath;
    private javax.swing.JButton btnSelectNBInstalPath;
    private javax.swing.JCheckBox chkCustomLinkPath;
    private javax.swing.JCheckBox chkNBInstalled;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JTextField txtLinkPath;
    private javax.swing.JTextField txtNBInstallPath;
    // End of variables declaration//GEN-END:variables
}