package de.zlvp.gui;

import static de.zlvp.Client.get;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTable;

import de.javasoft.swing.JYTableScrollPane;
import de.zlvp.entity.Zelt;
import de.zlvp.entity.Zeltverleih;
import de.zlvp.ui.AbstractJInternalFrame;
import de.zlvp.ui.JTableBuilder;
import de.zlvp.ui.JTableBuilders;

public class ZeltverleihVerwalten extends AbstractJInternalFrame {

    private static final long serialVersionUID = -1L;
    private JPanel jContentPane;
    private JPanel jPanel;
    private JPanel jPanelButtons;
    private JTable jTable;
    private JButton jButtonNeuerVerleih;

    private JButton jButtonLoeschen;

    private final JTableBuilder<Zeltverleih> tableBuilder;

    private Zeltverleih selectedVerleih;

    private final Zelt zelt;

    public ZeltverleihVerwalten(Zelt zelt) {
        this.zelt = zelt;
        tableBuilder = JTableBuilders.zeltverleih(cb -> get().getAllZeltverleih(zelt.getId(), cb));
        initialize();
        setup();
        getJButtonLoeschen().setEnabled(false);
        setVisible(true);
    }

    private void initialize() {
        this.setSize(1000, 400);
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
            jPanel.add(new JYTableScrollPane(getJTable()), BorderLayout.CENTER);
            jPanel.add(getJPanelButtons(), BorderLayout.SOUTH);
        }
        return jPanel;
    }

    private JPanel getJPanelButtons() {
        if (jPanelButtons == null) {
            jPanelButtons = new JPanel();
            jPanelButtons.setLayout(new FlowLayout());
            jPanelButtons.add(getJButtonNeuerVerleih());
            jPanelButtons.add(getJButtonLoeschen());
        }
        return jPanelButtons;
    }

    private JTable getJTable() {
        if (jTable == null) {
            jTable = tableBuilder.buildAndLoad();
            jTable.addMouseListener(new java.awt.event.MouseAdapter() {
                @Override
                public void mouseClicked(java.awt.event.MouseEvent e) {
                    selectedVerleih = tableBuilder.getSelectedValue();
                    getJButtonLoeschen().setEnabled(selectedVerleih != null);
                }
            });
            jTable.addComponentListener(new ComponentAdapter() {
                public void componentResized(ComponentEvent e) {
                    jTable.scrollRectToVisible(jTable.getCellRect(jTable.getRowCount() - 1, 0, true));
                }
            });
        }
        return jTable;
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

    private JButton getJButtonNeuerVerleih() {
        if (jButtonNeuerVerleih == null) {
            jButtonNeuerVerleih = new JButton();
            jButtonNeuerVerleih.setText("Neuer Verleih");
            jButtonNeuerVerleih.addActionListener(e -> {
                get().speichereZeltverleih(null, zelt.getId(), new Date(), "Neu", "Neu",
                        asyncCallback -> tableBuilder.refresh());
            });
        }
        return jButtonNeuerVerleih;
    }
}
