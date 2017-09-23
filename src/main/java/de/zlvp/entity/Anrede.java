package de.zlvp.entity;

public class Anrede extends AbstractEntity {
    private String bezeichnung;

    public Anrede(int id, String bezeichnung) {
        setId(id);
        this.bezeichnung = bezeichnung;
    }

    @Override
    public String getBezeichnung() {
        return bezeichnung;
    }

}
