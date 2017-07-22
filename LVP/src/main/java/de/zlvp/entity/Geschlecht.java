package de.zlvp.entity;

public class Geschlecht extends AbstractEntity {
    private String bezeichnung;

    public Geschlecht(int id, String bezeichnung) {
        setId(id);
        this.bezeichnung = bezeichnung;
    }

    @Override
    public String getBezeichnung() {
        return bezeichnung;
    }

}
