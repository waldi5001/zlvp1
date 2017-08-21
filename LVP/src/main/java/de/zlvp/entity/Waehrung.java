package de.zlvp.entity;

public enum Waehrung {
    EUR("€", 1), DM("DM", 2), CHF("CHF", 3);

    private final String display;
    private final int dbId;

    private Waehrung(String display, int dbId) {
        this.display = display;
        this.dbId = dbId;
    }

    public String getBezeichnung() {
        return display;
    }

    public int getDbId() {
        return dbId;
    }

    public static Waehrung fromDbId(int dbId) {
        for (Waehrung waehrung : values()) {
            if (waehrung.dbId == dbId) {
                return waehrung;
            }
        }
        throw new RuntimeException("Waehrung ID unbekannt " + dbId);
    }
}
