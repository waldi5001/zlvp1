package de.zlvp.gui;

import static de.zlvp.Client.get;

import java.awt.AWTKeyStroke;
import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.KeyboardFocusManager;
import java.awt.event.KeyEvent;
import java.util.HashSet;
import java.util.Set;

import javax.swing.InputMap;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.BadLocationException;

import de.javasoft.swing.JYTableScrollPane;
import de.zlvp.Events;
import de.zlvp.entity.Gruppe;
import de.zlvp.entity.Lager;
import de.zlvp.entity.Leiter;
import de.zlvp.entity.Teilnehmer;
import de.zlvp.entity.Zelt;
import de.zlvp.ui.JTableBuilder;
import de.zlvp.ui.JTableBuilders;

public class TPGruppe extends JTabbedPane {

    private static final long serialVersionUID = 1L;

    private JPanel jPanelLeiter;

    private JPanel jPanelDaten;

    private JPanel jPanel;

    private JTextField jTextFieldName;
    private JLabel jLabelName;
    private JLabel jLabelSchlachtruf;

    private JScrollPane jScrollPaneL;

    private JTable jTableLeiter;

    private JPanel jPanelTeilnehmer;

    private JScrollPane jScrollPaneT;

    private JTable jTableTeilnehmer;

    private JPanel jPanelZelt;

    private JScrollPane jScrollPaneZ;

    private JTable jTableZelt;

    private JScrollPane jScrollPaneSchlachtruf;

    private JTextArea jTextAreaSchlachtruf;

    private final Gruppe gruppe;

    private JTableBuilder<Leiter> tableBuilderLeiter;
    private JTableBuilder<Teilnehmer> tableBuilderTeilnehmer;
    private JTableBuilder<Zelt> tableBuilderZelt;

    public TPGruppe(Lager lager, Gruppe gruppe) {
        super();
        this.gruppe = gruppe;
        this.gruppe.setLager(lager);
        tableBuilderTeilnehmer = JTableBuilders.teilnehmer(gruppe, get()::getAllPersons,
                allTeilnehmer -> get().getAllTeilnehmer(gruppe.getId(), allTeilnehmer));
        tableBuilderLeiter = JTableBuilders.leiter(gruppe, get()::getAllPersons,
                allLeiter -> get().getAllLeiter(gruppe.getId(), allLeiter));
        tableBuilderZelt = JTableBuilders.zelt(gruppe,
                allZelt -> get().getAllZeltFromLager(gruppe.getLager().getId(), allZelt),
                allZeltFromGruppe -> get().getAllZeltFromGruppe(gruppe.getId(), allZeltFromGruppe));
        initialize();
        setupTabTraversalKeys();
    }

    private void initialize() {
        this.addTab("Daten", getJPanelDaten());
        this.addTab("Leiter", getJPanelLeiter());
        this.addTab("Teilnehmer", getJPanelTeilnehmer());
        this.addTab("Zelt(e) wählen", getJPanelZelt());
    }

    private JPanel getJPanelLeiter() {
        if (jPanelLeiter == null) {
            jPanelLeiter = new JPanel();
            jPanelLeiter.setLayout(new BorderLayout());
            jPanelLeiter.add(getJScrollPaneL(), java.awt.BorderLayout.CENTER);
        }
        return jPanelLeiter;
    }

