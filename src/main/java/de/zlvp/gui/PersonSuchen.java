package de.zlvp.gui;

import de.zlvp.Client;
import de.zlvp.entity.Geschlecht;
import de.zlvp.entity.Person;
import de.zlvp.ui.AbstractJInternalFrame;
import de.zlvp.ui.JComboBoxBuilder;
import de.zlvp.ui.JListBuilder;
import de.zlvp.ui.JListTransferHandler;
import java.awt.*;
import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;
import javax.swing.*;
import javax.swing.text.DateFormatter;

import static de.zlvp.Client.get;
import static java.util.Arrays.asList;
import static java.util.Collections.singletonList;

public class PersonSuchen extends AbstractJInternalFrame {

    private static final long serialVersionUID = 1L;

    private JPanel jContentPane;

    private JPanel jPanel;

    private JPanel jPanel3;

    private JButton jButtonSuchen;

    private JTextField jTextFieldVorname;

    private JTextField jTextFieldName;

    private JPanel jPanel1;
    private JLabel jLabelVorname;
    private JLabel jLabelName;

    private JScrollPane jScrollPane;

    private JList<Person> jListPerson;

    private JButton jButtonOK;

    private JPanel jPanel4;

    private JTextField jTextFieldAeName;

    private JTextField jTextFieldAeVorname;

    private JTextField jTextFieldStrasse;

    private JTextField jTextFieldPLZ;

    private JTextField jTextFieldOrt;

    private JFormattedTextField jFormattedTextFieldGebDat;

    private JTextField jTextFieldTelNr;

    private JTextField jTextFieldEMail;
    private JLabel jLabel;
    private JLabel jLabel1;
    private JLabel jLabel2;
    private JLabel jLabel3;
    private JLabel jLabel4;
    private JLabel jLabel5;
    private JLabel jLabel6;
    private JLabel jLabel7;

    private JSplitPane jSplitPane;

    private JPanel jPanel2;

    private JComboBox<Geschlecht> jComboBoxGeschlecht;
    private JLabel jLabel8;
    private JLabel jLabel9;
    private JTextField jTextFieldHandy;
    private JTextField jTextFieldNottel;
    private JLabel jLabel10;
    private JButton jButtonStatistik;

    private JButton jButtonAbbrechen;

    private final JComboBoxBuilder<Geschlecht> comboboxBuilderGeschlecht;

    private final JListBuilder<Person> jListBuilder;

    public PersonSuchen() {
        comboboxBuilderGeschlecht = JComboBoxBuilder
                .get(Geschlecht.class, allGeschlecht -> allGeschlecht.get(asList(Geschlecht.values())))
                .map(Geschlecht::getBezeichnung);

        jListBuilder = JListBuilder.get(Person.class, asyncCallback -> get()
                .findPerson(getJTextFieldVorname().getText(), getJTextFieldName().getText(), asyncCallback));

        initialize();
        setupDialog();
        getJButtonOK().setEnabled(false);
        getJButtonStatistik().setEnabled(false);
    }

    public PersonSuchen(int personId) {
        comboboxBuilderGeschlecht = JComboBoxBuilder
                .get(Geschlecht.class, allGeschlecht -> allGeschlecht.get(asList(Geschlecht.values())))
                .map(Geschlecht::getBezeichnung);

        jListBuilder =
                JListBuilder.get(Person.class, asyncCallback -> get().getPerson(personId, cb -> {
                    asyncCallback.get(singletonList(cb));
                    getJListPerson().setSelectedValue(cb, true);
                }));
        initialize();
        jListBuilder.refresh();
        setupDialog();
    }

