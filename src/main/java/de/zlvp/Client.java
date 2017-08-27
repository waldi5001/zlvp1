package de.zlvp;

import static java.lang.String.copyValueOf;

import java.sql.SQLException;

import javax.swing.JOptionPane;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import com.zaxxer.hikari.HikariDataSource;

import de.zlvp.controller.Controller;
import de.zlvp.controller.ExcelController;
import de.zlvp.reports.Reports;

public class Client implements ApplicationContextAware {
    private static ApplicationContext applicationContext;

    public static Controller get() {
        return applicationContext.getBean(Controller.class);
    }

    public static boolean login(String user, char[] passwort) {
        HikariDataSource datasource = applicationContext.getBean(HikariDataSource.class);
        datasource.setUsername(user);
        datasource.setPassword(copyValueOf(passwort));
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
