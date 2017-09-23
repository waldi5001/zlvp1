package de.zlvp.gui;

import static de.zlvp.Client.get;
import static java.util.Arrays.asList;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.text.DateFormatter;

import de.zlvp.entity.Geschlecht;
import de.zlvp.ui.AbstractJInternalFrame;
import de.zlvp.ui.JComboBoxBuilder;

public class PersonAnlegen extends AbstractJInternalFrame {

    private static final long serialVersionUID = -6991827451131673965L;

    private JPanel jContentPane;

    private JPanel jPanel;

    private JPanel jPanel1;
    private JLabel jLabel;
    private JLabel jLabel1;
    private JLabel jLabel2;
    private JLabel jLabel3;

    private JButton jButtonOK;

    private JButton jButtonNeu;

    private JButton jButtonAbbrechen;
    private JLabel jLabel4;
    private JLabel jLabel5;

    private JTextField jTextFieldPLZ;

    private JTextField jTextFieldOrt;

    private JTextField jTextFieldVorname;

    private JTextField jTextFieldName;

    private JTextField jTextFieldStrasse;
    private JTextField jTextFieldTelNr;

    private JTextField jTextFieldEMail;

    private JLabel jLabel6;

    private JFormattedTextField jFormattedTextFieldGebDat;

    private JButton jButtonLoeschen;

    private JComboBox<Geschlecht> jComboBoxGeschlecht;
    private JLabel jLabel7;
    private JLabel jLabel8;
    private JTextField jTextFieldHandy;
    private JTextField jTextFieldNottel;
    private JLabel jLabel51;

    private final JComboBoxBuilder<Geschlecht> comboboxBuilderGeschlecht;

    public PersonAnlegen() {
        comboboxBuilderGeschlecht = JComboBoxBuilder.get(Geschlecht.class,
                allGeschlecht -> allGeschlecht.get(asList(Geschlecht.values())));
        initialize();
        setupDialog();
    }

    private void initialize() {
        this.setSize(442, 302);
        this.setTitle("Person anlegen");
        this.setContentPane(getJContentPane());
    }

    private JPanel getJContentPane() {
        if (jContentPane == null) {
            jContentPane = new JPanel();
            jContentPane.setLayout(new BorderLayout());
            jContentPane.add(getJPanel(), java.awt.BorderLayout.CENTER);
            jContentPane.add(getJPanel1(), java.awt.BorderLayout.SOUTH);
        }
        return jContentPane;
    }

