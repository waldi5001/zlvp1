package de.zlvp.entity;

public class ZeltdetailBezeichnung extends AbstractEntity {

    private String bezeichnung;

    public ZeltdetailBezeichnung(int id, String bezeichnung) {
        setId(id);
        this.bezeichnung = bezeichnung;
        this.bezeichnung = this.bezeichnung != null ? this.bezeichnung : "";
    }

    @Override
    public String getBezeichnung() {
        return bezeichnung;
    }

}
