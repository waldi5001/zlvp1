package de.zlvp.reports;

import static java.lang.String.format;

import java.awt.Cursor;
import java.io.File;
import java.io.InputStream;
import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import javax.sql.DataSource;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.SwingWorker;
import javax.swing.filechooser.FileFilter;

import org.slf4j.MDC;

import de.zlvp.CursorToolkit;
import de.zlvp.gui.FensterKlasse;
import de.zlvp.ui.DesktopPane;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.export.JRCsvExporter;
import net.sf.jasperreports.export.SimpleCsvExporterConfiguration;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleWriterExporterOutput;
import net.sf.jasperreports.view.JasperViewer;

public class ReportsImpl implements Reports {

    private DataSource dataSource;
    private FensterKlasse fensterKlasse;

    @Override
    public void lageruebersicht(int lagerId) {
        showReport("LagerGruppeTeilnehmerLager", map("laid", lagerId));
    }

    @Override
    public void leiterLagerAusfuehrlich(int lagerId) {
        showReport("LeiterlisteAdresse", map("LID", lagerId));
    }

    @Override
    public void leiterJahrAusfuehrlich(int jahrId) {
        showReport("LeiterlisteAdresseJahr", map("JaID", jahrId));
    }

    @Override
    public void teilnehmerStatistik(int personId) {
        showReport("TeilnehmerBericht", map("PeID", personId));
    }

    @Override
    public void teilnehmerAusfuehrlichASC(int lagerId) {
        showReport("TeilnehmerlisteAdresseASC", map("LID", lagerId));
    }

    @Override
    public void teilnehmerAusfuehrlichGeschlecht(int lagerId) {
        showReport("TeilnehmerlisteAdresse", map("LID", lagerId));
    }

    @Override
    public void teilnehmerASC(int lagerId) {
        showReport("TeilnehmerlisteLager", map("LID", lagerId));
    }

    @Override
    public void teilnehmerGeschlecht(int lagerId) {
        showReport("TeilnehmerlisteName", map("LID", lagerId));
    }

    @Override
    public void jahresuebersicht(int jahrId) {
        showReport("LagerGruppeTeilnehmer", map("jaid", jahrId));
    }

    @Override
    public void jahresstatistik(int jahrId) {
        showReport("StatistikJahr", map("jaid", jahrId));
    }

    @Override
    public void alleZelte() {
        showReport("ZeltlisteAlle", new HashMap<>());
    }

    @Override
    public void zelteVonLager(int lagerId) {
        showReport("ZeltlisteLager", map("LaID", lagerId));
    }

    @Override
    public void zeltDetails(int zeltId) {
        showReport("ZeltDetailliste", map("zeID", zeltId));
    }

    @Override
    public void zeltHistorie(int zeltId) {
        showReport("ZeltHistory", map("zeID", zeltId));
    }

    @Override
    public void zeltSchaeden(int zeltId) {
        showReport("ZeltSchaeden", map("zeID", zeltId));
    }

    @Override
    public void teilnehmerVonGruppe(int lagerId) {
        showReport("LagerGruppeTeilnehmer2", map("LaID", lagerId));
    }

    @Override
    public void nachtwachenliste(int lagerId) {
        showReport("Nachtwachenliste", map("LID", lagerId));
    }

    @Override
    public void nachtwachenlisteGruppe(int lagerId) {
        showReport("NachtwachenlisteGruppe", map("LID", lagerId));
    }

    @Override
    public void legenda(int lagerId) {
        showReport("Legenda", map("laid", lagerId));
    }

    @Override
    public void teilnehmerHandyEmail(int lagerId) {
        showReport("TeilnehmerlisteAdresseHandyEmail", map("LID", lagerId));
    }

    @Override
    public void teilnehmerAdresseHandyEmail(int lagerId) {
        showReport("TeilnehmerlisteAdresseHandyEmailASC", map("LID", lagerId));
    }

    @Override
    public void etiketten(int lagerId) {
        showReport("Etiketten", map("laid", lagerId));
    }

    @Override
    public void etikettenLagerinfo() {
        showReport("EtikettenLagerinfo", new HashMap<>());
    }

    @Override
    public void programm(int lagerId) {
        showReport("Programm", map("LaID", lagerId));
    }

    @Override
    public void essen(int lagerId) {
        showReport("Essen", map("LaID", lagerId));
    }

