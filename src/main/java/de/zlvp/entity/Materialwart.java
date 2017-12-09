package de.zlvp.entity;

import java.util.Date;

public class Materialwart extends Person {

    public Materialwart(Integer id, Geschlecht geschlecht, String vorname, String name, String strasse, String sPLZ,
            String ort, Date daGebDat, String handy, String telNr, String email, String nottel) {
        super(id, geschlecht, vorname, name, strasse, sPLZ, ort, daGebDat, handy, telNr, email, nottel);
    }

    public Materialwart(Person person) {
        super(person.getId(), person.getGeschlecht(), person.getVorname(), person.getName(), person.getStrasse(),
                person.getPlz(), person.getOrt(), person.getGebDat(), person.getHandy(), person.getTelNr(),
                person.getEmail(), person.getNottel());
    }

    private Lager lager;

    public Lager getLager() {
        return lager;
    }

    public void setLager(Lager lager) {
        this.lager = lager;
    }

}
