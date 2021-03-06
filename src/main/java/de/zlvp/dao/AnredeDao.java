package de.zlvp.dao;

import java.util.List;

import de.zlvp.entity.Anrede;

public class AnredeDao extends AbstractDao<Anrede> {

    private static final String findAll = "SELECT anid, anrede FROM anrede ORDER BY anrede";
    private static final String findAnredeFromLegenda = "select an.* from anrede an inner join legenda lg on an.anid = lg.anrede where lg.lgid = ?";
    private static final String insertAnrede = "insert into anrede (anrede) values (?)";

    private RSE<Anrede> rse = rs -> new Anrede(rs.getInt("anid"), rs.getString("anrede"));

    public List<Anrede> getAll() {
        return select(findAll, rse);
    }

    public Anrede getAnredeFromLegenda(int legendaId) {
        return selectOne(findAnredeFromLegenda, ps -> ps.setInt(1, legendaId), rse);
    }

    public void speichereAnrede(String anrede) {
        jdbc.update(insertAnrede, anrede);
    }
}