    private JPanel getJPanel() {
        if (jPanel == null) {
            GridBagConstraints gridBagConstraints24 = new GridBagConstraints();
            gridBagConstraints24.gridx = 0;
            gridBagConstraints24.insets = new Insets(0, 10, 0, 0);
            gridBagConstraints24.gridy = 10;
            jLabel51 = new JLabel();
            jLabel51.setText("Not Telefonnummer");
            GridBagConstraints gridBagConstraints16 = new GridBagConstraints();
            gridBagConstraints16.fill = GridBagConstraints.BOTH;
            gridBagConstraints16.gridy = 10;
            gridBagConstraints16.weightx = 1.0;
            gridBagConstraints16.gridwidth = 2;
            gridBagConstraints16.insets = new Insets(0, 10, 0, 10);
            gridBagConstraints16.gridx = 1;
            GridBagConstraints gridBagConstraints23 = new GridBagConstraints();
            gridBagConstraints23.fill = GridBagConstraints.BOTH;
            gridBagConstraints23.gridy = 8;
            gridBagConstraints23.weightx = 1.0;
            gridBagConstraints23.insets = new Insets(0, 10, 0, 10);
            gridBagConstraints23.gridwidth = 2;
            gridBagConstraints23.gridx = 1;
            GridBagConstraints gridBagConstraints15 = new GridBagConstraints();
            gridBagConstraints15.gridx = 0;
            gridBagConstraints15.anchor = GridBagConstraints.WEST;
            gridBagConstraints15.insets = new Insets(0, 10, 0, 0);
            gridBagConstraints15.gridy = 8;
            jLabel8 = new JLabel();
            jLabel8.setText("Handynummer:");
            GridBagConstraints gridBagConstraints22 = new GridBagConstraints();
            gridBagConstraints22.gridx = 0;
            gridBagConstraints22.anchor = java.awt.GridBagConstraints.WEST;
            gridBagConstraints22.insets = new java.awt.Insets(0, 10, 0, 0);
            gridBagConstraints22.gridy = 0;
            jLabel7 = new JLabel();
            jLabel7.setText("Geschlecht");
            GridBagConstraints gridBagConstraints14 = new GridBagConstraints();
            gridBagConstraints14.fill = java.awt.GridBagConstraints.HORIZONTAL;
            gridBagConstraints14.gridy = 0;
            gridBagConstraints14.weightx = 1.0;
            gridBagConstraints14.anchor = java.awt.GridBagConstraints.CENTER;
            gridBagConstraints14.insets = new java.awt.Insets(0, 10, 0, 10);
            gridBagConstraints14.gridx = 1;
            GridBagConstraints gridBagConstraints13 = new GridBagConstraints();
            gridBagConstraints13.fill = java.awt.GridBagConstraints.HORIZONTAL;
            gridBagConstraints13.gridy = 6;
            gridBagConstraints13.weightx = 1.0;
            gridBagConstraints13.gridwidth = 2;
            gridBagConstraints13.insets = new java.awt.Insets(0, 10, 0, 10);
            gridBagConstraints13.gridx = 1;
            GridBagConstraints gridBagConstraints12 = new GridBagConstraints();
            gridBagConstraints12.gridx = 0;
            gridBagConstraints12.anchor = java.awt.GridBagConstraints.WEST;
            gridBagConstraints12.insets = new java.awt.Insets(0, 10, 0, 0);
            gridBagConstraints12.gridy = 6;
            jLabel6 = new JLabel();
            jLabel6.setText("Geb. Datum");
            GridBagConstraints gridBagConstraints21 = new GridBagConstraints();
            gridBagConstraints21.insets = new java.awt.Insets(0, 10, 0, 0);
            gridBagConstraints21.gridy = 1;
            gridBagConstraints21.anchor = java.awt.GridBagConstraints.WEST;
            GridBagConstraints gridBagConstraints11 = new GridBagConstraints();
            gridBagConstraints11.fill = java.awt.GridBagConstraints.HORIZONTAL;
            gridBagConstraints11.gridy = 9;
            gridBagConstraints11.weightx = 1.0;
            gridBagConstraints11.gridwidth = 3;
            gridBagConstraints11.insets = new java.awt.Insets(0, 10, 0, 10);
            gridBagConstraints11.gridx = 1;
            GridBagConstraints gridBagConstraints10 = new GridBagConstraints();
            gridBagConstraints10.fill = java.awt.GridBagConstraints.HORIZONTAL;
            gridBagConstraints10.gridy = 7;
            gridBagConstraints10.weightx = 1.0;
            gridBagConstraints10.gridwidth = 3;
            gridBagConstraints10.insets = new java.awt.Insets(0, 10, 0, 10);
            gridBagConstraints10.gridx = 1;
            GridBagConstraints gridBagConstraints9 = new GridBagConstraints();
            gridBagConstraints9.fill = java.awt.GridBagConstraints.HORIZONTAL;
            gridBagConstraints9.gridy = 4;
            gridBagConstraints9.weightx = 1.0;
            gridBagConstraints9.gridwidth = 4;
            gridBagConstraints9.insets = new java.awt.Insets(0, 10, 0, 10);
            gridBagConstraints9.gridx = 1;
            GridBagConstraints gridBagConstraints8 = new GridBagConstraints();
            gridBagConstraints8.fill = java.awt.GridBagConstraints.HORIZONTAL;
            gridBagConstraints8.gridy = 3;
            gridBagConstraints8.weightx = 1.0;
            gridBagConstraints8.gridwidth = 3;
            gridBagConstraints8.insets = new java.awt.Insets(0, 10, 0, 10);
            gridBagConstraints8.gridx = 1;
            GridBagConstraints gridBagConstraints7 = new GridBagConstraints();
            gridBagConstraints7.fill = java.awt.GridBagConstraints.HORIZONTAL;
            gridBagConstraints7.gridy = 1;
            gridBagConstraints7.weightx = 1.0;
            gridBagConstraints7.gridwidth = 3;
            gridBagConstraints7.insets = new java.awt.Insets(0, 10, 0, 10);
            gridBagConstraints7.gridx = 1;
            GridBagConstraints gridBagConstraints6 = new GridBagConstraints();
            gridBagConstraints6.fill = java.awt.GridBagConstraints.HORIZONTAL;
            gridBagConstraints6.gridy = 5;
            gridBagConstraints6.weightx = 1.0;
            gridBagConstraints6.insets = new java.awt.Insets(0, 0, 0, 10);
            gridBagConstraints6.gridx = 2;
            GridBagConstraints gridBagConstraints5 = new GridBagConstraints();
            gridBagConstraints5.fill = java.awt.GridBagConstraints.HORIZONTAL;
            gridBagConstraints5.gridy = 5;
            gridBagConstraints5.weightx = 1.0;
            gridBagConstraints5.insets = new java.awt.Insets(0, 10, 0, 5);
            gridBagConstraints5.anchor = java.awt.GridBagConstraints.WEST;
            gridBagConstraints5.gridx = 1;
            GridBagConstraints gridBagConstraints4 = new GridBagConstraints();
            gridBagConstraints4.gridx = 0;
            gridBagConstraints4.insets = new java.awt.Insets(0, 10, 0, 0);
            gridBagConstraints4.anchor = java.awt.GridBagConstraints.WEST;
            gridBagConstraints4.gridy = 9;
            jLabel5 = new JLabel();
            jLabel5.setText("Email Adresse");
            GridBagConstraints gridBagConstraints3 = new GridBagConstraints();
            gridBagConstraints3.gridx = 0;
            gridBagConstraints3.insets = new java.awt.Insets(0, 10, 0, 0);
            gridBagConstraints3.anchor = java.awt.GridBagConstraints.WEST;
            gridBagConstraints3.gridy = 7;
            jLabel4 = new JLabel();
            jLabel4.setText("Telefon Nr.");
            GridBagConstraints gridBagConstraints2 = new GridBagConstraints();
            gridBagConstraints2.gridx = 0;
            gridBagConstraints2.insets = new java.awt.Insets(0, 10, 0, 0);
            gridBagConstraints2.anchor = java.awt.GridBagConstraints.WEST;
            gridBagConstraints2.gridy = 5;
            jLabel3 = new JLabel();
            jLabel3.setText("PLZ / Ort");
            GridBagConstraints gridBagConstraints1 = new GridBagConstraints();
            gridBagConstraints1.gridx = 0;
            gridBagConstraints1.insets = new java.awt.Insets(0, 10, 0, 0);
            gridBagConstraints1.anchor = java.awt.GridBagConstraints.WEST;
            gridBagConstraints1.gridy = 4;
            jLabel2 = new JLabel();
            jLabel2.setText("Strasse");
            GridBagConstraints gridBagConstraints = new GridBagConstraints();
            gridBagConstraints.gridx = 0;
            gridBagConstraints.insets = new java.awt.Insets(0, 10, 0, 0);
            gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
            gridBagConstraints.gridy = 3;
            jLabel1 = new JLabel();
            jLabel1.setText("Nachname");
            jLabel = new JLabel();
            jLabel.setText("Vorname");
            jPanel = new JPanel();
            jPanel.setLayout(new GridBagLayout());
            jPanel.add(jLabel, gridBagConstraints21);
            jPanel.add(jLabel1, gridBagConstraints);
            jPanel.add(jLabel2, gridBagConstraints1);
            jPanel.add(jLabel3, gridBagConstraints2);
            jPanel.add(jLabel4, gridBagConstraints3);
            jPanel.add(jLabel5, gridBagConstraints4);
            jPanel.add(getJTextFieldPLZ(), gridBagConstraints5);
            jPanel.add(getJTextFieldOrt(), gridBagConstraints6);
            jPanel.add(getJTextFieldVorname(), gridBagConstraints7);
            jPanel.add(getJTextFieldName(), gridBagConstraints8);
            jPanel.add(getJTextFieldStrasse(), gridBagConstraints9);
            jPanel.add(getJTextFieldTelNR(), gridBagConstraints10);
            jPanel.add(getJTextFieldEMail(), gridBagConstraints11);
            jPanel.add(jLabel6, gridBagConstraints12);
            jPanel.add(getJFormattedTextFieldGebDat(), gridBagConstraints13);
            jPanel.add(getJComboBoxGeschlecht(), gridBagConstraints14);
            jPanel.add(jLabel7, gridBagConstraints22);
            jPanel.add(jLabel8, gridBagConstraints15);
            jPanel.add(getJTextFieldHandy(), gridBagConstraints23);
            jPanel.add(getJTextFieldNottel(), gridBagConstraints16);
            jPanel.add(jLabel51, gridBagConstraints24);
        }
        return jPanel;
    }

