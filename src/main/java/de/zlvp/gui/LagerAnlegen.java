package de.zlvp.gui;

import static de.zlvp.Client.get;
import static java.util.Objects.isNull;

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

import de.zlvp.Events;
import de.zlvp.entity.Lagerort;
import de.zlvp.ui.AbstractJInternalFrame;
import de.zlvp.ui.JComboBoxBuilder;

public class LagerAnlegen extends AbstractJInternalFrame {

    private static final long serialVersionUID = 1L;

    private JPanel jContentPane;

    private JPanel jPanel;

    private JPanel jPanel1;
    private JLabel jLabel1;
    private JLabel jLabel2;
    private JLabel jLabel3;

    private JButton jButtonOK;

    private JButton jButtonNeu;

    private JButton jButtonAbbrechen;
    private JLabel jLabel4;
    private JLabel jLabel5;

    private JTextField jTextFieldName;

    private JTextField jTextFieldMotto;

    private JFormattedTextField jFormattedTextFieldDatStart;
    private JFormattedTextField jFormattedTextFieldDatStop;

    private JButton jButtonLoeschen;
    private JComboBox<Lagerort> jComboBoxLagerort;

    private JComboBoxBuilder<Lagerort> comboboxBuilderLagerort;

    private int jahrId;

    public LagerAnlegen(int jahrId) {
        this.jahrId = jahrId;
        comboboxBuilderLagerort = JComboBoxBuilder.get(Lagerort.class, get()::getAllLagerort);
        initialize();
        setupDialog();
    }

