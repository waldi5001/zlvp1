package de.zlvp.dao;

import java.util.Date;
import java.util.List;

import de.zlvp.entity.Programm;

public class ProgrammDao extends AbstractDao<Programm> {

    private static final String allProgrammFromLager = "SELECT p.* FROM programm p WHERE p.lager = ? ORDER BY p.Datum";
    private static final String insertProgramm = "INSERT INTO programm (Lager, Datum, vormittag, nachmittag, nacht) VALUES (?,?,?,?,?)";
    private static final String updateProgramm = "UPDATE programm SET vormittag = ?, nachmittag = ?, nacht= ?, datum = ? WHERE prid = ?";
    private static final String deleteProgramm = "delete from programm WHERE prid = ?";

    public List<Programm> getAllFromLager(final int lagerId) {
        return select(allProgrammFromLager, ps -> ps.setInt(1, lagerId), rs -> new Programm(rs.getInt("prid"),
                rs.getString("vormittag"), rs.getString("nachmittag"), rs.getString("nacht"), rs.getDate("datum")));
    }

    public void speichereProgramm(int lagerID, final Date datum, final String morgen, final String mittag,
            final String abend) {
        insertOrUpdate(insertProgramm, ps -> {
            ps.setInt(1, lagerID);
            ps.setDate(2, new java.sql.Date(datum.getTime()));
            ps.setString(3, morgen);
            ps.setString(4, mittag);
            ps.setString(5, abend);
        });
    }

    public void aendereProgramm(int programmId, final String morgen, final String mittag, final String abend,
            final Date datum) {
        insertOrUpdate(updateProgramm, ps -> {
            ps.setString(1, morgen);
            ps.setString(2, mittag);
            ps.setString(3, abend);
            ps.setDate(4, new java.sql.Date(datum.getTime()));
            ps.setInt(5, programmId);
        });
    }

    public void loescheProgramm(int programmId) {
        insertOrUpdate(deleteProgramm, ps -> {
            ps.setInt(1, programmId);
        });
    }

}
