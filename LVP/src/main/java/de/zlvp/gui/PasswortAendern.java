package de.zlvp.gui;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import de.zlvp.Client;
import de.zlvp.ui.InternalFrame;

public class PasswortAendern extends InternalFrame {

    private static final long serialVersionUID = 1456325056875416840L;

    private JPanel jContentPane;

    private JPanel jPanel;

    private JPanel jPanel1;

    private JPanel jPanel2;

    private JButton jButtonOK;

    private JButton jButtonAbbrechen;
    private JLabel jLabel;
    private JLabel jLabel1;
    private JLabel jLabel2;
    private JLabel jLabel3;

    private JTextField jTextFieldBenutzer;

    private JPasswordField jPasswordFieldPasswortAlt;

    private JPasswordField jPasswordFieldPasswortNeu;

    private JPasswordField jPasswordFieldPasswortWdhlg;
    private String name = "";

    public PasswortAendern(String name) {
        this.name = name;
        initialize();
        setUp();
    }

    private void initialize() {
        this.setSize(300, 298);
        this.setContentPane(getJContentPane());
        this.setTitle("Passwort Ändern");
    }

    private JPanel getJContentPane() {
        if (jContentPane == null) {
            jContentPane = new JPanel();
            jContentPane.setLayout(new BorderLayout());
            jContentPane.add(getJPanel(), java.awt.BorderLayout.CENTER);
        }
        return jContentPane;
    }

    private JPanel getJPanel() {
        if (jPanel == null) {
            jPanel = new JPanel();
            jPanel.setLayout(new BorderLayout());
            jPanel.add(getJPanel2(), java.awt.BorderLayout.CENTER);
            jPanel.add(getJPanel1(), java.awt.BorderLayout.SOUTH);
        }
        return jPanel;
    }

    private JPanel getJPanel1() {
        if (jPanel1 == null) {
            jPanel1 = new JPanel();
            jPanel1.add(getJButtonOK(), null);
            jPanel1.add(getJButtonAbbrechen(), null);
        }
        return jPanel1;
    }

