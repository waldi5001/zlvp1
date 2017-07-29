package de.zlvp.dao;

import java.util.List;

import de.zlvp.entity.Geschlecht;
import de.zlvp.entity.Stab;

public class StabDao extends AbstractDao<Stab> {

    private static final String allStabFromLager = "SELECT stLaSt.stlastid, p.* FROM Person p INNER JOIN stLaSt ON p.PeID = stLaSt.Person INNER JOIN Lager l ON l.LaID = stLaSt.Lager WHERE l.LaID = ?;";
    private static final String stabById = "SELECT stLaSt.stlastid, p.* FROM Person p INNER JOIN stLaSt ON p.PeID = stLaSt.Person WHERE stLaSt.stLaStId = ?;";

    private static final String updateFunktion = "UPDATE stLaSt SET Funktion = ? WHERE stlastid = ?;";
    private static final String insertStab = "INSERT INTO stLaSt (lager,person) VALUES (?,?);";
    private static final String deleteStab = "DELETE FROM stLaSt WHERE stLaSt.stLaStId = ?;";

    public List<Stab> getAll(final int lagerId) {
        return select(allStabFromLager, ps -> ps.setInt(1, lagerId),
                rs -> new Stab(rs.getInt("stlastid"), Geschlecht.fromDbId(rs.getInt("geschlecht")), rs.getInt("peid"),
                        rs.getString("vorname"), rs.getString("nachname"), rs.getString("strasse"), rs.getString("plz"),
                        rs.getString("ort"), rs.getDate("gebDat"), rs.getString("handy"), rs.getString("telnr"),
                        rs.getString("email")));
    }

    public void speichereStaabFunktion(int laStID, int funktionId) {
        insertOrUpdate(updateFunktion, ps -> {
            ps.setInt(1, funktionId);
            ps.setInt(2, laStID);
        });
    }

    public Stab speichern(final int lagerId, final int personId) {
        final Integer id = (Integer) insertOrUpdate(insertStab, ps -> {
            ps.setInt(1, lagerId);
            ps.setInt(2, personId);
        }).get("stlastid");

        Stab s = selectOne(stabById, ps -> ps.setInt(1, id),
                rs -> new Stab(rs.getInt("stlastid"), Geschlecht.fromDbId(rs.getInt("geschlecht")), rs.getInt("peid"),
                        rs.getString("vorname"), rs.getString("nachname"), rs.getString("strasse"), rs.getString("plz"),
                        rs.getString("ort"), rs.getDate("gebDat"), rs.getString("handy"), rs.getString("telnr"),
                        rs.getString("email")));

        return s;
    }

    public void loeschen(int id) {
        delete(deleteStab, ps -> ps.setInt(1, id));
    }

}
