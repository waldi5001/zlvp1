package de.zlvp.reports;

import static java.lang.String.format;

import java.io.File;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileFilter;

import de.zlvp.controller.AsyncCallback;
import de.zlvp.ui.DesktopPane;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.export.JRCsvExporter;
import net.sf.jasperreports.export.SimpleCsvExporterConfiguration;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleWriterExporterOutput;

public class ReportsImpl implements Reports {

    private DataSource dataSource;

    @Override
    public void lageruebersicht(int lagerId, AsyncCallback<JasperPrint> cb) {
        cb.get(fillReport("LagerGruppeTeilnehmerLager", map("laid", lagerId)));
    }

    @Override
    public void leiterLagerAusfuehrlich(int lagerId, AsyncCallback<JasperPrint> cb) {
        cb.get(fillReport("LeiterlisteAdresse", map("LID", lagerId)));
    }

    @Override
    public void leiterJahrAusfuehrlich(int jahrId, AsyncCallback<JasperPrint> cb) {
        cb.get(fillReport("LeiterlisteAdresseJahr", map("JaID", jahrId)));
    }

    @Override
    public void teilnehmerStatistik(int personId, AsyncCallback<JasperPrint> cb) {
        cb.get(fillReport("TeilnehmerBericht", map("PeID", personId)));
    }

    @Override
    public void teilnehmerAusfuehrlichASC(int lagerId, AsyncCallback<JasperPrint> cb) {
        cb.get(fillReport("TeilnehmerlisteAdresseASC", map("LID", lagerId)));
    }

    @Override
    public void teilnehmerAusfuehrlichGeschlecht(int lagerId, AsyncCallback<JasperPrint> cb) {
        cb.get(fillReport("TeilnehmerlisteAdresse", map("LID", lagerId)));
    }

    @Override
    public void teilnehmerASC(int lagerId, AsyncCallback<JasperPrint> cb) {
        cb.get(fillReport("TeilnehmerlisteLager", map("LID", lagerId)));
    }

    @Override
    public void teilnehmerGeschlecht(int lagerId, AsyncCallback<JasperPrint> cb) {
        cb.get(fillReport("TeilnehmerlisteName", map("LID", lagerId)));
    }

    @Override
    public void jahresuebersicht(int jahrId, AsyncCallback<JasperPrint> cb) {
        cb.get(fillReport("LagerGruppeTeilnehmer", map("jaid", jahrId)));
    }

    @Override
    public void jahresstatistik(int jahrId, AsyncCallback<JasperPrint> cb) {
        cb.get(fillReport("StatistikJahr", map("jaid", jahrId)));
    }

    @Override
    public void alleZelte(AsyncCallback<JasperPrint> cb) {
        cb.get(fillReport("ZeltlisteAlle", new HashMap<>()));
    }

    @Override
    public void zelteVonLager(int lagerId, AsyncCallback<JasperPrint> cb) {
        cb.get(fillReport("ZeltlisteLager", map("LaID", lagerId)));
    }

    @Override
    public void zeltDetails(int zeltId, AsyncCallback<JasperPrint> cb) {
        cb.get(fillReport("ZeltDetailliste", map("zeID", zeltId)));
    }

    @Override
    public void zeltHistorie(int zeltId, AsyncCallback<JasperPrint> cb) {
        cb.get(fillReport("ZeltHistory", map("zeID", zeltId)));
    }

    @Override
    public void zeltSchaeden(int zeltId, AsyncCallback<JasperPrint> cb) {
        cb.get(fillReport("ZeltSchaeden", map("zeID", zeltId)));
    }

    @Override
    public void teilnehmerVonGruppe(int lagerId, AsyncCallback<JasperPrint> cb) {
        cb.get(fillReport("LagerGruppeTeilnehmer2", map("LaID", lagerId)));
    }

    @Override
    public void nachtwachenliste(int lagerId, AsyncCallback<JasperPrint> cb) {
        cb.get(fillReport("Nachtwachenliste", map("LID", lagerId)));
    }

    @Override
    public void nachtwachenlisteGruppe(int lagerId, AsyncCallback<JasperPrint> cb) {
        cb.get(fillReport("NachtwachenlisteGruppe", map("LID", lagerId)));
    }

    @Override
    public void legenda(int lagerId, AsyncCallback<JasperPrint> cb) {
        cb.get(fillReport("Legenda", map("laid", lagerId)));
    }

    @Override
    public void teilnehmerHandyEmail(int lagerId, AsyncCallback<JasperPrint> cb) {
        cb.get(fillReport("TeilnehmerlisteAdresseHandyEmail", map("LID", lagerId)));
    }

    @Override
    public void teilnehmerAdresseHandyEmail(int lagerId, AsyncCallback<JasperPrint> cb) {
        cb.get(fillReport("TeilnehmerlisteAdresseHandyEmailASC", map("LID", lagerId)));
    }

    @Override
    public void etiketten(int lagerId, AsyncCallback<JasperPrint> cb) {
        cb.get(fillReport("Etiketten", map("laid", lagerId)));
    }

    @Override
    public void etikettenLagerinfo(AsyncCallback<JasperPrint> cb) {
        cb.get(fillReport("EtikettenLagerinfo", new HashMap<>()));
    }

    @Override
    public void programm(int lagerId, AsyncCallback<JasperPrint> cb) {
        cb.get(fillReport("Programm", map("LaID", lagerId)));
    }

    @Override
    public void essen(int lagerId, AsyncCallback<JasperPrint> cb) {
        cb.get(fillReport("Essen", map("LaID", lagerId)));
    }

    @Override
    public void gesamtstatistik(AsyncCallback<JasperPrint> cb) {
        cb.get(fillReport("StatistikAlles", new HashMap<>()));
    }

    @Override
    public void personenVonLagerCSV(int lagerId, AsyncCallback<JasperPrint> cb) {
        cb.get(fillReport("personenVonLagerCSV", map("LID", lagerId)));
    }

    @Override
    public void exportLegendaCSVJahr(int jahrId, AsyncCallback<JasperPrint> cb) {
        cb.get(fillReport("LegendaExport", map("jaid", jahrId)));
    }

    private Map<String, Object> map(String key, Object value) {
        Map<String, Object> params = new HashMap<>();
        params.put(key, value);
        return params;
    }

    private JasperPrint fillReport(String name, Map<String, Object> params) {
        try {
            Connection connection = dataSource.getConnection();
            InputStream is = getClass().getResourceAsStream(format("/%s.jasper", name));
            return JasperFillManager.fillReport(is, params, connection);
        } catch (SQLException | JRException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    public static void exportReport(JasperPrint report) {
        try {
            JRCsvExporter cvsExporter = new JRCsvExporter();
            cvsExporter.setExporterInput(new SimpleExporterInput(report));
            cvsExporter.setExporterOutput(new SimpleWriterExporterOutput(new File(getSaveDialog())));
            SimpleCsvExporterConfiguration configuration = new SimpleCsvExporterConfiguration();
            configuration.setFieldDelimiter(";");
            cvsExporter.setConfiguration(configuration);
            cvsExporter.exportReport();
        } catch (JRException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    private static String getSaveDialog() {
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
                        "Die angegebene Datei existiert bereits in diesem Verzeichnis!\nMöchten Du diese Datei ersetzen?", "Warnung",
                        JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
                if (answer == JOptionPane.YES_OPTION) {
                    return fc.getSelectedFile().getAbsolutePath();
                } else if (answer == JOptionPane.NO_OPTION) {
                    return "";
                }
            }
            return pfad;
        }
        return null;
    }

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

}
