package de.zlvp.gui;

import static java.util.stream.Collectors.toList;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import de.javasoft.swing.JYTableScrollPane;
import de.zlvp.Client;
import de.zlvp.Events;
import de.zlvp.entity.Gruppe;
import de.zlvp.entity.Lager;
import de.zlvp.entity.Leiter;
import de.zlvp.entity.Person;
import de.zlvp.entity.Teilnehmer;
import de.zlvp.entity.Zelt;
import de.zlvp.ui.JTableBuilder;
import de.zlvp.ui.JTableBuilders;

public class TPGruppe extends JTabbedPane {

    private static final long serialVersionUID = 1L;

    private JPanel jPanelLeiter;

    private JPanel jPanelDaten;

    private JPanel jPanel;

    private JPanel jPanel3;

    private JButton jButtonOKDaten;

    private JButton jButtonOKLeiter;

    private JTextField jTextFieldName;
    private JLabel jLabelName;
    private JLabel jLabelSchlachtruf;

    private JScrollPane jScrollPaneL;

    private JTable jTableLeiter;

    private JPanel jPanelTeilnehmer;

    private JScrollPane jScrollPaneT;

    private JTable jTableTeilnehmer;

    private JButton jButtonOKTeilnehmer;

    private JPanel jPanelZelt;

    private JScrollPane jScrollPaneZ;

    private JTable jTableZelt;

    private JButton jButtonOKZelte;

    private JScrollPane jScrollPaneSchlachtruf;

    private JTextArea jTextAreaSchlachtruf;

    private final Gruppe gruppe;

    private final JTableBuilder<Leiter> tableBuilderLeiter;
    private final JTableBuilder<Teilnehmer> tableBuilderTeilnehmer;
    private final JTableBuilder<Zelt> tableBuilderZelt;

    public TPGruppe(Lager lager, Gruppe gruppe) {
        super();
        this.gruppe = gruppe;
        this.gruppe.setLager(lager);

        List<Person> allPerson = Client.get().getAllPerson();
        List<Zelt> allZelt = Client.get().getAllZeltFromLager(lager.getId());

        tableBuilderTeilnehmer = JTableBuilders.teilnehmer(gruppe, () -> {
            List<Teilnehmer> allFromGruppe = Client.get().getAllTeilnehmer(gruppe.getOriginalId());
            return allPerson.stream().map(p -> {
                for (Teilnehmer s : allFromGruppe) {
                    if (s.getOriginalId().equals(p.getId())) {
                        return s;
                    }
                }
                return new Teilnehmer(p);
            }).collect(toList());
        });

        tableBuilderLeiter = JTableBuilders.leiter(gruppe, () -> {
            List<Leiter> allFromGruppe = Client.get().getAllLeiter(gruppe.getOriginalId());
            return allPerson.stream().map(p -> {
                for (Leiter l : allFromGruppe) {
                    if (l.getOriginalId().equals(p.getId())) {
                        return l;
                    }
                }
                return new Leiter(p);
            }).collect(toList());
        });

        tableBuilderZelt = JTableBuilders.zelt(gruppe, () -> {
            List<Zelt> allFromGruppe = Client.get().getAllZeltFromGruppe(gruppe.getOriginalId());
            return allZelt.stream().map(z -> {
                for (Zelt zeltFromGruppe : allFromGruppe) {
                    if (zeltFromGruppe.getBezeichnung().equals(z.getBezeichnung())) {
                        return zeltFromGruppe;
                    }
                }
                return new Zelt(z);
            }).collect(toList());
        });

        initialize();
    }

    private void initialize() {
        this.setSize(562, 378);
        this.addTab("Daten", null, getJPanelDaten());
        this.addTab("Leiter", null, getJPanelLeiter());
        this.addTab("Teilnehmer", null, getJPanelTeilnehmer());
        this.addTab("Zelt(e) wählen", null, getJPanelZelt());
    }

    private JPanel getJPanelLeiter() {
        if (jPanelLeiter == null) {
            jPanelLeiter = new JPanel();
            jPanelLeiter.setLayout(new BorderLayout());
            jPanelLeiter.add(getJScrollPaneL(), java.awt.BorderLayout.CENTER);
            JPanel buttonPanel = new JPanel(new FlowLayout());
            buttonPanel.add(getJButtonOKLeiter());
            jPanelLeiter.add(buttonPanel, java.awt.BorderLayout.SOUTH);
        }
        return jPanelLeiter;
    }