    private JPanel getJPanel2() {
        if (jPanel2 == null) {
            GridBagConstraints gridBagConstraints7 = new GridBagConstraints();
            gridBagConstraints7.fill = java.awt.GridBagConstraints.HORIZONTAL;
            gridBagConstraints7.gridy = 3;
            gridBagConstraints7.weightx = 1.0;
            gridBagConstraints7.insets = new java.awt.Insets(10, 10, 0, 10);
            gridBagConstraints7.gridx = 1;
            GridBagConstraints gridBagConstraints6 = new GridBagConstraints();
            gridBagConstraints6.fill = java.awt.GridBagConstraints.HORIZONTAL;
            gridBagConstraints6.gridy = 2;
            gridBagConstraints6.weightx = 1.0;
            gridBagConstraints6.insets = new java.awt.Insets(10, 10, 0, 10);
            gridBagConstraints6.gridx = 1;
            GridBagConstraints gridBagConstraints5 = new GridBagConstraints();
            gridBagConstraints5.fill = java.awt.GridBagConstraints.HORIZONTAL;
            gridBagConstraints5.gridy = 1;
            gridBagConstraints5.weightx = 1.0;
            gridBagConstraints5.insets = new java.awt.Insets(10, 10, 0, 10);
            gridBagConstraints5.gridx = 1;
            GridBagConstraints gridBagConstraints4 = new GridBagConstraints();
            gridBagConstraints4.fill = java.awt.GridBagConstraints.HORIZONTAL;
            gridBagConstraints4.gridy = 0;
            gridBagConstraints4.weightx = 1.0;
            gridBagConstraints4.insets = new java.awt.Insets(10, 10, 0, 10);
            gridBagConstraints4.gridx = 1;
            GridBagConstraints gridBagConstraints3 = new GridBagConstraints();
            gridBagConstraints3.gridx = 0;
            gridBagConstraints3.insets = new java.awt.Insets(10, 10, 0, 0);
            gridBagConstraints3.anchor = java.awt.GridBagConstraints.WEST;
            gridBagConstraints3.gridy = 3;
            jLabel3 = new JLabel();
            jLabel3.setText("Wiederhohlen");
            GridBagConstraints gridBagConstraints2 = new GridBagConstraints();
            gridBagConstraints2.gridx = 0;
            gridBagConstraints2.insets = new java.awt.Insets(10, 10, 0, 0);
            gridBagConstraints2.anchor = java.awt.GridBagConstraints.WEST;
            gridBagConstraints2.gridy = 2;
            jLabel2 = new JLabel();
            jLabel2.setText("Neues Passwort");
            GridBagConstraints gridBagConstraints1 = new GridBagConstraints();
            gridBagConstraints1.gridx = 0;
            gridBagConstraints1.insets = new java.awt.Insets(10, 10, 0, 0);
            gridBagConstraints1.anchor = java.awt.GridBagConstraints.WEST;
            gridBagConstraints1.gridy = 1;
            jLabel1 = new JLabel();
            jLabel1.setText("Passwort");
            GridBagConstraints gridBagConstraints = new GridBagConstraints();
            gridBagConstraints.gridx = 0;
            gridBagConstraints.insets = new java.awt.Insets(10, 10, 0, 0);
            gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
            gridBagConstraints.gridy = 0;
            jLabel = new JLabel();
            jLabel.setText("Name");
            jPanel2 = new JPanel();
            jPanel2.setLayout(new GridBagLayout());
            jPanel2.add(jLabel, gridBagConstraints);
            jPanel2.add(jLabel1, gridBagConstraints1);
            jPanel2.add(jLabel2, gridBagConstraints2);
            jPanel2.add(jLabel3, gridBagConstraints3);
            jPanel2.add(getJTextFieldBenutzer(), gridBagConstraints4);
            jPanel2.add(getJPasswordFieldPasswortAlt(), gridBagConstraints5);
            jPanel2.add(getJPasswordFieldPasswortNeu(), gridBagConstraints6);
            jPanel2.add(getJPasswordFieldPasswortWdhlg(), gridBagConstraints7);
        }
        return jPanel2;
    }

    private JButton getJButtonOK() {
        if (jButtonOK == null) {
            jButtonOK = new JButton();
            jButtonOK.setText("OK");
            jButtonOK.addActionListener(e -> {
                String name = getJTextFieldBenutzer().getText().trim();
                String alt = String.valueOf(getJPasswordFieldPasswortAlt().getPassword());
                String neu = String.valueOf(getJPasswordFieldPasswortNeu().getPassword());
                String wdhlg = String.valueOf(getJPasswordFieldPasswortWdhlg().getPassword());

                if (neu.equals(wdhlg)) {
                    Client.login(name, alt);
                    Client.get().aenderePasswort(name, neu);
                }

                setVisible(false);
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

    private JTextField getJTextFieldBenutzer() {
        if (jTextFieldBenutzer == null) {
            jTextFieldBenutzer = new JTextField();
            jTextFieldBenutzer.setText(this.name);
        }
        return jTextFieldBenutzer;
    }

    private JPasswordField getJPasswordFieldPasswortAlt() {
        if (jPasswordFieldPasswortAlt == null) {
            jPasswordFieldPasswortAlt = new JPasswordField();
        }
        return jPasswordFieldPasswortAlt;
    }

    private JPasswordField getJPasswordFieldPasswortNeu() {
        if (jPasswordFieldPasswortNeu == null) {
            jPasswordFieldPasswortNeu = new JPasswordField();
        }
        return jPasswordFieldPasswortNeu;
    }

    private JPasswordField getJPasswordFieldPasswortWdhlg() {
        if (jPasswordFieldPasswortWdhlg == null) {
            jPasswordFieldPasswortWdhlg = new JPasswordField();
        }
        return jPasswordFieldPasswortWdhlg;
    }

}
