package de.zlvp.entity;

public class Funktion extends AbstractEntity {

    public Funktion(Integer id, String bezeichnung) {
        setId(id);
        this.bezeichnung = bezeichnung;
    }

    private String bezeichnung;

    @Override
    public String getBezeichnung() {
        return bezeichnung;
    }

    public void setBezeichnung(String bezeichnung) {
        this.bezeichnung = bezeichnung;
    }

}
