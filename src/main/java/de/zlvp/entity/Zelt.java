package de.zlvp.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Zelt extends AbstractEntity {

    public Zelt(Integer id, String brzNummer, double preis, Date angeschafft, Waehrung waehrung) {
        setId(id);
        this.brzNummer = brzNummer;
        this.angeschafft = angeschafft;
        this.preis = preis;
        this.waehrung = waehrung;
    }

    public Zelt(Zelt z) {
        this(z.getId(), z.getBezeichnung(), z.getPreis(), z.getAngeschafft(), z.getWaehrung());
    }

    private String brzNummer;

    private double preis;

    private Date angeschafft;

    private Waehrung waehrung;

    private List<Zeltdetail> zeltdetail;

    private List<Schaden> schaden;

    private Lager lager;
    private Gruppe gruppe;

    private boolean checked = false;

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public boolean isChecked() {
        return checked;
    }

    public List<Zeltdetail> getZeltdetail() {
        if (zeltdetail == null) {
            zeltdetail = new ArrayList<>();
        }
        return zeltdetail;
    }

    public List<Schaden> getSchaden() {
        if (schaden == null) {
            schaden = new ArrayList<>();
        }
        return schaden;
    }

    @Override
    public String getBezeichnung() {
        return brzNummer;
    }

    public double getPreis() {
        return preis;
    }

    public Date getAngeschafft() {
        return angeschafft;
    }

    public void setPreis(double preis) {
        this.preis = preis;
    }

    public void setAngeschafft(Date angeschafft) {
        this.angeschafft = angeschafft;
    }

    public Waehrung getWaehrung() {
        return waehrung;
    }

    public void setWaehrung(Waehrung waehrung) {
        this.waehrung = waehrung;
    }

    public Lager getLager() {
        return lager;
    }

    public void setLager(Lager lager) {
        this.lager = lager;
    }

    public Gruppe getGruppe() {
        return gruppe;
    }

    public void setGruppe(Gruppe gruppe) {
        this.gruppe = gruppe;
    }

}
