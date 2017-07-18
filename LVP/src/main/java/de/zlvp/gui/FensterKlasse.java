package de.zlvp.gui;

import java.awt.BorderLayout;
import java.awt.Desktop;
import java.awt.Toolkit;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

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

    private JDesktopPane jDesktopPane;

    private JMenuItem jMenuItemZeltAnlegen;

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

    private JMenuItem jMenuItemEMailLager;

    private JMenu jMenuExport;

    private JMenuItem jMenuItemBenutzerWechseln;

    private JMenuItem jMenuItemEtikettenLI1;

    private JMenuItem jMenuItemEMailJahr;
    private JMenuItem jMenuItemLegendaJahr;

    private JMenu jMenuLager;

    private JMenuItem jMenuItemProgramm;

    private JMenuItem jMenuItemEssen;

    private JMenuItem jMenuItemLegendaAendern;

    private JMenuItem jMenuItemLagerOrt;

    private JMenuItem jMenuItemOutlookLager;

    private JMenuItem jMenuItemExcelVorlageSpeichern;

    private JMenuItem jMenuItemExcelImportieren;

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
            jMenuDatei.add(getJMenuExcelItemVorlageSpeichern());
            jMenuDatei.add(getJMenuExcelItemExcelImportieren());
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
                        byte[] vorlage = Client.getExcelController().getVorlage();
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

    private JMenuItem getJMenuItemZeltAnlegen() {
        if (jMenuItemZeltAnlegen == null) {
            jMenuItemZeltAnlegen = new JMenuItem();
            jMenuItemZeltAnlegen.setText("Zelt anlegen");
            jMenuItemZeltAnlegen.addActionListener(e -> {
                new ZeltAnlegen();
            });
        }
        return jMenuItemZeltAnlegen;
    }

    private JMenuItem getJMenuItemOeffnen() {
        if (jMenuItemOeffnen == null) {
            jMenuItemOeffnen = new JMenuItem();
            jMenuItemOeffnen.setText("Öffnen");
            jMenuItemOeffnen.setEnabled(false);
            jMenuItemOeffnen.addActionListener(e -> {
                List<Jahr> allJahre = Client.get().getAllJahr();
                Jahr[] jahre = allJahre.toArray(new Jahr[allJahre.size()]);
                Jahr jahr = (Jahr) JOptionPane.showInputDialog(this, null, "Jahr wählen:", JOptionPane.PLAIN_MESSAGE,
                        null, jahre, null);
                if (jahr != null) {
                    new HauptFenster(jahr.getId());
                }
            });
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
            jMenuItemAePerson = new JMenuItem();
            jMenuItemAePerson.setText("Person");
            jMenuItemAePerson.addActionListener(e -> {
                new PersonSuchen();
            });
        }
        return jMenuItemAePerson;
    }

    private JMenu getJMenuMaterial() {
        if (jMenuMaterial == null) {
            jMenuMaterial = new JMenu();
            jMenuMaterial.setText("Material");
            jMenuMaterial.setEnabled(false);
            jMenuMaterial.add(getJMenuItemZeltAnlegen());
            jMenuMaterial.add(getJMenuItemZelteVerwalten());
            jMenuMaterial.add(getJMenuItemBezLöschen());
        }
        return jMenuMaterial;
    }

    private JMenuItem getJMenuItemZelteVerwalten() {
        if (jMenuItemZelteVerwalten == null) {
            jMenuItemZelteVerwalten = new JMenuItem();
            jMenuItemZelteVerwalten.setText("Zelte Verwalten");
            jMenuItemZelteVerwalten.addActionListener(e -> {
                new ZeltVerwalten();
            });
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

    public JMenuItem getJMenuItemTeStatistik() {
        if (jMenuItemTeStatistik == null) {
            jMenuItemTeStatistik = new JMenuItem();
            jMenuItemTeStatistik.setText("Statistik");
            jMenuItemTeStatistik.addActionListener(
                    e -> Client.getReports().teilnehmerStatistik(SelectionContext.get().getPerson().getOriginalId()));
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
                    e -> Client.getReports().leiterLagerAusfuehrlichASC(SelectionContext.get().getLager().getId()));
        }
        return jMenuItemLeLaAusf;
    }

    private JMenuItem getJMenuItemLeJaAusf() {
        if (jMenuItemLeJaAusf == null) {
            jMenuItemLeJaAusf = new JMenuItem();
            jMenuItemLeJaAusf.setText("Ausführlich");
            jMenuItemLeJaAusf.addActionListener(
                    e -> Client.getReports().leiterJahrAusfuehrlichASC(SelectionContext.get().getLager().getId()));
        }
        return jMenuItemLeJaAusf;
    }

    public JMenuItem getJMenuItemLaAlg() {
        if (jMenuItemLaAlg == null) {
            jMenuItemLaAlg = new JMenuItem();
            jMenuItemLaAlg.setText("Lagerübersicht");
            jMenuItemLaAlg.addActionListener(
                    e -> Client.getReports().lageruebersicht(SelectionContext.get().getLager().getId()));
        }
        return jMenuItemLaAlg;
    }

    public JMenuItem getJMenuItemJaAlg() {
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

    public JMenuItem getJMenuItemJaStatistik() {
        if (jMenuItemJaStatistik == null) {
            jMenuItemJaStatistik = new JMenuItem();
            jMenuItemJaStatistik.setText("Jahresstatistik");
            jMenuItemJaStatistik.addActionListener(
                    e -> Client.getReports().jahresstatistik(SelectionContext.get().getJahr().getId()));
        }
        return jMenuItemJaStatistik;
    }

    public JMenuItem getJMenuItemZeLager() {
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
        getJMenuItemEMailLager().setEnabled(false);
        getJMenuItemEMailJahr().setEnabled(false);
        getJMenuItemProgramm().setEnabled(false);
        getJMenuItemEssen().setEnabled(false);
        getJMenuItemNachtWache().setEnabled(false);
        getJMenuItemNachtWacheGruppe().setEnabled(false);
        getJMenuItemOutlookLager().setEnabled(false);
        getJMenuItemLegendaListen().setEnabled(false);
        getJMenuItemLegendaJahr().setEnabled(false);
        getJMenuExcelItemExcelImportieren().setEnabled(false);

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
        getJMenuItemOeffnen().setEnabled(true);
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
            jMenuItemSuchen = new JMenuItem();
            jMenuItemSuchen.setText("Person");
            jMenuItemSuchen.addActionListener(e -> {
                new PersonSuchen();
            });
        }
        return jMenuItemSuchen;
    }

    private JMenuItem getJMenuItemTeGruppe() {
        if (jMenuItemTeGruppe == null) {
            jMenuItemTeGruppe = new JMenuItem();
            jMenuItemTeGruppe.setText("Gruppe");
            jMenuItemTeGruppe.addActionListener(
                    e -> Client.getReports().teilnehmerVonGruppe(SelectionContext.get().getGruppe().getId()));
        }
        return jMenuItemTeGruppe;
    }

    private JMenuItem getJMenuItemBezLöschen() {
        if (jMenuItemBezLöschen == null) {
            jMenuItemBezLöschen = new JMenuItem();
            jMenuItemBezLöschen.setText("Bezeichnungen Löschen");
            jMenuItemBezLöschen.addActionListener(e -> {

                List<ZeltdetailBezeichnung> allZeltdetailBezeichnung = Client.get().getAllZeltdetailBezeichnung();
                ZeltdetailBezeichnung[] zdbs = allZeltdetailBezeichnung
                        .toArray(new ZeltdetailBezeichnung[allZeltdetailBezeichnung.size()]);
                ZeltdetailBezeichnung zdb = (ZeltdetailBezeichnung) JOptionPane.showInputDialog(this, null,
                        "Zeltdetail Bezeichnung löschen:", JOptionPane.PLAIN_MESSAGE, null, zdbs, null);

                if (zdb != null) {
                    Client.get().loescheZeltdetailBezeichnung(zdb.getId());
                }
            });
        }
        return jMenuItemBezLöschen;
    }

    public JMenuItem getJMenuItemNachtWache() {
        if (jMenuItemNachtWache == null) {
            jMenuItemNachtWache = new JMenuItem();
            jMenuItemNachtWache.setText("Nachtwache");
            jMenuItemNachtWache.addActionListener(
                    e -> Client.getReports().nachtwachenliste(SelectionContext.get().getLager().getId()));
        }
        return jMenuItemNachtWache;
    }

    public JMenuItem getJMenuItemNachtWacheGruppe() {
        if (jMenuItemNachtWacheGruppe == null) {
            jMenuItemNachtWacheGruppe = new JMenuItem();
            jMenuItemNachtWacheGruppe.setText("Nachtwache nach Gruppen");
            jMenuItemNachtWacheGruppe.addActionListener(
                    e -> Client.getReports().nachtwachenlisteGruppe(SelectionContext.get().getLager().getId()));
        }
        return jMenuItemNachtWacheGruppe;
    }

    public JMenuItem getJMenuItemLegendaListen() {
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

    public JMenuItem getJMenuItemEtiketten() {
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

    public JMenuItem getJMenuItemEMailLager() {
        if (jMenuItemEMailLager == null) {
            jMenuItemEMailLager = new JMenuItem();
            jMenuItemEMailLager.setText("EMail Adressen / Lager");
            jMenuItemEMailLager.setEnabled(false);
            jMenuItemEMailLager.addActionListener(
                    e -> Client.getReports().exportEmailCSVLager(SelectionContext.get().getLager().getId()));
        }
        return jMenuItemEMailLager;
    }

    private JMenu getJMenuExport() {
        if (jMenuExport == null) {
            jMenuExport = new JMenu();
            jMenuExport.setText("Export");
            jMenuExport.add(getJMenuItemEMailLager());
            jMenuExport.add(getJMenuItemEMailJahr());
            jMenuExport.add(getJMenuItemOutlookLager());
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

    public JMenuItem getJMenuItemEMailJahr() {
        if (jMenuItemEMailJahr == null) {
            jMenuItemEMailJahr = new JMenuItem();
            jMenuItemEMailJahr.setEnabled(false);
            jMenuItemEMailJahr.setText("EMail Adressen / Jahr");
            jMenuItemEMailJahr.addActionListener(
                    e -> Client.getReports().exportEmailCSVJahr(SelectionContext.get().getJahr().getId()));
        }
        return jMenuItemEMailJahr;
    }

    public JMenuItem getJMenuItemLegendaJahr() {
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

    public JMenuItem getJMenuItemProgramm() {
        if (jMenuItemProgramm == null) {
            jMenuItemProgramm = new JMenuItem();
            jMenuItemProgramm.setText("Programm");
            jMenuItemProgramm.setEnabled(false);
            jMenuItemProgramm
                    .addActionListener(e -> Client.getReports().programm(SelectionContext.get().getLager().getId()));
        }
        return jMenuItemProgramm;
    }

    public JMenuItem getJMenuItemEssen() {
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
                List<Lagerort> allLagerort = Client.get().getAllLagerort();
                Lagerort[] los = allLagerort.toArray(new Lagerort[allLagerort.size()]);

                Lagerort lo = (Lagerort) JOptionPane.showInputDialog(this, null, "Legenda öffnen für:",
                        JOptionPane.PLAIN_MESSAGE, null, los, null);

                if (lo != null) {
                    new LegendaVerwalten(lo);
                }
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
                    Client.get().speichereLagerort(lagerort);
                }
            });
        }
        return jMenuItemLagerOrt;
    }

    public JMenuItem getJMenuItemOutlookLager() {
        if (jMenuItemOutlookLager == null) {
            jMenuItemOutlookLager = new JMenuItem();
            jMenuItemOutlookLager.setText("Outlook / Lager");
            jMenuItemOutlookLager.setEnabled(false);
            jMenuItemOutlookLager.addActionListener(
                    e -> Client.getReports().exportOutlook(SelectionContext.get().getLager().getId()));
        }
        return jMenuItemOutlookLager;
    }

    @Subscribe
    private void jahrSelected(JahrSelected event) {
        disableMenuItems();
        Actions.lagerAnlegen().setJahr(event.get());
        getJMenuLeJa().setEnabled(true);
        getJMenuItemJaAlg().setEnabled(true);
        getJMenuItemJaStatistik().setEnabled(true);
        getJMenuListen().setEnabled(true);
        getJMenuItemEMailJahr().setEnabled(true);
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
        getJMenuItemEMailLager().setEnabled(true);
        getJMenuItemProgramm().setEnabled(true);
        getJMenuItemEssen().setEnabled(true);
        getJMenuItemNachtWache().setEnabled(true);
        getJMenuItemOutlookLager().setEnabled(true);
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
