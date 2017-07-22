package de.zlvp;

import java.awt.AWTEvent;
import java.awt.EventQueue;

import de.javasoft.swing.DetailsDialog;

public class ClientExceptionInterceptor extends EventQueue {

    @Override
    protected void dispatchEvent(AWTEvent newEvent) {
        try {
            super.dispatchEvent(newEvent);
        } catch (Throwable t) {
            t.printStackTrace();
            String message = t.getMessage();

            if (message == null || message.length() == 0) {
                message = "Fatal: " + t.getClass();
            }

            DetailsDialog.showDialog(null, "Fehler", message, t);
        }
    }
}