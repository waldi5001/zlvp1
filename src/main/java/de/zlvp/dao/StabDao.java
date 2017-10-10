package de.zlvp.dao;

import java.util.List;

import de.zlvp.entity.Funktion;
import de.zlvp.entity.Geschlecht;
import de.zlvp.entity.Stab;

public class StabDao extends AbstractDao<Stab> {

    private static final String allStabFromLager = "SELECT stLaSt.funktion, p.* FROM Person p INNER JOIN stLaSt ON p.PeID = stLaSt.Person INNER JOIN Lager l ON l.LaID = stLaSt.Lager WHERE l.LaID = ?";
    private static final String selectOne = "SELECT stlastid FROM stLaSt where lager = ? and person = ?";

    private static final String updateFunktion = "UPDATE stLaSt SET Funktion = ? WHERE lager = ? AND person = ?";
    private static final String insertStab = "INSERT INTO stLaSt (lager, person, funktion) VALUES (?,?,?)";
    private static final String deleteStab = "DELETE FROM stLaSt WHERE lager = ? AND person = ?";

    public List<Stab> getAll(int lagerId) {
        return select(allStabFromLager, ps -> ps.setInt(1, lagerId),
                rs -> new Stab(rs.getInt("peid"), Geschlecht.fromDbId(rs.getInt("geschlecht")),
                        Funktion.fromDbId(rs.getInt("funktion")), rs.getString("vorname"), rs.getString("nachname"),
                        rs.getString("strasse"), rs.getString("plz"), rs.getString("ort"), rs.getDate("gebDat"),
                        rs.getString("handy"), rs.getString("telnr"), rs.getString("email"), rs.getString("nottel")));
    }

    public void speichern(int lagerId, int personId, Funktion funktion) {
        List<Integer> ids = jdbc.queryForList(selectOne, Integer.class, lagerId, personId);
        if (ids.isEmpty()) {
            insertOrUpdate(insertStab, ps -> {
                ps.setInt(1, lagerId);
                ps.setInt(2, personId);
                ps.setInt(3, funktion.getDbId());
            });
        } else {
            insertOrUpdate(updateFunktion, ps -> {
                ps.setInt(1, funktion.getDbId());
                ps.setInt(2, lagerId);
                ps.setInt(3, personId);
            });
        }
    }

    public void loeschen(int lagerId, int personId) {
        jdbc.update(deleteStab, lagerId, personId);
    }

}
