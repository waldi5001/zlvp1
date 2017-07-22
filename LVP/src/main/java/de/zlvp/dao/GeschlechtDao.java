package de.zlvp.dao;

import java.util.List;

import de.zlvp.entity.Geschlecht;

public class GeschlechtDao extends AbstractDao<Geschlecht> {

    private static final String findAll = "SELECT geid, name FROM geschlecht ORDER BY name";
    private static final String findFromPerson = "SELECT g.geid, g.name FROM person p inner join geschlecht g on p.geschlecht = g.geid where p.peid = ?";

    public List<Geschlecht> getAll() {
        return select(findAll, rs -> new Geschlecht(rs.getInt("geid"), rs.getString("name")));
    }

    public Geschlecht getFromPerson(int personId) {
        return selectOne(findFromPerson, ps -> ps.setInt(1, personId),
                rs -> new Geschlecht(rs.getInt("geid"), rs.getString("name")));
    }

}
