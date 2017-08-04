package de.zlvp.gui;

import static de.zlvp.Client.get;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;

import de.zlvp.Client;
import de.zlvp.entity.Zelt;
import de.zlvp.ui.AbstractJInternalFrame;
import de.zlvp.ui.JListBuilder;

public class ZeltDetailListen extends AbstractJInternalFrame {

    private static final long serialVersionUID = 2547154648280401084L;

    private JPanel jContentPane;

    private JPanel jPanel;

    private JSplitPane jSplitPane;

    private JPanel jPanel1;

    private JPanel jPanel2;

    private JList<Zelt> jListZelt;

    private JPanel jPanel3;

    private JButton jButtonAbbrechen;

    private JScrollPane jScrollPane;

    private JButton jButtonZeDetail;

    private JButton jButtonZeSchäden;

    private JButton jButtonZeHistory;

    public ZeltDetailListen() {
        initialize();
        setUp();
    }

    private void initialize() {
        this.setSize(356, 363);
        this.setContentPane(getJContentPane());
        this.setTitle("Zelt Listen");
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
            jPanel.add(getJSplitPane(), java.awt.BorderLayout.CENTER);
            jPanel.add(getJPanel3(), java.awt.BorderLayout.SOUTH);
        }
        return jPanel;
    }

    private JSplitPane getJSplitPane() {
        if (jSplitPane == null) {
            jSplitPane = new JSplitPane();
            jSplitPane.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);
            jSplitPane.setTopComponent(getJPanel1());
            jSplitPane.setBottomComponent(getJPanel2());
            jSplitPane.setDividerLocation(150);
        }
        return jSplitPane;
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

    private JPanel getJPanel2() {
        if (jPanel2 == null) {
            GridBagConstraints gridBagConstraints2 = new GridBagConstraints();
            gridBagConstraints2.gridx = 0;
            gridBagConstraints2.anchor = java.awt.GridBagConstraints.WEST;
            gridBagConstraints2.insets = new java.awt.Insets(10, 0, 0, 0);
            gridBagConstraints2.fill = java.awt.GridBagConstraints.HORIZONTAL;
            gridBagConstraints2.gridy = 2;
            GridBagConstraints gridBagConstraints1 = new GridBagConstraints();
            gridBagConstraints1.gridx = 0;
            gridBagConstraints1.anchor = java.awt.GridBagConstraints.WEST;
            gridBagConstraints1.insets = new java.awt.Insets(10, 0, 0, 0);
            gridBagConstraints1.fill = java.awt.GridBagConstraints.HORIZONTAL;
            gridBagConstraints1.gridy = 1;
            GridBagConstraints gridBagConstraints = new GridBagConstraints();
            gridBagConstraints.gridx = 0;
            gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
            gridBagConstraints.insets = new java.awt.Insets(10, 0, 0, 0);
            gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
            gridBagConstraints.gridy = 0;
            jPanel2 = new JPanel();
            jPanel2.setLayout(new GridBagLayout());
            jPanel2.add(getJButtonZeDetail(), gridBagConstraints);
            jPanel2.add(getJButtonZeSchäden(), gridBagConstraints1);
            jPanel2.add(getJButtonZeHistory(), gridBagConstraints2);
        }
        return jPanel2;
    }

    private JList<Zelt> getJListZelt() {
        if (jListZelt == null) {
            jListZelt = JListBuilder.get(Zelt.class, get()::getAllZelt).build();
        }
        return jListZelt;
    }

    private JPanel getJPanel3() {
        if (jPanel3 == null) {
            jPanel3 = new JPanel();
            jPanel3.add(getJButtonAbbrechen(), null);
        }
        return jPanel3;
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
            jScrollPane = new JScrollPane();
            jScrollPane.setViewportView(getJListZelt());
        }
        return jScrollPane;
    }

    private JButton getJButtonZeDetail() {
        if (jButtonZeDetail == null) {
            jButtonZeDetail = new JButton();
            jButtonZeDetail.setText("Zelt Detail Liste");
            jButtonZeDetail.addActionListener(e -> {
                Zelt zelt = getJListZelt().getSelectedValue();
                Client.getReports().zeltDetails(zelt.getId());
            });
        }
        return jButtonZeDetail;
    }

    private JButton getJButtonZeSchäden() {
        if (jButtonZeSchäden == null) {
            jButtonZeSchäden = new JButton();
            jButtonZeSchäden.setText("Schäden");
            jButtonZeSchäden.addActionListener(e -> {
                Zelt zelt = getJListZelt().getSelectedValue();
                Client.getReports().zeltSchaeden(zelt.getId());
            });
        }
        return jButtonZeSchäden;
    }

    private JButton getJButtonZeHistory() {
        if (jButtonZeHistory == null) {
            jButtonZeHistory = new JButton();
            jButtonZeHistory.setText("History");
            jButtonZeHistory.addActionListener(e -> {
                Zelt zelt = getJListZelt().getSelectedValue();
                Client.getReports().zeltHistorie(zelt.getId());
            });
        }
        return jButtonZeHistory;
    }

}
