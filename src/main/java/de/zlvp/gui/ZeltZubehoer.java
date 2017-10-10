package de.zlvp.gui;

import static de.zlvp.Client.get;
import static java.util.stream.Collectors.toList;

import java.awt.BorderLayout;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;

import de.javasoft.swing.JYTableScrollPane;
import de.zlvp.entity.Zelt;
import de.zlvp.entity.Zeltdetail;
import de.zlvp.entity.ZeltdetailBezeichnung;
import de.zlvp.ui.AbstractJInternalFrame;
import de.zlvp.ui.JComboBoxBuilder;
import de.zlvp.ui.JTableBuilder;
import de.zlvp.ui.JTableBuilder.ColumnBuilder;

public class ZeltZubehoer extends AbstractJInternalFrame {

    private static final long serialVersionUID = 7743223447839598598L;

    private JPanel jContentPane;

    private JPanel jPanel;

    private JPanel jPanelButtons;

    private JButton jButtonBezHinz;

    private JTable jTable;

    private JComboBox<ZeltdetailBezeichnung> jComboBoxBezeichnungTabelle;

    private JButton jButtonLoeschen;

    private JButton jButtonHinzufuegen;

    private final Zelt zelt;

    final private JTableBuilder<Zeltdetail> tableBuilder;
    final private JComboBoxBuilder<ZeltdetailBezeichnung> jComboBoxBezeichnungTabelleBuilder;

    public ZeltZubehoer(Zelt zelt) {
        this.zelt = zelt;

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
                .delete((zds, cb) -> get().loescheZeltdetails(zds.stream().map(zd -> zd.getId()).collect(toList()), cb))//
                .addColumn(ColumnBuilder.get(String.class).add("Schlüssel").preferredWidth(80).build())
                .addColumn(ColumnBuilder.get(Integer.class).add("Anzahl").preferredWidth(50).build())//
                .addColumn(ColumnBuilder.get(ZeltdetailBezeichnung.class).add("Bezeichnung").preferredWidth(140)
                        .add(getJComboBoxBezeichnungTabelle()).build());

        initialize();
        setup();
        getJButtonLoeschen().setEnabled(false);
        setVisible(true);
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
            jPanel.add(new JYTableScrollPane(getJTable()), java.awt.BorderLayout.CENTER);
            jPanel.add(getJPanelButtons(), java.awt.BorderLayout.SOUTH);
        }
        return jPanel;
    }

    private JPanel getJPanelButtons() {
        if (jPanelButtons == null) {
            jPanelButtons = new JPanel();
            jPanelButtons.add(getJButtonHinzufuegen());
            jPanelButtons.add(getJButtonBezeichnungHinz());
            jPanelButtons.add(getJButtonLoeschen());
        }
        return jPanelButtons;
    }

    private JButton getJButtonBezeichnungHinz() {
        if (jButtonBezHinz == null) {
            jButtonBezHinz = new JButton();
            jButtonBezHinz.setText("Bezeichnung hinzufügen");
            jButtonBezHinz.addActionListener(e -> {
                String detailBezeichnung = JOptionPane.showInternalInputDialog(desktopPane, "Bezeichnung eingeben");
                if (detailBezeichnung != null && !detailBezeichnung.isEmpty()) {
                    get().speichereZeltDetailBezeichnung(detailBezeichnung,
                            cb -> jComboBoxBezeichnungTabelleBuilder.refresh());
                }
            });
        }
        return jButtonBezHinz;
    }

    private JTable getJTable() {
        if (jTable == null) {
            jTable = tableBuilder.buildAndLoad();
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

    public JComboBox<ZeltdetailBezeichnung> getJComboBoxBezeichnungTabelle() {
        if (jComboBoxBezeichnungTabelle == null) {
            jComboBoxBezeichnungTabelle = jComboBoxBezeichnungTabelleBuilder.build();
        }
        return jComboBoxBezeichnungTabelle;
    }

    private JButton getJButtonLoeschen() {
        if (jButtonLoeschen == null) {
            jButtonLoeschen = new JButton();
            jButtonLoeschen.setText("Löschen");
            jButtonLoeschen.addActionListener(e -> {
                tableBuilder.deleteSelectedRows();
            });
        }
        return jButtonLoeschen;
    }

    private JButton getJButtonHinzufuegen() {
        if (jButtonHinzufuegen == null) {
            jButtonHinzufuegen = new JButton();
            jButtonHinzufuegen.setText("Hinzufügen");
            jButtonHinzufuegen.addActionListener(e -> {
                get().speichereZeltdetail(null, zelt.getId(), 0, 1, "BR-Z-", cb -> tableBuilder.refresh());
            });
        }
        return jButtonHinzufuegen;
    }

}
