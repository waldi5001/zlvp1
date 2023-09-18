package de.zlvp.ui;

import static de.zlvp.Client.get;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.*;

import de.zlvp.Client;
import de.zlvp.Events;
import de.zlvp.entity.Jahr;
import de.zlvp.entity.Lager;
import de.zlvp.gui.Benutzerverwaltung;
import de.zlvp.gui.HauptFenster;
import de.zlvp.gui.LagerAnlegen;
import de.zlvp.gui.PersonSuchen;
import de.zlvp.gui.ZeltVerwalten;

public class Actions {

    private static final LagerAnlegenAction lagerAnlegen = new LagerAnlegenAction();
    private static final GruppeAnlegenAction gruppeAnlegen = new GruppeAnlegenAction();
    private static final BenutzerverwaltungAction benutzerverwaltung = new BenutzerverwaltungAction();
    private static final OeffnenAction oeffnen = new OeffnenAction();
    private static final PersonAendernAction personAendernAction = new PersonAendernAction();
    private static final ZelteVerwaltenAction zelteVerwaltenAction = new ZelteVerwaltenAction();

    private static final ZelteVerwaltenAction aktualisierenAction = new ZelteVerwaltenAction();

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

    public static OeffnenAction oeffnen() {
        return oeffnen;
    }

    public static PersonAendernAction personAendern() {
        return personAendernAction;
    }

    public static ZelteVerwaltenAction zelteVerwalten() {
        return zelteVerwaltenAction;
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
                get().speichereGruppe(true, null, lager.getId(), gruppenname, null,
                        cb -> Events.get().fireGruppeSaved(cb, null, lager));
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
            putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_B, KeyEvent.CTRL_DOWN_MASK));
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            new Benutzerverwaltung();
        }
    }

    public static class OeffnenAction extends AbstractAction {

        private static final long serialVersionUID = 1L;

        public OeffnenAction() {
            super("Öffnen");
            setEnabled(false);
            putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_O, KeyEvent.CTRL_DOWN_MASK));
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            Client.get().getAllJahr(allJahre -> {
                Jahr[] jahre = allJahre.toArray(new Jahr[allJahre.size()]);
                Jahr jahr = (Jahr) JOptionPane.showInputDialog(null, null, "Jahr wählen:", JOptionPane.PLAIN_MESSAGE,
                        null, jahre, null);
                if (jahr != null) {
                    new HauptFenster(jahr.getId());
                }
            });
        }
    }

    public static class PersonAendernAction extends AbstractAction {
        private static final long serialVersionUID = 1L;

        public PersonAendernAction() {
            super("Person");
            setEnabled(false);
            putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_P, KeyEvent.CTRL_DOWN_MASK));
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            new PersonSuchen();
        }

    }

    public static class ZelteVerwaltenAction extends AbstractAction {
        private static final long serialVersionUID = 1L;

        public ZelteVerwaltenAction() {
            super("Zelte Verwalten");
            setEnabled(false);
            putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_M, KeyEvent.CTRL_DOWN_MASK));
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            new ZeltVerwalten();
        }

    }

    private abstract static class AktualisierenAction extends AbstractAction {
        public AktualisierenAction() {
            super("Aktualisieren");
            setEnabled(true);
            putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_F5, 0));
        }

    }

}
