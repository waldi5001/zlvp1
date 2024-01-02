package de.zlvp.gui;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Toolkit;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import com.google.common.eventbus.Subscribe;

import de.zlvp.Client;
import de.zlvp.Events;
import de.zlvp.Events.LoginSuccessfull;
import de.zlvp.ui.AbstractJInternalFrame;

public class LoginDialog extends AbstractJInternalFrame {

    private static final long serialVersionUID = 1L;

    private JPanel jContentPane;

    private JPanel jPanel;

    private JPanel jPanel1;

    private JPanel jPanel2;

    private JButton jButtonOK;

    private JButton jButtonAbbrechen;

    private JPanel jPanel3;
    private JLabel jLabel;
    private JLabel jLabel1;
    private JTextField jTextFieldName;
    private JPasswordField jPasswordFieldPasswort;

    private JPanel jPanel4;

    private JButton jButtonPasswortAendern;

    public LoginDialog() {
        initialize();
        setupDialog();
        this.setResizable(false);
        
        Events.bus().register(this);
    }

    private void initialize() {
        this.setSize(500, 200);
        this.setTitle("Login");
        this.setContentPane(getJContentPane());
        this.getRootPane().setDefaultButton(getJButtonOK());
    }

    private JPanel getJContentPane() {
        if (jContentPane == null) {
            jContentPane = new JPanel();
            jContentPane.setLayout(new BorderLayout());
            jContentPane.add(getJPanel2(), java.awt.BorderLayout.CENTER);
        }
        return jContentPane;
    }

    private JPanel getJPanel() {
        if (jPanel == null) {
            jPanel = new JPanel();
            jPanel.add(getJButtonOK(), null);
            jPanel.add(getJButtonAbbrechen(), null);
            jPanel.add(getJButtonPasswortAendern(), null);
        }
        return jPanel;
    }

    private JPanel getJPanel1() {
        if (jPanel1 == null) {
            jPanel1 = new JPanel();
            jPanel1.setLayout(new BorderLayout());
            jPanel1.add(getJPanel3(), java.awt.BorderLayout.SOUTH);
            jPanel1.add(getJPanel4(), java.awt.BorderLayout.CENTER);
        }
        return jPanel1;
    }

    private JPanel getJPanel2() {
        if (jPanel2 == null) {
            jPanel2 = new JPanel();
            jPanel2.setLayout(new BorderLayout());
            jPanel2.add(getJPanel1(), java.awt.BorderLayout.CENTER);
            jPanel2.add(getJPanel(), java.awt.BorderLayout.SOUTH);
        }
        return jPanel2;
    }

    private JButton getJButtonOK() {
        if (jButtonOK == null) {
            jButtonOK = new JButton();
            jButtonOK.setText("OK");
            jButtonOK.addActionListener(e -> {
                String name = getJTextField().getText().trim();
                char[] passwort = getJPasswordField().getPassword();
                boolean login = Client.login(name, passwort);
                if (login) {
                    setVisible(false);
                    Events.get().fireLoginSuccessfull(name);
                }
            });
        }
        return jButtonOK;
    }

    private JButton getJButtonAbbrechen() {
        if (jButtonAbbrechen == null) {
            jButtonAbbrechen = new JButton();
            jButtonAbbrechen.setText("Abbrechen");
            jButtonAbbrechen.addActionListener(e -> setVisible(false));
        }
        return jButtonAbbrechen;
    }

    private JPanel getJPanel3() {
        if (jPanel3 == null) {
            GridBagConstraints gridBagConstraints3 = new GridBagConstraints();
            gridBagConstraints3.fill = java.awt.GridBagConstraints.HORIZONTAL;
            gridBagConstraints3.gridy = 1;
            gridBagConstraints3.weightx = 1.0;
            gridBagConstraints3.insets = new java.awt.Insets(10, 0, 0, 10);
            gridBagConstraints3.gridx = 2;
            GridBagConstraints gridBagConstraints2 = new GridBagConstraints();
            gridBagConstraints2.gridy = 0;
            gridBagConstraints2.anchor = java.awt.GridBagConstraints.WEST;
            gridBagConstraints2.insets = new java.awt.Insets(10, 10, 0, 10);
            gridBagConstraints2.gridx = 0;
            GridBagConstraints gridBagConstraints1 = new GridBagConstraints();
            gridBagConstraints1.fill = java.awt.GridBagConstraints.HORIZONTAL;
            gridBagConstraints1.gridy = 0;
            gridBagConstraints1.weightx = 1.0;
            gridBagConstraints1.insets = new java.awt.Insets(10, 0, 0, 10);
            gridBagConstraints1.gridx = 2;
            GridBagConstraints gridBagConstraints = new GridBagConstraints();
            gridBagConstraints.gridx = 0;
            gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
            gridBagConstraints.insets = new java.awt.Insets(10, 10, 0, 10);
            gridBagConstraints.gridy = 1;
            jLabel1 = new JLabel();
            jLabel1.setText("Passwort");
            jLabel = new JLabel();
            jLabel.setText("Name");
            jPanel3 = new JPanel();
            jPanel3.setLayout(new GridBagLayout());
            jPanel3.add(jLabel, gridBagConstraints2);
            jPanel3.add(jLabel1, gridBagConstraints);
            jPanel3.add(getJTextField(), gridBagConstraints1);
            jPanel3.add(getJPasswordField(), gridBagConstraints3);
        }
        return jPanel3;
    }

    private JTextField getJTextField() {
        if (jTextFieldName == null) {
            jTextFieldName = new JTextField();
        }
        return jTextFieldName;
    }

    private JPasswordField getJPasswordField() {
        if (jPasswordFieldPasswort == null) {
            jPasswordFieldPasswort = new JPasswordField();
        }
        return jPasswordFieldPasswort;
    }

    private JPanel getJPanel4() {
        if (jPanel4 == null) {
            jPanel4 = new JPanel();
            jPanel4.setLayout(new BorderLayout());
            Image bild = Toolkit.getDefaultToolkit().getImage(getClass().getResource("/logo.jpg"));
            jPanel4.add(new ImageComponent(bild), java.awt.BorderLayout.CENTER);
        }
        return jPanel4;
    }

    private JButton getJButtonPasswortAendern() {
        if (jButtonPasswortAendern == null) {
            jButtonPasswortAendern = new JButton();
            jButtonPasswortAendern.setText("Passwort ändern");
            jButtonPasswortAendern.addActionListener(e -> {
                new PasswortAendern(getJTextField().getText().trim());
            });
        }
        return jButtonPasswortAendern;
    }
    
    @Subscribe
    private void loginSuccessful(LoginSuccessfull event) {
        setVisible(false);
    }
}
