package de.zlvp.gui;

import static de.zlvp.Client.get;

import java.awt.BorderLayout;
import java.awt.Desktop;
import java.awt.Toolkit;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.filechooser.FileFilter;

import com.google.common.eventbus.Subscribe;

import de.javasoft.plaf.synthetica.SyntheticaRootPaneUI;
import de.javasoft.swing.AboutDialog;
import de.javasoft.swing.ExtendedFileChooser;
import de.zlvp.Client;
import de.zlvp.ClientExceptionInterceptor;
import de.zlvp.Events;
import de.zlvp.Events.DisableMenuItems;
import de.zlvp.Events.GruppeSelected;
import de.zlvp.Events.JahrSelected;
import de.zlvp.Events.LagerSelected;
import de.zlvp.Events.LoginSuccessfull;
import de.zlvp.Events.PersonSelected;
import de.zlvp.SelectionContext;
import de.zlvp.entity.Jahr;
import de.zlvp.entity.Lagerort;
import de.zlvp.entity.ZeltdetailBezeichnung;
import de.zlvp.ui.Actions;
import de.zlvp.ui.DesktopPane;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.view.JasperViewer;

public class FensterKlasse extends JFrame {

    private static final long serialVersionUID = 1L;

    private JMenu jMenuZelt;

    private JMenuItem jMenuItemTeStatistik;

    private JMenu jMenuTeLa;

    private JMenu jMenuTeAusf;

    private JMenu jMenuTKurz;

    private JMenuItem jMenuItemTeAusfASC;

    private JMenuItem jMenuItemTeAusfGesch;

    private JMenuItem jMenuItemTeKurASC;

    private JMenuItem jMenuItemTeKurGesch;

    private JMenu jMenuLeLa;

    private JMenu jMenuLeJa;

    private JMenuItem jMenuItemLeLaAusf;

    private JMenuItem jMenuItemLeJaAusf;

    private JMenuItem jMenuItemLaAlg;

    private JMenuItem jMenuItemJaAlg;

    private JMenu jMenuStatistik;

    private JMenuItem jMenuItemGesStatistik;

    private JMenuItem jMenuItemJaStatistik;

    private JMenuItem jMenuItemZeLager;

    private JMenuItem jMenuItemZeAlle;

    private JMenuItem jMenuItemZeDetail;

    private JMenu jMenuSuchen;

    private JMenuItem jMenuItemSuchen;

    private JMenuItem jMenuItemTeGruppe;

    private JMenuItem jMenuItemBezLöschen;

    private JMenuItem jMenuItemNachtWache;

    private JMenuItem jMenuItemNachtWacheGruppe;
    private JMenuItem jMenuItemLegendaListen;

    private JPanel jContentPane;

    private JMenuBar jJMenuBar;

    private JMenu jMenuDatei;

    private JMenu jMenuAnlegen;

    private JMenuItem jMenuItemBeende;

    private JMenuItem jMenuItemPerson;

    private JMenuItem jMenuItemUeber;
    private JMenuItem jMenuItemBenutzerverwaltung;

    private JDesktopPane jDesktopPane;

    private JMenuItem jMenuItemOeffnen;

    private JMenu jMenuAendern;

    private JMenuItem jMenuItemAePerson;

    private JMenu jMenuMaterial;

    private JMenuItem jMenuItemZelteVerwalten;

    private JMenu jMenuListen;

    private JMenu jMenuTeilnehmer;

    private JMenu jMenuLeiter;

    private JMenu jMenuAllgemein;

    private JMenuItem jMenuItemTeAusfGeschHaEm;

    private JMenuItem jMenuItemTeAusfHaEmASC;

    private JMenuItem jMenuItemEtiketten;

    private JMenu jMenuLagerinfo;

    private JMenuItem jMenuItemLagerinfoVerwalten;

    private JMenu jMenuEtiketten;

    private JMenuItem jMenuItemEtikettenLI;

    private JMenu jMenuExport;

    private JMenuItem jMenuItemBenutzerWechseln;

    private JMenuItem jMenuItemEtikettenLI1;

    private JMenuItem jMenuItemLegendaJahr;

    private JMenu jMenuLager;

    private JMenuItem jMenuItemProgramm;

    private JMenuItem jMenuItemEssen;

    private JMenuItem jMenuItemLegendaAendern;

    private JMenuItem jMenuItemLagerOrt;

    private JMenuItem jMenuItemOutlookLager;

    private JMenuItem jMenuItemExcelVorlageSpeichern;

    private JMenuItem jMenuItemExcelImportieren;

    private JMenuItem jMenuItemReportOeffnen;

    private JMenuItem jMenuItemLegendatyp;

    private JMenuItem jMenuItemZelt;

    private JMenuItem jMenuItemAnrede;

    public FensterKlasse() {
        super();

        Events.bus().register(this);
        Toolkit.getDefaultToolkit().getSystemEventQueue().push(new ClientExceptionInterceptor());

        initialize();
        disableMenuItems();
        new LoginDialog();
    }

    private void initialize() {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setJMenuBar(getJJMenuBar());
        this.setSize(800, 800);
        this.setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/zelt.jpg")));

        // set state after #setVisible to make sure maximized icon is set
        if (this.getRootPane().getUI() instanceof SyntheticaRootPaneUI) {
            ((SyntheticaRootPaneUI) this.getRootPane().getUI()).setMaximizedBounds(this);
        }
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);

