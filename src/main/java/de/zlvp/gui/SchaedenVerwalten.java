package de.zlvp.gui;

import static de.zlvp.Client.get;

import java.awt.BorderLayout;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTable;

import de.javasoft.swing.JYTableScrollPane;
import de.zlvp.entity.Schaden;
import de.zlvp.entity.Zelt;
import de.zlvp.ui.AbstractJInternalFrame;
import de.zlvp.ui.JTableBuilder;
import de.zlvp.ui.JTableBuilders;

public class SchaedenVerwalten extends AbstractJInternalFrame {

    private static final long serialVersionUID = 3140216101445215956L;

    private JPanel jContentPane;

    private JPanel jPanel;

    private JPanel jPanel1;

    private JButton jButtonHinzufuegen;

    private JTable jTableSchaeden;

    private JButton jButtonLoeschen;

    private final Zelt zelt;

    private Schaden selectedSchaden;

    private final JTableBuilder<Schaden> tableBuilder;

    public SchaedenVerwalten(Zelt zelt) {
        this.zelt = zelt;
        tableBuilder = JTableBuilders.schaden(asyncCallback -> get().getAllSchaeden(zelt.getId(), asyncCallback));
        initialize();
        setup();
        getJButtonLoeschen().setEnabled(false);
        setVisible(true);
    }

    private void initialize() {
        this.setSize(1000, 400);
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
            jPanel.add(new JYTableScrollPane(getJTableSchaeden()), java.awt.BorderLayout.CENTER);
            jPanel.add(getJPanelButtons(), java.awt.BorderLayout.SOUTH);
        }
        return jPanel;
    }

    private JPanel getJPanelButtons() {
        if (jPanel1 == null) {
            jPanel1 = new JPanel();
            jPanel1.add(getJButtonHinzufuegen(), null);
            jPanel1.add(getJButtonLoeschen(), null);
        }
        return jPanel1;
    }

    private JButton getJButtonHinzufuegen() {
        if (jButtonHinzufuegen == null) {
            jButtonHinzufuegen = new JButton();
            jButtonHinzufuegen.setText("Hinzufügen");
            jButtonHinzufuegen.addActionListener(e -> {
                get().speichereSchaden(null, zelt.getId(), new Date(), "Neuer Schaden",
                        asyncCallback -> tableBuilder.refresh());
            });
        }
        return jButtonHinzufuegen;
    }

    private JTable getJTableSchaeden() {
        if (jTableSchaeden == null) {
            jTableSchaeden = tableBuilder.buildAndLoad();
            jTableSchaeden.addMouseListener(new java.awt.event.MouseAdapter() {
                @Override
                public void mouseClicked(java.awt.event.MouseEvent e) {
                    selectedSchaden = tableBuilder.getSelectedValue();
                    if (selectedSchaden == null) {
                        getJButtonLoeschen().setEnabled(false);
                    } else {
                        getJButtonLoeschen().setEnabled(true);
                    }
                }

            });
        }
        return jTableSchaeden;
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

}
