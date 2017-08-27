package de.zlvp.entity;

import java.util.Date;

public class Lagerinfo extends Person {

    public Lagerinfo(Integer id, Geschlecht geschlecht, int personId, String vorname, String name, String strasse,
            String sPLZ, String ort, Date daGebDat, String handy, String telNr, String email) {
        super(id, geschlecht, vorname, name, strasse, sPLZ, ort, daGebDat, handy, telNr, email, null);
        setOriginalId(personId);
    }

    public Lagerinfo(Person p) {
        super(null, p.getGeschlecht(), p.getVorname(), p.getName(), p.getStrasse(), p.getPlz(), p.getOrt(),
                p.getGebDat(), p.getHandy(), p.getTelNr(), p.getEmail(), p.getNottel());
        setOriginalId(p.getId());
    }

    private boolean checked = false;

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public boolean isChecked() {
        return checked;
    }

}
