package de.zlvp.dao;

import java.util.List;

import de.zlvp.entity.Geschlecht;
import de.zlvp.entity.Leiter;

public class LeiterDao extends AbstractDao<Leiter> {

    private static final String findAllLeiterFromGruppe = "SELECT stGrLe.stGrLe, p.* FROM Person p INNER JOIN stGrLe ON p.PeID = stGrLe.Person WHERE stGrLe.gruppe = ? order by p.nachname, p.vorname";
    private static final String findLeiterById = "SELECT stGrLe.stGrLe, p.* FROM Person p INNER JOIN stGrLe ON p.PeID = stGrLe.Person WHERE stGrLe.stGrLe = ?";

    private static final String insertLeiter = "INSERT INTO stGrLe (gruppe,person) VALUES (?,?);";
    private static final String deleteLeiter = "DELETE FROM stGrLe WHERE gruppe = ? and person = ?";

    public List<Leiter> getAll(int gruppe) {
        return select(findAllLeiterFromGruppe, ps -> ps.setInt(1, gruppe),
                rs -> new Leiter(rs.getInt("stGrLe"), Geschlecht.fromDbId(rs.getInt("geschlecht")), rs.getInt("peid"),
                        rs.getString("vorname"), rs.getString("nachname"), rs.getString("strasse"), rs.getString("plz"),
                        rs.getString("ort"), rs.getDate("gebDat"), rs.getString("handy"), rs.getString("telnr"),
                        rs.getString("email")));
    }

    public Leiter get(int id) {
        return selectOne(findLeiterById, ps -> ps.setInt(1, id),
                rs -> new Leiter(rs.getInt("stGrLe"), Geschlecht.fromDbId(rs.getInt("geschlecht")), rs.getInt("peid"),
                        rs.getString("vorname"), rs.getString("nachname"), rs.getString("strasse"), rs.getString("plz"),
                        rs.getString("ort"), rs.getDate("gebDat"), rs.getString("handy"), rs.getString("telnr"),
                        rs.getString("email")));
    }

    public void speichere(int gruppeId, final int personId) {
        insertOrUpdate(insertLeiter, ps -> {
            ps.setInt(1, gruppeId);
            ps.setInt(2, personId);
        });
    }

    public void loesche(int gruppeId, int personId) {
        delete(deleteLeiter, ps -> {
            ps.setInt(1, gruppeId);
            ps.setInt(2, personId);
        });
    }

}
