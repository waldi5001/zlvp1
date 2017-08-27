package de.zlvp.ui;

import javax.swing.JDesktopPane;

public class DesktopPane {

    private DesktopPane() {
    }

    private static final JDesktopPane desktopPane = new JDesktopPane();

    public static JDesktopPane get() {
        return desktopPane;
    }

}
