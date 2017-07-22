package de.zlvp.gui;

import static java.util.stream.Collectors.toList;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.beans.PropertyVetoException;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTable;

import de.javasoft.swing.JYTableScrollPane;
import de.zlvp.Client;
import de.zlvp.entity.Lagerinfo;
import de.zlvp.entity.Person;
import de.zlvp.ui.InternalFrame;
import de.zlvp.ui.JTableBuilder;
import de.zlvp.ui.JTableBuilders;

public class LagerinfoVerwalten extends InternalFrame {

    private static final long serialVersionUID = 1L;

    private JPanel jContentPane;
    private JPanel jPanel;
    private JPanel jPanelButtons;
    private JButton jButtonOK;
    private JTable jTablePerson;
    private JButton jButtonAbbrechen;

    private JTableBuilder<Lagerinfo> tableBuilder;

    public LagerinfoVerwalten() {

        List<Person> allPersons = Client.get().getAllPerson();
        tableBuilder = JTableBuilders.lagerinfo(() -> {
            List<Lagerinfo> allLagerinfo = Client.get().getAllLagerinfo();
            return allPersons.stream().map(p -> {
                for (Lagerinfo li : allLagerinfo) {
                    if (p.getId().equals(li.getOriginalId())) {
                        li.setChecked(true);
                        return li;
                    }
                }
                return new Lagerinfo(p);
            }).collect(toList());
        });

        initialize();
        setUp();
        setMaximizable(true);
        try {
            setMaximum(true);
        } catch (PropertyVetoException e) {
            e.printStackTrace();
        }
    }

    private void initialize() {
        this.setSize(542, 364);
        this.setTitle("Lagerinfo Verwalten");
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
            jPanel.add(new JYTableScrollPane(getJTablePerson()), java.awt.BorderLayout.CENTER);
            jPanel.add(getJPanel2(), java.awt.BorderLayout.SOUTH);
        }
        return jPanel;
    }

    private JPanel getJPanel2() {
        if (jPanelButtons == null) {
            jPanelButtons = new JPanel();
            jPanelButtons.setLayout(new FlowLayout());
            jPanelButtons.add(getJButtonOK());
            jPanelButtons.add(getJButtonAbbrechen());
        }
        return jPanelButtons;
    }

    private JButton getJButtonOK() {
        if (jButtonOK == null) {
            jButtonOK = new JButton();
            jButtonOK.setText("OK");
            jButtonOK.addActionListener(e -> tableBuilder.save());
        }
        return jButtonOK;
    }

    private JTable getJTablePerson() {
        if (jTablePerson == null) {
            jTablePerson = tableBuilder.build();
        }
        return jTablePerson;
    }

    private JButton getJButtonAbbrechen() {
        if (jButtonAbbrechen == null) {
            jButtonAbbrechen = new JButton();
            jButtonAbbrechen.setText("Abbrechen");
            jButtonAbbrechen.addActionListener(e -> setVisible(false));
        }
        return jButtonAbbrechen;
    }

} // @jve:decl-index=0:visual-constraint="10,10"
