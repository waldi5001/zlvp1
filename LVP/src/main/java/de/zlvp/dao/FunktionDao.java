package de.zlvp.dao;

import java.util.List;

import de.zlvp.entity.Funktion;

public class FunktionDao extends AbstractDao<Funktion> {

    private static final String findAll = "SELECT fuid,Name FROM Funktion ORDER BY Name;";
    private static final String findFromStabAndLager = "SELECT f.* FROM Funktion f inner join stLaSt st ON f.fuid = st.funktion where st.stlastid = ?";

    public List<Funktion> getAll() {
        return select(findAll, rs -> new Funktion(rs.getInt("fuid"), rs.getString("Name")));
    }

    public Funktion getFromStab(int id) {
        return selectOne(findFromStabAndLager, ps -> {
            ps.setInt(1, id);
        }, rs -> new Funktion(rs.getInt("fuid"), rs.getString("Name")));
    }

}
