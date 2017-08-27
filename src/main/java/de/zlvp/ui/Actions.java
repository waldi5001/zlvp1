package de.zlvp.ui;

import static de.zlvp.Client.get;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.JOptionPane;

import de.zlvp.Events;
import de.zlvp.entity.Jahr;
import de.zlvp.entity.Lager;
import de.zlvp.gui.Benutzerverwaltung;
import de.zlvp.gui.LagerAnlegen;

public class Actions {

    private static final LagerAnlegenAction lagerAnlegen = new LagerAnlegenAction();
    private static final GruppeAnlegenAction gruppeAnlegen = new GruppeAnlegenAction();
    private static final BenutzerverwaltungAction benutzerverwaltung = new BenutzerverwaltungAction();

    private Actions() {
    }

    public static LagerAnlegenAction lagerAnlegen() {
        return lagerAnlegen;
    }

    public static GruppeAnlegenAction gruppeAnlegen() {
        return gruppeAnlegen;
    }

    public static BenutzerverwaltungAction benutzerverwaltung() {
        return benutzerverwaltung;
    }

    public static class LagerAnlegenAction extends AbstractAction {
        private static final long serialVersionUID = 1L;

        private Jahr jahr;

        public LagerAnlegenAction() {
            super("Lager anlegen");
            setEnabled(false);
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            new LagerAnlegen(jahr.getId());
        }

        public void setJahr(Jahr jahr) {
            setEnabled(true);
            this.jahr = jahr;
        }
    }

    public static class GruppeAnlegenAction extends AbstractAction {
        private static final long serialVersionUID = 1L;

        private Lager lager;

        public GruppeAnlegenAction() {
            super("Gruppe anlegen");
            setEnabled(false);
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            String gruppenname = JOptionPane.showInputDialog("Neuen Gruppenname eingeben");
            if (gruppenname != null && !gruppenname.isEmpty()) {
                get().speichereGruppe(null, null, lager.getId(), gruppenname, null,
                        asyncCallback -> Events.get().fireAktualisieren());
            }
        }

        public void setLager(Lager lager) {
            setEnabled(true);
            this.lager = lager;
        }
    }

    public static class BenutzerverwaltungAction extends AbstractAction {
        private static final long serialVersionUID = 1L;

        public BenutzerverwaltungAction() {
            super("Benutzerverwaltung");
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            new Benutzerverwaltung();
        }
    }

}
