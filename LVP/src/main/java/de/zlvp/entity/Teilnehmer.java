package de.zlvp.entity;

import java.util.Date;

public class Teilnehmer extends Person {

    public Teilnehmer(Integer id, int personId, String vorname, String name, String strasse, String sPLZ, String ort,
            Date daGebDat, String handy, String telNr, String email) {
        super(id, vorname, name, strasse, sPLZ, ort, daGebDat, handy, telNr, email);
        setOriginalId(personId);
    }

    public Teilnehmer(Person person) {
        super(null, person.getVorname(), person.getName(), person.getStrasse(), person.getPlz(), person.getOrt(),
                person.getGebDat(), person.getHandy(), person.getTelNr(), person.getEmail());
        setOriginalId(person.getId());
        setGeschlecht(person.getGeschlecht());
    }

    private Gruppe gruppe;

    public Gruppe getGruppe() {
        return gruppe;
    }

    public void setGruppe(Gruppe gruppe) {
        this.gruppe = gruppe;
    }

}