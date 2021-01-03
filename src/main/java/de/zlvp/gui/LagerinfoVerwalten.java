package de.zlvp.gui;

import static de.zlvp.Client.get;

import java.awt.BorderLayout;

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
    private JTable jTablePerson;

    private JTableBuilder<Lagerinfo> tableBuilder;

    public LagerinfoVerwalten() {
        tableBuilder =
                JTableBuilders.lagerinfo(get()::getAllPersons, get()::getAllLagerinfo).doubleClicked(selectedValue -> new PersonSuchen(selectedValue.getId()));
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
        }
        return jPanel;
    }

    private JTable getJTablePerson() {
        if (jTablePerson == null) {
            jTablePerson = tableBuilder.buildAndLoad();
        }
        return jTablePerson;
    }

}
