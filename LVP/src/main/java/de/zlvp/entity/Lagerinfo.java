package de.zlvp.entity;

import java.util.Date;

public class Lagerinfo extends Person {

    public Lagerinfo(Integer id, int personId, String vorname, String name, String strasse, String sPLZ, String ort,
            Date daGebDat, String handy, String telNr, String email) {
        super(id, vorname, name, strasse, sPLZ, ort, daGebDat, handy, telNr, email);
        setOriginalId(personId);
    }

    public Lagerinfo(Person p) {
        super(null, p.getVorname(), p.getName(), p.getStrasse(), p.getPlz(), p.getOrt(), p.getGebDat(), p.getHandy(),
                p.getTelNr(), p.getEmail());
        setOriginalId(p.getId());
        setGeschlecht(p.getGeschlecht());
    }

    private boolean checked = false;

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public boolean isChecked() {
        return checked;
    }

}
