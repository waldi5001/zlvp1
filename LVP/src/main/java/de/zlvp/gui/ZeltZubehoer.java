package de.zlvp.gui;

import static de.zlvp.Client.get;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;

import de.javasoft.swing.JYTableScrollPane;
import de.zlvp.entity.Zelt;
import de.zlvp.entity.Zeltdetail;
import de.zlvp.entity.ZeltdetailBezeichnung;
import de.zlvp.ui.InternalFrame;
import de.zlvp.ui.JComboBoxBuilder;
import de.zlvp.ui.JTableBuilder;
import de.zlvp.ui.JTableBuilder.ColumnBuilder;

public class ZeltZubehoer extends InternalFrame {

    private static final long serialVersionUID = 7743223447839598598L;

    private JPanel jContentPane;

    private JPanel jPanel;

    private JPanel jPanel1;

    private JPanel jPanel2;

    private JButton jButtonAbbrechen;
    private JButton jButtonBezHinz;

    private JScrollPane jScrollPane;

    private JTable jTable;

    private JPanel jPanel3;

    private JTextField jTextFieldSchluessel;

    private JTextField jTextFieldAnzahl;

    private JComboBox<ZeltdetailBezeichnung> jComboBoxBezeichnung;

    private JComboBox<ZeltdetailBezeichnung> jComboBoxBezeichnungTabelle;

    private JButton jButtonLoeschen;

    private JButton jButtonHinzufuegen;

    private JButton jButtonSpeichern;

    private final Zelt zelt;

    final private JTableBuilder<Zeltdetail> tableBuilder;
    final private JComboBoxBuilder<ZeltdetailBezeichnung> jComboBoxBezeichnungBuilder;
    final private JComboBoxBuilder<ZeltdetailBezeichnung> jComboBoxBezeichnungTabelleBuilder;

    public ZeltZubehoer(Zelt zelt) {
        this.zelt = zelt;

        jComboBoxBezeichnungBuilder = JComboBoxBuilder.get(ZeltdetailBezeichnung.class,
                get()::getAllZeltdetailBezeichnung);

        jComboBoxBezeichnungTabelleBuilder = JComboBoxBuilder.get(ZeltdetailBezeichnung.class,
                get()::getAllZeltdetailBezeichnung);

        tableBuilder = JTableBuilder
                .get(Zeltdetail.class, asyncCallback -> get().getAllZeltdetail(zelt.getId(), asyncCallback))
                .set((zd, val, index) -> {
                    if (index == 0) {
                        zd.setBrzNummer((String) val);
                    } else if (index == 1) {
                        zd.setAnzahl((int) val);
                    } else if (index == 2) {
                        zd.setZeltdetailbezeichnung((ZeltdetailBezeichnung) val);
                    }
                })//
                .get((zd, index) -> {
                    if (index == 0) {
                        return zd.getBrzNummer();
                    } else if (index == 1) {
                        return zd.getAnzahl();
                    } else if (index == 2) {
                        return zd.getZeltdetailbezeichnung();
                    }
                    return null;
                })
                .save((zd, cb) -> get().speichereZeltdetail(zd.getId(), zelt.getId(), zd.getAnzahl(),
                        zd.getZeltdetailbezeichnung().getId(), zd.getBrzNummer(), cb))//
                .addColumn(ColumnBuilder.get(String.class).add("Schlüssel").preferredWidth(80).build())
                .addColumn(ColumnBuilder.get(Integer.class).add("Anzahl").preferredWidth(50).build())//
                .addColumn(ColumnBuilder.get(ZeltdetailBezeichnung.class).add("Bezeichnung").preferredWidth(140)
                        .add(getJComboBoxBezeichnungTabelle()).build());

        initialize();
        setUp();
        getJButtonLoeschen().setEnabled(false);
    }

