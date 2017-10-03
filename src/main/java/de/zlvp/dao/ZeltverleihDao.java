package de.zlvp.dao;

import java.util.Date;
import java.util.List;

import de.zlvp.entity.Zeltverleih;

public class ZeltverleihDao extends AbstractDao<Zeltverleih> {

    private static final String findAllFromZelt = "select zv.* from zverleih zv where zv.ze = ? order by datum";
    private static final String insertVerleih = "insert into zverleih (ze,datum,person,bemerkung) values (?,?,?,?)";
    private static final String updateVerleih = "update zverleih set datum = ?, person = ?, bemerkung = ? where zvid = ?";
    private static final String deleteVerleih = "delete from zverleih where zvid = ?";

    public List<Zeltverleih> getAllFromZelt(int zeltId) {
        return select(findAllFromZelt, ps -> ps.setInt(1, zeltId), rs -> {
            return new Zeltverleih(rs.getInt("zvid"), rs.getDate("datum"), rs.getString("person"),
                    rs.getString("bemerkung"));
        });
    }

    public void speichern(Integer id, int zeltId, Date datum, String person, String bemerkung) {
        if (id == null) {
            insertOrUpdate(insertVerleih, ps -> {
                ps.setInt(1, zeltId);
                ps.setDate(2, new java.sql.Date(datum.getTime()));
                ps.setString(3, person);
                ps.setString(4, bemerkung);
            });
        } else {
            insertOrUpdate(updateVerleih, ps -> {
                ps.setDate(1, new java.sql.Date(datum.getTime()));
                ps.setString(2, person);
                ps.setString(3, bemerkung);
                ps.setInt(4, id);
            });
        }
    }

    public void loeschen(int id) {
        jdbc.update(deleteVerleih, id);
    }

}
