package de.zlvp.gui;

import static de.zlvp.Client.get;

import java.awt.AWTKeyStroke;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.KeyboardFocusManager;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.ItemEvent;
import java.awt.event.KeyEvent;
import java.beans.PropertyChangeListener;
import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

import javax.swing.InputMap;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.DateFormatter;

import com.google.common.eventbus.Subscribe;

import de.javasoft.swing.JYTableScrollPane;
import de.zlvp.Events;
import de.zlvp.Events.LagerSelected;
import de.zlvp.entity.Essen;
import de.zlvp.entity.Gruppe;
import de.zlvp.entity.Lager;
import de.zlvp.entity.Lagerort;
import de.zlvp.entity.Materialwart;
import de.zlvp.entity.Programm;
import de.zlvp.entity.Stab;
import de.zlvp.entity.Zelt;
import de.zlvp.ui.JComboBoxBuilder;
import de.zlvp.ui.JTableBuilder;
import de.zlvp.ui.JTableBuilders;

public class TPLager extends JTabbedPane {

    private static final long serialVersionUID = 1;

    private JPanel jPanelStab;
    private JPanel jPanelGruppe;
    private JPanel jPanelZelte;
    private JPanel jPanelProgramm;
    private JScrollPane jScrollPaneS;
    private JTable jTableStab;
    private JScrollPane jScrollPaneG;
    private JTable jTableGruppe;
    private JScrollPane jScrollPaneZ;
    private JTable jTableZelte;
    private JPanel jPanel15;
    private JPanel jPanelEssen;
    private JPanel jPanel18;
    private JPanel jPanelDaten;
    private JTextField jTextFieldName;
    private JTextField jTextFieldThema;
    private JFormattedTextField jFormattedTextFieldDatumStart;
    private JFormattedTextField jFormattedTextFieldDatumStop;
    private JPanel jPanel1;
    private JLabel jLabel4;
    private JLabel jLabel5;
    private JLabel jLabel6;
    private JLabel jLabel7;
    private JLabel jLabel8;
    private JPanel jPanelMaterialwart;
    private JScrollPane jScrollPaneMW;
    private JTable jTableMaterialwart;
    private JScrollPane jScrollPaneProgramm;
    private JTable jTableProgramm;
    private JButton jButtonHinzufuegenProgramm;
    private JScrollPane jScrollPaneEssen;
    private JTable jTableEssen;
    private JButton jButtonHinzufuegenEssen;
    private JComboBox<Lagerort> jComboBoxLagerOrt;
    private JPanel jPanelLegenda;
    private Lager lager;

    private JComboBoxBuilder<Lagerort> comboboxBuilderLagerort;

    private JTableBuilder<Stab> tableBuilderStab;
    private JTableBuilder<Materialwart> tableBuilderMaterialwart;
    private JTableBuilder<Zelt> tableBuilderZelt;
    private JTableBuilder<Gruppe> tableBuilderGruppe;
    private JTableBuilder<Programm> tableBuilderProgramm;
    private JTableBuilder<Essen> tableBuilderEssen;

    private JCheckBox jCheckBoxAlleGruppenAnzeigen;

    private JButton jButtonLoeschenProgramm;
    private JButton jButtonLoeschenEssen;

    public TPLager() {
        Events.bus().register(this);

        tableBuilderStab = JTableBuilders.stab(() -> lager, get()::getAllPersons,
                allStab -> get().getAllStab(lager.getId(), allStab));
        tableBuilderMaterialwart = JTableBuilders.materialwart(() -> lager, get()::getAllPersons,
                allMaterialwart -> get().getAllMaterialwart(lager.getId(), allMaterialwart));
        tableBuilderZelt = JTableBuilders.zelteVonLager(() -> lager, get()::getAllZelt,
                allZeltFromLager -> get().getAllZeltFromLager(lager.getId(), allZeltFromLager));
        tableBuilderGruppe = JTableBuilders.gruppe(() -> lager, getJCheckBoxAlleGruppenAnzeigen()::isSelected,
                cb -> cb.get(lager.getGruppe()));
        comboboxBuilderLagerort = JComboBoxBuilder.get(Lagerort.class, get()::getAllLagerort, () -> {
            if (lager != null) {
                if (lager.getLagerort() != null) {
                    jComboBoxLagerOrt.setSelectedItem(lager.getLagerort());
                } else {
                    jComboBoxLagerOrt.setSelectedIndex(-1);
                }
            }
        });
        tableBuilderProgramm = JTableBuilders.programm(() -> lager, cb -> get().getAllProgramm(lager.getId(), cb));
        tableBuilderEssen = JTableBuilders.essen(() -> lager, cb -> get().getAllEssen(lager.getId(), cb));
        initialize();
        setupTabTraversalKeys();
    }

