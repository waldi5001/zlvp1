package de.zlvp;

import java.awt.Cursor;
import java.awt.event.MouseAdapter;

import javax.swing.JComponent;
import javax.swing.RootPaneContainer;

public class CursorToolkit {
    private final static MouseAdapter mouseAdapter = new MouseAdapter() {
    };

    private CursorToolkit() {
    }

    public static void startWaitCursor(JComponent component) {
        RootPaneContainer root = ((RootPaneContainer) component.getTopLevelAncestor());
        root.getGlassPane().setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        // root.getGlassPane().addMouseListener(mouseAdapter);
        root.getGlassPane().setVisible(true);
    }

    public static void stopWaitCursor(JComponent component) {
        RootPaneContainer root = ((RootPaneContainer) component.getTopLevelAncestor());
        root.getGlassPane().setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
        // root.getGlassPane().removeMouseListener(mouseAdapter);
        root.getGlassPane().setVisible(false);
    }

}