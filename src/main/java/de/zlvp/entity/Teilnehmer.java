package de.zlvp.entity;

import java.util.Date;

public class Teilnehmer extends Person {

    public Teilnehmer(Integer id, Geschlecht geschlecht, String vorname, String name, String strasse, String sPLZ,
            String ort, Date daGebDat, String handy, String telNr, String email, String nottel) {
        super(id, geschlecht, vorname, name, strasse, sPLZ, ort, daGebDat, handy, telNr, email, nottel);
    }

    public Teilnehmer(Person person) {
        super(person.getId(), person.getGeschlecht(), person.getVorname(), person.getName(), person.getStrasse(),
                person.getPlz(), person.getOrt(), person.getGebDat(), person.getHandy(), person.getTelNr(),
                person.getEmail(), person.getNottel());
    }

    private Gruppe gruppe;

    public Gruppe getGruppe() {
        return gruppe;
    }

    public void setGruppe(Gruppe gruppe) {
        this.gruppe = gruppe;
    }

}
