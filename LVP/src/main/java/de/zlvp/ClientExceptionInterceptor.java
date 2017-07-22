package de.zlvp;

import java.awt.AWTEvent;
import java.awt.EventQueue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.javasoft.swing.DetailsDialog;

public class ClientExceptionInterceptor extends EventQueue {

    private static Logger log = LoggerFactory.getLogger(ClientExceptionInterceptor.class);

    @Override
    protected void dispatchEvent(AWTEvent newEvent) {
        try {
            super.dispatchEvent(newEvent);
        } catch (Throwable t) {
            log.error(t.getMessage(), t);
            String message = t.getMessage();

            if (message == null || message.length() == 0) {
                message = "Fatal: " + t.getClass();
            }

            DetailsDialog.showDialog(null, "Fehler", message, t);
        }
    }
}