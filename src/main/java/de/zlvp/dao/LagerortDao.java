package de.zlvp.dao;

import java.util.List;

import de.zlvp.entity.Lagerort;

public class LagerortDao extends AbstractDao<Lagerort> {

    private static final String findAll = "SELECT la.* FROM lagerort la order by lagerort";
    private static final String findOne = "SELECT la.* FROM lagerort la where la.loid = ?";
    private static final String findFromLager = "select lo.* from lagerort lo inner join stlalo st on lo.loid = st.lagerort where st.lager = ?";
    private static final String insertLagerort = "INSERT INTO lagerort (lagerort) VALUES (?)";

    public List<Lagerort> getAll() {
        return select(findAll, rs -> new Lagerort(rs.getInt("loid"), rs.getString("lagerort")));
    }

    public Lagerort get(int id) {
        return selectOne(findOne, ps -> ps.setInt(1, id),
                rs -> new Lagerort(rs.getInt("loid"), rs.getString("lagerort")));
    }

    public void speichern(String lagerort) {
        insertOrUpdate(insertLagerort, ps -> ps.setString(1, lagerort));
    }

    public Lagerort getFromLager(int lagerId) {
        return selectOne(findFromLager, ps -> ps.setInt(1, lagerId),
                rs -> new Lagerort(rs.getInt("loid"), rs.getString("lagerort")));
    }

}