    private void initialize() {
        this.addTab("Daten", null, getJPanelDaten());
        this.addTab("Staab", null, getJPanelStab());
        this.addTab("Gruppe", null, getJPanelGruppe());
        this.addTab("Materialwart", null, getJPanelMaterialwart());
        this.addTab("Zelte", null, getJPanelZelte());
        this.addTab("Programm", null, getJPanelProgramm());
        this.addTab("Essen", null, getJPanelEssen());
        this.addTab("Legenda", null, getJPanelLegenda());
    }

    private JPanel getJPanelStab() {
        if (jPanelStab == null) {
            jPanelStab = new JPanel();
            jPanelStab.setLayout(new BorderLayout());
            jPanelStab.add(getJScrollPaneS(), BorderLayout.CENTER);
        }
        return jPanelStab;
    }

    private JPanel getJPanelGruppe() {
        if (jPanelGruppe == null) {
            jPanelGruppe = new JPanel();
            jPanelGruppe.setLayout(new BorderLayout());
            jPanelGruppe.add(getJScrollPaneG(), java.awt.BorderLayout.CENTER);

            JPanel panel = new JPanel(new GridLayout(1, 2));
            panel.add(getJCheckBoxAlleGruppenAnzeigen());

            jPanelGruppe.add(panel, java.awt.BorderLayout.SOUTH);
        }
        return jPanelGruppe;
    }

    private JCheckBox getJCheckBoxAlleGruppenAnzeigen() {
        if (jCheckBoxAlleGruppenAnzeigen == null) {
            jCheckBoxAlleGruppenAnzeigen = new JCheckBox("Alle Gruppen anzeigen");
            jCheckBoxAlleGruppenAnzeigen.addActionListener(e -> tableBuilderGruppe.refresh());
        }
        return jCheckBoxAlleGruppenAnzeigen;
    }

    private JPanel getJPanelZelte() {
        if (jPanelZelte == null) {
            jPanelZelte = new JPanel();
            jPanelZelte.setLayout(new BorderLayout());
            jPanelZelte.add(getJScrollPaneZ(), java.awt.BorderLayout.CENTER);
        }
        return jPanelZelte;
    }

    private JPanel getJPanelProgramm() {
        if (jPanelProgramm == null) {
            jPanelProgramm = new JPanel();
            jPanelProgramm.setLayout(new BorderLayout());
            jPanelProgramm.add(getJScrollPaneProgramm(), java.awt.BorderLayout.CENTER);
            jPanelProgramm.add(getJPanel15(), java.awt.BorderLayout.SOUTH);
        }
        return jPanelProgramm;
    }

    private JScrollPane getJScrollPaneS() {
        if (jScrollPaneS == null) {
            jScrollPaneS = new JYTableScrollPane(getJTableStab());
        }
        return jScrollPaneS;
    }

    private JTable getJTableStab() {
        if (jTableStab == null) {
            jTableStab = tableBuilderStab.build();
        }
        return jTableStab;
    }

    private JScrollPane getJScrollPaneG() {
        if (jScrollPaneG == null) {
            jScrollPaneG = new JYTableScrollPane(getJTableGruppe());
        }
        return jScrollPaneG;
    }

    private JTable getJTableGruppe() {
        if (jTableGruppe == null) {
            jTableGruppe = tableBuilderGruppe.build();
        }
        return jTableGruppe;
    }