    private void initialize() {
        this.setSize(553, 350);
        this.setContentPane(getJContentPane());
        this.setTitle("Zelt - Zubehör");
        this.getRootPane().setDefaultButton(getJButtonHinzufuegen());
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
            jPanel1.add(getJButtonBezeichnungHinz(), null);
            jPanel1.add(getJButtonLoeschen(), null);
            jPanel1.add(getJButtonSpeichern(), null);
            jPanel1.add(getJButtonAbbrechen(), null);
        }
        return jPanel1;
    }

    private JPanel getJPanel2() {
        if (jPanel2 == null) {
            GridBagConstraints gridBagConstraints1 = new GridBagConstraints();
            gridBagConstraints1.gridx = 0;
            gridBagConstraints1.fill = java.awt.GridBagConstraints.HORIZONTAL;
            gridBagConstraints1.gridy = 2;
            GridBagConstraints gridBagConstraints = new GridBagConstraints();
            gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
            gridBagConstraints.gridy = 0;
            gridBagConstraints.weightx = 1.0;
            gridBagConstraints.weighty = 1.0;
            gridBagConstraints.insets = new java.awt.Insets(10, 10, 0, 10);
            gridBagConstraints.gridx = 0;
            jPanel2 = new JPanel();
            jPanel2.setLayout(new GridBagLayout());
            jPanel2.add(getJScrollPane(), gridBagConstraints);
            jPanel2.add(getJPanel3(), gridBagConstraints1);
        }
        return jPanel2;
    }

    private JButton getJButtonSpeichern() {
        if (jButtonSpeichern == null) {
            jButtonSpeichern = new JButton();
            jButtonSpeichern.setText("Speichern");
            jButtonSpeichern.addActionListener(e -> {
                tableBuilder.save();
                setVisible(false);
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

    private JButton getJButtonBezeichnungHinz() {
        if (jButtonBezHinz == null) {
            jButtonBezHinz = new JButton();
            jButtonBezHinz.setText("Bezeichnung hinzufügen");
            jButtonBezHinz.addActionListener(e -> {
                String detailBezeichnung = JOptionPane.showInputDialog(desktopPane, "Bezeichnung eingeben");
                if (detailBezeichnung != null && !detailBezeichnung.isEmpty()) {
                    get().speichereZeltDetailBezeichnung(detailBezeichnung, asyncCallback -> {
                        jComboBoxBezeichnungBuilder.refresh();
                        jComboBoxBezeichnungTabelleBuilder.refresh();
                    });
                }
            });
        }
        return jButtonBezHinz;
    }

    private JScrollPane getJScrollPane() {
        if (jScrollPane == null) {
            jScrollPane = new JYTableScrollPane(getJTable());
        }
        return jScrollPane;
    }

    private JTable getJTable() {
        if (jTable == null) {
            jTable = tableBuilder.build();

            jTable.addMouseListener(new java.awt.event.MouseAdapter() {
                @Override
                public void mouseClicked(java.awt.event.MouseEvent e) {
                    Zeltdetail selectedValue = tableBuilder.getSelectedValue();
                    if (selectedValue == null) {
                        getJButtonLoeschen().setEnabled(false);
                    } else {
                        getJButtonLoeschen().setEnabled(true);

                    }
                }
            });
        }
        return jTable;
    }

    private JPanel getJPanel3() {
        if (jPanel3 == null) {
            GridBagConstraints gridBagConstraints4 = new GridBagConstraints();
            gridBagConstraints4.fill = java.awt.GridBagConstraints.HORIZONTAL;
            gridBagConstraints4.insets = new java.awt.Insets(5, 0, 5, 10);
            gridBagConstraints4.weightx = 1.0;
            GridBagConstraints gridBagConstraints3 = new GridBagConstraints();
            gridBagConstraints3.fill = java.awt.GridBagConstraints.HORIZONTAL;
            gridBagConstraints3.insets = new java.awt.Insets(5, 0, 5, 10);
            gridBagConstraints3.weightx = 1.0;
            GridBagConstraints gridBagConstraints2 = new GridBagConstraints();
            gridBagConstraints2.fill = java.awt.GridBagConstraints.HORIZONTAL;
            gridBagConstraints2.gridx = 0;
            gridBagConstraints2.gridy = 0;
            gridBagConstraints2.weightx = 1.0;
            gridBagConstraints2.insets = new java.awt.Insets(5, 10, 5, 10);
            jPanel3 = new JPanel();
            jPanel3.setLayout(new GridBagLayout());
            jPanel3.add(getJTextFieldSchluessel(), gridBagConstraints2);
            jPanel3.add(getJTextFieldAnzahl(), gridBagConstraints3);
            jPanel3.add(getJComboBoxBezeichnung(), gridBagConstraints4);
        }
        return jPanel3;
    }

    private JTextField getJTextFieldSchluessel() {
        if (jTextFieldSchluessel == null) {
            jTextFieldSchluessel = new JTextField();
        }
        return jTextFieldSchluessel;
    }

    private JTextField getJTextFieldAnzahl() {
        if (jTextFieldAnzahl == null) {
            jTextFieldAnzahl = new JTextField();
        }
        return jTextFieldAnzahl;
    }

    public JComboBox<ZeltdetailBezeichnung> getJComboBoxBezeichnung() {
        if (jComboBoxBezeichnung == null) {
            jComboBoxBezeichnung = jComboBoxBezeichnungBuilder.build();
        }
        return jComboBoxBezeichnung;
    }

    public JComboBox<ZeltdetailBezeichnung> getJComboBoxBezeichnungTabelle() {
        if (jComboBoxBezeichnungTabelle == null) {
            jComboBoxBezeichnungTabelle = jComboBoxBezeichnungTabelleBuilder.build();
        }
        return jComboBoxBezeichnungTabelle;
    }

    public class InteractiveTableModelListener implements TableModelListener {
        @Override
        public void tableChanged(TableModelEvent evt) {
        }
    }

    private JButton getJButtonLoeschen() {
        if (jButtonLoeschen == null) {
            jButtonLoeschen = new JButton();
            jButtonLoeschen.setText("Löschen");
            jButtonLoeschen.addActionListener(e -> {
                get().loescheZeltdetail(tableBuilder.getSelectedValue().getId(),
                        asyncCallback -> tableBuilder.refresh());
            });
        }
        return jButtonLoeschen;
    }

    private JButton getJButtonHinzufuegen() {
        if (jButtonHinzufuegen == null) {
            jButtonHinzufuegen = new JButton();
            jButtonHinzufuegen.setText("Hinzufügen");
            jButtonHinzufuegen.addActionListener(e -> {
                String schluessel = getJTextFieldSchluessel().getText();
                int anzahl = getJTextFieldAnzahl().getText() != null && !getJTextFieldAnzahl().getText().isEmpty()
                        ? Integer.valueOf(getJTextFieldAnzahl().getText()) : 0;
                ZeltdetailBezeichnung selectedItem = (ZeltdetailBezeichnung) getJComboBoxBezeichnung()
                        .getSelectedItem();
                get().speichereZeltdetail(null, zelt.getId(), anzahl, selectedItem.getId(), schluessel,
                        asyncCallback -> tableBuilder.refresh());
            });
        }
        return jButtonHinzufuegen;
    }

}
