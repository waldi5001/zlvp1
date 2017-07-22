package de.zlvp.gui;

import java.beans.PropertyVetoException;

import javax.swing.JRootPane;
import javax.swing.SwingUtilities;

import de.zlvp.entity.Lagerort;
import de.zlvp.ui.InternalFrame;

public class LegendaVerwalten extends InternalFrame {

    private static final long serialVersionUID = -1L;

    final Lagerort lagerort;

    LegendaVerwaltenPanel legendaVerwalten;

    public LegendaVerwalten(Lagerort lagerort) {
        this.lagerort = lagerort;

        initialize();
        setUp();

        JRootPane rootPane = SwingUtilities.getRootPane(legendaVerwalten.getJButtonSpeichern());
        rootPane.setDefaultButton(legendaVerwalten.getJButtonSpeichern());

        setMaximizable(true);
        try {
            setMaximum(true);
        } catch (PropertyVetoException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    private void initialize() {
        this.setSize(580, 531);
        this.setTitle("Legenda für: " + lagerort.getName());
        legendaVerwalten = new LegendaVerwaltenPanel(lagerort);
        this.setContentPane(legendaVerwalten);
    }

}
