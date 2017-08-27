package de.zlvp.gui;

import javax.swing.JRootPane;
import javax.swing.SwingUtilities;

import de.zlvp.entity.Lagerort;
import de.zlvp.ui.AbstractJInternalFrame;

public class LegendaVerwalten extends AbstractJInternalFrame {

    private static final long serialVersionUID = -1L;

    final Lagerort lagerort;

    LegendaVerwaltenPanel legendaVerwalten;

    public LegendaVerwalten(Lagerort lagerort) {
        this.lagerort = lagerort;

        initialize();
        setup();

        JRootPane rootPane = SwingUtilities.getRootPane(legendaVerwalten.getJButtonSpeichern());
        rootPane.setDefaultButton(legendaVerwalten.getJButtonSpeichern());
        setVisible(true);
    }

    private void initialize() {
        this.setSize(580, 531);
        this.setTitle("Legenda für: " + lagerort.getName());
        legendaVerwalten = new LegendaVerwaltenPanel(lagerort);
        this.setContentPane(legendaVerwalten);
    }

}
