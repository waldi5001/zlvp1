package de.zlvp.gui;

import static de.zlvp.Client.get;
import static javax.swing.JSplitPane.VERTICAL_SPLIT;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridBagLayout;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextField;

import de.zlvp.Client;
import de.zlvp.entity.User;
import de.zlvp.ui.AbstractJInternalFrame;
import de.zlvp.ui.DualListField;
import de.zlvp.ui.DualListField.ElementAddedCallback;
import de.zlvp.ui.DualListField.ElementRemovedCallback;
import de.zlvp.ui.JListBuilder;

public class Benutzerverwaltung extends AbstractJInternalFrame {
    private static final long serialVersionUID = 1L;

    private JButton jButtonOK;
    private JButton jButtonAnlegen;
    private JButton jButtonLoeschen;
    private JPanel jPanelSouth;
    private JPanel jPanelButtons;

    private JList<User> jListBenutzer;
    private JScrollPane jScrollPaneBenutzerListe;
    private JSplitPane jSplitPane;

    private JListBuilder<User> jListBuilder;
    private JListBuilder<String> jListBuilderTo;
    private JListBuilder<String> jListBuilderFrom;

    public Benutzerverwaltung() {
        jListBuilder = JListBuilder.get(User.class, Client.get()::getAllUsers).map(u -> u.getName());

        initialize();
        setupDialog();
    }

    private void initialize() {
        this.setSize(500, 350);
        this.setTitle("Benutzerverwaltung");

        JPanel jPanel = new JPanel(new GridBagLayout());
        jPanel.add(getJSplitPane(), defaultGridbagConstraints());

        getContentPane().add(jPanel, BorderLayout.CENTER);

        getJSplitPane().add(getjScrollPaneBenutzerListe());

        jListBuilderFrom = JListBuilder.get(String.class, Client.get()::getAllGroups);
        jListBuilderTo = JListBuilder.get(String.class, asyncCallback -> {
            if (getjListBenutzer().getSelectedValue() != null) {
                asyncCallback.get(new ArrayList<>(getjListBenutzer().getSelectedValue().getGroups()));
            }
        });

        getJSplitPane().add(createDualListField());

        getContentPane().add(getjPanelButtons(), BorderLayout.SOUTH);
        getjPanelButtons().add(getjButtonAnlegen());
        getjPanelButtons().add(getjButtonOK());
        getjPanelButtons().add(getjButtonLoeschen());
    }

    private DualListField<String, String> createDualListField() {
        return new DualListField<>(jListBuilderFrom.build(), jListBuilderTo.build(),
                (ElementAddedCallback<String>) from -> {
                    Client.get().grantUser(getjListBenutzer().getSelectedValue().getName(), from, cb -> {
                    });
                }, (ElementRemovedCallback<String>) to -> {
                    Client.get().revokeUser(getjListBenutzer().getSelectedValue().getName(), to, cb -> {
                    });
                });
    }

    public JList<User> getjListBenutzer() {
        if (jListBenutzer == null) {
            jListBenutzer = jListBuilder.build();
            jListBenutzer.addListSelectionListener(e -> {
                if (!e.getValueIsAdjusting()) {
                    jListBuilderFrom.refresh();
                    jListBuilderTo.refresh();
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

    public JSplitPane getJSplitPane() {
        if (jSplitPane == null) {
            jSplitPane = new JSplitPane(VERTICAL_SPLIT);
            jSplitPane.setResizeWeight(0.3);
        }
        return jSplitPane;
    }

    public JButton getjButtonOK() {
        if (jButtonOK == null) {
            jButtonOK = new JButton("OK");
            jButtonOK.addActionListener(e -> setVisible(false));
        }
        return jButtonOK;
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
                    Client.get().createUser(username.getText(), password.getPassword(),
                            asyncCallback -> jListBuilder.refresh());
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

    public JButton getjButtonLoeschen() {
        if (jButtonLoeschen == null) {
            jButtonLoeschen = new JButton("Löschen");
            jButtonLoeschen.addActionListener(e -> {
                User user = getjListBenutzer().getSelectedValue();
                get().dropUser(user.getName(), asyncCallback -> jListBuilder.refresh());
            });
        }
        return jButtonLoeschen;
    }

}