    @Override
    public void gesamtstatistik() {
        showReport("StatistikAlles", new HashMap<>());
    }

    @Override
    public void personenVonLagerCSV(int lagerId) {
        export("personenVonLagerCSV", map("LID", lagerId));
    }

    @Override
    public void exportLegendaCSVJahr(int jahrId) {
        export("LegendaExport", map("jaid", jahrId));
    }

    private Map<String, Object> map(String key, Object value) {
        Map<String, Object> params = new HashMap<>();
        params.put(key, value);
        return params;
    }

    private void showReport(String name, Map<String, Object> params) {
        CursorToolkit.startWaitCursor(fensterKlasse.getRootPane());
        Map<String, String> copyOfContextMap = MDC.getCopyOfContextMap();
        SwingWorker<JasperPrint, Void> sw = new SwingWorker<JasperPrint, Void>() {
            @Override
            protected JasperPrint doInBackground() throws Exception {
                MDC.setContextMap(copyOfContextMap);
                Connection connection = dataSource.getConnection();
                InputStream is = getClass().getResourceAsStream(format("/%s.jasper", name));
                return JasperFillManager.fillReport(is, params, connection);
            }

            @Override
            protected void done() {
                try {
                    CursorToolkit.stopWaitCursor(fensterKlasse.getRootPane());
                    JasperViewer.viewReport(get(), false);
                } catch (InterruptedException | ExecutionException e) {
                    throw new RuntimeException(e.getMessage(), e);
                } finally {
                    MDC.clear();
                }
            }
        };
        sw.execute();
    }

    private void export(String name, Map<String, Object> params) {
        SwingWorker<JasperPrint, Void> sw = new SwingWorker<JasperPrint, Void>() {
            @Override
            protected JasperPrint doInBackground() throws Exception {
                DesktopPane.get().setCursor(new Cursor(Cursor.WAIT_CURSOR));
                Connection connection = dataSource.getConnection();
                InputStream stream = getClass().getResourceAsStream(format("/%s.jasper", name));
                return JasperFillManager.fillReport(stream, params, connection);
            }

            @Override
            protected void done() {
                try {
                    DesktopPane.get().setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
                    JRCsvExporter cvsExporter = new JRCsvExporter();
                    cvsExporter.setExporterInput(new SimpleExporterInput(get()));
                    cvsExporter.setExporterOutput(new SimpleWriterExporterOutput(new File(getSaveDialog())));
                    SimpleCsvExporterConfiguration configuration = new SimpleCsvExporterConfiguration();
                    configuration.setFieldDelimiter(";");
                    cvsExporter.setConfiguration(configuration);
                    cvsExporter.exportReport();
                } catch (InterruptedException | ExecutionException | JRException e) {
                    throw new RuntimeException(e.getMessage(), e);
                }
            }
        };
        sw.execute();
    }

    private String getSaveDialog() {
        JFileChooser fc = new JFileChooser();

        fc.setFileFilter(new FileFilter() {
            @Override
            public boolean accept(File f) {
                return f.isDirectory() || f.getName().toLowerCase().endsWith(".csv");
            }

            @Override
            public String getDescription() {
                return "*.csv - Comma Separated Values";
            }
        });
        fc.setDialogType(JFileChooser.SAVE_DIALOG);

        int state = fc.showSaveDialog(DesktopPane.get());

        if (state == JFileChooser.APPROVE_OPTION) {
            String pfad = fc.getSelectedFile().getAbsolutePath();
            if (!pfad.endsWith(".csv")) {
                pfad = pfad + ".csv";
            }

            File saveFile = fc.getSelectedFile();
            if (saveFile.exists()) {
                int answer = JOptionPane.showConfirmDialog(null,
                        "Die angegebene Datei existiert bereits in diesem Verzeichnis!\nMöchten Du diese Datei ersetzen?",
                        "Warnung", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);

                switch (answer) {
                case JOptionPane.YES_OPTION:

                    pfad = fc.getSelectedFile().getAbsolutePath();
                    break;

                case JOptionPane.NO_OPTION:
                    pfad = "";
                }
            }
            return pfad;
        }
        return null;
    }

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void setFensterKlasse(FensterKlasse fensterklasse) {
        this.fensterKlasse = fensterklasse;
    }
}
