package de.zlvp.dao;

import java.util.Date;
import java.util.List;

import de.zlvp.entity.Geschlecht;
import de.zlvp.entity.Materialwart;

public class MaterialwartDao extends AbstractDao<Materialwart> {

    private static final String findAll = "select p.* from person p inner join stlama st on p.peid = st.person_id where st.lager_id = ?";
    private static final String delete = "delete from stlama where lager_id = ? and person_id = ?";
    private static final String insert = "insert into stlama (lager_id,person_id) values (?,?)";

    public List<Materialwart> getAll(int lagerId) {
        return select(findAll, ps -> ps.setInt(1, lagerId),
                rs -> new Materialwart(rs.getInt("peid"), Geschlecht.fromDbId(rs.getInt("geschlecht")),
                        rs.getString("vorname"), rs.getString("nachname"), rs.getString("strasse"), rs.getString("plz"),
                        rs.getString("ort"), new Date(rs.getDate("gebDat").getTime()), rs.getString("handy"),
                        rs.getString("telnr"), rs.getString("email"), rs.getString("nottel")));
    }

    public void speichern(Integer lagerId, int personId) {
        insertOrUpdate(insert, ps -> {
            ps.setInt(1, lagerId);
            ps.setInt(2, personId);
        });
    }

    public void loeschen(int lagerId, int personid) {
        jdbc.update(delete, lagerId, personid);
    }

}
