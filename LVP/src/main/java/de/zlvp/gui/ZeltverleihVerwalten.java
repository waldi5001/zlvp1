package de.zlvp.gui;

import static de.zlvp.Client.get;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.text.DateFormatter;

import de.javasoft.swing.JYTableScrollPane;
import de.zlvp.entity.Zelt;
import de.zlvp.entity.Zeltverleih;
import de.zlvp.ui.AbstractJInternalFrame;
import de.zlvp.ui.JTableBuilder;
import de.zlvp.ui.JTableBuilder.ColumnBuilder;

public class ZeltverleihVerwalten extends AbstractJInternalFrame {

    private static final long serialVersionUID = -8229650640088466174L;
    private JPanel jContentPane;
    private JPanel jPanel;
    private JPanel jPanel1;
    private JPanel jPanel2;
    private JSplitPane jSplitPane;
    private JPanel jPanel3;
    private JPanel jPanel4;
    private JTextField jTextFieldPerson;
    private JTable jTable;
    private JPanel jPanel5;
    private JScrollPane jScrollPane;
    private JTextArea jTextAreaBemerkung;
    private JFormattedTextField jFormattedTextFieldDatum;
    private JLabel jLabel;
    private JScrollPane jScrollPane1;
    private JLabel jLabel1;
    private JLabel jLabel2;
    private JButton jButtonAbbrechen;
    private JButton jButtonNeuerVerleih;

    private JButton jButtonAendern;
    private JButton jButtonLoeschen;

    private final JTableBuilder<Zeltverleih> tableBuilder;

    private Zeltverleih selectedVerleih;

    private final Zelt zelt;

    public ZeltverleihVerwalten(Zelt zelt) {
        this.zelt = zelt;
        tableBuilder = JTableBuilder.get(Zeltverleih.class,
                asyncCallback -> get().getAllZeltverleih(zelt.getId(), asyncCallback));
        initialize();
        setupDialog();
        getJButtonLoeschen().setEnabled(false);
        getJButtonAendern().setEnabled(false);
    }

    private void initialize() {
        this.setSize(450, 500);
        this.setTitle("Zeltverleih verwalten");
        this.setContentPane(getJContentPane());
    }

    private JPanel getJContentPane() {
        if (jContentPane == null) {
            jContentPane = new JPanel();
            jContentPane.setLayout(new BorderLayout());
            jContentPane.add(getJPanel(), BorderLayout.CENTER);
        }
        return jContentPane;
    }

    private JPanel getJPanel() {
        if (jPanel == null) {
            jPanel = new JPanel();
            jPanel.setLayout(new BorderLayout());
            jPanel.add(getJPanel1(), BorderLayout.SOUTH);
            jPanel.add(getJPanel2(), BorderLayout.CENTER);
        }
        return jPanel;
    }

    private JPanel getJPanel1() {
        if (jPanel1 == null) {
            jPanel1 = new JPanel();
            jPanel1.setLayout(new FlowLayout());
            jPanel1.add(getJButtonNeuerVerleih(), null);
            jPanel1.add(getJButtonAendern(), null);
            jPanel1.add(getJButtonLoeschen(), null);
            jPanel1.add(getJButtonAbbrechen(), null);
        }
        return jPanel1;
    }

    private JPanel getJPanel2() {
        if (jPanel2 == null) {
            jPanel2 = new JPanel();
            jPanel2.setLayout(new BorderLayout());
            jPanel2.add(getJSplitPane(), BorderLayout.CENTER);
        }
        return jPanel2;
    }

