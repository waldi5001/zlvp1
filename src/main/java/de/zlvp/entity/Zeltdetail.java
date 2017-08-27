package de.zlvp.entity;

public class Zeltdetail extends AbstractEntity {

    private String brzNummer;

    private int anzahl;

    private ZeltdetailBezeichnung zeltdetailbezeichnung;

    public Zeltdetail(int id, int anzahl, String brzNummer) {
        setId(id);
        this.brzNummer = brzNummer;
        this.anzahl = anzahl;
    }

    @Override
    public String getBezeichnung() {
        return getBrzNummer();
    }

    public void setBrzNummer(String brzNummer) {
        this.brzNummer = brzNummer;
    }

    public String getBrzNummer() {
        return brzNummer;
    }

    public ZeltdetailBezeichnung getZeltdetailbezeichnung() {
        return zeltdetailbezeichnung;
    }

    public void setZeltdetailbezeichnung(ZeltdetailBezeichnung zeltdetailbezeichnung) {
        this.zeltdetailbezeichnung = zeltdetailbezeichnung;
    }

    public int getAnzahl() {
        return anzahl;
    }

    public void setAnzahl(int anzahl) {
        this.anzahl = anzahl;
    }

}
