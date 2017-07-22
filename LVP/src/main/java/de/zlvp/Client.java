package de.zlvp;

import java.sql.SQLException;

import javax.swing.JOptionPane;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.jdbc.datasource.SingleConnectionDataSource;

import de.zlvp.controller.Controller;
import de.zlvp.controller.ExcelController;
import de.zlvp.reports.Reports;

public class Client implements ApplicationContextAware {
    private static ApplicationContext applicationContext;

    public static Controller get() {
        return applicationContext.getBean(Controller.class);
    }

    public static boolean login(String user, String passwort) {
        SingleConnectionDataSource datasource = applicationContext.getBean(SingleConnectionDataSource.class);
        datasource.setUsername(user);
        datasource.setPassword(passwort);
        try {
            datasource.getConnection();
            return true;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Fehler", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

    public static Reports getReports() {
        return applicationContext.getBean(Reports.class);
    }

    public static ExcelController getExcelController() {
        return applicationContext.getBean(ExcelController.class);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        Client.applicationContext = applicationContext;
    }

}