    private JScrollPane getJScrollPaneZ() {
        if (jScrollPaneZ == null) {
            jScrollPaneZ = new JYTableScrollPane(getJTableZelte());
        }
        return jScrollPaneZ;
    }

    private JTable getJTableZelte() {
        if (jTableZelte == null) {
            jTableZelte = tableBuilderZelt.build();
        }
        return jTableZelte;
    }

    private JPanel getJPanel15() {
        if (jPanel15 == null) {
            jPanel15 = new JPanel();
            jPanel15.setLayout(new FlowLayout());
            jPanel15.add(getJButtonHinzufuegenProgramm(), null);
            jPanel15.add(getJButtonLoeschenProgramm(), null);
        }
        return jPanel15;
    }

    private JPanel getJPanelEssen() {
        if (jPanelEssen == null) {
            jPanelEssen = new JPanel();
            jPanelEssen.setLayout(new BorderLayout());
            jPanelEssen.add(getJScrollPaneEssen(), java.awt.BorderLayout.CENTER);
            jPanelEssen.add(getJPanel18(), java.awt.BorderLayout.SOUTH);
        }
        return jPanelEssen;
    }

    private JPanel getJPanel18() {
        if (jPanel18 == null) {
            jPanel18 = new JPanel();
            jPanel18.setLayout(new FlowLayout());
            jPanel18.add(getJButtonHinzufuegenEssen(), null);
            jPanel18.add(getJButtonLoeschenEssen(), null);
        }
        return jPanel18;
    }

    private JPanel getJPanelDaten() {
        if (jPanelDaten == null) {
            jPanelDaten = new JPanel();
            jPanelDaten.setLayout(new BorderLayout());
            jPanelDaten.add(getJPanel1(), java.awt.BorderLayout.CENTER);
        }
        return jPanelDaten;
    }

