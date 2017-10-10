package de.zlvp.gui;

import static de.zlvp.Client.get;

import java.awt.BorderLayout;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;

import de.javasoft.swing.JYTableScrollPane;
import de.zlvp.entity.Zelt;
import de.zlvp.ui.AbstractJInternalFrame;
import de.zlvp.ui.JTableBuilder;
import de.zlvp.ui.JTableBuilders;

public class ZeltVerwalten extends AbstractJInternalFrame {

    private static final long serialVersionUID = 1L;

    private JPanel jContentPane;

    private JPanel jPanel;

    private JTable jTable;

    private JPanel jPanel3;

    private JButton jButtonSchaeden;

    private JButton jButtonVerleih;

    private JButton jButtonDetailAendern;

    private JTableBuilder<Zelt> jTableBuilder;

    public ZeltVerwalten() {
        jTableBuilder = JTableBuilders.zelt(asyncCallback -> get().getAllZelt(result -> {
            asyncCallback.get(result);
            setVisible(true);
        }));

        initialize();
        setup();
        getJButtonVerleih().setEnabled(false);
        getJButtonDetailAendern().setEnabled(false);
        getJButtonSchaeden().setEnabled(false);
    }

    private void initialize() {
        this.setContentPane(getJContentPane());
        this.setTitle("Zelte Verwalten / Ändern");
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

    private JTable getJTable() {
        if (jTable == null) {
            jTable = jTableBuilder.buildAndLoad();
            jTable.getSelectionModel().addListSelectionListener(e -> {
                ListSelectionModel lsm = (ListSelectionModel) e.getSource();
                if (lsm.isSelectionEmpty()) {
                    getJButtonVerleih().setEnabled(false);
                    getJButtonDetailAendern().setEnabled(false);
                    getJButtonSchaeden().setEnabled(false);
                } else {
                    getJButtonVerleih().setEnabled(true);
                    getJButtonDetailAendern().setEnabled(true);
                    getJButtonSchaeden().setEnabled(true);
                }
            });

        }
        return jTable;
    }

    private JPanel getJPanelButtons() {
        if (jPanel3 == null) {
            jPanel3 = new JPanel();
            jPanel3.add(getJButtonDetailAendern());
            jPanel3.add(getJButtonSchaeden());
            jPanel3.add(getJButtonVerleih());
        }
        return jPanel3;
    }

    private JButton getJButtonDetailAendern() {
        if (jButtonDetailAendern == null) {
            jButtonDetailAendern = new JButton();
            jButtonDetailAendern.setText("Detail ändern");
            jButtonDetailAendern.addActionListener(e -> {
                Zelt selectedValue = jTableBuilder.getSelectedValue();
                new ZeltZubehoer(selectedValue);
            });
        }
        return jButtonDetailAendern;
    }

    private JButton getJButtonSchaeden() {
        if (jButtonSchaeden == null) {
            jButtonSchaeden = new JButton();
            jButtonSchaeden.setText("Schäden");
            jButtonSchaeden.addActionListener(e -> {
                Zelt selectedValue = jTableBuilder.getSelectedValue();
                new SchaedenVerwalten(selectedValue);
            });
        }
        return jButtonSchaeden;
    }

    private JButton getJButtonVerleih() {
        if (jButtonVerleih == null) {
            jButtonVerleih = new JButton();
            jButtonVerleih.setText("Verleih");
            jButtonVerleih.addActionListener(e -> {
                Zelt selectedValue = jTableBuilder.getSelectedValue();
                new ZeltverleihVerwalten(selectedValue);
            });
        }
        return jButtonVerleih;
    }

}