    private void initialize() {
        this.setSize(442, 218);
        this.setTitle("Lager anlegen");
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
            GridBagConstraints gridBagConstraints12 = new GridBagConstraints();
            gridBagConstraints12.fill = GridBagConstraints.BOTH;
            gridBagConstraints12.gridy = 4;
            gridBagConstraints12.weightx = 1.0;
            gridBagConstraints12.insets = new Insets(0, 10, 0, 10);
            gridBagConstraints12.gridx = 3;
            GridBagConstraints gridBagConstraints11 = new GridBagConstraints();
            gridBagConstraints11.fill = java.awt.GridBagConstraints.HORIZONTAL;
            gridBagConstraints11.gridy = 6;
            gridBagConstraints11.weightx = 1.0;
            gridBagConstraints11.gridwidth = 3;
            gridBagConstraints11.insets = new java.awt.Insets(0, 10, 0, 10);
            gridBagConstraints11.gridx = 1;
            GridBagConstraints gridBagConstraints10 = new GridBagConstraints();
            gridBagConstraints10.fill = java.awt.GridBagConstraints.HORIZONTAL;
            gridBagConstraints10.gridy = 5;
            gridBagConstraints10.weightx = 1.0;
            gridBagConstraints10.gridwidth = 3;
            gridBagConstraints10.insets = new java.awt.Insets(0, 10, 0, 10);
            gridBagConstraints10.gridx = 1;
            GridBagConstraints gridBagConstraints9 = new GridBagConstraints();
            gridBagConstraints9.fill = java.awt.GridBagConstraints.HORIZONTAL;
            gridBagConstraints9.gridy = 3;
            gridBagConstraints9.weightx = 1.0;
            gridBagConstraints9.gridwidth = 4;
            gridBagConstraints9.insets = new java.awt.Insets(0, 10, 0, 10);
            gridBagConstraints9.gridx = 1;
            GridBagConstraints gridBagConstraints8 = new GridBagConstraints();
            gridBagConstraints8.fill = java.awt.GridBagConstraints.HORIZONTAL;
            gridBagConstraints8.gridy = 2;
            gridBagConstraints8.weightx = 1.0;
            gridBagConstraints8.gridwidth = 3;
            gridBagConstraints8.insets = new java.awt.Insets(0, 10, 0, 10);
            gridBagConstraints8.gridx = 1;
            GridBagConstraints gridBagConstraints4 = new GridBagConstraints();
            gridBagConstraints4.gridx = 0;
            gridBagConstraints4.insets = new java.awt.Insets(0, 10, 0, 0);
            gridBagConstraints4.anchor = java.awt.GridBagConstraints.WEST;
            gridBagConstraints4.gridy = 6;
            jLabel5 = new JLabel();
            jLabel5.setText("Datum Stop");
            GridBagConstraints gridBagConstraints3 = new GridBagConstraints();
            gridBagConstraints3.gridx = 0;
            gridBagConstraints3.insets = new java.awt.Insets(0, 10, 0, 0);
            gridBagConstraints3.anchor = java.awt.GridBagConstraints.WEST;
            gridBagConstraints3.gridy = 5;
            jLabel4 = new JLabel();
            jLabel4.setText("Datum Start");
            GridBagConstraints gridBagConstraints2 = new GridBagConstraints();
            gridBagConstraints2.gridx = 0;
            gridBagConstraints2.insets = new java.awt.Insets(0, 10, 0, 0);
            gridBagConstraints2.anchor = java.awt.GridBagConstraints.WEST;
            gridBagConstraints2.gridy = 4;
            jLabel3 = new JLabel();
            jLabel3.setText("Ort");
            GridBagConstraints gridBagConstraints1 = new GridBagConstraints();
            gridBagConstraints1.gridx = 0;
            gridBagConstraints1.insets = new java.awt.Insets(0, 10, 0, 0);
            gridBagConstraints1.anchor = java.awt.GridBagConstraints.WEST;
            gridBagConstraints1.gridy = 3;
            jLabel2 = new JLabel();
            jLabel2.setText("Motto");
            GridBagConstraints gridBagConstraints = new GridBagConstraints();
            gridBagConstraints.gridx = 0;
            gridBagConstraints.insets = new java.awt.Insets(0, 10, 0, 0);
            gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
            gridBagConstraints.gridy = 2;
            jLabel1 = new JLabel();
            jLabel1.setText("Name");
            jPanel = new JPanel();
            jPanel.setLayout(new GridBagLayout());
            jPanel.add(jLabel1, gridBagConstraints);
            jPanel.add(jLabel2, gridBagConstraints1);
            jPanel.add(jLabel3, gridBagConstraints2);
            jPanel.add(jLabel4, gridBagConstraints3);
            jPanel.add(jLabel5, gridBagConstraints4);
            jPanel.add(getJTextFieldName(), gridBagConstraints8);
            jPanel.add(getJTextFieldMotto(), gridBagConstraints9);
            jPanel.add(getJFormattedTextFieldDatStart(), gridBagConstraints10);
            jPanel.add(getJFormattedTextFieldDatStop(), gridBagConstraints11);
            jPanel.add(getJComboBoxLagerort(), gridBagConstraints12);
        }
        return jPanel;
    }

    private JPanel getJPanel1() {
        if (jPanel1 == null) {
            jPanel1 = new JPanel();
            jPanel1.add(getJButtonOK(), null);
            jPanel1.add(getJButtonAbbrechen(), null);
            jPanel1.add(getJButtonNeu(), null);
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

                getJTextFieldMotto().setText("");
                getJTextFieldName().setText("");
                getJComboBoxLagerort().setSelectedIndex(-1);
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

    private JTextField getJTextFieldName() {
        if (jTextFieldName == null) {
            jTextFieldName = new JTextField();
        }
        return jTextFieldName;
    }

    private JTextField getJTextFieldMotto() {
        if (jTextFieldMotto == null) {
            jTextFieldMotto = new JTextField();
        }
        return jTextFieldMotto;
    }

    private JFormattedTextField getJFormattedTextFieldDatStart() {
        if (jFormattedTextFieldDatStart == null) {
            jFormattedTextFieldDatStart = new JFormattedTextField(
                    new DateFormatter(DateFormat.getDateInstance(DateFormat.SHORT, Locale.GERMAN)));
            jFormattedTextFieldDatStart.setValue(new Date());
        }
        return jFormattedTextFieldDatStart;
    }

    private JFormattedTextField getJFormattedTextFieldDatStop() {
        if (jFormattedTextFieldDatStop == null) {
            jFormattedTextFieldDatStop = new JFormattedTextField(
                    new DateFormatter(DateFormat.getDateInstance(DateFormat.SHORT, Locale.GERMAN)));
            jFormattedTextFieldDatStop.setValue(new Date());
        }
        return jFormattedTextFieldDatStop;
    }

    private JButton getJButtonLoeschen() {
        if (jButtonLoeschen == null) {
            jButtonLoeschen = new JButton();
            jButtonLoeschen.setText("Löschen");
            jButtonLoeschen.addActionListener(e -> {
                getJTextFieldMotto().setText("");
                getJTextFieldName().setText("");
                getJComboBoxLagerort().setSelectedIndex(-1);
                getJFormattedTextFieldDatStart().setValue(new Date());
                getJFormattedTextFieldDatStop().setValue(new Date());
            });
        }
        return jButtonLoeschen;
    }

    private JComboBox<Lagerort> getJComboBoxLagerort() {
        if (jComboBoxLagerort == null) {
            jComboBoxLagerort = comboboxBuilderLagerort.build();
            jComboBoxLagerort.setSelectedIndex(-1);
        }
        return jComboBoxLagerort;
    }

    private void speichern() {
        Date start = (Date) getJFormattedTextFieldDatStart().getValue();
        Date stop = (Date) getJFormattedTextFieldDatStop().getValue();
        String sName = getJTextFieldName().getText();
        String sThema = getJTextFieldMotto().getText();
        Lagerort lagerort = (Lagerort) getJComboBoxLagerort().getSelectedItem();

        if (isNull(start) || isNull(stop) || isNull(sName) || sName.isEmpty() || isNull(lagerort)) {
            throw new RuntimeException("Folgende Felder müssen gefüllt sein: Name, Ort, Datum Start und Datum Stop");
        }
        // Das Lager bei fireLagerSaved wird aktuell noch nicht benötigt.
        get().speichereLager(null, sName, sThema, start, stop, jahrId, lagerort.getId(), cb -> Events.get().fireLagerSaved(null));
    }

}
