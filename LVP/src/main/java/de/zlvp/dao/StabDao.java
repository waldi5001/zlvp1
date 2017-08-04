package de.zlvp.dao;

import java.util.List;

import de.zlvp.entity.Funktion;
import de.zlvp.entity.Geschlecht;
import de.zlvp.entity.Stab;

public class StabDao extends AbstractDao<Stab> {

    private static final String allStabFromLager = "SELECT stLaSt.stlastid, stLaSt.funktion, p.* FROM Person p INNER JOIN stLaSt ON p.PeID = stLaSt.Person INNER JOIN Lager l ON l.LaID = stLaSt.Lager WHERE l.LaID = ?";

    private static final String updateFunktion = "UPDATE stLaSt SET Funktion = ? WHERE stlastid = ?;";
    private static final String insertStab = "INSERT INTO stLaSt (lager, person, funktion) VALUES (?,?,?);";
    private static final String deleteStab = "DELETE FROM stLaSt WHERE stLaSt.stLaStId = ?;";

    public List<Stab> getAll(int lagerId) {
        return select(allStabFromLager, ps -> ps.setInt(1, lagerId),
                rs -> new Stab(rs.getInt("stlastid"), Geschlecht.fromDbId(rs.getInt("geschlecht")),
                        Funktion.fromDbId(rs.getInt("funktion")), rs.getInt("peid"), rs.getString("vorname"),
                        rs.getString("nachname"), rs.getString("strasse"), rs.getString("plz"), rs.getString("ort"),
                        rs.getDate("gebDat"), rs.getString("handy"), rs.getString("telnr"), rs.getString("email")));
    }

    public void speichern(Integer id, int lagerId, int personId, Funktion funktion) {
        if (id == null) {
            insertOrUpdate(insertStab, ps -> {
                ps.setInt(1, lagerId);
                ps.setInt(2, personId);
                ps.setInt(3, funktion.getDbId());
            });
        } else {
            insertOrUpdate(updateFunktion, ps -> {
                ps.setInt(1, funktion.getDbId());
                ps.setInt(2, id);
            });
        }
    }

    public void loeschen(int id) {
        delete(deleteStab, ps -> ps.setInt(1, id));
    }

}
