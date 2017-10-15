package de.zlvp.dao;

import java.util.List;

import de.zlvp.entity.Zeltdetail;

public class ZeltdetailDao extends AbstractDao<Zeltdetail> {

    private static final String findAllFromZelt = "select zd.* from zeltdetail zd where zd.zelt = ? order by schluessel";
    private static final String insert = "insert into zeltdetail (anzahl,zelt,bezeichnung,schluessel) values (?,?,?,?)";
    private static final String update = "update zeltdetail set anzahl = ?, zelt = ?, bezeichnung = ?, schluessel = ? where zdid = ?";
    private static final String delete = "delete from zeltdetail where zdid = ?";

    public List<Zeltdetail> getAllFromZelt(int zeltId) {
        return select(findAllFromZelt, ps -> {
            ps.setInt(1, zeltId);
        }, rs -> new Zeltdetail(rs.getInt("zdid"), rs.getInt("anzahl"), rs.getString("schluessel")));
    }

    public void speichern(Integer id, int zeltId, int anzahl, int bezeichnung, String schluessel) {
        if (id == null) {
            insertOrUpdate(insert, ps -> {
                ps.setInt(1, anzahl);
                ps.setInt(2, zeltId);
                ps.setInt(3, bezeichnung);
                ps.setString(4, schluessel);
            });
        } else {
            insertOrUpdate(update, ps -> {
                ps.setInt(1, anzahl);
                ps.setInt(2, zeltId);
                ps.setInt(3, bezeichnung);
                ps.setString(4, schluessel);
                ps.setInt(5, id);
            });
        }
    }

    public void loeschen(int id) {
        jdbc.update(delete, id);
    }
}