    private JPanel getJPanel1() {
        if (jPanel1 == null) {
            jPanel1 = new JPanel();
            jPanel1.add(getJButtonNeu(), null);
            jPanel1.add(getJButtonOK(), null);
            jPanel1.add(getJButtonAbbrechen(), null);
            jPanel1.add(getJButtonLoeschen(), null);
        }
        return jPanel1;
    }

    private JButton getJButtonOK() {
        if (jButtonOK == null) {
            jButtonOK = new JButton();
            jButtonOK.setText("OK");
            jButtonOK.addActionListener(e -> {
                speichern();

                setVisible(false);
            });
        }
        return jButtonOK;
    }

    private JButton getJButtonNeu() {
        if (jButtonNeu == null) {
            jButtonNeu = new JButton();
            jButtonNeu.setText("Neu");
            jButtonNeu.addActionListener(e -> {
                speichern();

                clearFields();
            });
        }
        return jButtonNeu;
    }

    private JButton getJButtonAbbrechen() {
        if (jButtonAbbrechen == null) {
            jButtonAbbrechen = new JButton();
            jButtonAbbrechen.setText("Abbrechen");
            jButtonAbbrechen.addActionListener(e -> setVisible(false));
        }
        return jButtonAbbrechen;
    }

    private JTextField getJTextFieldPLZ() {
        if (jTextFieldPLZ == null) {
            jTextFieldPLZ = new JTextField();
            jTextFieldPLZ.setText("78199");
        }
        return jTextFieldPLZ;
    }

