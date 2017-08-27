package de.zlvp.entity;

public enum Funktion {
    REMOVE("", 0), Fahrer(1), Lagerleiter(2), Programm(3), Küche(4), Gruppenleiter(5);

    private final Integer dbId;
    private final String bezeichnung;

    private Funktion(String bezeichnung, Integer dbId) {
        this.dbId = dbId;
        this.bezeichnung = bezeichnung;
    }

    private Funktion(Integer dbId) {
        this.dbId = dbId;
        this.bezeichnung = name();
    }

    public int getDbId() {
        return dbId;
    }

    public static Funktion fromDbId(int dbId) {
        for (Funktion funktion : values()) {
            if (funktion.dbId.equals(dbId)) {
                return funktion;
            }
        }
        throw new RuntimeException("Funktion ID unbekannt");
    }

    public String getBezeichnung() {
        return bezeichnung;
    }
}
