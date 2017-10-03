package de.zlvp.entity;

public class Lagerort extends AbstractEntity {
    private String name;

    @Override
    public String getBezeichnung() {
        return getName();
    }

    public Lagerort(int id, String name) {
        setId(id);
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
