package de.zlvp.gui;

import static de.zlvp.Client.get;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.text.DateFormatter;

import de.zlvp.entity.Schaden;
import de.zlvp.entity.Zelt;
import de.zlvp.ui.InternalFrame;
import de.zlvp.ui.JTableBuilder;
import de.zlvp.ui.JTableBuilder.ColumnBuilder;

public class SchaedenVerwalten extends InternalFrame {

    private static final long serialVersionUID = 3140216101445215956L;

    private JPanel jContentPane;

    private JPanel jPanel;

    private JPanel jPanel1;

    private JPanel jPanel2;

    private JButton jButtonHinzufuegen;

    private JButton jButtonAendern;
    private JScrollPane jScrollPaneDatum;

    private JFormattedTextField jFormattedTextFieldDatum;
    private JTextArea jTextAreaSchaden;

    private JSplitPane jSplitPane;

    private JPanel jPanel3;

    private JScrollPane jScrollPaneSchäden;

    private JTable jTableSchaeden;

    private JButton jButtonLoeschen;

    private final Zelt zelt;

    private Schaden selectedSchaden;

    private final JTableBuilder<Schaden> tableBuilder;

    public SchaedenVerwalten(Zelt zelt) {
        this.zelt = zelt;
        tableBuilder = JTableBuilder.get(Schaden.class,
                asyncCallback -> get().getAllSchaeden(zelt.getId(), asyncCallback));
        initialize();
        setUp();
        getJButtonLoeschen().setEnabled(false);
        getJButtonAendern().setEnabled(false);
    }

