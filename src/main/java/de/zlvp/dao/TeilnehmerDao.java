package de.zlvp.dao;

import java.util.List;

import de.zlvp.entity.Geschlecht;
import de.zlvp.entity.Teilnehmer;

public class TeilnehmerDao extends AbstractDao<Teilnehmer> {

    private static final String findAllTeilnehmerFromGruppe = "SELECT sttegr.sttegrid, p.* FROM Person p INNER JOIN sttegr ON p.PeID = sttegr.Person WHERE sttegr.gruppe = ? order by p.nachname, p.vorname";
    private static final String findTeilnehmer = "SELECT sttegr.sttegrid, p.* FROM Person p INNER JOIN sttegr ON p.PeID = sttegr.Person WHERE sttegr.sttegrid = ?";

    private static final String insertTeilnehmer = "INSERT INTO stTeGr (gruppe,person) VALUES (?,?);";
    private static final String deleteTeilnehmer = "DELETE FROM stTeGr WHERE stTeGr.stTeGrId = ?;";

    public List<Teilnehmer> getAll(final int gruppe) {
        return select(findAllTeilnehmerFromGruppe, ps -> ps.setInt(1, gruppe),
                rs -> new Teilnehmer(rs.getInt("sttegrid"), Geschlecht.fromDbId(rs.getInt("geschlecht")), rs.getInt("peid"),
                        rs.getString("vorname"), rs.getString("nachname"), rs.getString("strasse"), rs.getString("plz"),
                        rs.getString("ort"), rs.getDate("gebDat"), rs.getString("handy"), rs.getString("telnr"),
                        rs.getString("email")));
    }

    public void speichere(int gruppeId, int personId) {
        insertOrUpdate(insertTeilnehmer, ps -> {
            ps.setInt(1, gruppeId);
            ps.setInt(2, personId);
        });
    }

    public void loesche(int id) {
        delete(deleteTeilnehmer, ps -> ps.setInt(1, id));
    }

    public Teilnehmer get(int id) {
        return selectOne(findTeilnehmer, ps -> ps.setInt(1, id),
                rs -> new Teilnehmer(rs.getInt("sttegrid"), Geschlecht.fromDbId(rs.getInt("geschlecht")), rs.getInt("peid"),
                        rs.getString("vorname"), rs.getString("nachname"), rs.getString("strasse"), rs.getString("plz"),
                        rs.getString("ort"), rs.getDate("gebDat"), rs.getString("handy"), rs.getString("telnr"),
                        rs.getString("email")));
    }

}
