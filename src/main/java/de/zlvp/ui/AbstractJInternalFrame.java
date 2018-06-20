package de.zlvp.ui;

import static java.awt.event.KeyEvent.VK_ESCAPE;
import static java.awt.event.KeyEvent.VK_W;
import static javax.swing.KeyStroke.getKeyStroke;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.beans.PropertyVetoException;

import javax.swing.AbstractAction;
import javax.swing.JDesktopPane;
import javax.swing.JInternalFrame;

public class AbstractJInternalFrame extends JInternalFrame {

    protected final JDesktopPane desktopPane;

    protected Dimension sizeDesktopPane;

    public AbstractJInternalFrame() {
        this.desktopPane = DesktopPane.get();

        AbstractAction closeAction = new AbstractAction() {
            private static final long serialVersionUID = 1L;

            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    setClosed(true);
                } catch (PropertyVetoException e1) {
                    e1.printStackTrace();
                }
            }
        };

        getRootPane().getInputMap(WHEN_IN_FOCUSED_WINDOW).put(getKeyStroke(VK_W, KeyEvent.CTRL_DOWN_MASK), "close");
        getRootPane().getInputMap(WHEN_IN_FOCUSED_WINDOW).put(getKeyStroke(VK_ESCAPE, 0), "close");
        getRootPane().getActionMap().put("close", closeAction);
    }

    protected void setupDialog() {
        desktopPane.add(this);
        Dimension sizeDesktopPane = desktopPane.getSize();
        setResizable(true);
        setClosable(true);
        setIconifiable(true);
        setVisible(true);

        if (sizeDesktopPane.width == 0 && sizeDesktopPane.height == 0) {
            sizeDesktopPane = getToolkit().getScreenSize();
        }
        setBounds((sizeDesktopPane.width - this.getSize().width) / 2,
                (sizeDesktopPane.height - this.getSize().height) / 2, this.getSize().width, this.getSize().height);
    }

    protected void setup() {
        desktopPane.add(this);
        Dimension sizeDesktopPane = desktopPane.getSize();
        setResizable(true);
        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);

        if (sizeDesktopPane.width == 0 && sizeDesktopPane.height == 0) {
            sizeDesktopPane = getToolkit().getScreenSize();
        }
        setBounds((sizeDesktopPane.width - this.getSize().width) / 2,
                (sizeDesktopPane.height - this.getSize().height) / 2, this.getSize().width, this.getSize().height);
        try {
            setMaximum(true);
        } catch (PropertyVetoException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    private static final long serialVersionUID = 1L;

    protected GridBagConstraints defaultGridbagConstraints() {
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.BOTH;
        constraints.insets = new Insets(5, 5, 5, 5);
        constraints.weightx = 1.0;
        constraints.weighty = 1.0;
        return constraints;
    }

}
