package de.zlvp.gui;

import static de.zlvp.Client.get;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTable;

import de.javasoft.swing.JYTableScrollPane;
import de.zlvp.entity.Lagerinfo;
import de.zlvp.ui.AbstractJInternalFrame;
import de.zlvp.ui.JTableBuilder;
import de.zlvp.ui.JTableBuilders;

public class LagerinfoVerwalten extends AbstractJInternalFrame {

    private static final long serialVersionUID = 1L;

    private JPanel jContentPane;
    private JPanel jPanel;
    private JPanel jPanelButtons;
    private JTable jTablePerson;
    private JButton jButtonAbbrechen;

    private JTableBuilder<Lagerinfo> tableBuilder;

    public LagerinfoVerwalten() {
        tableBuilder = JTableBuilders.lagerinfo(get()::getAllPersons, get()::getAllLagerinfo);
        initialize();
        setup();
        // Sollte erst nach Laden gemacht werden.
        setVisible(true);
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
            jPanelButtons.add(getJButtonAbbrechen());
        }
        return jPanelButtons;
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

}
