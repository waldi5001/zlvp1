package de.zlvp.dao;

import java.util.Date;
import java.util.List;

import de.zlvp.entity.Schaden;

public class SchadenDao extends AbstractDao<Schaden> {

    private static final String findAllFromZelt = "select s.* from schaeden s where s.zeid = ? order by datum";
    private static final String insertSchaden = "insert into schaeden (zeid,datum,bezeichnung) values (?,?,?)";
    private static final String updateSchaden = "update schaeden set datum = ?, bezeichnung = ? where scid = ?";
    private static final String deleteSchaden = "delete from schaeden where scid = ?";

    public List<Schaden> getAllFromZelt(int zeltId) {
        return select(findAllFromZelt, ps -> ps.setInt(1, zeltId),
                rs -> new Schaden(rs.getInt("scid"), rs.getDate("datum"), rs.getString("bezeichnung")));
    }

    public void speichern(Integer id, int zeltId, Date datum, String schaden) {
        if (id == null) {
            insertOrUpdate(insertSchaden, ps -> {
                ps.setInt(1, zeltId);
                ps.setDate(2, new java.sql.Date(datum.getTime()));
                ps.setString(3, schaden);
            });
        } else {
            insertOrUpdate(updateSchaden, ps -> {
                ps.setDate(1, new java.sql.Date(datum.getTime()));
                ps.setString(2, schaden);
                ps.setInt(3, id);
            });
        }
    }

    public void loeschen(Integer id) {
        delete(deleteSchaden, ps -> ps.setInt(1, id));
    }

}
