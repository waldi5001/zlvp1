package de.zlvp.dao;

import java.util.List;

import de.zlvp.entity.Geschlecht;
import de.zlvp.entity.Materialwart;

public class MaterialwartDao extends AbstractDao<Materialwart> {

    private static final String findAll = "select p.* from person p inner join stlama st on p.peid = st.pe where st.la = ?";
    private static final String delete = "delete from stlama where la = ? and pe = ?";
    private static final String insert = "insert into stlama (la,pe) values (?,?)";

    public List<Materialwart> getAll(int lagerId) {
        return select(findAll, ps -> ps.setInt(1, lagerId),
                rs -> new Materialwart(rs.getInt("peid"), Geschlecht.fromDbId(rs.getInt("geschlecht")),
                        rs.getString("vorname"), rs.getString("nachname"), rs.getString("strasse"), rs.getString("plz"),
                        rs.getString("ort"), rs.getDate("gebDat"), rs.getString("handy"), rs.getString("telnr"),
                        rs.getString("email"), rs.getString("nottel")));
    }

    public void speichern(Integer lagerId, int personId) {
        insertOrUpdate(insert, ps -> {
            ps.setInt(1, lagerId);
            ps.setInt(2, personId);
        });
    }

    public void loeschen(int lagerId, int personid) {
        delete(delete, ps -> {
            ps.setInt(1, lagerId);
            ps.setInt(2, personid);
        });
    }

}
