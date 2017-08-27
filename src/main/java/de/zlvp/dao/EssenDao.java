package de.zlvp.dao;

import java.util.Date;
import java.util.List;

import de.zlvp.entity.Essen;

public class EssenDao extends AbstractDao<Essen> {

    private static final String allEssenFromLager = "SELECT e.* FROM Essen e WHERE lager = ? ORDER BY Datum";
    private static final String insertEssen = "INSERT INTO Essen (Lager, Datum, morgen, mittag, abend) VALUES (?,?,?,?,?)";
    private static final String updateEssen = "UPDATE Essen SET morgen = ?, mittag = ?, abend = ?, datum = ? WHERE EsID = ?";
    private static final String deleteEssen = "delete from Essen WHERE EsID = ?";

    public List<Essen> getAllFromLager(final int lagerId) {
        return select(allEssenFromLager, ps -> ps.setInt(1, lagerId), rs -> new Essen(rs.getInt("esid"),
                rs.getString("morgen"), rs.getString("mittag"), rs.getString("abend"), rs.getDate("datum")));
    }

    public void speichereEssen(int lagerID, final Date datum, final String morgen, final String mittag,
            final String abend) {
        insertOrUpdate(insertEssen, ps -> {
            ps.setInt(1, lagerID);
            ps.setDate(2, new java.sql.Date(datum.getTime()));
            ps.setString(3, morgen);
            ps.setString(4, mittag);
            ps.setString(5, abend);
        });
    }

    public void aendereEssen(int essenID, final String morgen, final String mittag, final String abend,
            final Date datum) {
        insertOrUpdate(updateEssen, ps -> {
            ps.setString(1, morgen);
            ps.setString(2, mittag);
            ps.setString(3, abend);
            ps.setDate(4, new java.sql.Date(datum.getTime()));
            ps.setInt(5, essenID);
        });
    }

    public void loescheEssen(int essenID) {
        insertOrUpdate(deleteEssen, ps -> {
            ps.setInt(1, essenID);
        });
    }

}
