package de.zlvp.entity;

import java.util.Date;

public class Stab extends Person {

    public Stab(Integer id, int personId, String vorname, String name, String strasse, String sPLZ, String ort,
            Date daGebDat, String handy, String telNr, String email) {
        super(id, vorname, name, strasse, sPLZ, ort, daGebDat, handy, telNr, email);
        setOriginalId(personId);
    }

    public Stab(Person person) {
        super(null, person.getVorname(), person.getName(), person.getStrasse(), person.getPlz(), person.getOrt(),
                person.getGebDat(), person.getHandy(), person.getTelNr(), person.getEmail());
        setOriginalId(person.getId());
        setGeschlecht(person.getGeschlecht());
    }

    private Funktion funktion;
    private Lager lager;

    public Lager getLager() {
        return lager;
    }

    public void setLager(Lager lager) {
        this.lager = lager;
    }

    public Funktion getFunktion() {
        return funktion;
    }

    public void setFunktion(Funktion funktion) {
        this.funktion = funktion;
    }

}