package de.zlvp.dao;

import java.util.List;

import de.zlvp.entity.Geschlecht;
import de.zlvp.entity.Teilnehmer;

public class TeilnehmerDao extends AbstractDao<Teilnehmer> {

    private static final String findAllTeilnehmerFromGruppe = "SELECT p.* FROM Person p INNER JOIN sttegr ON p.PeID = sttegr.Person WHERE sttegr.gruppe = ? order by p.nachname, p.vorname";
    private static final String findTeilnehmer = "SELECT p.* FROM Person p INNER JOIN sttegr ON p.PeID = sttegr.Person WHERE sttegr.sttegrid = ?";

    private static final String insertTeilnehmer = "INSERT INTO stTeGr (gruppe,person) VALUES (?,?)";
    private static final String deleteTeilnehmer = "DELETE FROM stTeGr WHERE gruppe = ? AND person = ?";

    public List<Teilnehmer> getAll(final int gruppe) {
        return select(findAllTeilnehmerFromGruppe, ps -> ps.setInt(1, gruppe),
                rs -> new Teilnehmer(rs.getInt("peid"), Geschlecht.fromDbId(rs.getInt("geschlecht")),
                        rs.getString("vorname"), rs.getString("nachname"), rs.getString("strasse"), rs.getString("plz"),
                        rs.getString("ort"), rs.getDate("gebDat"), rs.getString("handy"), rs.getString("telnr"),
                        rs.getString("email"), rs.getString("nottel")));
    }

    public void speichere(int personId, int gruppeId) {
        insertOrUpdate(insertTeilnehmer, ps -> {
            ps.setInt(1, gruppeId);
            ps.setInt(2, personId);
        });
    }

    public void loeschen(int personId, int gruppeId) {
        jdbc.update(deleteTeilnehmer, gruppeId, personId);
    }

    public Teilnehmer get(int id) {
        return selectOne(findTeilnehmer, ps -> ps.setInt(1, id),
                rs -> new Teilnehmer(rs.getInt("peid"), Geschlecht.fromDbId(rs.getInt("geschlecht")),
                        rs.getString("vorname"), rs.getString("nachname"), rs.getString("strasse"), rs.getString("plz"),
                        rs.getString("ort"), rs.getDate("gebDat"), rs.getString("handy"), rs.getString("telnr"),
                        rs.getString("email"), rs.getString("nottel")));
    }

}
