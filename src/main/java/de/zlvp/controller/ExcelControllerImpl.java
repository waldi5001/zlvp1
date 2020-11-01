package de.zlvp.controller;

import static de.zlvp.entity.Geschlecht.Maennlich;
import static de.zlvp.entity.Geschlecht.Weiblich;
import static java.util.stream.Collectors.toList;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

import javax.swing.ProgressMonitor;
import javax.swing.SwingWorker;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.DataValidation;
import org.apache.poi.ss.usermodel.DataValidationConstraint;
import org.apache.poi.ss.usermodel.DataValidationHelper;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddressList;
import org.apache.poi.xssf.usermodel.XSSFDataValidationHelper;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.javasoft.swing.DetailsDialog;
import de.zlvp.dao.GruppeDao;
import de.zlvp.dao.PersonDao;
import de.zlvp.dao.TeilnehmerDao;
import de.zlvp.entity.Gruppe;
import de.zlvp.entity.Person;
import de.zlvp.ui.DesktopPane;

public class ExcelControllerImpl implements ExcelController, PropertyChangeListener {

    private static Logger log = LoggerFactory.getLogger(ExcelController.class);

    private PersonDao personDao;
    private GruppeDao gruppeDao;
    private TeilnehmerDao teilnehmerDao;

    private final ProgressMonitor progressMonitor = new ProgressMonitor(DesktopPane.get(), "Personen importieren", "",
            0, 100);

