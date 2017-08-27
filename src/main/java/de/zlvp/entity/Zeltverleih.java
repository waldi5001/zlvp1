package de.zlvp.entity;

import java.util.Date;

import de.zlvp.Helferlein;

public class Zeltverleih extends AbstractEntity {

    public Zeltverleih(int id, Date datum, String person, String bemerkung) {
        setId(id);
        this.datum = datum;
        this.person = person;
        this.bemerkung = bemerkung;
    }

    private Date datum;

    private String person;

    private String bemerkung;

    private Zelt zelt;

    public Date getDatum() {
        return datum;
    }

    public void setDatum(Date datum) {
        this.datum = datum;
    }

    public String getPerson() {
        return person;
    }

    public void setPerson(String person) {
        this.person = person;
    }

    public String getBemerkung() {
        return bemerkung;
    }

    public void setBemerkung(String bemerkung) {
        this.bemerkung = bemerkung;
    }

    public Zelt getZelt() {
        return zelt;
    }

    public void setZelt(Zelt zelt) {
        this.zelt = zelt;
    }

    @Override
    public String getBezeichnung() {
        return Helferlein.dateToString(datum);
    }
}