    private JPanel getJPanelDaten() {
        if (jPanelDaten == null) {
            jPanelDaten = new JPanel();
            jPanelDaten.setLayout(new BorderLayout());
            jPanelDaten.add(getJPanel(), java.awt.BorderLayout.CENTER);
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

    private JTextField getJTextFieldName() {
        if (jTextFieldName == null) {
            jTextFieldName = new JTextField();
            jTextFieldName.setText(gruppe.getName());
            jTextFieldName.getDocument().addDocumentListener(new DocumentListener() {
                @Override
                public void changedUpdate(DocumentEvent documentEvent) {
                }

                @Override
                public void insertUpdate(DocumentEvent documentEvent) {
                    save(documentEvent);
                }

                @Override
                public void removeUpdate(DocumentEvent documentEvent) {
                    save(documentEvent);
                }

                private void save(DocumentEvent documentEvent) {
                    try {
                        String name = documentEvent.getDocument().getText(0, documentEvent.getDocument().getLength());
                        gruppe.setName(name);
                        get().aendereGruppe(gruppe.getId(), gruppe.getName(), gruppe.getSchlachtruf(), cb -> {
                            Events.get().fireGruppeSaved(gruppe, null, null);
                        });
                    } catch (BadLocationException e) {
                        throw new RuntimeException(e.getMessage(), e);
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

    private JPanel getJPanelZelt() {
        if (jPanelZelt == null) {
            jPanelZelt = new JPanel();
            jPanelZelt.setLayout(new BorderLayout());
            jPanelZelt.add(getJScrollPaneZ(), java.awt.BorderLayout.CENTER);
        }
        return jPanelZelt;
    }

    private JScrollPane getJScrollPaneZ() {
        if (jScrollPaneZ == null) {
            jScrollPaneZ = new JYTableScrollPane(getJTableZelt());
        }
        return jScrollPaneZ;
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
            jTextAreaSchlachtruf.getDocument().addDocumentListener(new DocumentListener() {
                @Override
                public void changedUpdate(DocumentEvent documentEvent) {
                }

                @Override
                public void insertUpdate(DocumentEvent documentEvent) {
                    save(documentEvent);
                }

                @Override
                public void removeUpdate(DocumentEvent documentEvent) {
                    save(documentEvent);
                }

                private void save(DocumentEvent documentEvent) {
                    try {
                        String schlachtruf = documentEvent.getDocument().getText(0,
                                documentEvent.getDocument().getLength());
                        gruppe.setSchlachtruf(schlachtruf);
                        get().aendereGruppe(gruppe.getId(), gruppe.getName(), gruppe.getSchlachtruf(), cb -> {
                        });
                    } catch (BadLocationException e) {
                        throw new RuntimeException(e.getMessage(), e);
                    }
                }
            });
        }
        return jTextAreaSchlachtruf;
    }

    private JTable getJTableZelt() {
        if (jTableZelt == null) {
            jTableZelt = tableBuilderZelt.build();
        }
        return jTableZelt;
    }

    private void setupTabTraversalKeys() {
        KeyStroke ctrlTab = KeyStroke.getKeyStroke(KeyEvent.VK_TAB, KeyEvent.CTRL_DOWN_MASK);
        KeyStroke ctrlShiftTab = KeyStroke.getKeyStroke(KeyEvent.VK_TAB,
                KeyEvent.SHIFT_DOWN_MASK | KeyEvent.CTRL_DOWN_MASK);

        // Remove ctrl-tab from normal focus traversal
        Set<AWTKeyStroke> forwardKeys = new HashSet<>(
                getFocusTraversalKeys(KeyboardFocusManager.FORWARD_TRAVERSAL_KEYS));
        forwardKeys.remove(ctrlTab);
        setFocusTraversalKeys(KeyboardFocusManager.FORWARD_TRAVERSAL_KEYS, forwardKeys);

        // Remove ctrl-shift-tab from normal focus traversal
        Set<AWTKeyStroke> backwardKeys = new HashSet<>(
                getFocusTraversalKeys(KeyboardFocusManager.BACKWARD_TRAVERSAL_KEYS));
        backwardKeys.remove(ctrlShiftTab);
        setFocusTraversalKeys(KeyboardFocusManager.BACKWARD_TRAVERSAL_KEYS, backwardKeys);

        // Add keys to the tab's input map
        InputMap inputMap = getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
        inputMap.put(ctrlTab, "navigateNext");
        inputMap.put(ctrlShiftTab, "navigatePrevious");
    }
}