    private void initialize() {
        this.setSize(500, 598);
        this.setTitle("Person Suchen / Ändern");
        this.setContentPane(getJContentPane());
        this.getRootPane().setDefaultButton(getJButtonSuchen());
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
            jPanel.add(getJPanel3(), java.awt.BorderLayout.SOUTH);
            jPanel.add(getJPanel1(), java.awt.BorderLayout.NORTH);
            jPanel.add(getJPanel2(), java.awt.BorderLayout.CENTER);
        }
        return jPanel;
    }

    private JPanel getJPanel3() {
        if (jPanel3 == null) {
            jPanel3 = new JPanel();
            jPanel3.add(getJButtonOK(), null);
            jPanel3.add(getJButtonStatistik(), null);
            jPanel3.add(getJButtonAbbrechen(), null);
        }
        return jPanel3;
    }

    private JButton getJButtonSuchen() {
        if (jButtonSuchen == null) {
            jButtonSuchen = new JButton();
            jButtonSuchen.setText("Suchen");
            jButtonSuchen.addActionListener(e -> suchen());
        }
        return jButtonSuchen;
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

    private JPanel getJPanel1() {
        if (jPanel1 == null) {
            GridBagConstraints gridBagConstraints3 = new GridBagConstraints();
            gridBagConstraints3.gridx = 0;
            gridBagConstraints3.insets = new java.awt.Insets(0, 10, 0, 0);
            gridBagConstraints3.gridy = 0;
            jLabelName = new JLabel();
            jLabelName.setText("Nachname");
            GridBagConstraints gridBagConstraints2 = new GridBagConstraints();
            gridBagConstraints2.gridx = 2;
            gridBagConstraints2.gridy = 0;
            jLabelVorname = new JLabel();
            jLabelVorname.setText("Vorname");
            GridBagConstraints gridBagConstraints11 = new GridBagConstraints();
            gridBagConstraints11.gridx = 5;
            gridBagConstraints11.insets = new java.awt.Insets(0, 0, 0, 10);
            GridBagConstraints gridBagConstraints1 = new GridBagConstraints();
            gridBagConstraints1.fill = java.awt.GridBagConstraints.HORIZONTAL;
            gridBagConstraints1.gridx = 1;
            gridBagConstraints1.gridy = 0;
            gridBagConstraints1.insets = new java.awt.Insets(10, 10, 10, 10);
            gridBagConstraints1.weightx = 1.0;
            GridBagConstraints gridBagConstraints = new GridBagConstraints();
            gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
            gridBagConstraints.gridx = 3;
            gridBagConstraints.insets = new java.awt.Insets(10, 10, 10, 10);
            gridBagConstraints.weightx = 1.0;
            jPanel1 = new JPanel();
            jPanel1.setLayout(new GridBagLayout());
            jPanel1.add(getJTextFieldVorname(), gridBagConstraints);
            jPanel1.add(getJTextFieldName(), gridBagConstraints1);
            jPanel1.add(getJButtonSuchen(), gridBagConstraints11);
            jPanel1.add(jLabelVorname, gridBagConstraints2);
            jPanel1.add(jLabelName, gridBagConstraints3);
        }
        return jPanel1;
    }

    private JScrollPane getJScrollPane() {
        if (jScrollPane == null) {
            jScrollPane = new JScrollPane();
            jScrollPane.setViewportView(getJListPerson());
        }
        return jScrollPane;
    }

    private JList<Person> getJListPerson() {
        if (jListPerson == null) {
            jListPerson = jListBuilder.map(p -> p.getName() + ", " + p.getVorname()).build();
            jListPerson.addListSelectionListener(e -> {
                Person p = getJListPerson().getSelectedValue();
                if (p != null) {
                    getJComboBoxGeschlecht().setSelectedItem(p.getGeschlecht());

                    getJTextFieldAeName().setText(p.getName());
                    getJTextFieldAeVorname().setText(p.getVorname());
                    getJTextFieldStrasse().setText(p.getStrasse());
                    getJTextFieldPLZ().setText(p.getPlz());
                    getJTextFieldOrt().setText(p.getOrt());
                    getJFormattedTextFieldGebDat().setValue(p.getGebDat());
                    getJTextFieldTelNr().setText(p.getTelNr());
                    getJTextFieldEMail().setText(p.getEmail());
                    getJTextFieldHandy().setText(p.getHandy());
                    getJTextFieldNottel().setText(p.getNottel());

                    getJButtonOK().setEnabled(true);
                    getJButtonStatistik().setEnabled(true);
                } else {
                    getJButtonOK().setEnabled(false);
                    getJButtonStatistik().setEnabled(false);
                }

            });
            jListPerson.setDragEnabled(true);
            jListPerson.setTransferHandler(new JListTransferHandler());
        }
        return jListPerson;
    }

    private JButton getJButtonOK() {
        if (jButtonOK == null) {
            jButtonOK = new JButton();
            jButtonOK.setText("OK");
            jButtonOK.addActionListener(e -> {
                Person selectedPerson = getJListPerson().getSelectedValue();
                Geschlecht geschlecht = (Geschlecht) getJComboBoxGeschlecht().getSelectedItem();

                String name = getJTextFieldAeName().getText();
                String vorname = getJTextFieldAeVorname().getText();
                String strasse = getJTextFieldStrasse().getText();
                String plz = getJTextFieldPLZ().getText();
                String ort = getJTextFieldOrt().getText();
                Date gebtag = (Date) getJFormattedTextFieldGebDat().getValue();
                String telnr = getJTextFieldTelNr().getText();
                String email = getJTextFieldEMail().getText();
                String handy = getJTextFieldHandy().getText();
                String nottel = getJTextFieldNottel().getText();

                get().speicherePerson(selectedPerson.getId(), geschlecht, vorname, name, strasse, plz, ort, gebtag,
                        telnr, email, handy, nottel, asyncCallback -> suchen());
            });
        }
        return jButtonOK;
    }

    private JPanel getJPanel4() {
        if (jPanel4 == null) {
            GridBagConstraints gridBagConstraints27 = new GridBagConstraints();
            gridBagConstraints27.gridx = 0;
            gridBagConstraints27.anchor = GridBagConstraints.WEST;
            gridBagConstraints27.insets = new Insets(10, 0, 0, 0);
            gridBagConstraints27.gridy = 10;
            jLabel10 = new JLabel();
            jLabel10.setText("Not Telefonnummer");
            GridBagConstraints gridBagConstraints26 = new GridBagConstraints();
            gridBagConstraints26.fill = GridBagConstraints.BOTH;
            gridBagConstraints26.gridy = 10;
            gridBagConstraints26.weightx = 1.0;
            gridBagConstraints26.insets = new Insets(10, 10, 0, 0);
            gridBagConstraints26.gridwidth = 3;
            gridBagConstraints26.gridx = 1;
            GridBagConstraints gridBagConstraints25 = new GridBagConstraints();
            gridBagConstraints25.fill = GridBagConstraints.BOTH;
            gridBagConstraints25.gridy = 8;
            gridBagConstraints25.weightx = 1.0;
            gridBagConstraints25.insets = new Insets(10, 10, 0, 0);
            gridBagConstraints25.gridwidth = 4;
            gridBagConstraints25.gridx = 1;
            GridBagConstraints gridBagConstraints24 = new GridBagConstraints();
            gridBagConstraints24.gridx = 0;
            gridBagConstraints24.insets = new Insets(10, 0, 0, 0);
            gridBagConstraints24.anchor = GridBagConstraints.WEST;
            gridBagConstraints24.gridy = 8;
            jLabel9 = new JLabel();
            jLabel9.setText("Handynummer");
            GridBagConstraints gridBagConstraints23 = new GridBagConstraints();
            gridBagConstraints23.gridx = 0;
            gridBagConstraints23.anchor = java.awt.GridBagConstraints.WEST;
            gridBagConstraints23.insets = new java.awt.Insets(0, 0, 0, 0);
            gridBagConstraints23.gridy = 0;
            jLabel8 = new JLabel();
            jLabel8.setText("Geschlecht");
            GridBagConstraints gridBagConstraints22 = new GridBagConstraints();
            gridBagConstraints22.fill = java.awt.GridBagConstraints.HORIZONTAL;
            gridBagConstraints22.gridy = 0;
            gridBagConstraints22.weightx = 1.0;
            gridBagConstraints22.insets = new java.awt.Insets(0, 10, 0, 10);
            gridBagConstraints22.gridx = 1;
            GridBagConstraints gridBagConstraints20 = new GridBagConstraints();
            gridBagConstraints20.gridx = 2;
            gridBagConstraints20.insets = new java.awt.Insets(10, 0, 0, 10);
            gridBagConstraints20.gridy = 5;
            jLabel7 = new JLabel();
            jLabel7.setText("Ort");
            GridBagConstraints gridBagConstraints19 = new GridBagConstraints();
            gridBagConstraints19.gridx = 0;
            gridBagConstraints19.insets = new Insets(10, 0, 0, 0);
            gridBagConstraints19.anchor = java.awt.GridBagConstraints.WEST;
            gridBagConstraints19.gridy = 9;
            jLabel6 = new JLabel();
            jLabel6.setText("EMail");
            GridBagConstraints gridBagConstraints18 = new GridBagConstraints();
            gridBagConstraints18.gridx = 0;
            gridBagConstraints18.insets = new java.awt.Insets(10, 0, 0, 0);
            gridBagConstraints18.anchor = java.awt.GridBagConstraints.WEST;
            gridBagConstraints18.gridy = 7;
            jLabel5 = new JLabel();
            jLabel5.setText("Telefonnummer");
            GridBagConstraints gridBagConstraints17 = new GridBagConstraints();
            gridBagConstraints17.gridx = 0;
            gridBagConstraints17.insets = new java.awt.Insets(10, 0, 0, 0);
            gridBagConstraints17.anchor = java.awt.GridBagConstraints.WEST;
            gridBagConstraints17.gridy = 6;
            jLabel4 = new JLabel();
            jLabel4.setText("Geburtstag");
            GridBagConstraints gridBagConstraints16 = new GridBagConstraints();
            gridBagConstraints16.gridx = 0;
            gridBagConstraints16.insets = new java.awt.Insets(10, 0, 0, 0);
            gridBagConstraints16.anchor = java.awt.GridBagConstraints.WEST;
            gridBagConstraints16.gridy = 5;
            jLabel3 = new JLabel();
            jLabel3.setText("PLZ");
            GridBagConstraints gridBagConstraints15 = new GridBagConstraints();
            gridBagConstraints15.gridx = 0;
            gridBagConstraints15.insets = new java.awt.Insets(10, 0, 0, 0);
            gridBagConstraints15.anchor = java.awt.GridBagConstraints.WEST;
            gridBagConstraints15.gridy = 3;
            jLabel2 = new JLabel();
            jLabel2.setText("Strasse");
            GridBagConstraints gridBagConstraints14 = new GridBagConstraints();
            gridBagConstraints14.gridx = 0;
            gridBagConstraints14.insets = new java.awt.Insets(10, 0, 0, 0);
            gridBagConstraints14.anchor = java.awt.GridBagConstraints.WEST;
            gridBagConstraints14.gridy = 2;
            jLabel1 = new JLabel();
            jLabel1.setText("Vorname");
            GridBagConstraints gridBagConstraints13 = new GridBagConstraints();
            gridBagConstraints13.gridx = 0;
            gridBagConstraints13.insets = new java.awt.Insets(10, 0, 0, 0);
            gridBagConstraints13.anchor = java.awt.GridBagConstraints.WEST;
            gridBagConstraints13.gridy = 1;
            jLabel = new JLabel();
            jLabel.setText("Name");
            GridBagConstraints gridBagConstraints12 = new GridBagConstraints();
            gridBagConstraints12.fill = java.awt.GridBagConstraints.HORIZONTAL;
            gridBagConstraints12.gridy = 9;
            gridBagConstraints12.weightx = 1.0;
            gridBagConstraints12.gridwidth = 3;
            gridBagConstraints12.insets = new Insets(10, 10, 0, 0);
            gridBagConstraints12.gridx = 1;
            GridBagConstraints gridBagConstraints10 = new GridBagConstraints();
            gridBagConstraints10.fill = java.awt.GridBagConstraints.HORIZONTAL;
            gridBagConstraints10.gridy = 7;
            gridBagConstraints10.weightx = 1.0;
            gridBagConstraints10.gridwidth = 3;
            gridBagConstraints10.insets = new java.awt.Insets(10, 10, 0, 0);
            gridBagConstraints10.gridx = 1;
            GridBagConstraints gridBagConstraints9 = new GridBagConstraints();
            gridBagConstraints9.fill = java.awt.GridBagConstraints.HORIZONTAL;
            gridBagConstraints9.gridy = 6;
            gridBagConstraints9.weightx = 1.0;
            gridBagConstraints9.gridwidth = 3;
            gridBagConstraints9.insets = new java.awt.Insets(10, 10, 0, 0);
            gridBagConstraints9.gridx = 1;
            GridBagConstraints gridBagConstraints8 = new GridBagConstraints();
            gridBagConstraints8.fill = java.awt.GridBagConstraints.HORIZONTAL;
            gridBagConstraints8.gridy = 5;
            gridBagConstraints8.weightx = 1.0;
            gridBagConstraints8.insets = new java.awt.Insets(10, 0, 0, 0);
            gridBagConstraints8.gridx = 3;
            GridBagConstraints gridBagConstraints7 = new GridBagConstraints();
            gridBagConstraints7.fill = java.awt.GridBagConstraints.HORIZONTAL;
            gridBagConstraints7.gridy = 5;
            gridBagConstraints7.weightx = 1.0;
            gridBagConstraints7.insets = new java.awt.Insets(10, 10, 0, 10);
            gridBagConstraints7.gridx = 1;
            GridBagConstraints gridBagConstraints6 = new GridBagConstraints();
            gridBagConstraints6.fill = java.awt.GridBagConstraints.HORIZONTAL;
            gridBagConstraints6.gridy = 3;
            gridBagConstraints6.weightx = 1.0;
            gridBagConstraints6.gridwidth = 3;
            gridBagConstraints6.insets = new java.awt.Insets(10, 10, 0, 0);
            gridBagConstraints6.gridx = 1;
            GridBagConstraints gridBagConstraints5 = new GridBagConstraints();
            gridBagConstraints5.fill = java.awt.GridBagConstraints.HORIZONTAL;
            gridBagConstraints5.gridy = 2;
            gridBagConstraints5.weightx = 1.0;
            gridBagConstraints5.gridwidth = 3;
            gridBagConstraints5.insets = new java.awt.Insets(10, 10, 0, 0);
            gridBagConstraints5.gridx = 1;
            GridBagConstraints gridBagConstraints4 = new GridBagConstraints();
            gridBagConstraints4.fill = java.awt.GridBagConstraints.HORIZONTAL;
            gridBagConstraints4.gridy = 1;
            gridBagConstraints4.gridx = 1;
            gridBagConstraints4.gridwidth = 3;
            gridBagConstraints4.insets = new java.awt.Insets(10, 10, 0, 0);
            gridBagConstraints4.weightx = 1.0;
            jPanel4 = new JPanel();
            jPanel4.setLayout(new GridBagLayout());
            jPanel4.add(getJTextFieldAeName(), gridBagConstraints4);
            jPanel4.add(getJTextFieldAeVorname(), gridBagConstraints5);
            jPanel4.add(getJTextFieldStrasse(), gridBagConstraints6);
            jPanel4.add(getJTextFieldPLZ(), gridBagConstraints7);
            jPanel4.add(getJTextFieldOrt(), gridBagConstraints8);
            jPanel4.add(getJFormattedTextFieldGebDat(), gridBagConstraints9);
            jPanel4.add(getJTextFieldTelNr(), gridBagConstraints10);
            jPanel4.add(getJTextFieldEMail(), gridBagConstraints12);
            jPanel4.add(jLabel, gridBagConstraints13);
            jPanel4.add(jLabel1, gridBagConstraints14);
            jPanel4.add(jLabel2, gridBagConstraints15);
            jPanel4.add(jLabel3, gridBagConstraints16);
            jPanel4.add(jLabel4, gridBagConstraints17);
            jPanel4.add(jLabel5, gridBagConstraints18);
            jPanel4.add(jLabel6, gridBagConstraints19);
            jPanel4.add(jLabel7, gridBagConstraints20);
            jPanel4.add(getJComboBoxGeschlecht(), gridBagConstraints22);
            jPanel4.add(jLabel8, gridBagConstraints23);
            jPanel4.add(jLabel9, gridBagConstraints24);
            jPanel4.add(getJTextFieldHandy(), gridBagConstraints25);
            jPanel4.add(getJTextFieldNottel(), gridBagConstraints26);
            jPanel4.add(jLabel10, gridBagConstraints27);
        }
        return jPanel4;
    }

    private JTextField getJTextFieldAeName() {
        if (jTextFieldAeName == null) {
            jTextFieldAeName = new JTextField();
        }
        return jTextFieldAeName;
    }

    private JTextField getJTextFieldAeVorname() {
        if (jTextFieldAeVorname == null) {
            jTextFieldAeVorname = new JTextField();
        }
        return jTextFieldAeVorname;
    }

    private JTextField getJTextFieldStrasse() {
        if (jTextFieldStrasse == null) {
            jTextFieldStrasse = new JTextField();
        }
        return jTextFieldStrasse;
    }

    private JTextField getJTextFieldPLZ() {
        if (jTextFieldPLZ == null) {
            jTextFieldPLZ = new JTextField();
        }
        return jTextFieldPLZ;
    }

    private JTextField getJTextFieldOrt() {
        if (jTextFieldOrt == null) {
            jTextFieldOrt = new JTextField();
        }
        return jTextFieldOrt;
    }

    private JFormattedTextField getJFormattedTextFieldGebDat() {
        if (jFormattedTextFieldGebDat == null) {
            jFormattedTextFieldGebDat = new JFormattedTextField(
                    new DateFormatter(DateFormat.getDateInstance(DateFormat.SHORT, Locale.GERMAN)));
        }
        return jFormattedTextFieldGebDat;
    }

    private JTextField getJTextFieldTelNr() {
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

    private JSplitPane getJSplitPane() {
        if (jSplitPane == null) {
            jSplitPane = new JSplitPane();
            jSplitPane.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);
            jSplitPane.setBottomComponent(getJPanel4());
            jSplitPane.setTopComponent(getJScrollPane());
            jSplitPane.setDividerLocation(172);
        }
        return jSplitPane;
    }

    private JPanel getJPanel2() {
        if (jPanel2 == null) {
            GridBagConstraints gridBagConstraints21 = new GridBagConstraints();
            gridBagConstraints21.fill = java.awt.GridBagConstraints.BOTH;
            gridBagConstraints21.gridx = 0;
            gridBagConstraints21.gridy = 0;
            gridBagConstraints21.ipadx = 0;
            gridBagConstraints21.weightx = 1.0;
            gridBagConstraints21.weighty = 1.0;
            gridBagConstraints21.insets = new java.awt.Insets(0, 10, 0, 10);
            jPanel2 = new JPanel();
            jPanel2.setLayout(new GridBagLayout());
            jPanel2.add(getJSplitPane(), gridBagConstraints21);
        }
        return jPanel2;
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

    private JButton getJButtonStatistik() {
        if (jButtonStatistik == null) {
            jButtonStatistik = new JButton();
            jButtonStatistik.setText("Statistik");
            jButtonStatistik.addActionListener(e -> {
                if (getJListPerson().getSelectedValue() != null) {
                    Person person = getJListPerson().getSelectedValue();
                    Client.getReports().teilnehmerStatistik(person.getId());
                }
            });
        }
        return jButtonStatistik;
    }

    private JButton getJButtonAbbrechen() {
        if (jButtonAbbrechen == null) {
            jButtonAbbrechen = new JButton();
            jButtonAbbrechen.setText("Abbrechen");
            jButtonAbbrechen.addActionListener(e -> setVisible(false));
        }
        return jButtonAbbrechen;
    }

    private void suchen() {
        Person selectedPerson = getJListPerson().getSelectedValue();
        jListBuilder.refresh();
        getJListPerson().setSelectedValue(selectedPerson, true);
    }
}
