package de.zlvp.controller;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFDataValidationHelper;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.DataValidation;
import org.apache.poi.ss.usermodel.DataValidationConstraint;
import org.apache.poi.ss.usermodel.DataValidationHelper;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddressList;

public class ExcelControllerImpl implements ExcelController {

    private Controller controller;

    @Override
    public byte[] getVorlage() {
        try {
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            Workbook wb = new HSSFWorkbook();

            CellStyle cs = wb.createCellStyle();
            DataFormat dateFormat = wb.createDataFormat();
            cs.setDataFormat(dateFormat.getFormat("dd.MM.yy"));

            Sheet s = wb.createSheet();
            Row titelRow = s.createRow(0);

            DataValidationHelper validationHelper = new HSSFDataValidationHelper((HSSFSheet) s);
            CellRangeAddressList addressList = new CellRangeAddressList(1, 1000, 0, 0);
            DataValidationConstraint constraint = validationHelper
                    .createExplicitListConstraint(new String[] { "Herr", "Frau" });
            DataValidation dataValidation = validationHelper.createValidation(constraint, addressList);
            s.addValidationData(dataValidation);

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

            for (int i = 0; i <= 10; i++) {
                s.autoSizeColumn(i);
            }

            wb.write(out);
            out.close();
            return out.toByteArray();
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    @Override
    public void importieren(byte[] sheet) {
        try {
            HSSFWorkbook wb = new HSSFWorkbook(new ByteArrayInputStream(sheet));
            HSSFSheet s = wb.getSheetAt(0);
            for (int r = 1; r < s.getPhysicalNumberOfRows(); r++) {
                HSSFRow row = s.getRow(r);

                String anrede = null, nachname = null, vorname = null, strasse = null, plz = null, ort = null,
                        telefon = null, handy = null, nottel = null, email = null;
                Date gebdat = null;

                for (int c = 0; c < row.getLastCellNum(); c++) {
                    HSSFCell cell = row.getCell(c);
                    if (c == 0) {
                        anrede = cell.getStringCellValue();
                    } else if (c == 1) {
                        nachname = cell.getStringCellValue();
                    } else if (c == 2) {
                        vorname = cell.getStringCellValue();
                    } else if (c == 3) {
                        strasse = cell.getStringCellValue();
                    } else if (c == 4) {
                        plz = cell.getStringCellValue();
                    } else if (c == 5) {
                        ort = cell.getStringCellValue();
                    } else if (c == 6) {
                        gebdat = cell.getDateCellValue();
                    } else if (c == 7) {
                        telefon = cell.getStringCellValue();
                    } else if (c == 8) {
                        handy = cell.getStringCellValue();
                    } else if (c == 9) {
                        nottel = cell.getStringCellValue();
                    } else if (c == 10) {
                        email = cell.getStringCellValue();
                    }
                }
                controller.speicherePerson(null, "Herr".equals(anrede) ? 1 : 2, vorname, nachname, strasse, plz, ort,
                        gebdat, telefon, email, handy, nottel);
            }
        } catch (Throwable e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    public void setController(Controller controller) {
        this.controller = controller;
    }

}
