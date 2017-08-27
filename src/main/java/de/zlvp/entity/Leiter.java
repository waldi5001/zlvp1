package de.zlvp.entity;

import java.util.Date;

public class Leiter extends Person {

    public Leiter(Integer id, Geschlecht geschlecht, int personId, String vorname, String name, String strasse,
            String sPLZ, String ort, Date daGebDat, String handy, String telNr, String email) {
        super(id, geschlecht, vorname, name, strasse, sPLZ, ort, daGebDat, handy, telNr, email, null);
        setOriginalId(personId);
    }

    public Leiter(Person person) {
        super(null, person.getGeschlecht(), person.getVorname(), person.getName(), person.getStrasse(), person.getPlz(),
                person.getOrt(), person.getGebDat(), person.getHandy(), person.getTelNr(), person.getEmail(),
                person.getNottel());
        setOriginalId(person.getId());
    }

    private Gruppe gruppe;

    public Gruppe getGruppe() {
        return gruppe;
    }

    public void setGruppe(Gruppe gruppe) {
        this.gruppe = gruppe;
    }

}