    private void initialize() {
        this.setSize(363, 332);
        this.setTitle("Schäden");
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
            jPanel1.add(getJButtonHinzufuegen(), null);
            jPanel1.add(getJButtonAendern(), null);
            jPanel1.add(getJButtonLoeschen(), null);
            jPanel1.add(getJButtonAbbrechen(), null);
        }
        return jPanel1;
    }

    private JPanel getJPanel2() {
        if (jPanel2 == null) {
            GridBagConstraints gridBagConstraints11 = new GridBagConstraints();
            gridBagConstraints11.fill = java.awt.GridBagConstraints.BOTH;
            gridBagConstraints11.weighty = 1.0;
            gridBagConstraints11.weightx = 1.0;
            GridBagConstraints gridBagConstraints2 = new GridBagConstraints();
            gridBagConstraints2.fill = java.awt.GridBagConstraints.BOTH;
            gridBagConstraints2.gridy = 2;
            gridBagConstraints2.weightx = 1.0;
            gridBagConstraints2.weighty = 1.0;
            gridBagConstraints2.insets = new java.awt.Insets(10, 10, 0, 10);
            gridBagConstraints2.gridx = 0;
            jPanel2 = new JPanel();
            jPanel2.setLayout(new GridBagLayout());
            jPanel2.add(getJSplitPane(), gridBagConstraints11);
        }
        return jPanel2;
    }

    private JButton getJButtonHinzufuegen() {
        if (jButtonHinzufuegen == null) {
            jButtonHinzufuegen = new JButton();
            jButtonHinzufuegen.setText("Hinzufügen");
            jButtonHinzufuegen.addActionListener(e -> {
                String text = getJTextAreaSchaden().getText();
                Date value = (Date) getJFormattedTextFieldDatum().getValue();

                get().speichereSchaden(null, zelt.getId(), value != null ? value : new Date(),
                        text != null && !text.isEmpty() ? text : "Neuer Schaden", asyncCallback -> load());
            });
        }
        return jButtonHinzufuegen;
    }

    private JButton getJButtonAendern() {
        if (jButtonAendern == null) {
            jButtonAendern = new JButton();
            jButtonAendern.setText("Ändern");
            jButtonAendern.addActionListener(e -> {
                Date datum = (Date) getJFormattedTextFieldDatum().getValue();
                String schaden = getJTextAreaSchaden().getText();

                get().speichereSchaden(selectedSchaden.getId(), zelt.getId(), datum, schaden, asyncCallback -> {
                    getJButtonLoeschen().setEnabled(false);
                    getJButtonAendern().setEnabled(false);
                    load();
                });

            });
        }
        return jButtonAendern;
    }

    private JScrollPane getJScrollPane() {
        if (jScrollPaneDatum == null) {
            jScrollPaneDatum = new JScrollPane();
            jScrollPaneDatum.setViewportView(getJTextAreaSchaden());
        }
        return jScrollPaneDatum;
    }

    private JFormattedTextField getJFormattedTextFieldDatum() {
        if (jFormattedTextFieldDatum == null) {
            jFormattedTextFieldDatum = new JFormattedTextField(
                    new DateFormatter(DateFormat.getDateInstance(DateFormat.SHORT, Locale.GERMAN)));
        }
        return jFormattedTextFieldDatum;
    }

    private JTextArea getJTextAreaSchaden() {
        if (jTextAreaSchaden == null) {
            jTextAreaSchaden = new JTextArea();
        }
        return jTextAreaSchaden;
    }

    private JSplitPane getJSplitPane() {
        if (jSplitPane == null) {
            jSplitPane = new JSplitPane();
            jSplitPane.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);
            jSplitPane.setDividerLocation(100);
            jSplitPane.setTopComponent(getJScrollPaneSchäden());
            jSplitPane.setBottomComponent(getJPanel3());
        }
        return jSplitPane;
    }

    private JPanel getJPanel3() {
        if (jPanel3 == null) {
            GridBagConstraints gridBagConstraints1 = new GridBagConstraints();
            gridBagConstraints1.fill = java.awt.GridBagConstraints.HORIZONTAL;
            gridBagConstraints1.gridy = 1;
            gridBagConstraints1.ipadx = 284;
            gridBagConstraints1.weightx = 1.0;
            gridBagConstraints1.insets = new java.awt.Insets(10, 10, 0, 10);
            gridBagConstraints1.gridx = 0;
            GridBagConstraints gridBagConstraints = new GridBagConstraints();
            gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
            gridBagConstraints.gridy = 0;
            gridBagConstraints.ipadx = 285;
            gridBagConstraints.ipady = 81;
            gridBagConstraints.weightx = 1.0;
            gridBagConstraints.weighty = 1.0;
            gridBagConstraints.insets = new java.awt.Insets(0, 10, 0, 10);
            gridBagConstraints.gridx = 0;
            jPanel3 = new JPanel();
            jPanel3.setLayout(new GridBagLayout());
            jPanel3.add(getJScrollPane(), gridBagConstraints);
            jPanel3.add(getJFormattedTextFieldDatum(), gridBagConstraints1);
        }
        return jPanel3;
    }

    private JScrollPane getJScrollPaneSchäden() {
        if (jScrollPaneSchäden == null) {
            jScrollPaneSchäden = new JScrollPane();
            jScrollPaneSchäden.setViewportView(getJTableSchaeden());
        }
        return jScrollPaneSchäden;
    }

    private JTable getJTableSchaeden() {
        if (jTableSchaeden == null) {
            jTableSchaeden = tableBuilder//
                    .addColumn(ColumnBuilder.get(Date.class).add("Datum").preferredWidth(70).build())//
                    .addColumn(ColumnBuilder.get(String.class).add("Schaden").preferredWidth(270).build())//
                    .get((s, index) -> {
                        if (index == 0) {
                            return s.getDatum();
                        } else if (index == 1) {
                            return s.getSchaden();
                        }
                        return null;
                    }).build();
            jTableSchaeden.addMouseListener(new java.awt.event.MouseAdapter() {
                @Override
                public void mouseClicked(java.awt.event.MouseEvent e) {
                    selectedSchaden = tableBuilder.getSelectedValue();
                    if (selectedSchaden == null) {
                        getJButtonLoeschen().setEnabled(false);
                        getJButtonAendern().setEnabled(false);
                    } else {
                        getJButtonLoeschen().setEnabled(true);
                        getJButtonAendern().setEnabled(true);

                        getJFormattedTextFieldDatum().setValue(selectedSchaden.getDatum());
                        getJTextAreaSchaden().setText(selectedSchaden.getSchaden());
                    }
                }

            });
            jTableSchaeden.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        }
        return jTableSchaeden;
    }

    private JButton getJButtonLoeschen() {
        if (jButtonLoeschen == null) {
            jButtonLoeschen = new JButton();
            jButtonLoeschen.setText("Löschen");
            jButtonLoeschen.addActionListener(e -> {
                get().loescheSchaden(selectedSchaden.getId(), asyncCallback -> {
                    getJTextAreaSchaden().setText(null);
                    getJFormattedTextFieldDatum().setValue(null);
                    getJButtonLoeschen().setEnabled(false);
                    getJButtonAendern().setEnabled(false);
                    load();
                });
            });
        }
        return jButtonLoeschen;
    }

    private JButton jButtonAbbrechen;

    private JButton getJButtonAbbrechen() {
        if (jButtonAbbrechen == null) {
            jButtonAbbrechen = new JButton();
            jButtonAbbrechen.setText("Abbrechen");
            jButtonAbbrechen.addActionListener(e -> setVisible(false));
        }
        return jButtonAbbrechen;
    }

    private void load() {
        tableBuilder.refresh();
    }

}