    private JPanel getJPanelDaten() {
        if (jPanelDaten == null) {
            jPanelDaten = new JPanel();
            jPanelDaten.setLayout(new BorderLayout());
            jPanelDaten.add(getJPanel(), java.awt.BorderLayout.CENTER);
            jPanelDaten.add(getJPanel3(), java.awt.BorderLayout.SOUTH);
        }
        return jPanelDaten;
    }

    private JPanel getJPanel() {
        if (jPanel == null) {
            GridBagConstraints gridBagConstraints1 = new GridBagConstraints();
            gridBagConstraints1.fill = java.awt.GridBagConstraints.BOTH;
            gridBagConstraints1.gridy = 2;
            gridBagConstraints1.weightx = 1.0;
            gridBagConstraints1.weighty = 1.0;
            gridBagConstraints1.insets = new java.awt.Insets(10, 10, 10, 10);
            gridBagConstraints1.gridx = 1;
            GridBagConstraints gridBagConstraints11 = new GridBagConstraints();
            gridBagConstraints11.gridx = 0;
            gridBagConstraints11.insets = new java.awt.Insets(10, 10, 0, 0);
            gridBagConstraints11.anchor = java.awt.GridBagConstraints.WEST;
            gridBagConstraints11.gridy = 2;
            jLabelSchlachtruf = new JLabel();
            jLabelSchlachtruf.setText("Schlachtruf");
            GridBagConstraints gridBagConstraints2 = new GridBagConstraints();
            gridBagConstraints2.gridx = 0;
            gridBagConstraints2.insets = new java.awt.Insets(10, 10, 0, 0);
            gridBagConstraints2.anchor = java.awt.GridBagConstraints.WEST;
            gridBagConstraints2.gridy = 1;
            jLabelName = new JLabel();
            jLabelName.setText("Name");
            GridBagConstraints gridBagConstraints = new GridBagConstraints();
            gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
            gridBagConstraints.gridy = 1;
            gridBagConstraints.weightx = 1.0;
            gridBagConstraints.insets = new java.awt.Insets(10, 10, 0, 10);
            gridBagConstraints.gridx = 1;
            jPanel = new JPanel();
            jPanel.setLayout(new GridBagLayout());
            jPanel.add(getJTextFieldName(), gridBagConstraints);
            jPanel.add(jLabelName, gridBagConstraints2);
            jPanel.add(jLabelSchlachtruf, gridBagConstraints11);
            jPanel.add(getJScrollPaneSchlachtruf(), gridBagConstraints1);
        }
        return jPanel;
    }

    private JPanel getJPanel3() {
        if (jPanel3 == null) {
            jPanel3 = new JPanel();
            jPanel3.setLayout(new FlowLayout());
            jPanel3.add(getJButtonOKDaten(), null);
        }
        return jPanel3;
    }

    private JButton getJButtonOKDaten() {
        if (jButtonOKDaten == null) {
            jButtonOKDaten = new JButton();
            jButtonOKDaten.setText("OK");
            jButtonOKDaten.addActionListener(e -> {
                Client.get().speichereGruppe(gruppe.getId(), gruppe.getOriginalId(), gruppe.getLager().getId(),
                        getJTextFieldName().getText(), getJTextAreaSchlachtruf().getText());
                Events.get().fireAktualisieren();
            });
        }
        return jButtonOKDaten;
    }

    private JButton getJButtonOKLeiter() {
        if (jButtonOKLeiter == null) {
            jButtonOKLeiter = new JButton();
            jButtonOKLeiter.setText("OK");
            jButtonOKLeiter.addActionListener(e -> {
                tableBuilderLeiter.save();
                Events.get().fireAktualisieren();
            });
        }
        return jButtonOKLeiter;
    }

