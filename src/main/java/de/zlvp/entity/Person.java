package de.zlvp.entity;

import static java.lang.String.format;

import java.util.Date;

public class Person extends AbstractEntity {

    public Person(Integer id, Geschlecht geschlecht, String vorname, String name, String strasse, String sPLZ,
            String ort, Date daGebDat, String handy, String telNr, String email, String nottel) {
        this.vorname = vorname;
        this.strasse = strasse;
        this.plz = sPLZ;
        this.ort = ort;
        this.name = name;
        this.telNr = telNr;
        this.email = email;
        this.gebDat = daGebDat;
        this.handy = handy;
        this.nottel = nottel;
        this.geschlecht = geschlecht;
        setId(id);
    }

    @Override
    public String getBezeichnung() {
        return format("%s, %s", getName(), getVorname());
    }

    private String vorname;
    private String strasse;
    private String plz;
    private String ort;
    private String name;
    private String telNr;
    private String email;

    private Date gebDat;
    private Geschlecht geschlecht;
    private String handy;
    private String nottel;
    private boolean checked = false;

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public boolean isChecked() {
        return checked;
    }

    public String getVorname() {
        return vorname;
    }

    public void setVorname(String vorname) {
        this.vorname = vorname;
    }

    public String getStrasse() {
        return strasse;
    }

    public void setStrasse(String strasse) {
        this.strasse = strasse;
    }

    public String getPlz() {
        return plz;
    }

    public void setPlz(String plz) {
        this.plz = plz;
    }

    public String getOrt() {
        return ort;
    }

    public void setOrt(String ort) {
        this.ort = ort;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTelNr() {
        return telNr;
    }

    public void setTelNr(String telNr) {
        this.telNr = telNr;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getGebDat() {
        return gebDat;
    }

    public void setGebDat(Date gebDat) {
        this.gebDat = gebDat;
    }

    public Geschlecht getGeschlecht() {
        return geschlecht;
    }

    public String getHandy() {
        return handy;
    }

    public void setHandy(String handy) {
        this.handy = handy;
    }

    public String getNottel() {
        return nottel;
    }

    public void setNottel(String nottel) {
        this.nottel = nottel;
    }

    @Override
    public String toString() {
        return getBezeichnung();
    }

}
