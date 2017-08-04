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
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextField;
import javax.swing.text.DateFormatter;

import de.zlvp.entity.Zelt;
import de.zlvp.ui.AbstractJInternalFrame;
import de.zlvp.ui.JListBuilder;

public class ZeltVerwalten extends AbstractJInternalFrame {

    private static final long serialVersionUID = 2547154648280401084L;

    private JPanel jContentPane;

    private JPanel jPanel;

    private JSplitPane jSplitPane;

    private JPanel jPanel1;

    private JPanel jPanel2;

    private JList<Zelt> jList;

    private JTextField jTextFieldBezeichnung;

    private JFormattedTextField jFormattedTextFieldAngeschafft;

    private JTextField jTextFieldPreis;
    private JLabel jLabel;
    private JLabel jLabel1;
    private JLabel jLabel2;

    private JPanel jPanel3;

    private JButton jButtonAendern;

    private JButton jButtonDetailAendern;

    private JButton jButtonAbbrechen;

    private JScrollPane jScrollPane;

    private JListBuilder<Zelt> jListBuilder;

    public ZeltVerwalten() {
        jListBuilder = JListBuilder.get(Zelt.class, get()::getAllZelt);

        initialize();
        setUp();
        getJButtonVerleih().setEnabled(false);
        getJButtonAendern().setEnabled(false);
        getJButtonDetailAendern().setEnabled(false);
        getJButtonSchaeden().setEnabled(false);
    }

