package de.zlvp.gui;

import static de.zlvp.Client.get;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTable;

import com.google.common.eventbus.Subscribe;

import de.javasoft.swing.JYTableScrollPane;
import de.zlvp.Events;
import de.zlvp.Events.LagerSaved;
import de.zlvp.entity.Lagerort;
import de.zlvp.entity.Legenda;
import de.zlvp.ui.JTableBuilder;
import de.zlvp.ui.JTableBuilders;

public class LegendaVerwaltenPanel extends JPanel {

    private static final long serialVersionUID = -1L;
    private JTable jTableLegenda;
    private JPanel jPanelButtons;
    private JButton jButtonNeu;

    private Lagerort lagerort;
    private JTableBuilder<Legenda> tableBuilderLegenda;

    public LegendaVerwaltenPanel(Lagerort lagerort) {
        Events.bus().register(this);

        this.lagerort = lagerort;

        tableBuilderLegenda = JTableBuilders
                .legenda(legendas -> get().getAllLegendaFromLagerort(lagerort.getOriginalId(), legendas));

        initialize();
    }

    private void initialize() {
        setLayout(new BorderLayout());
        add(new JYTableScrollPane(getJTableLegenda()), BorderLayout.CENTER);
        add(getJPanelButtons(), BorderLayout.SOUTH);
    }

    public JTable getJTableLegenda() {
        if (jTableLegenda == null) {
            jTableLegenda = tableBuilderLegenda.build();
        }
        return jTableLegenda;
    }

    private JPanel getJPanelButtons() {
        if (jPanelButtons == null) {
            jPanelButtons = new JPanel();
            jPanelButtons.setLayout(new FlowLayout());
            jPanelButtons.add(getJButtonNeu());
        }
        return jPanelButtons;
    }

    private JButton getJButtonNeu() {
        if (jButtonNeu == null) {
            jButtonNeu = new JButton();
            jButtonNeu.setText("Neu");
            jButtonNeu.addActionListener(e -> {
                get().speichereLegenda(null, lagerort.getOriginalId(), "Neu", "Neu", null, null, null, null, null, null,
                        null, null, null, null, null, asyncCallback -> tableBuilderLegenda.refresh());
            });
        }
        return jButtonNeu;
    }

    @Subscribe
    public void lagersaved(LagerSaved event) {
        if (event.lagerort() != null) {
            lagerort = event.lagerort();
            tableBuilderLegenda.refresh();
        }
    }
}