    private JSplitPane getJSplitPane() {
        if (jSplitPane == null) {
            jSplitPane = new JSplitPane();
            jSplitPane.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);
            jSplitPane.setBottomComponent(getJPanel4());
            jSplitPane.setTopComponent(getJPanel3());
            jSplitPane.setDividerLocation(250);
        }
        return jSplitPane;
    }

    private JPanel getJPanel3() {
        if (jPanel3 == null) {
            jPanel3 = new JPanel();
            jPanel3.setLayout(new BorderLayout());
            jPanel3.add(getJPanel5(), BorderLayout.NORTH);
        }
        return jPanel3;
    }

    private JPanel getJPanel4() {
        if (jPanel4 == null) {
            GridBagConstraints gridBagConstraints2 = new GridBagConstraints();
            gridBagConstraints2.gridx = 1;
            gridBagConstraints2.insets = new Insets(0, 10, 0, 0);
            gridBagConstraints2.anchor = GridBagConstraints.WEST;
            gridBagConstraints2.gridy = 3;
            jLabel2 = new JLabel();
            jLabel2.setText("Bemerkung");
            GridBagConstraints gridBagConstraints11 = new GridBagConstraints();
            gridBagConstraints11.gridx = 1;
            gridBagConstraints11.insets = new Insets(10, 10, 0, 0);
            gridBagConstraints11.anchor = GridBagConstraints.WEST;
            gridBagConstraints11.gridy = 2;
            jLabel1 = new JLabel();
            jLabel1.setText("Person");
            GridBagConstraints gridBagConstraints5 = new GridBagConstraints();
            gridBagConstraints5.fill = GridBagConstraints.BOTH;
            gridBagConstraints5.weighty = 1.0;
            gridBagConstraints5.gridx = 2;
            gridBagConstraints5.gridy = 3;
            gridBagConstraints5.insets = new Insets(10, 10, 10, 10);
            gridBagConstraints5.weightx = 1.0;
            GridBagConstraints gridBagConstraints4 = new GridBagConstraints();
            gridBagConstraints4.gridx = 1;
            gridBagConstraints4.insets = new Insets(10, 10, 0, 0);
            gridBagConstraints4.anchor = GridBagConstraints.WEST;
            gridBagConstraints4.gridy = 1;
            jLabel = new JLabel();
            jLabel.setText("Datum");
            GridBagConstraints gridBagConstraints3 = new GridBagConstraints();
            gridBagConstraints3.fill = GridBagConstraints.HORIZONTAL;
            gridBagConstraints3.gridx = 2;
            gridBagConstraints3.gridy = 1;
            gridBagConstraints3.insets = new Insets(10, 10, 0, 10);
            gridBagConstraints3.weightx = 1.0;
            GridBagConstraints gridBagConstraints = new GridBagConstraints();
            gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
            gridBagConstraints.gridx = 2;
            gridBagConstraints.gridy = 2;
            gridBagConstraints.insets = new Insets(10, 10, 0, 10);
            gridBagConstraints.weightx = 1.0;
            jPanel4 = new JPanel();
            jPanel4.setLayout(new GridBagLayout());
            jPanel4.add(getJTextFieldPerson(), gridBagConstraints);
            jPanel4.add(getJFormattedTextFieldDatum(), gridBagConstraints3);
            jPanel4.add(jLabel, gridBagConstraints4);
            jPanel4.add(getJScrollPane1(), gridBagConstraints5);
            jPanel4.add(jLabel1, gridBagConstraints11);
            jPanel4.add(jLabel2, gridBagConstraints2);
        }
        return jPanel4;
    }

    private JTextField getJTextFieldPerson() {
        if (jTextFieldPerson == null) {
            jTextFieldPerson = new JTextField();
        }
        return jTextFieldPerson;
    }

    private JTable getJTable() {
        if (jTable == null) {
            jTable = tableBuilder//
                    .addColumn(ColumnBuilder.get(Date.class).add("Datum").preferredWidth(30).build())//
                    .addColumn(ColumnBuilder.get(String.class).add("Person").build())//
                    .addColumn(ColumnBuilder.get(String.class).add("Bemerkung").build())//
                    .get((zv, index) -> {
                        if (index == 0) {
                            return zv.getDatum();
                        } else if (index == 1) {
                            return zv.getPerson();
                        } else if (index == 2) {
                            return zv.getBemerkung();
                        }
                        return null;
                    }).build();
            jTable.addMouseListener(new java.awt.event.MouseAdapter() {
                @Override
                public void mouseClicked(java.awt.event.MouseEvent e) {
                    selectedVerleih = tableBuilder.getSelectedValue();
                    if (selectedVerleih == null) {
                        getJButtonLoeschen().setEnabled(false);
                        getJButtonAendern().setEnabled(false);
                    } else {
                        getJButtonLoeschen().setEnabled(true);
                        getJButtonAendern().setEnabled(true);

                        getJFormattedTextFieldDatum().setValue(selectedVerleih.getDatum());
                        getJTextFieldPerson().setText(selectedVerleih.getPerson());
                        getJTextAreaBemerkung().setText(selectedVerleih.getBemerkung());
                    }
                }
            });
        }
        return jTable;
    }

    private JPanel getJPanel5() {
        if (jPanel5 == null) {
            GridBagConstraints gridBagConstraints1 = new GridBagConstraints();
            gridBagConstraints1.fill = GridBagConstraints.BOTH;
            gridBagConstraints1.weighty = 1.0;
            gridBagConstraints1.weightx = 1.0;
            jPanel5 = new JPanel();
            jPanel5.setLayout(new GridBagLayout());
            jPanel5.add(getJScrollPane(), gridBagConstraints1);
        }
        return jPanel5;
    }

    private JScrollPane getJScrollPane() {
        if (jScrollPane == null) {
            jScrollPane = new JYTableScrollPane(getJTable());
        }
        return jScrollPane;
    }

    private JTextArea getJTextAreaBemerkung() {
        if (jTextAreaBemerkung == null) {
            jTextAreaBemerkung = new JTextArea();
        }
        return jTextAreaBemerkung;
    }

    private JFormattedTextField getJFormattedTextFieldDatum() {
        if (jFormattedTextFieldDatum == null) {
            jFormattedTextFieldDatum = new JFormattedTextField(
                    new DateFormatter(DateFormat.getDateInstance(DateFormat.SHORT, Locale.GERMAN)));
        }
        return jFormattedTextFieldDatum;
    }

    private JScrollPane getJScrollPane1() {
        if (jScrollPane1 == null) {
            jScrollPane1 = new JScrollPane();
            jScrollPane1.setViewportView(getJTextAreaBemerkung());
        }
        return jScrollPane1;
    }

    private JButton getJButtonAbbrechen() {
        if (jButtonAbbrechen == null) {
            jButtonAbbrechen = new JButton();
            jButtonAbbrechen.setText("Abbrechen");
            jButtonAbbrechen.addActionListener(e -> setVisible(false));
        }
        return jButtonAbbrechen;
    }

    private JButton getJButtonNeuerVerleih() {
        if (jButtonNeuerVerleih == null) {
            jButtonNeuerVerleih = new JButton();
            jButtonNeuerVerleih.setText("Neuer Verleih");
            jButtonNeuerVerleih.addActionListener(e -> {
                Date datum = (Date) getJFormattedTextFieldDatum().getValue();
                String person = getJTextFieldPerson().getText();
                String bemerkung = getJTextAreaBemerkung().getText();
                get().speichereZeltverleih(null, zelt.getId(), datum != null ? datum : new Date(),
                        person != null && !person.isEmpty() ? person : "Neu",
                        bemerkung != null && !bemerkung.isEmpty() ? bemerkung : "Neu",
                        asyncCallback -> tableBuilder.refresh());
            });
        }
        return jButtonNeuerVerleih;
    }

    private JButton getJButtonAendern() {
        if (jButtonAendern == null) {
            jButtonAendern = new JButton();
            jButtonAendern.setText("Ändern");
            jButtonAendern.addActionListener(e -> {
                Date datum = (Date) getJFormattedTextFieldDatum().getValue();
                String person = getJTextFieldPerson().getText();
                String bemerkung = getJTextAreaBemerkung().getText();
                get().speichereZeltverleih(selectedVerleih.getId(), zelt.getId(), datum != null ? datum : new Date(),
                        person != null && !person.isEmpty() ? person : "Neu",
                        bemerkung != null && !bemerkung.isEmpty() ? bemerkung : "Neu",
                        asyncCallback -> tableBuilder.refresh());

            });
        }
        return jButtonAendern;
    }

    private JButton getJButtonLoeschen() {
        if (jButtonLoeschen == null) {
            jButtonLoeschen = new JButton();
            jButtonLoeschen.setText("Löschen");
            jButtonLoeschen.addActionListener(e -> {
                get().loescheZeltverleih(selectedVerleih.getId(), asyncCallback -> tableBuilder.refresh());
            });
        }
        return jButtonLoeschen;
    }
}
