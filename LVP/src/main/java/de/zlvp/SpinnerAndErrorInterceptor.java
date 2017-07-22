package de.zlvp;

import java.awt.Cursor;

import javax.swing.JInternalFrame;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.javasoft.swing.DetailsDialog;
import de.zlvp.gui.FensterKlasse;
import de.zlvp.ui.DesktopPane;

public class SpinnerAndErrorInterceptor implements MethodInterceptor {
    private static Logger log = LoggerFactory.getLogger(SpinnerAndErrorInterceptor.class);

    private FensterKlasse fensterKlasse;

    @Override
    public Object invoke(MethodInvocation invocation) {
        try {
            for (JInternalFrame jInternalFrame : DesktopPane.get().getAllFrames()) {
                jInternalFrame.setCursor(new Cursor(Cursor.WAIT_CURSOR));
            }
            fensterKlasse.setCursor(new Cursor(Cursor.WAIT_CURSOR));

            return invocation.proceed();
        } catch (Throwable e) {
            log.error(e.getMessage(), e);
            String message = e.getMessage();
            if (message == null || message.length() == 0) {
                message = "Fatal: " + e.getClass();
            }
            DetailsDialog.showDialog(null, "Fehler", message, e);
        } finally {
            for (JInternalFrame jInternalFrame : DesktopPane.get().getAllFrames()) {
                jInternalFrame.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
            }
            fensterKlasse.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
        }
        return null;
    }

    public void setFensterKlasse(FensterKlasse fensterKlasse) {
        this.fensterKlasse = fensterKlasse;
    }

}
