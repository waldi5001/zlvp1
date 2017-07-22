package de.zlvp.ui;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

public class TreePopup extends JPopupMenu {

    private static final long serialVersionUID = 1L;

    public TreePopup() {
        add(new JMenuItem(Actions.lagerAnlegen()));
        add(new JMenuItem(Actions.gruppeAnlegen()));
    }

}
