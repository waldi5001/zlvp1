package de.zlvp.gui;

import de.zlvp.entity.Lagerort;
import de.zlvp.ui.AbstractJInternalFrame;

public class LegendaVerwalten extends AbstractJInternalFrame {

    private static final long serialVersionUID = -1L;

    final Lagerort lagerort;

    public LegendaVerwalten(Lagerort lagerort) {
        this.lagerort = lagerort;

        initialize();
        setup();

        setVisible(true);
    }

    private void initialize() {
        this.setSize(580, 531);
        this.setTitle("Legenda für: " + lagerort.getName());
        this.setContentPane(new LegendaVerwaltenPanel(() -> lagerort));
    }

}
