package de.zlvp.entity;

import java.util.ArrayList;
import java.util.List;

public class Jahr extends AbstractEntity {

    @Override
    public String getBezeichnung() {
        return String.valueOf(getJahr());
    }

    public Jahr(int id, int jahr) {
        setId(id);
        this.jahr = jahr;
    }

    private List<Lager> lager;

    public List<Lager> getLager() {
        if (lager == null) {
            lager = new ArrayList<>();
        }
        return lager;
    }

    private int jahr;

    public int getJahr() {
        return jahr;
    }

    public void setJahr(int jahr) {
        this.jahr = jahr;
    }

}
