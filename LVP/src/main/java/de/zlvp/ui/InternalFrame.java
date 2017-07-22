package de.zlvp.ui;

import java.awt.Dimension;

import javax.swing.JDesktopPane;
import javax.swing.JInternalFrame;

public class InternalFrame extends JInternalFrame {

    protected final JDesktopPane desktopPane;

    protected Dimension sizeDesktopPane;

    public InternalFrame() {
        this.desktopPane = DesktopPane.get();
    }

    protected void setUp() {
        desktopPane.add(this);
        Dimension sizeDesktopPane = desktopPane.getSize();
        this.setResizable(true);
        this.setClosable(true);
        this.setIconifiable(true);
        this.setVisible(true);

        if (sizeDesktopPane.width == 0 && sizeDesktopPane.height == 0) {
            sizeDesktopPane = getToolkit().getScreenSize();
        }
        this.setBounds((sizeDesktopPane.width - this.getSize().width) / 2,
                (sizeDesktopPane.height - this.getSize().height) / 2, this.getSize().width, this.getSize().height);
    }

    private static final long serialVersionUID = 1L;

}