    @Override
    public byte[] getVorlage(Integer jahrId) {
        try {
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            Workbook wb = new XSSFWorkbook();

            CellStyle cs = wb.createCellStyle();
            DataFormat dateFormat = wb.createDataFormat();
            cs.setDataFormat(dateFormat.getFormat("dd.MM.yy"));

            Sheet s = wb.createSheet();
            Row titelRow = s.createRow(0);

            DataValidationHelper validationHelper = new XSSFDataValidationHelper((XSSFSheet) s);
            CellRangeAddressList anredeRange = new CellRangeAddressList(1, 1000, 0, 0);
            DataValidationConstraint constraint = validationHelper
                    .createExplicitListConstraint(new String[] { "Herr", "Frau" });
            DataValidation dataValidation = validationHelper.createValidation(constraint, anredeRange);
            s.addValidationData(dataValidation);

            if (jahrId != null) {
                List<String> gruppenNamen = gruppeDao.getAllFromJahr(jahrId).stream().map(Gruppe::getBezeichnung)
                        .collect(toList());

                CellRangeAddressList gruppenRange = new CellRangeAddressList(1, 1000, 11, 11);

                DataValidationConstraint gruppenConstraints = validationHelper
                        .createExplicitListConstraint(gruppenNamen.toArray(new String[gruppenNamen.size()]));

                s.addValidationData(validationHelper.createValidation(gruppenConstraints, gruppenRange));
            }

            titelRow.createCell(0).setCellValue("Anrede");
            titelRow.createCell(1).setCellValue("Nachname");
            titelRow.createCell(2).setCellValue("Vorname");
            titelRow.createCell(3).setCellValue("Straße");
            titelRow.createCell(4).setCellValue("PLZ");
            titelRow.createCell(5).setCellValue("Ort");
            titelRow.createCell(6).setCellValue("Geburtstag");
            titelRow.createCell(7).setCellValue("Telefon");
            titelRow.createCell(8).setCellValue("Handy");
            titelRow.createCell(9).setCellValue("Notfall Telefon");
            titelRow.createCell(10).setCellValue("Email");
            titelRow.createCell(11).setCellValue("Gruppe");

            Row beispielRow = s.createRow(1);
            beispielRow.createCell(0).setCellValue("Frau");
            beispielRow.createCell(1).setCellValue("Langstrumpf");
            beispielRow.createCell(2).setCellValue("Pipi");
            beispielRow.createCell(3).setCellValue("Villa Kunterbunt Strasse 1a");
            beispielRow.createCell(4).setCellValue("78199");
            beispielRow.createCell(5).setCellValue("Bräunlingen");

            Calendar c = Calendar.getInstance();
            c.set(1944, 4, 21);
            Cell geburtstag = beispielRow.createCell(6);
            geburtstag.setCellValue(c);
            geburtstag.setCellStyle(cs);

            beispielRow.createCell(7).setCellValue("0771/333999");
            beispielRow.createCell(8).setCellValue("0176/333999");
            beispielRow.createCell(9).setCellValue("0771/222666");
            beispielRow.createCell(10).setCellValue("PippiLottaViktualia@TakkaTukkaland.kk");
            beispielRow.createCell(11).setCellValue("");

            for (int i = 0; i <= 11; i++) {
                s.autoSizeColumn(i);
            }

            wb.write(out);
            wb.close();
            out.close();
            return out.toByteArray();
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    @Override
    public void importieren(byte[] sheet) {
        try {
            Workbook wb = new XSSFWorkbook(new ByteArrayInputStream(sheet));
            Sheet s = wb.getSheetAt(0);

            List<Object[]> failed = new ArrayList<>();

            progressMonitor.setProgress(0);
            progressMonitor.setMillisToPopup(10);
            SwingWorker<Integer, Object[]> sw = new SwingWorker<Integer, Object[]>() {
                @Override
                protected Integer doInBackground() throws Exception {
                    for (int r = 1; r < s.getPhysicalNumberOfRows(); r++) {
                        Row row = s.getRow(r);
                        if (row != null) {// Es kann passieren das eine Zeile leer ist und dann ist row == null
                            String anrede = null, nachname = null, vorname = null, strasse = null, plz = null, ort = null,
                                    telefon = null, handy = null, nottel = null, email = null, gruppe = null;
                            Date gebdat = null;

                            for (int c = 0; c < row.getLastCellNum(); c++) {
                                Cell cell = row.getCell(c);
                                if (cell != null) {
                                    if (c == 0) {
                                        anrede = cell.getStringCellValue().trim();
                                    } else if (c == 1) {
                                        nachname = cell.getStringCellValue().trim();
                                    } else if (c == 2) {
                                        vorname = cell.getStringCellValue().trim();
                                    } else if (c == 3) {
                                        strasse = cell.getStringCellValue().trim();
                                    } else if (c == 4 && cell.getCellType() == CellType.STRING) {
                                        plz = cell.getStringCellValue().trim();
                                    } else if (c == 4 && cell.getCellType() == CellType.NUMERIC) {
                                        plz = new Integer(new Double(cell.getNumericCellValue()).intValue()).toString();
                                    } else if (c == 5) {
                                        ort = cell.getStringCellValue().trim();
                                    } else if (c == 6) {
                                        gebdat = cell.getDateCellValue();
                                    } else if (c == 7) {
                                        telefon = cell.getStringCellValue().trim();
                                    } else if (c == 8) {
                                        handy = cell.getStringCellValue().trim();
                                    } else if (c == 9) {
                                        nottel = cell.getStringCellValue().trim();
                                    } else if (c == 10) {
                                        email = cell.getStringCellValue().trim();
                                    } else if (c == 11) {
                                        gruppe = cell.getStringCellValue().trim();
                                    }
                                }
                            }

                            try {
                                setProgress(r * 100 / s.getPhysicalNumberOfRows());
                                Person person = personDao.speichern(null, vorname, nachname, gebdat, strasse, plz, ort, telefon, email,
                                        "Herr".equals(anrede) ? Maennlich : Weiblich, handy, nottel);
                                if (gruppe != null) {
                                    teilnehmerDao.speichere(person.getId(), gruppeDao.findGruppe(gruppe).getId());
                                }
                                Thread.sleep(100L);
                            } catch (Throwable e) {
                                log.error(e.getMessage(), e);
                                failed.add(new Object[] { anrede, vorname, nachname,
                                        new SimpleDateFormat("dd.MM.yy").format(gebdat), strasse, plz, ort, telefon, email,
                                        handy, nottel, "\n\t" + e.getCause().getMessage().replaceAll("\n", "") });
                            }
                        }
                    }
                    return 1;
                }

                @Override
                protected void done() {
                    try {
                        get();
                    } catch (InterruptedException | ExecutionException e) {
                        log.error(e.getMessage(), e);
                        DetailsDialog.showDialog(null, "Fehler beim Import",
                                "Bei der Verarbeitung des Excels kam es zu einem Fehler", e);
                    }

                    progressMonitor.setProgress(100);
                    if (!failed.isEmpty()) {
                        List<String> collect = failed.stream().map(f -> Arrays.toString(f) + "\n")
                                .collect(Collectors.toList());
                        DetailsDialog.showDialog(null, "Fehler beim Import",
                                "Einige Einträge konnten nicht importiert werden. Siehe Details",
                                collect.toString().substring(1), DetailsDialog.ERROR_TYPE);
                    }
                }
            };
            sw.addPropertyChangeListener(this);
            sw.execute();
        } catch (Throwable e) {
            log.error(e.getMessage(), e);
        }
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if ("progress" == evt.getPropertyName()) {
            int progress = (Integer) evt.getNewValue();
            progressMonitor.setProgress(progress);
            String message = String.format("Fertig: %d%%.\n", progress);
            progressMonitor.setNote(message);
        }
    }

    public void setPersonDao(PersonDao personDao) {
        this.personDao = personDao;
    }

    public void setGruppeDao(GruppeDao gruppeDao) {
        this.gruppeDao = gruppeDao;
    }

    public void setTeilnehmerDao(TeilnehmerDao teilnehmerDao) {
        this.teilnehmerDao = teilnehmerDao;
    }
}
