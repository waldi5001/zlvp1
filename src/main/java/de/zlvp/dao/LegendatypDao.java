package de.zlvp.dao;

import java.util.List;

import de.zlvp.entity.Legendatyp;

public class LegendatypDao extends AbstractDao<Legendatyp> {

    private static final String findAll = "select lgt.* from legendatyp lgt order by lgt.typ";
    private static final String findFromLegenda = "select lt.* from legendatyp lt inner join legenda lg on lt.tyid = lg.typ where lg.lgid = ?";

    public List<Legendatyp> getAll() {
        return select(findAll, rs -> new Legendatyp(rs.getInt("tyid"), rs.getString("typ")));
    }

    public Legendatyp getFromLegenda(int legendaId) {
        return selectOne(findFromLegenda, ps -> ps.setInt(1, legendaId),
                rs -> new Legendatyp(rs.getInt("tyid"), rs.getString("typ")));
    }

}