        this.setContentPane(getJContentPane());
        this.setTitle("ZLVP - ZeltLager Verwaltungs Programm");
    }

    private JPanel getJContentPane() {
        if (jContentPane == null) {
            jContentPane = new JPanel();
            jContentPane.setLayout(new BorderLayout());
            jContentPane.add(getJDesktopPane(), java.awt.BorderLayout.CENTER);
        }
        return jContentPane;
    }

    private JMenuBar getJJMenuBar() {
        if (jJMenuBar == null) {
            jJMenuBar = new JMenuBar();
            jJMenuBar.add(getJMenuDatei());
            jJMenuBar.add(getJMenuAnlegen());
            jJMenuBar.add(getJMenuAendern());
            jJMenuBar.add(getJMenuMaterial());
            jJMenuBar.add(getJMenuListen());
            jJMenuBar.add(getJMenuLagerinfo());
            jJMenuBar.add(getJMenuSuchen());
        }
        return jJMenuBar;
    }

    private JMenu getJMenuDatei() {
        if (jMenuDatei == null) {
            jMenuDatei = new JMenu();
            jMenuDatei.setText("Datei");
            jMenuDatei.add(getJMenuItemOeffnen());
            jMenuDatei.add(getJMenuItemBenutzerWechseln());
            jMenuDatei.add(getJMenuExport());
            jMenuDatei.add(getJMenuItemUeber());
            jMenuDatei.add(getJMenuItemReportOeffnen());
            jMenuDatei.add(getJMenuExcelItemVorlageSpeichern());
            jMenuDatei.add(getJMenuExcelItemExcelImportieren());
            jMenuDatei.add(getjMenuItemBenutzerverwaltung());
            jMenuDatei.add(getJMenuItemBeende());
        }
        return jMenuDatei;
    }

    private JMenu getJMenuAnlegen() {
        if (jMenuAnlegen == null) {
            jMenuAnlegen = new JMenu();
            jMenuAnlegen.setText("Anlegen");
            jMenuAnlegen.setEnabled(false);
            jMenuAnlegen.add(getJMenuItemPerson());
            jMenuAnlegen.add(getJMenuItemLagerOrt());
            jMenuAnlegen.add(getJMenuItemLegendatyp());
            jMenuAnlegen.add(getJMenuItemZelt());
            jMenuAnlegen.add(getJMenuItemAnrede());
        }
        return jMenuAnlegen;
    }

    private JMenuItem getJMenuItemBeende() {
        if (jMenuItemBeende == null) {
            jMenuItemBeende = new JMenuItem();
            jMenuItemBeende.setText("Beenden");
            jMenuItemBeende.addActionListener(e -> {
                System.exit(0);
            });
        }
        return jMenuItemBeende;
    }

    private JMenuItem getJMenuItemUeber() {
        if (jMenuItemUeber == null) {
            jMenuItemUeber = new JMenuItem();
            jMenuItemUeber.setText("Info");
            jMenuItemUeber.addActionListener(e -> {
                try {
                    AboutDialog.showDialog(null, null, "",
                            String.format("Version: %s", getClass().getPackage().getImplementationVersion()));
                } catch (IOException e1) {
                    throw new RuntimeException(e1.getMessage(), e1);
                }
            });
        }
        return jMenuItemUeber;
    }

    private JMenuItem getJMenuItemReportOeffnen() {
        if (jMenuItemReportOeffnen == null) {
            jMenuItemReportOeffnen = new JMenuItem();
            jMenuItemReportOeffnen.setText("Report öffnen");
            jMenuItemReportOeffnen.addActionListener(e -> {
                ExtendedFileChooser chooser = new ExtendedFileChooser();

                chooser.setFileFilter(new FileFilter() {
                    @Override
                    public boolean accept(File f) {
                        return f.isDirectory() || f.getName().toLowerCase().endsWith(".jrprint");
                    }

                    @Override
                    public String getDescription() {
                        return "*.jrprint - JasperReports";
                    }
                });

                if (chooser.showOpenDialog(DesktopPane.get()) == ExtendedFileChooser.APPROVE_OPTION) {
                    try {
                        JasperViewer.viewReport(chooser.getSelectedFile().getCanonicalPath(), false, false);
                    } catch (IOException e1) {
                        throw new RuntimeException(e1.getMessage(), e1);
                    } catch (JRException e1) {
                        throw new RuntimeException(e1.getMessage(), e1);
                    }
                }
            });
        }
        return jMenuItemReportOeffnen;
    }

    private JMenuItem getJMenuExcelItemVorlageSpeichern() {
        if (jMenuItemExcelVorlageSpeichern == null) {
            jMenuItemExcelVorlageSpeichern = new JMenuItem();
            jMenuItemExcelVorlageSpeichern.setText("Excel Vorlage speichern");
            jMenuItemExcelVorlageSpeichern.addActionListener(e -> {
                ExtendedFileChooser chooser = new ExtendedFileChooser();

                chooser.setFileFilter(new FileFilter() {
                    @Override
                    public boolean accept(File f) {
                        return f.isDirectory() || f.getName().toLowerCase().endsWith(".xlsx");
                    }

                    @Override
                    public String getDescription() {
                        return "*.xlsx - Excel Sheet";
                    }
                });

                if (chooser.showOpenDialog(DesktopPane.get()) == ExtendedFileChooser.APPROVE_OPTION) {
                    try {
                        Jahr jahr = SelectionContext.get().getJahr();
                        byte[] vorlage = Client.getExcelController().getVorlage(jahr != null ? jahr.getId() : null);
                        String canonicalPath = chooser.getSelectedFile().getCanonicalPath();
                        Path path = Paths.get(canonicalPath.endsWith("xlsx") ? canonicalPath : canonicalPath + ".xlsx");
                        Files.write(path, vorlage);
                        Desktop.getDesktop().open(path.toFile());
                    } catch (IOException e1) {
                        throw new RuntimeException(e1.getMessage(), e1);
                    }
                }
            });
        }
        return jMenuItemExcelVorlageSpeichern;
    }

    private JMenuItem getJMenuExcelItemExcelImportieren() {
        if (jMenuItemExcelImportieren == null) {
            jMenuItemExcelImportieren = new JMenuItem();
            jMenuItemExcelImportieren.setText("Excel importieren");
            jMenuItemExcelImportieren.setEnabled(false);
            jMenuItemExcelImportieren.addActionListener(e -> {
                ExtendedFileChooser chooser = new ExtendedFileChooser();

                chooser.setFileFilter(new FileFilter() {
                    @Override
                    public boolean accept(File f) {
                        return f.isDirectory() || f.getName().toLowerCase().endsWith(".xlsx");
                    }

                    @Override
                    public String getDescription() {
                        return "*.xlsx - Excel Sheet";
                    }
                });

                if (chooser.showOpenDialog(DesktopPane.get()) == ExtendedFileChooser.APPROVE_OPTION) {
                    try {
                        Path path = Paths.get(chooser.getSelectedFile().getCanonicalPath());
                        byte[] content = Files.readAllBytes(path);
                        Client.getExcelController().importieren(content);
                    } catch (IOException e1) {
                        throw new RuntimeException(e1.getMessage(), e1);
                    }
                }
            });
        }
        return jMenuItemExcelImportieren;
    }

    private JMenuItem getJMenuItemPerson() {
        if (jMenuItemPerson == null) {
            jMenuItemPerson = new JMenuItem();
            jMenuItemPerson.setText("Person");
            jMenuItemPerson.addActionListener(e -> {
                new PersonAnlegen();
            });
        }
        return jMenuItemPerson;
    }

    private JDesktopPane getJDesktopPane() {
        if (jDesktopPane == null) {
            jDesktopPane = DesktopPane.get();
        }
        return jDesktopPane;
    }

    private JMenuItem getJMenuItemOeffnen() {
        if (jMenuItemOeffnen == null) {
            jMenuItemOeffnen = new JMenuItem();
            jMenuItemOeffnen.setAction(Actions.oeffnen());
        }
        return jMenuItemOeffnen;
    }

    private JMenu getJMenuAendern() {
        if (jMenuAendern == null) {
            jMenuAendern = new JMenu();
            jMenuAendern.setText("Ändern");
            jMenuAendern.setEnabled(false);
            jMenuAendern.add(getJMenuItemAePerson());
            jMenuAendern.add(getJMenuItemLegendaAendern());

        }
        return jMenuAendern;
    }

    private JMenuItem getJMenuItemAePerson() {
        if (jMenuItemAePerson == null) {
            jMenuItemAePerson = new JMenuItem(Actions.personAendern());
        }
        return jMenuItemAePerson;
    }

    private JMenu getJMenuMaterial() {
        if (jMenuMaterial == null) {
            jMenuMaterial = new JMenu();
            jMenuMaterial.setText("Material");
            jMenuMaterial.setEnabled(false);
            jMenuMaterial.add(getJMenuItemZelteVerwalten());
            jMenuMaterial.add(getJMenuItemBezLöschen());
        }
        return jMenuMaterial;
    }

    private JMenuItem getJMenuItemZelteVerwalten() {
        if (jMenuItemZelteVerwalten == null) {
            jMenuItemZelteVerwalten = new JMenuItem(Actions.zelteVerwalten());
        }
        return jMenuItemZelteVerwalten;
    }

    public JMenu getJMenuListen() {
        if (jMenuListen == null) {
            jMenuListen = new JMenu();
            jMenuListen.setText("Listen");
            jMenuListen.setEnabled(false);
            jMenuListen.add(getJMenuTeilnehmer());
            jMenuListen.add(getJMenuLeiter());
            jMenuListen.add(getJMenuLager());
            jMenuListen.add(getJMenuAllgemein());
            jMenuListen.add(getJMenuZelt());
        }
        return jMenuListen;
    }

    public JMenu getJMenuTeilnehmer() {
        if (jMenuTeilnehmer == null) {
            jMenuTeilnehmer = new JMenu();
            jMenuTeilnehmer.setText("Teilnehmer");
            jMenuTeilnehmer.add(getJMenuItemTeStatistik());
            jMenuTeilnehmer.add(getJMenuTeLa());
        }
        return jMenuTeilnehmer;
    }

    public JMenu getJMenuLeiter() {
        if (jMenuLeiter == null) {
            jMenuLeiter = new JMenu();
            jMenuLeiter.setText("Leiter");
            jMenuLeiter.add(getJMenuLeLa());
            jMenuLeiter.add(getJMenuLeJa());
        }
        return jMenuLeiter;
    }

    public JMenu getJMenuAllgemein() {
        if (jMenuAllgemein == null) {
            jMenuAllgemein = new JMenu();
            jMenuAllgemein.setText("Allgemein");
            jMenuAllgemein.add(getJMenuStatistik());
        }
        return jMenuAllgemein;
    }

    public JMenu getJMenuZelt() {
        if (jMenuZelt == null) {
            jMenuZelt = new JMenu();
            jMenuZelt.setText("Zelt");
            jMenuZelt.add(getJMenuItemZeLager());
            jMenuZelt.add(getJMenuItemZeAlle());
            jMenuZelt.add(getJMenuItemZeDetail());
        }
        return jMenuZelt;
    }

    private JMenuItem getJMenuItemTeStatistik() {
        if (jMenuItemTeStatistik == null) {
            jMenuItemTeStatistik = new JMenuItem();
            jMenuItemTeStatistik.setText("Statistik");
            jMenuItemTeStatistik.addActionListener(
                    e -> Client.getReports().teilnehmerStatistik(SelectionContext.get().getPerson().getId()));
        }
        return jMenuItemTeStatistik;
    }

    public JMenu getJMenuTeLa() {
        if (jMenuTeLa == null) {
            jMenuTeLa = new JMenu();
            jMenuTeLa.setText("Teilnehmer / Lager");
            jMenuTeLa.add(getJMenuTeAusf());
            jMenuTeLa.add(getJMenuTKurz());
        }
        return jMenuTeLa;
    }

    private JMenu getJMenuTeAusf() {
        if (jMenuTeAusf == null) {
            jMenuTeAusf = new JMenu();
            jMenuTeAusf.setText("Ausführlich");
            jMenuTeAusf.add(getJMenuItemTeAusfASC());
            jMenuTeAusf.add(getJMenuItemTeAusfHaEmASC());
            jMenuTeAusf.add(getJMenuItemTeAusfGesch());
            jMenuTeAusf.add(getJMenuItemTeAusfGeschHaEm());
        }
        return jMenuTeAusf;
    }

    private JMenu getJMenuTKurz() {
        if (jMenuTKurz == null) {
            jMenuTKurz = new JMenu();
            jMenuTKurz.setText("Nur Name");
            jMenuTKurz.add(getJMenuItemTeKurASC());
            jMenuTKurz.add(getJMenuItemTeKurGesch());
            jMenuTKurz.add(getJMenuItemTeGruppe());
        }
        return jMenuTKurz;
    }

    private JMenuItem getJMenuItemTeAusfASC() {
        if (jMenuItemTeAusfASC == null) {
            jMenuItemTeAusfASC = new JMenuItem();
            jMenuItemTeAusfASC.setText("Alphabetisch für Lager");
            jMenuItemTeAusfASC.addActionListener(
                    e -> Client.getReports().teilnehmerAusfuehrlichASC(SelectionContext.get().getLager().getId()));
        }
        return jMenuItemTeAusfASC;
    }

    private JMenuItem getJMenuItemTeAusfGesch() {
        if (jMenuItemTeAusfGesch == null) {
            jMenuItemTeAusfGesch = new JMenuItem();
            jMenuItemTeAusfGesch.setText("Geschlecht für Lager");
            jMenuItemTeAusfGesch.addActionListener(e -> Client.getReports()
                    .teilnehmerAusfuehrlichGeschlecht(SelectionContext.get().getLager().getId()));
        }
        return jMenuItemTeAusfGesch;
    }

    private JMenuItem getJMenuItemTeKurASC() {
        if (jMenuItemTeKurASC == null) {
            jMenuItemTeKurASC = new JMenuItem();
            jMenuItemTeKurASC.setText("Alphabetisch");
            jMenuItemTeKurASC.addActionListener(
                    e -> Client.getReports().teilnehmerASC(SelectionContext.get().getLager().getId()));
        }
        return jMenuItemTeKurASC;
    }

    private JMenuItem getJMenuItemTeKurGesch() {
        if (jMenuItemTeKurGesch == null) {
            jMenuItemTeKurGesch = new JMenuItem();
            jMenuItemTeKurGesch.setText("Geschlecht");
            jMenuItemTeKurGesch.addActionListener(
                    e -> Client.getReports().teilnehmerGeschlecht(SelectionContext.get().getLager().getId()));
        }
        return jMenuItemTeKurGesch;
    }

    public JMenu getJMenuLeLa() {
        if (jMenuLeLa == null) {
            jMenuLeLa = new JMenu();
            jMenuLeLa.setText("Leiter / Lager");
            jMenuLeLa.add(getJMenuItemLeLaAusf());
        }
        return jMenuLeLa;
    }

    public JMenu getJMenuLeJa() {
        if (jMenuLeJa == null) {
            jMenuLeJa = new JMenu();
            jMenuLeJa.setText("Leiter / Jahr");
            jMenuLeJa.add(getJMenuItemLeJaAusf());
        }
        return jMenuLeJa;
    }

    private JMenuItem getJMenuItemLeLaAusf() {
        if (jMenuItemLeLaAusf == null) {
            jMenuItemLeLaAusf = new JMenuItem();
            jMenuItemLeLaAusf.setText("Ausführlich");
            jMenuItemLeLaAusf.addActionListener(
                    e -> Client.getReports().leiterLagerAusfuehrlich(SelectionContext.get().getLager().getId()));
        }
        return jMenuItemLeLaAusf;
    }

    private JMenuItem getJMenuItemLeJaAusf() {
        if (jMenuItemLeJaAusf == null) {
            jMenuItemLeJaAusf = new JMenuItem();
            jMenuItemLeJaAusf.setText("Ausführlich");
            jMenuItemLeJaAusf.addActionListener(
                    e -> Client.getReports().leiterJahrAusfuehrlich(SelectionContext.get().getJahr().getId()));
        }
        return jMenuItemLeJaAusf;
    }

    private JMenuItem getJMenuItemLaAlg() {
        if (jMenuItemLaAlg == null) {
            jMenuItemLaAlg = new JMenuItem();
            jMenuItemLaAlg.setText("Lagerübersicht");
            jMenuItemLaAlg.addActionListener(
                    e -> Client.getReports().lageruebersicht(SelectionContext.get().getLager().getId()));
        }
        return jMenuItemLaAlg;
    }

    private JMenuItem getJMenuItemJaAlg() {
        if (jMenuItemJaAlg == null) {
            jMenuItemJaAlg = new JMenuItem();
            jMenuItemJaAlg.setText("Jahresübersicht");
            jMenuItemJaAlg.addActionListener(
                    e -> Client.getReports().jahresuebersicht(SelectionContext.get().getJahr().getId()));
        }
        return jMenuItemJaAlg;
    }

    private JMenu getJMenuStatistik() {
        if (jMenuStatistik == null) {
            jMenuStatistik = new JMenu();
            jMenuStatistik.setText("Statistik");
            jMenuStatistik.add(getJMenuItemGesStatistik());
            jMenuStatistik.add(getJMenuItemJaStatistik());
        }
        return jMenuStatistik;
    }

    public JMenuItem getJMenuItemGesStatistik() {
        if (jMenuItemGesStatistik == null) {
            jMenuItemGesStatistik = new JMenuItem();
            jMenuItemGesStatistik.setText("Gesamt Statistik");
            jMenuItemGesStatistik.addActionListener(e -> Client.getReports().gesamtstatistik());
        }
        return jMenuItemGesStatistik;
    }

    private JMenuItem getJMenuItemJaStatistik() {
        if (jMenuItemJaStatistik == null) {
            jMenuItemJaStatistik = new JMenuItem();
            jMenuItemJaStatistik.setText("Jahresstatistik");
            jMenuItemJaStatistik.addActionListener(
                    e -> Client.getReports().jahresstatistik(SelectionContext.get().getJahr().getId()));
        }
        return jMenuItemJaStatistik;
    }

    private JMenuItem getJMenuItemZeLager() {
        if (jMenuItemZeLager == null) {
            jMenuItemZeLager = new JMenuItem();
            jMenuItemZeLager.setText("Zelt / Lager");
            jMenuItemZeLager.addActionListener(
                    e -> Client.getReports().zelteVonLager(SelectionContext.get().getLager().getId()));
        }
        return jMenuItemZeLager;
    }

    private JMenuItem getJMenuItemZeAlle() {
        if (jMenuItemZeAlle == null) {
            jMenuItemZeAlle = new JMenuItem();
            jMenuItemZeAlle.setText("Alle");
            jMenuItemZeAlle.addActionListener(e -> Client.getReports().alleZelte());
        }
        return jMenuItemZeAlle;
    }

    private JMenuItem getJMenuItemZeDetail() {
        if (jMenuItemZeDetail == null) {
            jMenuItemZeDetail = new JMenuItem();
            jMenuItemZeDetail.setText("Zelt Details");
            jMenuItemZeDetail.addActionListener(e -> {
                new ZeltDetailListen();
            });
        }
        return jMenuItemZeDetail;
    }

    public void disableMenuItems() {
        getJMenuItemJaAlg().setEnabled(false);
        getJMenuItemJaStatistik().setEnabled(false);
        getJMenuItemLaAlg().setEnabled(false);
        getJMenuItemTeStatistik().setEnabled(false);
        getJMenuItemZeLager().setEnabled(false);
        getJMenuLeJa().setEnabled(false);
        getJMenuLeLa().setEnabled(false);
        getJMenuTeLa().setEnabled(false);
        getJMenuItemEtiketten().setEnabled(false);
        getJMenuItemProgramm().setEnabled(false);
        getJMenuItemEssen().setEnabled(false);
        getJMenuItemNachtWache().setEnabled(false);
        getJMenuItemNachtWacheGruppe().setEnabled(false);
        getJMenuItemPersonenVonLager().setEnabled(false);
        getJMenuItemLegendaListen().setEnabled(false);
        getJMenuItemLegendaJahr().setEnabled(false);

        Actions.lagerAnlegen().setEnabled(false);
        Actions.gruppeAnlegen().setEnabled(false);
    }

    public void enableMenuItems() {
        getJMenuAnlegen().setEnabled(true);
        getJMenuAendern().setEnabled(true);
        getJMenuMaterial().setEnabled(true);
        getJMenuListen().setEnabled(true);
        getJMenuLagerinfo().setEnabled(true);
        getJMenuSuchen().setEnabled(true);
        Actions.personAendern().setEnabled(true);
        Actions.oeffnen().setEnabled(true);
        Actions.zelteVerwalten().setEnabled(true);
        getJMenuExcelItemExcelImportieren().setEnabled(true);
    }

    private JMenu getJMenuSuchen() {
        if (jMenuSuchen == null) {
            jMenuSuchen = new JMenu();
            jMenuSuchen.setText("Suchen");
            jMenuSuchen.setEnabled(false);
            jMenuSuchen.add(getJMenuItemSuchen());
        }
        return jMenuSuchen;
    }

    private JMenuItem getJMenuItemSuchen() {
        if (jMenuItemSuchen == null) {
            jMenuItemSuchen = new JMenuItem(Actions.personAendern());
        }
        return jMenuItemSuchen;
    }

    private JMenuItem getJMenuItemTeGruppe() {
        if (jMenuItemTeGruppe == null) {
            jMenuItemTeGruppe = new JMenuItem();
            jMenuItemTeGruppe.setText("Gruppe");
            jMenuItemTeGruppe.addActionListener(
                    e -> Client.getReports().teilnehmerVonGruppe(SelectionContext.get().getLager().getId()));
        }
        return jMenuItemTeGruppe;
    }

    private JMenuItem getJMenuItemBezLöschen() {
        if (jMenuItemBezLöschen == null) {
            jMenuItemBezLöschen = new JMenuItem();
            jMenuItemBezLöschen.setText("Bezeichnungen Löschen");
            jMenuItemBezLöschen.addActionListener(e -> {
                get().getAllZeltdetailBezeichnung(allZeltdetailBezeichnung -> {
                    ZeltdetailBezeichnung[] zdbs = allZeltdetailBezeichnung
                            .toArray(new ZeltdetailBezeichnung[allZeltdetailBezeichnung.size()]);
                    ZeltdetailBezeichnung zdb = (ZeltdetailBezeichnung) JOptionPane.showInputDialog(this, null,
                            "Zeltdetail Bezeichnung löschen:", JOptionPane.PLAIN_MESSAGE, null, zdbs, null);

                    if (zdb != null) {
                        Client.get().loescheZeltdetailBezeichnung(zdb.getId(), cb -> {
                        });
                    }
                });
            });
        }
        return jMenuItemBezLöschen;
    }

    private JMenuItem getJMenuItemNachtWache() {
        if (jMenuItemNachtWache == null) {
            jMenuItemNachtWache = new JMenuItem();
            jMenuItemNachtWache.setText("Nachtwache");
            jMenuItemNachtWache.addActionListener(
                    e -> Client.getReports().nachtwachenliste(SelectionContext.get().getLager().getId()));
        }
        return jMenuItemNachtWache;
    }

    private JMenuItem getJMenuItemNachtWacheGruppe() {
        if (jMenuItemNachtWacheGruppe == null) {
            jMenuItemNachtWacheGruppe = new JMenuItem();
            jMenuItemNachtWacheGruppe.setText("Nachtwache nach Gruppen");
            jMenuItemNachtWacheGruppe.addActionListener(
                    e -> Client.getReports().nachtwachenlisteGruppe(SelectionContext.get().getLager().getId()));
        }
        return jMenuItemNachtWacheGruppe;
    }

    private JMenuItem getJMenuItemLegendaListen() {
        if (jMenuItemLegendaListen == null) {
            jMenuItemLegendaListen = new JMenuItem();
            jMenuItemLegendaListen.setText("Legenda");
            jMenuItemLegendaListen
                    .addActionListener(e -> Client.getReports().legenda(SelectionContext.get().getLager().getId()));
        }
        return jMenuItemLegendaListen;
    }

    private JMenuItem getJMenuItemTeAusfGeschHaEm() {
        if (jMenuItemTeAusfGeschHaEm == null) {
            jMenuItemTeAusfGeschHaEm = new JMenuItem();
            jMenuItemTeAusfGeschHaEm.setText("Geschlecht mit EMail und Handy");
            jMenuItemTeAusfGeschHaEm.addActionListener(
                    e -> Client.getReports().teilnehmerHandyEmail(SelectionContext.get().getLager().getId()));
        }
        return jMenuItemTeAusfGeschHaEm;
    }

    private JMenuItem getJMenuItemTeAusfHaEmASC() {
        if (jMenuItemTeAusfHaEmASC == null) {
            jMenuItemTeAusfHaEmASC = new JMenuItem();
            jMenuItemTeAusfHaEmASC.setText("Alphabetisch mit EMail und Handy");
            jMenuItemTeAusfHaEmASC.addActionListener(
                    e -> Client.getReports().teilnehmerAdresseHandyEmail(SelectionContext.get().getLager().getId()));
        }
        return jMenuItemTeAusfHaEmASC;
    }

    private JMenuItem getJMenuItemEtiketten() {
        if (jMenuItemEtiketten == null) {
            jMenuItemEtiketten = new JMenuItem();
            jMenuItemEtiketten.setText("Etiketten");
            jMenuItemEtiketten
                    .addActionListener(e -> Client.getReports().etiketten(SelectionContext.get().getLager().getId()));
        }
        return jMenuItemEtiketten;
    }

    private JMenu getJMenuLagerinfo() {
        if (jMenuLagerinfo == null) {
            jMenuLagerinfo = new JMenu();
            jMenuLagerinfo.setText("Lagerinfo");
            jMenuLagerinfo.setEnabled(false);
            jMenuLagerinfo.add(getJMenuItemLagerinfoVerwalten());
            jMenuLagerinfo.add(getJMenuItemEtikettenLI1());
        }
        return jMenuLagerinfo;
    }

    private JMenuItem getJMenuItemLagerinfoVerwalten() {
        if (jMenuItemLagerinfoVerwalten == null) {
            jMenuItemLagerinfoVerwalten = new JMenuItem();
            jMenuItemLagerinfoVerwalten.setText("Lagerinfo Verwalten");
            jMenuItemLagerinfoVerwalten.addActionListener(e -> {
                new LagerinfoVerwalten();
            });
        }
        return jMenuItemLagerinfoVerwalten;
    }

    private JMenu getJMenuEtiketten() {
        if (jMenuEtiketten == null) {
            jMenuEtiketten = new JMenu();
            jMenuEtiketten.setText("Etiketten");
            jMenuEtiketten.add(getJMenuItemEtiketten());
            jMenuEtiketten.add(getJMenuItemEtikettenLI());
        }
        return jMenuEtiketten;
    }

    private JMenuItem getJMenuItemEtikettenLI() {
        if (jMenuItemEtikettenLI == null) {
            jMenuItemEtikettenLI = new JMenuItem();
            jMenuItemEtikettenLI.setText("Etiketten Lagerinfo");
            jMenuItemEtikettenLI.addActionListener(e -> Client.getReports().etikettenLagerinfo());
        }
        return jMenuItemEtikettenLI;
    }

    private JMenu getJMenuExport() {
        if (jMenuExport == null) {
            jMenuExport = new JMenu();
            jMenuExport.setText("Export");
            jMenuExport.add(getJMenuItemPersonenVonLager());
            jMenuExport.add(getJMenuItemLegendaJahr());
        }
        return jMenuExport;
    }

    private JMenuItem getJMenuItemBenutzerWechseln() {
        if (jMenuItemBenutzerWechseln == null) {
            jMenuItemBenutzerWechseln = new JMenuItem();
            jMenuItemBenutzerWechseln.setText("Benutzer wechseln");
            jMenuItemBenutzerWechseln.addActionListener(e -> {
                new LoginDialog();
            });
        }
        return jMenuItemBenutzerWechseln;
    }

    private JMenuItem getJMenuItemEtikettenLI1() {
        if (jMenuItemEtikettenLI1 == null) {
            jMenuItemEtikettenLI1 = new JMenuItem();
            jMenuItemEtikettenLI1.setText("Etiketten Lagerinfo");
            jMenuItemEtikettenLI1.addActionListener(e -> Client.getReports().etikettenLagerinfo());
        }
        return jMenuItemEtikettenLI1;
    }

    private JMenuItem getJMenuItemLegendaJahr() {
        if (jMenuItemLegendaJahr == null) {
            jMenuItemLegendaJahr = new JMenuItem();
            jMenuItemLegendaJahr.setEnabled(false);
            jMenuItemLegendaJahr.setText("Legenda / Jahr");
            jMenuItemLegendaJahr.addActionListener(
                    e -> Client.getReports().exportLegendaCSVJahr(SelectionContext.get().getJahr().getId()));
        }
        return jMenuItemLegendaJahr;
    }

    private JMenu getJMenuLager() {
        if (jMenuLager == null) {
            jMenuLager = new JMenu();
            jMenuLager.setText("Lager");
            jMenuLager.add(getJMenuEtiketten());
            jMenuLager.add(getJMenuItemNachtWache());
            jMenuLager.add(getJMenuItemNachtWacheGruppe());
            jMenuLager.add(getJMenuItemLaAlg());
            jMenuLager.add(getJMenuItemJaAlg());
            jMenuLager.add(getJMenuItemProgramm());
            jMenuLager.add(getJMenuItemEssen());
            jMenuLager.add(getJMenuItemLegendaListen());
        }
        return jMenuLager;
    }

    private JMenuItem getJMenuItemProgramm() {
        if (jMenuItemProgramm == null) {
            jMenuItemProgramm = new JMenuItem();
            jMenuItemProgramm.setText("Programm");
            jMenuItemProgramm.setEnabled(false);
            jMenuItemProgramm
                    .addActionListener(e -> Client.getReports().programm(SelectionContext.get().getLager().getId()));
        }
        return jMenuItemProgramm;
    }

    private JMenuItem getJMenuItemEssen() {
        if (jMenuItemEssen == null) {
            jMenuItemEssen = new JMenuItem();
            jMenuItemEssen.setText("Essen");
            jMenuItemEssen.setEnabled(false);
            jMenuItemEssen.addActionListener(e -> Client.getReports().essen(SelectionContext.get().getLager().getId()));
        }
        return jMenuItemEssen;
    }

    private JMenuItem getJMenuItemLegendaAendern() {
        if (jMenuItemLegendaAendern == null) {
            jMenuItemLegendaAendern = new JMenuItem();
            jMenuItemLegendaAendern.setText("Legenda");
            jMenuItemLegendaAendern.addActionListener(e -> {
                get().getAllLagerort(allLagerort -> {
                    Lagerort[] los = allLagerort.toArray(new Lagerort[allLagerort.size()]);

                    Lagerort lo = (Lagerort) JOptionPane.showInputDialog(null, null, "Legenda öffnen für:",
                            JOptionPane.PLAIN_MESSAGE, null, los, null);

                    if (lo != null) {
                        new LegendaVerwalten(lo);
                    }
                });

            });
        }
        return jMenuItemLegendaAendern;
    }

    private JMenuItem getJMenuItemLagerOrt() {
        if (jMenuItemLagerOrt == null) {
            jMenuItemLagerOrt = new JMenuItem();
            jMenuItemLagerOrt.setText("Lagerort");
            jMenuItemLagerOrt.addActionListener(e -> {
                String lagerort = JOptionPane.showInputDialog("Neuen Lagerort eingeben");
                if (lagerort != null && !lagerort.isEmpty()) {
                    get().speichereLagerort(lagerort, cb -> {
                    });
                }
            });
        }
        return jMenuItemLagerOrt;
    }

    private JMenuItem getJMenuItemLegendatyp() {
        if (jMenuItemLegendatyp == null) {
            jMenuItemLegendatyp = new JMenuItem();
            jMenuItemLegendatyp.setText("Legendatyp");
            jMenuItemLegendatyp.addActionListener(e -> {
                String lgendatyp = JOptionPane.showInputDialog("Neuen Legendatyp eingeben");
                if (lgendatyp != null && !lgendatyp.isEmpty()) {
                    get().speichereLegendatyp(lgendatyp, cb -> {
                    });
                }
            });
        }
        return jMenuItemLegendatyp;
    }

    private JMenuItem getJMenuItemZelt() {
        if (jMenuItemZelt == null) {
            jMenuItemZelt = new JMenuItem();
            jMenuItemZelt.setText("Zelt");
            jMenuItemZelt.addActionListener(e -> {
                String zeltBezeichnung = JOptionPane.showInputDialog("Zeltbezeichnung eingeben", "BR-Z-");
                if (zeltBezeichnung != null && !zeltBezeichnung.isEmpty()) {
                    get().speichereZelt(null, zeltBezeichnung, null, 0, null, cb -> {
                    });
                }
            });
        }
        return jMenuItemZelt;
    }

    private JMenuItem getJMenuItemAnrede() {
        if (jMenuItemAnrede == null) {
            jMenuItemAnrede = new JMenuItem();
            jMenuItemAnrede.setText("Legenda Anrede");
            jMenuItemAnrede.addActionListener(e -> {
                String anrede = JOptionPane.showInputDialog("Legenda Anrede eingeben");
                if (anrede != null && !anrede.isEmpty()) {
                    get().speichereAnrede(anrede, cb -> {
                    });
                }
            });
        }
        return jMenuItemAnrede;
    }

    private JMenuItem getJMenuItemPersonenVonLager() {
        if (jMenuItemOutlookLager == null) {
            jMenuItemOutlookLager = new JMenuItem();
            jMenuItemOutlookLager.setText("Alle Personen von Lager");
            jMenuItemOutlookLager.setEnabled(false);
            jMenuItemOutlookLager.addActionListener(
                    e -> Client.getReports().personenVonLagerCSV(SelectionContext.get().getLager().getId()));
        }
        return jMenuItemOutlookLager;
    }

    public JMenuItem getjMenuItemBenutzerverwaltung() {
        if (jMenuItemBenutzerverwaltung == null) {
            jMenuItemBenutzerverwaltung = new JMenuItem(Actions.benutzerverwaltung());
        }
        return jMenuItemBenutzerverwaltung;
    }

    @Subscribe
    private void jahrSelected(JahrSelected event) {
        disableMenuItems();
        Actions.lagerAnlegen().setJahr(event.get());
        getJMenuLeJa().setEnabled(true);
        getJMenuItemJaAlg().setEnabled(true);
        getJMenuItemJaStatistik().setEnabled(true);
        getJMenuListen().setEnabled(true);
        getJMenuItemLegendaJahr().setEnabled(true);

        SelectionContext.get().setJahr(event.get());
    }

    @Subscribe
    private void lagerSelected(LagerSelected event) {
        disableMenuItems();
        getJMenuItemLaAlg().setEnabled(true);
        getJMenuItemEtiketten().setEnabled(true);
        getJMenuItemZeLager().setEnabled(true);
        getJMenuLeLa().setEnabled(true);
        getJMenuTeLa().setEnabled(true);
        getJMenuItemProgramm().setEnabled(true);
        getJMenuItemEssen().setEnabled(true);
        getJMenuItemNachtWache().setEnabled(true);
        getJMenuItemPersonenVonLager().setEnabled(true);
        getJMenuItemNachtWacheGruppe().setEnabled(true);
        getJMenuItemLegendaListen().setEnabled(true);

        SelectionContext.get().setLager(event.get());
        Actions.gruppeAnlegen().setLager(event.get());
    }

    @Subscribe
    private void gruppeSelected(GruppeSelected event) {
        disableMenuItems();
        SelectionContext.get().setGruppe(event.get());
    }

    @Subscribe
    private void personSelected(PersonSelected event) {
        disableMenuItems();
        getJMenuItemTeStatistik().setEnabled(true);
        SelectionContext.get().setPerson(event.get());
    }

    @Subscribe
    private void disableMenuItems(DisableMenuItems event) {
        disableMenuItems();
        getJMenuItemTeStatistik().setEnabled(true);
    }

    @Subscribe
    private void loginSuccessful(LoginSuccessfull event) {
        enableMenuItems();
        setTitle("ZLVP - ZeltLager Verwaltungs Programm - " + event.getUsername());
    }

}
