package de.zlvp.gui;

import static de.zlvp.Client.get;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;

import de.javasoft.swing.JYTableScrollPane;
import de.zlvp.entity.Zelt;
import de.zlvp.ui.AbstractJInternalFrame;
import de.zlvp.ui.JTableBuilder;
import de.zlvp.ui.JTableBuilders;

public class ZeltVerwalten extends AbstractJInternalFrame {

    private static final long serialVersionUID = 2547154648280401084L;

    private JPanel jContentPane;

    private JPanel jPanel;

    private JPanel jPanel1;

    private JTable jTable;

    private JPanel jPanel3;

    private JButton jButtonDetailAendern;

    private JButton jButtonAbbrechen;

    private JScrollPane jScrollPane;

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
            jPanel.add(getJPanel1(), java.awt.BorderLayout.CENTER);
            jPanel.add(getJPanel3(), java.awt.BorderLayout.SOUTH);
        }
        return jPanel;
    }

    private JPanel getJPanel1() {
        if (jPanel1 == null) {
            GridBagConstraints gridBagConstraints11 = new GridBagConstraints();
            gridBagConstraints11.fill = java.awt.GridBagConstraints.BOTH;
            gridBagConstraints11.weighty = 1.0;
            gridBagConstraints11.insets = new java.awt.Insets(10, 10, 0, 10);
            gridBagConstraints11.weightx = 1.0;
            jPanel1 = new JPanel();
            jPanel1.setLayout(new GridBagLayout());
            jPanel1.add(getJScrollPane(), gridBagConstraints11);
        }
        return jPanel1;
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

    private JPanel getJPanel3() {
        if (jPanel3 == null) {
            jPanel3 = new JPanel();
            jPanel3.add(getJButtonDetailAendern(), null);
            jPanel3.add(getJButtonSchaeden(), null);
            jPanel3.add(getJButtonVerleih(), null);
            jPanel3.add(getJButtonAbbrechen(), null);
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

    private JButton getJButtonAbbrechen() {
        if (jButtonAbbrechen == null) {
            jButtonAbbrechen = new JButton();
            jButtonAbbrechen.setText("Abbrechen");
            jButtonAbbrechen.addActionListener(e -> setVisible(false));
        }
        return jButtonAbbrechen;
    }

    private JScrollPane getJScrollPane() {
        if (jScrollPane == null) {
            jScrollPane = new JYTableScrollPane(getJTable());
        }
        return jScrollPane;
    }

    private JButton jButtonSchaeden;

    private JButton jButtonVerleih;

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