    private JTextField getJTextFieldOrt() {
        if (jTextFieldOrt == null) {
            jTextFieldOrt = new JTextField();
            jTextFieldOrt.setText("Bräunlingen");
        }
        return jTextFieldOrt;
    }

    private JTextField getJTextFieldVorname() {
        if (jTextFieldVorname == null) {
            jTextFieldVorname = new JTextField();
        }
        return jTextFieldVorname;
    }

    private JTextField getJTextFieldName() {
        if (jTextFieldName == null) {
            jTextFieldName = new JTextField();
        }
        return jTextFieldName;
    }

    private JTextField getJTextFieldStrasse() {
        if (jTextFieldStrasse == null) {
            jTextFieldStrasse = new JTextField();
        }
        return jTextFieldStrasse;
    }

    private JTextField getJTextFieldTelNR() {
        if (jTextFieldTelNr == null) {
            jTextFieldTelNr = new JTextField();
        }
        return jTextFieldTelNr;
    }

    private JTextField getJTextFieldEMail() {
        if (jTextFieldEMail == null) {
            jTextFieldEMail = new JTextField();
        }
        return jTextFieldEMail;
    }

    private JFormattedTextField getJFormattedTextFieldGebDat() {
        if (jFormattedTextFieldGebDat == null) {
            jFormattedTextFieldGebDat = new JFormattedTextField(
                    new DateFormatter(DateFormat.getDateInstance(DateFormat.SHORT, Locale.GERMAN)));
        }
        return jFormattedTextFieldGebDat;
    }

    private JButton getJButtonLoeschen() {
        if (jButtonLoeschen == null) {
            jButtonLoeschen = new JButton();
            jButtonLoeschen.setText("Löschen");
            jButtonLoeschen.addActionListener(e -> clearFields());
        }
        return jButtonLoeschen;
    }

    private JComboBox<Geschlecht> getJComboBoxGeschlecht() {
        if (jComboBoxGeschlecht == null) {
            jComboBoxGeschlecht = comboboxBuilderGeschlecht.build();
        }
        return jComboBoxGeschlecht;
    }

    private JTextField getJTextFieldHandy() {
        if (jTextFieldHandy == null) {
            jTextFieldHandy = new JTextField();
        }
        return jTextFieldHandy;
    }

    private JTextField getJTextFieldNottel() {
        if (jTextFieldNottel == null) {
            jTextFieldNottel = new JTextField();
        }
        return jTextFieldNottel;
    }

    private void speichern() {
        Geschlecht geschlecht = (Geschlecht) getJComboBoxGeschlecht().getSelectedItem();
        String nachname = getJTextFieldName().getText();
        String vorname = getJTextFieldVorname().getText();
        String strasse = getJTextFieldStrasse().getText();
        String plz = getJTextFieldPLZ().getText();
        String ort = getJTextFieldOrt().getText();
        Date gebtag = (Date) getJFormattedTextFieldGebDat().getValue();
        String telnr = getJTextFieldTelNR().getText();
        String email = getJTextFieldEMail().getText();
        String handy = getJTextFieldHandy().getText();
        String nottel = getJTextFieldNottel().getText();

        get().speicherePerson(null, geschlecht, vorname, nachname, strasse, plz, ort, gebtag, telnr, email, handy,
                nottel, cb -> {
                });
    }

    private void clearFields() {
        getJComboBoxGeschlecht().setSelectedItem("");
        getJTextFieldName().setText("");
        getJTextFieldVorname().setText("");
        getJTextFieldStrasse().setText("");
        getJFormattedTextFieldGebDat().setValue(null);
        getJTextFieldTelNR().setText("");
        getJTextFieldEMail().setText("");
        getJTextFieldHandy().setText("");
        getJTextFieldNottel().setText("");
    }
}
