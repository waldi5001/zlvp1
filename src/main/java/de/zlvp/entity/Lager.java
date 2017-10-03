package de.zlvp.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Lager extends AbstractEntity {

    public Lager(int id, String name, String thema, Date datumStart, Date datumStop) {
        setId(id);
        this.name = name;
        this.thema = thema;
        this.datumStart = datumStart;
        this.datumStop = datumStop;
    }

    @Override
    public String getBezeichnung() {
        return getName();
    }

    private String name;
    private String thema;
    private Date datumStart;
    private Date datumStop;

    private Lagerort lagerort;
    private Jahr jahr;

    private List<Gruppe> gruppe;
    private List<Zelt> zelt;
    private List<Materialwart> materialwart;

    public List<Gruppe> getGruppe() {
        if (gruppe == null) {
            gruppe = new ArrayList<>();
        }
        return gruppe;
    }

    public List<Zelt> getZelt() {
        if (zelt == null) {
            zelt = new ArrayList<>();
        }
        return zelt;
    }

    public List<Materialwart> getMaterialwart() {
        if (materialwart == null) {
            materialwart = new ArrayList<>();
        }
        return materialwart;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getThema() {
        return thema;
    }

    public void setThema(String thema) {
        this.thema = thema;
    }

    public Date getDatumStart() {
        return datumStart;
    }

    public void setDatumStart(Date datumStart) {
        this.datumStart = datumStart;
    }

    public Date getDatumStop() {
        return datumStop;
    }

    public void setDatumStop(Date datumStop) {
        this.datumStop = datumStop;
    }

    public Lagerort getLagerort() {
        return lagerort;
    }

    public void setLagerort(Lagerort lagerort) {
        this.lagerort = lagerort;
    }

    public Jahr getJahr() {
        return jahr;
    }

    public void setJahr(Jahr jahr) {
        this.jahr = jahr;
    }

}
