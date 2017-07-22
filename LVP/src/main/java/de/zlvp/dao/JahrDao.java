package de.zlvp.dao;

import java.util.List;

import de.zlvp.entity.Jahr;

public class JahrDao extends AbstractDao<Jahr> {

    private static final String findAll = "SELECT jaid, jahr FROM Jahr ORDER BY Jahr DESC;";
    private static final String find = "SELECT jaid, jahr FROM Jahr where jahr.jaid = ?";
    private static final String findByBezeichnung = "SELECT jaid, jahr FROM Jahr where jahr.jahr = ?";
    private static final String insert = "insert into Jahr (jahr) values (?)";

    public List<Jahr> getAll() {
        return select(findAll, rs -> new Jahr(rs.getInt("jaid"), rs.getInt("jahr")));
    }

    public Jahr getJahr(int jahrId) {
        return selectOne(find, ps -> ps.setInt(1, jahrId), rs -> new Jahr(rs.getInt("jaid"), rs.getInt("jahr")));
    }

    public Jahr getJahrByBezeichnung(int jahr) {
        return selectOne(findByBezeichnung, ps -> ps.setInt(1, jahr),
                rs -> new Jahr(rs.getInt("jaid"), rs.getInt("jahr")));
    }

    public Jahr insertJahr(int jahr) {
        Integer id = (Integer) insertOrUpdate(insert, ps -> ps.setInt(1, jahr)).get("jaid");
        return getJahr(id);
    }

}
