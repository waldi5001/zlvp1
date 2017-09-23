package de.zlvp.entity;

import static de.zlvp.Helferlein.dateToString;

import java.util.Date;

public class Programm extends AbstractEntity {
    private String morgen;
    private String mittag;
    private String abend;
    private Date datum;

    @Override
    public String getBezeichnung() {
        return dateToString(getDatum());
    }

    public Programm(int id, String morgen, String mittag, String abend, Date datum) {
        setId(id);
        this.morgen = morgen;
        this.mittag = mittag;
        this.abend = abend;
        this.datum = datum;
    }

    public String getMorgen() {
        return morgen;
    }

    public String getMittag() {
        return mittag;
    }

    public String getAbend() {
        return abend;
    }

    public Date getDatum() {
        return datum;
    }

    public void setMorgen(String morgen) {
        this.morgen = morgen;
    }

    public void setMittag(String mittag) {
        this.mittag = mittag;
    }

    public void setAbend(String abend) {
        this.abend = abend;
    }

    public void setDatum(Date datum) {
        this.datum = datum;
    }
}