    private JTextField getJTextFieldName() {
        if (jTextFieldName == null) {
            jTextFieldName = new JTextField();
            jTextFieldName.setText(gruppe.getName());
            jTextFieldName.addFocusListener(new java.awt.event.FocusAdapter() {
                @Override
                public void focusLost(java.awt.event.FocusEvent e) {
                    if (getJTextFieldName().getText().contains(",")) {
                        JOptionPane.showMessageDialog(null, "Keine Kommas im Gruppenname");
                        getJButtonOKDaten().setEnabled(false);
                    } else {
                        getJButtonOKDaten().setEnabled(true);
                    }
                }
            });
        }
        return jTextFieldName;
    }

    private JScrollPane getJScrollPaneL() {
        if (jScrollPaneL == null) {
            jScrollPaneL = new JYTableScrollPane(getJTableLeiter());
        }
        return jScrollPaneL;
    }

    private JTable getJTableLeiter() {
        if (jTableLeiter == null) {
            jTableLeiter = tableBuilderLeiter.build();
        }
        return jTableLeiter;
    }

    private JPanel getJPanelTeilnehmer() {
        if (jPanelTeilnehmer == null) {
            jPanelTeilnehmer = new JPanel();
            jPanelTeilnehmer.setLayout(new BorderLayout());
            jPanelTeilnehmer.add(getJScrollPaneT(), java.awt.BorderLayout.CENTER);
            JPanel buttonPanel = new JPanel(new FlowLayout());
            buttonPanel.add(getJButtonOKTeilnehmer());
            jPanelTeilnehmer.add(buttonPanel, java.awt.BorderLayout.SOUTH);
        }
        return jPanelTeilnehmer;
    }

    private JScrollPane getJScrollPaneT() {
        if (jScrollPaneT == null) {
            jScrollPaneT = new JYTableScrollPane(getJTableTeilnehmer());
        }
        return jScrollPaneT;
    }

    private JTable getJTableTeilnehmer() {
        if (jTableTeilnehmer == null) {
            jTableTeilnehmer = tableBuilderTeilnehmer.build();
        }
        return jTableTeilnehmer;
    }

    private JButton getJButtonOKTeilnehmer() {
        if (jButtonOKTeilnehmer == null) {
            jButtonOKTeilnehmer = new JButton();
            jButtonOKTeilnehmer.setText("OK");
            jButtonOKTeilnehmer.addActionListener(e -> {
                tableBuilderTeilnehmer.save();
                Events.get().fireAktualisieren();
            });
        }
        return jButtonOKTeilnehmer;
    }

    private JPanel getJPanelZelt() {
        if (jPanelZelt == null) {
            jPanelZelt = new JPanel();
            jPanelZelt.setLayout(new BorderLayout());
            jPanelZelt.add(getJScrollPaneZ(), java.awt.BorderLayout.CENTER);
            JPanel buttonPanel = new JPanel(new FlowLayout());
            buttonPanel.add(getJButtonOKZelte());
            jPanelZelt.add(buttonPanel, java.awt.BorderLayout.SOUTH);
        }
        return jPanelZelt;
    }

    private JScrollPane getJScrollPaneZ() {
        if (jScrollPaneZ == null) {
            jScrollPaneZ = new JYTableScrollPane(getJTableZelt());
        }
        return jScrollPaneZ;
    }

    private JButton getJButtonOKZelte() {
        if (jButtonOKZelte == null) {
            jButtonOKZelte = new JButton();
            jButtonOKZelte.setText("OK");
            jButtonOKZelte.addActionListener(e -> tableBuilderZelt.save());
        }
        return jButtonOKZelte;
    }

    private JScrollPane getJScrollPaneSchlachtruf() {
        if (jScrollPaneSchlachtruf == null) {
            jScrollPaneSchlachtruf = new JScrollPane();
            jScrollPaneSchlachtruf.setViewportView(getJTextAreaSchlachtruf());
        }
        return jScrollPaneSchlachtruf;
    }

    private JTextArea getJTextAreaSchlachtruf() {
        if (jTextAreaSchlachtruf == null) {
            jTextAreaSchlachtruf = new JTextArea();
            jTextAreaSchlachtruf.setText(gruppe.getSchlachtruf());
        }
        return jTextAreaSchlachtruf;
    }

    private JTable getJTableZelt() {
        if (jTableZelt == null) {
            jTableZelt = tableBuilderZelt.build();
        }
        return jTableZelt;
    }

}
