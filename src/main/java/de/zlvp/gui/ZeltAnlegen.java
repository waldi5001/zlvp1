package de.zlvp.gui;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.text.DateFormat;
import java.util.Locale;

import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.text.DateFormatter;

import de.zlvp.ui.AbstractJInternalFrame;

public class ZeltAnlegen extends AbstractJInternalFrame {

    private static final long serialVersionUID = -3235038169942542628L;

    private JPanel jContentPane;

    private JPanel jPanel;

    private JPanel jPanel1;

    private JPanel jPanel2;

    private JButton jButtonSpeichern;

    private JButton jButtonAbbrechen;
    private JLabel jLabel;
    private JLabel jLabel2;
    private JLabel jLabel3;

    private JTextField jTextFieldBezeichnung;

    private JFormattedTextField jFormattedTextFieldAngeschafft;

    private JTextField jTextFieldPreis;

    private JButton jButtonDetailsHinz;

    public ZeltAnlegen() {
        initialize();
        setupDialog();
    }

    private void initialize() {
        this.setSize(400, 200);
        this.setTitle("Zelt anlegen");
        this.setContentPane(getJContentPane());
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
            jPanel.add(getJPanel1(), java.awt.BorderLayout.SOUTH);
            jPanel.add(getJPanel2(), java.awt.BorderLayout.CENTER);
        }
        return jPanel;
    }

    private JPanel getJPanel1() {
        if (jPanel1 == null) {
            jPanel1 = new JPanel();
            jPanel1.add(getJButtonSpeichern(), null);
            jPanel1.add(getJButtonAbbrechen(), null);
            jPanel1.add(getJButtonDetailsHinz(), null);
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
            GridBagConstraints gridBagConstraints4 = new GridBagConstraints();
            gridBagConstraints4.fill = java.awt.GridBagConstraints.HORIZONTAL;
            gridBagConstraints4.gridy = 0;
            gridBagConstraints4.weightx = 1.0;
            gridBagConstraints4.insets = new java.awt.Insets(10, 10, 0, 10);
            gridBagConstraints4.gridx = 1;
            GridBagConstraints gridBagConstraints3 = new GridBagConstraints();
            gridBagConstraints3.gridx = 0;
            gridBagConstraints3.insets = new java.awt.Insets(10, 10, 0, 10);
            gridBagConstraints3.anchor = java.awt.GridBagConstraints.WEST;
            gridBagConstraints3.gridy = 3;
            jLabel3 = new JLabel();
            jLabel3.setText("Preis");
            GridBagConstraints gridBagConstraints2 = new GridBagConstraints();
            gridBagConstraints2.gridx = 0;
            gridBagConstraints2.anchor = java.awt.GridBagConstraints.WEST;
            gridBagConstraints2.insets = new java.awt.Insets(10, 10, 0, 10);
            gridBagConstraints2.gridy = 2;
            jLabel2 = new JLabel();
            jLabel2.setText("Angeschafft");
            GridBagConstraints gridBagConstraints = new GridBagConstraints();
            gridBagConstraints.gridx = 0;
            gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
            gridBagConstraints.insets = new java.awt.Insets(10, 10, 0, 10);
            gridBagConstraints.gridy = 0;
            jLabel = new JLabel();
            jLabel.setText("Bezeichnung");
            jPanel2 = new JPanel();
            jPanel2.setLayout(new GridBagLayout());
            jPanel2.add(jLabel, gridBagConstraints);
            jPanel2.add(jLabel2, gridBagConstraints2);
            jPanel2.add(jLabel3, gridBagConstraints3);
            jPanel2.add(getJTextFieldBezeichnung(), gridBagConstraints4);
            jPanel2.add(getJFormattedTextFieldAngeschafft(), gridBagConstraints6);
            jPanel2.add(getJTextFieldPreis(), gridBagConstraints7);
        }
        return jPanel2;
    }

    private JButton getJButtonSpeichern() {
        if (jButtonSpeichern == null) {
            jButtonSpeichern = new JButton();
            jButtonSpeichern.setText("Speichern");
            jButtonSpeichern.addActionListener(e -> {
                // String bezeichnung =
                // getJTextFieldBezeichnung().getText().trim();
                // String angeschafft =
                // getJFormattedTextFieldAngeschafft().getText().trim();
                // double preis = 0;
                // if (!getJTextFieldPreis().getText().equals("")) {
                // preis =
                // Double.parseDouble(getJTextFieldPreis().getText().trim().replace(',',
                // '.'));
                // }
                // z.speichereZelt(bezeichnung, preis, angeschafft);
                getJButtonDetailsHinz().setEnabled(true);
                getJButtonSpeichern().setEnabled(false);
            });
        }
        return jButtonSpeichern;
    }

    private JButton getJButtonAbbrechen() {
        if (jButtonAbbrechen == null) {
            jButtonAbbrechen = new JButton();
            jButtonAbbrechen.setText("Abbrechen");
            jButtonAbbrechen.addActionListener(e -> setVisible(false));
        }
        return jButtonAbbrechen;
    }

    private JTextField getJTextFieldBezeichnung() {
        if (jTextFieldBezeichnung == null) {
            jTextFieldBezeichnung = new JTextField();
            jTextFieldBezeichnung.setText("BR-Z-");
        }
        return jTextFieldBezeichnung;
    }

    private JFormattedTextField getJFormattedTextFieldAngeschafft() {
        if (jFormattedTextFieldAngeschafft == null) {
            jFormattedTextFieldAngeschafft = new JFormattedTextField(
                    new DateFormatter(DateFormat.getDateInstance(DateFormat.SHORT, Locale.GERMAN)));
        }
        return jFormattedTextFieldAngeschafft;
    }

    private JTextField getJTextFieldPreis() {
        if (jTextFieldPreis == null) {
            jTextFieldPreis = new JTextField();
        }
        return jTextFieldPreis;
    }

    private JButton getJButtonDetailsHinz() {
        if (jButtonDetailsHinz == null) {
            jButtonDetailsHinz = new JButton();
            jButtonDetailsHinz.setText("Zubehör");
            jButtonDetailsHinz.setEnabled(false);
            jButtonDetailsHinz.addActionListener(e -> {
                // int zID =
                // z.getIID(getJTextFieldBezeichnung().getText().toString());
                // ZeltZubehoer zz = new ZeltZubehoer(zID,
                // desktopPaneGroesse);
                // getFensterKlasse().getJDesktopPane().add(zz);
                // zz.setVisible(true);
                // zz.setFensterKlasse(getFensterKlasse());
            });
        }
        return jButtonDetailsHinz;
    }

}
