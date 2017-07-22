package de.zlvp.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Zelt extends AbstractEntity {

    public Zelt(Integer id, Integer zeltId, String brzNummer, double preis, Date angeschafft, String waehrung) {
        setId(id);
        setOriginalId(zeltId);
        this.brzNummer = brzNummer;
        this.angeschafft = angeschafft;
        this.preis = preis;
        this.waehrung = waehrung;
    }

    public Zelt(Zelt z) {
        this(z.getId(), z.getOriginalId(), z.getBezeichnung(), z.getPreis(), z.getAngeschafft(), z.getWaehrung());
    }

    private String brzNummer;

    private double preis;

    private Date angeschafft;

    private String waehrung;

    private List<Zeltdetail> zeltdetail;

    private List<Schaden> schaden;

    private Set<Lager> lager;
    private Set<Gruppe> gruppe;

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

    public Set<Lager> getLager() {
        if (lager == null) {
            lager = new HashSet<>();
        }
        return lager;
    }

    public Set<Gruppe> getGruppe() {
        if (gruppe == null) {
            gruppe = new HashSet<>();
        }
        return gruppe;
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

    public String getWaehrung() {
        return waehrung;
    }

}
