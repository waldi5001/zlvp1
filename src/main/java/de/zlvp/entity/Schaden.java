package de.zlvp.entity;

import static de.zlvp.Helferlein.dateToString;
import static java.lang.String.format;

import java.util.Date;

public class Schaden extends AbstractEntity {

    public Schaden(Integer id, Date datum, String schaden) {
        this.datum = datum;
        this.schaden = schaden;
        setId(id);
    }

    private Zelt zelt;
    private Date datum;
    private String schaden;

    public Date getDatum() {
        return datum;
    }

    public String getSchaden() {
        return schaden;
    }

    public Zelt getZelt() {
        return zelt;
    }

    public void setZelt(Zelt zelt) {
        this.zelt = zelt;
    }

    public void setDatum(Date datum) {
        this.datum = datum;
    }

    public void setSchaden(String schaden) {
        this.schaden = schaden;
    }

    @Override
    public String getBezeichnung() {
        return format("%s: %s", dateToString(getDatum()), getSchaden());
    }
}
