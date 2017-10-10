package de.zlvp.dao;

import java.util.List;

import de.zlvp.entity.Legendatyp;

public class LegendatypDao extends AbstractDao<Legendatyp> {

    private static final String findAll = "select lgt.* from legendatyp lgt order by lgt.typ";
    private static final String findFromLegenda = "select lt.* from legendatyp lt inner join legenda lg on lt.tyid = lg.typ where lg.lgid = ?";
    private static final String insertLegendatyp = "insert into legendatyp (typ) values (?)";

    private RSE<Legendatyp> rse = rs -> new Legendatyp(rs.getInt("tyid"), rs.getString("typ"));

    public List<Legendatyp> getAll() {
        return select(findAll, rse);
    }

    public Legendatyp getFromLegenda(int legendaId) {
        return selectOne(findFromLegenda, ps -> ps.setInt(1, legendaId), rse);
    }

    public void speichern(String legendatyp) {
        jdbc.update(insertLegendatyp, legendatyp);
    }

}
