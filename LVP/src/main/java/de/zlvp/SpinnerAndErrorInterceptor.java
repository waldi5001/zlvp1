package de.zlvp;

import java.awt.Cursor;

import javax.swing.JInternalFrame;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

import de.javasoft.swing.DetailsDialog;
import de.zlvp.gui.FensterKlasse;
import de.zlvp.ui.DesktopPane;

public class SpinnerAndErrorInterceptor implements MethodInterceptor {

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
            DetailsDialog.showDialog(null, "Fehler", e.getMessage(), e);
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