    private JTextField getJTextFieldName() {
        if (jTextFieldName == null) {
            jTextFieldName = new JTextField();
            jTextFieldName.addFocusListener(new FocusAdapter() {
                @Override
                public void focusLost(FocusEvent e) {
                    lager.setName(jTextFieldName.getText());
                    get().aendereLager(lager.getId(), lager.getName(), lager.getThema(), lager.getDatumStart(),
                            lager.getDatumStop(), result -> {
                                Events.get().fireLagerSaved(lager);
                            });
                }

            });
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
                        lager.setName(name);
                        Events.get().fireLagerSaved(lager);
                    } catch (BadLocationException e) {
                        throw new RuntimeException(e.getMessage(), e);
                    }
                }
            });
        }
        return jTextFieldName;
    }

    private JTextField getJTextFieldThema() {
        if (jTextFieldThema == null) {
            jTextFieldThema = new JTextField();
            jTextFieldThema.addFocusListener(new FocusAdapter() {
                @Override
                public void focusLost(FocusEvent e) {
                    lager.setThema(jTextFieldThema.getText());
                    get().aendereLager(lager.getId(), lager.getName(), lager.getThema(), lager.getDatumStart(),
                            lager.getDatumStop(), result -> {
                                Events.get().fireLagerSaved(lager);
                            });
                }

            });
        }
        return jTextFieldThema;
    }

    private JFormattedTextField getJFormattedTextFieldDatumStart() {
        if (jFormattedTextFieldDatumStart == null) {
            jFormattedTextFieldDatumStart = new JFormattedTextField(
                    new DateFormatter(DateFormat.getDateInstance(DateFormat.SHORT, Locale.GERMAN)));
            PropertyChangeListener l = evt -> {
                lager.setDatumStart((Date) evt.getNewValue());
                get().aendereLager(lager.getId(), lager.getName(), lager.getThema(), lager.getDatumStart(),
                        lager.getDatumStop(), result -> {
                        });
            };
            jFormattedTextFieldDatumStart.addPropertyChangeListener("value", l);
        }
        return jFormattedTextFieldDatumStart;
    }

    private JFormattedTextField getJFormattedTextFieldDatumStop() {
        if (jFormattedTextFieldDatumStop == null) {
            jFormattedTextFieldDatumStop = new JFormattedTextField(
                    new DateFormatter(DateFormat.getDateInstance(DateFormat.SHORT, Locale.GERMAN)));
            PropertyChangeListener l = evt -> {
                lager.setDatumStop((Date) evt.getNewValue());
                get().aendereLager(lager.getId(), lager.getName(), lager.getThema(), lager.getDatumStart(),
                        lager.getDatumStop(), result -> {
                        });
            };
            jFormattedTextFieldDatumStop.addPropertyChangeListener("value", l);
        }
        return jFormattedTextFieldDatumStop;
    }

    private JPanel getJPanel1() {
        if (jPanel1 == null) {
            GridBagConstraints gridBagConstraints43 = new GridBagConstraints();
            gridBagConstraints43.fill = GridBagConstraints.BOTH;
            gridBagConstraints43.gridy = 3;
            gridBagConstraints43.weightx = 1.0;
            gridBagConstraints43.insets = new Insets(10, 10, 0, 10);
            gridBagConstraints43.gridx = 1;
            GridBagConstraints gridBagConstraints39 = new GridBagConstraints();
            gridBagConstraints39.gridx = 0;
            gridBagConstraints39.insets = new java.awt.Insets(10, 10, 0, 0);
            gridBagConstraints39.anchor = java.awt.GridBagConstraints.WEST;
            gridBagConstraints39.gridy = 6;
            jLabel8 = new JLabel();
            jLabel8.setText("Datum Stop:");
            GridBagConstraints gridBagConstraints38 = new GridBagConstraints();
            gridBagConstraints38.gridx = 0;
            gridBagConstraints38.insets = new java.awt.Insets(10, 10, 0, 0);
            gridBagConstraints38.anchor = java.awt.GridBagConstraints.WEST;
            gridBagConstraints38.gridy = 4;
            jLabel7 = new JLabel();
            jLabel7.setText("Datum Start:");
            GridBagConstraints gridBagConstraints37 = new GridBagConstraints();
            gridBagConstraints37.gridx = 0;
            gridBagConstraints37.insets = new java.awt.Insets(10, 10, 0, 0);
            gridBagConstraints37.anchor = java.awt.GridBagConstraints.WEST;
            gridBagConstraints37.gridy = 3;
            jLabel6 = new JLabel();
            jLabel6.setText("Ort:");
            GridBagConstraints gridBagConstraints36 = new GridBagConstraints();
            gridBagConstraints36.gridx = 0;
            gridBagConstraints36.insets = new java.awt.Insets(10, 10, 0, 0);
            gridBagConstraints36.anchor = java.awt.GridBagConstraints.WEST;
            gridBagConstraints36.gridy = 2;
            jLabel5 = new JLabel();
            jLabel5.setText("Thema:");
            GridBagConstraints gridBagConstraints33 = new GridBagConstraints();
            gridBagConstraints33.gridx = 0;
            gridBagConstraints33.insets = new java.awt.Insets(10, 10, 0, 0);
            gridBagConstraints33.anchor = java.awt.GridBagConstraints.WEST;
            gridBagConstraints33.gridy = 0;
            jLabel4 = new JLabel();
            jLabel4.setText("Name:");
            GridBagConstraints gridBagConstraints40 = new GridBagConstraints();
            gridBagConstraints40.fill = java.awt.GridBagConstraints.HORIZONTAL;
            gridBagConstraints40.gridx = 1;
            gridBagConstraints40.gridy = 0;
            gridBagConstraints40.weightx = 1.0;
            gridBagConstraints40.insets = new java.awt.Insets(10, 10, 0, 10);
            GridBagConstraints gridBagConstraints42 = new GridBagConstraints();
            gridBagConstraints42.fill = java.awt.GridBagConstraints.HORIZONTAL;
            gridBagConstraints42.gridwidth = 2;
            gridBagConstraints42.gridx = 1;
            gridBagConstraints42.gridy = 2;
            gridBagConstraints42.weightx = 1.0;
            gridBagConstraints42.insets = new java.awt.Insets(10, 10, 0, 10);
            GridBagConstraints gridBagConstraints44 = new GridBagConstraints();
            gridBagConstraints44.fill = java.awt.GridBagConstraints.HORIZONTAL;
            gridBagConstraints44.gridx = 1;
            gridBagConstraints44.gridy = 4;
            gridBagConstraints44.weightx = 1.0;
            gridBagConstraints44.insets = new java.awt.Insets(10, 10, 0, 10);
            GridBagConstraints gridBagConstraints45 = new GridBagConstraints();
            gridBagConstraints45.fill = java.awt.GridBagConstraints.HORIZONTAL;
            gridBagConstraints45.gridx = 1;
            gridBagConstraints45.gridy = 6;
            gridBagConstraints45.weightx = 1.0;
            gridBagConstraints45.insets = new java.awt.Insets(10, 10, 0, 10);
            jPanel1 = new JPanel();
            jPanel1.setLayout(new GridBagLayout());
            jPanel1.add(getJFormattedTextFieldDatumStop(), gridBagConstraints45);
            jPanel1.add(getJFormattedTextFieldDatumStart(), gridBagConstraints44);
            jPanel1.add(getJTextFieldThema(), gridBagConstraints42);
            jPanel1.add(getJTextFieldName(), gridBagConstraints40);
            jPanel1.add(jLabel4, gridBagConstraints33);
            jPanel1.add(jLabel5, gridBagConstraints36);
            jPanel1.add(jLabel6, gridBagConstraints37);
            jPanel1.add(jLabel7, gridBagConstraints38);
            jPanel1.add(jLabel8, gridBagConstraints39);
            jPanel1.add(getJComboBoxLagerOrt(), gridBagConstraints43);
        }
        return jPanel1;
    }

    private JPanel getJPanelMaterialwart() {
        if (jPanelMaterialwart == null) {
            jPanelMaterialwart = new JPanel();
            jPanelMaterialwart.setLayout(new BorderLayout());
            jPanelMaterialwart.add(getJScrollPaneMW(), BorderLayout.CENTER);
        }
        return jPanelMaterialwart;
    }

    private JScrollPane getJScrollPaneMW() {
        if (jScrollPaneMW == null) {
            jScrollPaneMW = new JYTableScrollPane(getJTableMaterialwart());
        }
        return jScrollPaneMW;
    }

    private JTable getJTableMaterialwart() {
        if (jTableMaterialwart == null) {
            jTableMaterialwart = tableBuilderMaterialwart.build();
        }
        return jTableMaterialwart;
    }

    private JScrollPane getJScrollPaneProgramm() {
        if (jScrollPaneProgramm == null) {
            jScrollPaneProgramm = new JYTableScrollPane(getJTableProgramm());
        }
        return jScrollPaneProgramm;
    }

    private JTable getJTableProgramm() {
        if (jTableProgramm == null) {
            jTableProgramm = tableBuilderProgramm.build();
        }
        return jTableProgramm;
    }

    private JButton getJButtonHinzufuegenProgramm() {
        if (jButtonHinzufuegenProgramm == null) {
            jButtonHinzufuegenProgramm = new JButton();
            jButtonHinzufuegenProgramm.setText("Hinzufügen");
            jButtonHinzufuegenProgramm.addActionListener(e -> {
                if (getJTableProgramm().getModel().getRowCount() == 0) {
                    get().speichereProgramm(lager.getId(), null, lager.getDatumStart(), null, null, null,
                            asyncCallback -> tableBuilderProgramm.refresh());
                } else {
                    Date letztesDatum = (Date) getJTableProgramm().getModel()
                            .getValueAt(getJTableProgramm().getModel().getRowCount() - 1, 0);

                    Calendar c = Calendar.getInstance();
                    c.setTime(letztesDatum);
                    c.add(Calendar.DAY_OF_MONTH, 1);

                    get().speichereProgramm(lager.getId(), null, c.getTime(), null, null, null,
                            asyncCallback -> tableBuilderProgramm.refresh());
                }
            });
        }
        return jButtonHinzufuegenProgramm;
    }

    private JButton getJButtonLoeschenProgramm() {
        if (jButtonLoeschenProgramm == null) {
            jButtonLoeschenProgramm = new JButton();
            jButtonLoeschenProgramm.setText("Löschen");
            jButtonLoeschenProgramm.addActionListener(e -> {
                tableBuilderProgramm.deleteSelectedRows();
            });
        }
        return jButtonLoeschenProgramm;
    }

    private JButton getJButtonLoeschenEssen() {
        if (jButtonLoeschenEssen == null) {
            jButtonLoeschenEssen = new JButton();
            jButtonLoeschenEssen.setText("Löschen");
            jButtonLoeschenEssen.addActionListener(e -> {
                tableBuilderEssen.deleteSelectedRows();
            });
        }
        return jButtonLoeschenEssen;
    }

    private JScrollPane getJScrollPaneEssen() {
        if (jScrollPaneEssen == null) {
            jScrollPaneEssen = new JYTableScrollPane(getJTableEssen());
        }
        return jScrollPaneEssen;
    }

    private JTable getJTableEssen() {
        if (jTableEssen == null) {
            jTableEssen = tableBuilderEssen.build();
        }
        return jTableEssen;
    }

    private JButton getJButtonHinzufuegenEssen() {
        if (jButtonHinzufuegenEssen == null) {
            jButtonHinzufuegenEssen = new JButton();
            jButtonHinzufuegenEssen.setText("Hinzufügen");
            jButtonHinzufuegenEssen.addActionListener(e -> {
                if (getJTableEssen().getModel().getRowCount() == 0) {
                    get().speichereEssen(lager.getId(), null, lager.getDatumStart(), null, null, null,
                            cb -> tableBuilderEssen.refresh());
                } else {
                    Date letztesDatum = (Date) getJTableEssen().getModel()
                            .getValueAt(getJTableEssen().getModel().getRowCount() - 1, 0);

                    Calendar c = Calendar.getInstance();
                    c.setTime(letztesDatum);
                    c.add(Calendar.DAY_OF_MONTH, 1);

                    get().speichereEssen(lager.getId(), null, c.getTime(), null, null, null,
                            cb -> tableBuilderEssen.refresh());
                }
            });
        }
        return jButtonHinzufuegenEssen;
    }

    private JComboBox<Lagerort> getJComboBoxLagerOrt() {
        if (jComboBoxLagerOrt == null) {
            jComboBoxLagerOrt = comboboxBuilderLagerort.build();
            jComboBoxLagerOrt.addItemListener(e -> {
                if (e.getStateChange() == ItemEvent.SELECTED && jComboBoxLagerOrt.isPopupVisible()) {
                    lager.setLagerort((Lagerort) e.getItem());
                    get().speichereLager(lager.getId(), lager.getName(), lager.getThema(), lager.getDatumStart(),
                            lager.getDatumStop(), lager.getJahr().getId(), lager.getLagerort().getId(),
                            cb -> Events.get().fireLagerSaved(lager));
                }
            });
        }
        return jComboBoxLagerOrt;
    }

    private JPanel getJPanelLegenda() {
        if (jPanelLegenda == null) {
            jPanelLegenda = new LegendaVerwaltenPanel(
                    () -> lager == null ? new Lagerort(0, 0, "") : lager.getLagerort());
        }
        return jPanelLegenda;
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

    @Subscribe
    private void aktualisiere(LagerSelected event) {
        this.lager = event.get();
        getJFormattedTextFieldDatumStart().setValue(lager.getDatumStart());
        getJFormattedTextFieldDatumStop().setValue(lager.getDatumStop());
        getJTextFieldThema().setText(lager.getThema());
        getJTextFieldName().setText(lager.getName());

        comboboxBuilderLagerort.refresh();
        tableBuilderStab.refresh();
        tableBuilderMaterialwart.refresh();
        tableBuilderZelt.refresh();
        tableBuilderGruppe.refresh();
        tableBuilderProgramm.refresh();
        tableBuilderEssen.refresh();
    }

}
