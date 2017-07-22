package de.zlvp.gui;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import de.zlvp.Client;
import de.zlvp.entity.User;
import de.zlvp.ui.InternalFrame;
import de.zlvp.ui.JListBuilder;

public class Benutzerverwaltung extends InternalFrame {

    private JButton jButtonOK;
    private JButton jButtonAbbrechen;
    private JButton jButtonAnlegen;
    private JButton jButtonLoeschen;
    private JPanel jPanelSouth;
    private JPanel jPanelButtons;
    private JPanel jPanelCheckboxes;

    private JList<User> jListBenutzer;
    private JScrollPane jScrollPaneBenutzerListe;
    private JPanel jPanel;

    private static final long serialVersionUID = 1L;
    private JListBuilder<User> jListBuilder;

    public Benutzerverwaltung() {
        jListBuilder = JListBuilder.get(User.class, () -> Client.get().getAllUsers()).map(u -> u.getName());

        initialize();
        setUp();
        showEmpty();
    }

    private void initialize() {
        this.setSize(500, 300);
        this.setTitle("Benutzerverwaltung");
        this.setContentPane(getJPanel());

        getJPanel().add(getjScrollPaneBenutzerListe(), BorderLayout.CENTER);
        getJPanel().add(getjPanelSouth(), BorderLayout.SOUTH);

        getjPanelSouth().add(getjPanelCheckboxes(), BorderLayout.CENTER);

        refreshCheckBoxPane();

        getjPanelSouth().add(getjPanelButtons(), BorderLayout.SOUTH);
        getjPanelButtons().add(getjButtonAnlegen());
        getjPanelButtons().add(getjButtonOK());
        getjPanelButtons().add(getjButtonAbbrechen());
        getjPanelButtons().add(getjButtonLoeschen());
    }

    public JList<User> getjListBenutzer() {
        if (jListBenutzer == null) {
            jListBenutzer = jListBuilder.build();
            jListBenutzer.addListSelectionListener(e -> {
                User user = getjListBenutzer().getSelectedValue();
                if (user != null) {
                    CardLayout layout = (CardLayout) getjPanelCheckboxes().getLayout();
                    layout.show(getjPanelCheckboxes(), user.getName());
                }
            });
        }
        return jListBenutzer;
    }

    public JScrollPane getjScrollPaneBenutzerListe() {
        if (jScrollPaneBenutzerListe == null) {
            jScrollPaneBenutzerListe = new JScrollPane(getjListBenutzer());
        }
        return jScrollPaneBenutzerListe;
    }

    public JPanel getJPanel() {
        if (jPanel == null) {
            jPanel = new JPanel(new BorderLayout());
        }
        return jPanel;
    }

    public JButton getjButtonOK() {
        if (jButtonOK == null) {
            jButtonOK = new JButton("OK");
            jButtonOK.addActionListener(e -> {
                save();

                setVisible(false);
            });
        }
        return jButtonOK;
    }

    private void save() {
        Map<String, Set<String>> map = new HashMap<>();
        for (Component component : getjPanelCheckboxes().getComponents()) {
            if (component.getName() != null) {
                map.put(component.getName(), new HashSet<>());
                for (Component c : ((JPanel) component).getComponents()) {
                    JCheckBox checkBox = (JCheckBox) c;
                    if (checkBox.isSelected()) {
                        map.get(component.getName()).add(checkBox.getText());
                    }
                }
            }
        }
        Client.get().grantUser(map);
    }

    public JButton getjButtonAbbrechen() {
        if (jButtonAbbrechen == null) {
            jButtonAbbrechen = new JButton("Abbrechen");
            jButtonAbbrechen.addActionListener(e -> setVisible(false));
        }
        return jButtonAbbrechen;
    }

    public JButton getjButtonAnlegen() {
        if (jButtonAnlegen == null) {
            jButtonAnlegen = new JButton("Benutzer Anlegen");
            jButtonAnlegen.addActionListener(e -> {
                JTextField username = new JTextField();
                JPasswordField password = new JPasswordField();
                Object[] message = { "Benutzername:", username, "Passwort:", password };
                int option = JOptionPane.showConfirmDialog(null, message, "Neuen Benutzer anlegen",
                        JOptionPane.OK_CANCEL_OPTION);
                if (option == JOptionPane.OK_OPTION) {
                    Client.get().createUser(username.getText(), password.getPassword());
                    jListBuilder.refresh();
                    refreshCheckBoxPane();
                }
            });
        }
        return jButtonAnlegen;
    }

    public JPanel getjPanelSouth() {
        if (jPanelSouth == null) {
            jPanelSouth = new JPanel();
            jPanelSouth.setLayout(new BorderLayout());
        }
        return jPanelSouth;
    }

    public JPanel getjPanelButtons() {
        if (jPanelButtons == null) {
            jPanelButtons = new JPanel(new FlowLayout(FlowLayout.LEFT));
        }
        return jPanelButtons;
    }

    public JPanel getjPanelCheckboxes() {
        if (jPanelCheckboxes == null) {
            jPanelCheckboxes = new JPanel(new CardLayout());
        }
        return jPanelCheckboxes;
    }

    public JButton getjButtonLoeschen() {
        if (jButtonLoeschen == null) {
            jButtonLoeschen = new JButton("Löschen");
            jButtonLoeschen.addActionListener(e -> {
                save();
                User user = getjListBenutzer().getSelectedValue();
                Client.get().dropUser(user.getName());
                refreshCheckBoxPane();
                jListBuilder.refresh();
                showEmpty();
            });
        }
        return jButtonLoeschen;
    }

    private void refreshCheckBoxPane() {
        getjPanelCheckboxes().removeAll();

        getjPanelCheckboxes().add(new JPanel(), "EMPTY");

        List<String> allGroups = Client.get().getAllGroups();

        for (User user : Client.get().getAllUsers()) {
            JPanel checkboxpanelForUser = new JPanel(new GridLayout(3, 0));
            checkboxpanelForUser.setName(user.getName());
            for (String group : allGroups) {
                JCheckBox checkbox = new JCheckBox(group);
                for (String groupOfUser : user.getGroups()) {
                    if (group.equals(groupOfUser)) {
                        checkbox.setSelected(true);
                    }
                }
                checkboxpanelForUser.add(checkbox);
            }
            getjPanelCheckboxes().add(checkboxpanelForUser, checkboxpanelForUser.getName());
        }
    }

    private void showEmpty() {
        CardLayout layout = (CardLayout) getjPanelCheckboxes().getLayout();
        layout.show(getjPanelCheckboxes(), "EMPTY");
    }
}
