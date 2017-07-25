package de.zlvp.entity;

import java.util.Date;

public class Legenda extends Person {

    public Legenda() {
        super(null, null, null, null, null, null, null, null, null, null, null);
    }

    public Legenda(Integer id, String vorname, String name, String strasse, String sPLZ, String ort, Date daGebDat,
            String handy, String telNr, String email) {
        super(id, vorname, name, strasse, sPLZ, ort, daGebDat, handy, telNr, email, null);
    }

    private Legendatyp legendaTyp;

    private Lagerort lagerOrt;

    private String firma;

    private String fax;

    private Anrede anrede;

    private String bemerkung;

    @Override
    public String getBezeichnung() {
        return getLegendaTyp() == null ? "Neu" : getLegendaTyp().getBezeichnung();
    }

    public Legendatyp getLegendaTyp() {
        return legendaTyp;
    }

    public void setLegendaTyp(Legendatyp legendaTyp) {
        this.legendaTyp = legendaTyp;
    }

    public Lagerort getLagerOrt() {
        return lagerOrt;
    }

    public void setLagerOrt(Lagerort lagerOrt) {
        this.lagerOrt = lagerOrt;
    }

    public String getFirma() {
        return firma;
    }

    public void setFirma(String firma) {
        this.firma = firma;
    }

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    public Anrede getAnrede() {
        return anrede;
    }

    public void setAnrede(Anrede anrede) {
        this.anrede = anrede;
    }

    public String getBemerkung() {
        return bemerkung;
    }

    public void setBemerkung(String bemerkung) {
        this.bemerkung = bemerkung;
    }

}
