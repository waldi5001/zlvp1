package de.zlvp.entity;

public enum Geschlecht {
    Maennlich("Herr", 1), Weiblich("Frau", 2), Divers("Divers", 3);

    private final String display;
    private final int dbId;

    private Geschlecht(String display, int dbId) {
        this.display = display;
        this.dbId = dbId;
    }

    public static Geschlecht fromDbId(int dbId) {
        for (Geschlecht geschlecht : values()) {
            if (geschlecht.dbId == dbId) {
                return geschlecht;
            }
        }
        throw new RuntimeException("Geschlecht ID unbekannt " + dbId);
    }

    public String getBezeichnung() {
        return display;
    }

    public int getDbId() {
        return dbId;
    }
}
