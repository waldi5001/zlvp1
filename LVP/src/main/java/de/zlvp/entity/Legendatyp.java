package de.zlvp.entity;

public class Legendatyp extends AbstractEntity {

    private String bezeichnung;

    public Legendatyp(int id, String bezeichnung) {
        setId(id);
        this.bezeichnung = bezeichnung;
    }

    @Override
    public String getBezeichnung() {
        return bezeichnung;
    }
}