    private void initialize() {
        this.setSize(520, 396);
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
            GridBagConstraints gridBagConstraints6 = new GridBagConstraints();
            gridBagConstraints6.gridx = 0;
            gridBagConstraints6.anchor = java.awt.GridBagConstraints.WEST;
            gridBagConstraints6.insets = new java.awt.Insets(10, 10, 0, 0);
            gridBagConstraints6.gridy = 2;
            jLabel2 = new JLabel();
            jLabel2.setText("Preis:");
            GridBagConstraints gridBagConstraints5 = new GridBagConstraints();
            gridBagConstraints5.gridx = 0;
            gridBagConstraints5.anchor = java.awt.GridBagConstraints.WEST;
            gridBagConstraints5.insets = new java.awt.Insets(10, 10, 0, 0);
            gridBagConstraints5.gridy = 1;
            jLabel1 = new JLabel();
            jLabel1.setText("Angeschafft:");
            GridBagConstraints gridBagConstraints4 = new GridBagConstraints();
            gridBagConstraints4.gridx = 0;
            gridBagConstraints4.anchor = java.awt.GridBagConstraints.WEST;
            gridBagConstraints4.insets = new java.awt.Insets(10, 10, 0, 0);
            gridBagConstraints4.gridy = 0;
            jLabel = new JLabel();
            jLabel.setText("Bezeichnung:");
            GridBagConstraints gridBagConstraints3 = new GridBagConstraints();
            gridBagConstraints3.fill = java.awt.GridBagConstraints.HORIZONTAL;
            gridBagConstraints3.gridy = 2;
            gridBagConstraints3.weightx = 1.0;
            gridBagConstraints3.insets = new java.awt.Insets(10, 10, 0, 10);
            gridBagConstraints3.gridx = 1;
            GridBagConstraints gridBagConstraints2 = new GridBagConstraints();
            gridBagConstraints2.fill = java.awt.GridBagConstraints.HORIZONTAL;
            gridBagConstraints2.gridy = 1;
            gridBagConstraints2.weightx = 1.0;
            gridBagConstraints2.insets = new java.awt.Insets(10, 10, 0, 10);
            gridBagConstraints2.gridx = 1;
            GridBagConstraints gridBagConstraints1 = new GridBagConstraints();
            gridBagConstraints1.fill = java.awt.GridBagConstraints.HORIZONTAL;
            gridBagConstraints1.gridy = 0;
            gridBagConstraints1.weightx = 1.0;
            gridBagConstraints1.insets = new java.awt.Insets(10, 10, 0, 10);
            gridBagConstraints1.gridx = 1;
            jPanel2 = new JPanel();
            jPanel2.setLayout(new GridBagLayout());
            jPanel2.add(getJTextFieldBezeichnung(), gridBagConstraints1);
            jPanel2.add(getJFormattedTextFieldAngeschafft(), gridBagConstraints2);
            jPanel2.add(getJTextFieldPreis(), gridBagConstraints3);
            jPanel2.add(jLabel, gridBagConstraints4);
            jPanel2.add(jLabel1, gridBagConstraints5);
            jPanel2.add(jLabel2, gridBagConstraints6);
        }
        return jPanel2;
    }

    private JList<Zelt> getJList() {
        if (jList == null) {
            jList = jListBuilder.build();
            jList.addListSelectionListener(e -> {
                Zelt zelt = getJList().getSelectedValue();
                if (zelt != null) {
                    getJButtonVerleih().setEnabled(true);
                    getJButtonAendern().setEnabled(true);
                    getJButtonDetailAendern().setEnabled(true);
                    getJButtonSchaeden().setEnabled(true);

                    getJTextFieldBezeichnung().setText(zelt.getBezeichnung());
                    getJFormattedTextFieldAngeschafft().setValue(zelt.getAngeschafft());
                    getJTextFieldPreis().setText(Double.toString(zelt.getPreis()));
                } else {
                    getJButtonVerleih().setEnabled(false);
                    getJButtonAendern().setEnabled(false);
                    getJButtonDetailAendern().setEnabled(false);
                    getJButtonSchaeden().setEnabled(false);
                }

            });
        }
        return jList;
    }

    private JTextField getJTextFieldBezeichnung() {
        if (jTextFieldBezeichnung == null) {
            jTextFieldBezeichnung = new JTextField();
        }
        return jTextFieldBezeichnung;
    }

    private JFormattedTextField getJFormattedTextFieldAngeschafft() {
        if (jFormattedTextFieldAngeschafft == null) {
            jFormattedTextFieldAngeschafft = new JFormattedTextField(
                    new DateFormatter(DateFormat.getDateInstance(DateFormat.SHORT, Locale.GERMAN)));
        }
        return jFormattedTextFieldAngeschafft;
    }

    private JTextField getJTextFieldPreis() {
        if (jTextFieldPreis == null) {
            jTextFieldPreis = new JTextField();
        }
        return jTextFieldPreis;
    }

    private JPanel getJPanel3() {
        if (jPanel3 == null) {
            jPanel3 = new JPanel();
            jPanel3.add(getJButtonAendern(), null);
            jPanel3.add(getJButtonDetailAendern(), null);
            jPanel3.add(getJButtonSchaeden(), null);
            jPanel3.add(getJButtonVerleih(), null);
            jPanel3.add(getJButtonAbbrechen(), null);
        }
        return jPanel3;
    }

    private JButton getJButtonAendern() {
        if (jButtonAendern == null) {
            jButtonAendern = new JButton();
            jButtonAendern.setText("Ändern");
            jButtonAendern.addActionListener(e -> {
                Zelt selectedValue = getJList().getSelectedValue();
                String bezeichnung = getJTextFieldBezeichnung().getText();
                Date angeschafft = (Date) getJFormattedTextFieldAngeschafft().getValue();
                double preis = 0;
                get().speichereZelt(selectedValue.getId(), bezeichnung, angeschafft, preis, asyncCallback -> {
                    jListBuilder.refresh();
                    getJList().setSelectedValue(selectedValue, true);
                });
            });
        }
        return jButtonAendern;
    }

    private JButton getJButtonDetailAendern() {
        if (jButtonDetailAendern == null) {
            jButtonDetailAendern = new JButton();
            jButtonDetailAendern.setText("Detail ändern");
            jButtonDetailAendern.addActionListener(e -> {
                Zelt selectedValue = getJList().getSelectedValue();
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
            jScrollPane = new JScrollPane();
            jScrollPane.setViewportView(getJList());
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
                Zelt selectedValue = getJList().getSelectedValue();
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
                Zelt selectedValue = getJList().getSelectedValue();
                new ZeltverleihVerwalten(selectedValue);
            });
        }
        return jButtonVerleih;
    }

} // @jve:decl-index=0:visual-constraint="10,10"
