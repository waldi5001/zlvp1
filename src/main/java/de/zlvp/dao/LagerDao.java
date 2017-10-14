package de.zlvp.dao;

import java.util.Date;
import java.util.List;

import de.zlvp.entity.Lager;

public class LagerDao extends AbstractDao<Lager> {

    private static final String findAllFromJahr = "SELECT l.* FROM LAGER l INNER JOIN stLaJa st ON st.lager = l.laid WHERE st.jahr = ? order by l.name";
    private static final String find = "SELECT l.* FROM LAGER l WHERE l.laid = ?";
    private static final String insertLager = "INSERT INTO LAGER (name, thema, datumstart, datumstop) VALUES (?, ?, ?, ?)";
    private static final String updateLager = "update LAGER set name=?, thema=?, datumstart=?, datumstop=? where laid = ?";
    private static final String insertLagerort = "INSERT INTO stLaLo (lager, lagerort) VALUES (?, ?)";
    private static final String updateLagerort = "update stLaLo set lagerort = ? where lager = ?";
    private static final String insertJahr = "INSERT INTO stLaJa (lager, jahr) VALUES (?, ?)";

    private RSE<Lager> rse = rs -> new Lager(rs.getInt("laid"), rs.getString("name"), rs.getString("thema"),
            new Date(rs.getDate("datumStart").getTime()), new Date(rs.getDate("datumStop").getTime()));

    public List<Lager> getAll(int jahrId) {
        return select(findAllFromJahr, ps -> ps.setInt(1, jahrId), rse);
    }

    public Lager get(int lagerId) {
        return selectOne(find, ps -> ps.setInt(1, lagerId), rse);
    }

    public void speichern(Integer lagerId, String name, String thema, Date start, Date stop, int lagerortId,
            int jahrId) {
        if (lagerId == null) {
            Integer id = (Integer) insertOrUpdate(insertLager, ps -> {
                ps.setString(1, name);
                ps.setString(2, thema);
                ps.setDate(3, new java.sql.Date(start.getTime()));
                ps.setDate(4, new java.sql.Date(stop.getTime()));
            }).get("laid");
            insertOrUpdate(insertLagerort, ps -> {
                ps.setInt(1, id);
                ps.setInt(2, lagerortId);
            });
            insertOrUpdate(insertJahr, ps -> {
                ps.setInt(1, id);
                ps.setInt(2, jahrId);
            });
        } else {
            insertOrUpdate(updateLager, ps -> {
                ps.setString(1, name);
                ps.setString(2, thema);
                ps.setDate(3, new java.sql.Date(start.getTime()));
                ps.setDate(4, new java.sql.Date(stop.getTime()));
                ps.setInt(5, lagerId);
            });
            insertOrUpdate(updateLagerort, ps -> {
                ps.setInt(1, lagerortId);
                ps.setInt(2, lagerId);
            });
        }
    }

    public void aendern(Integer lagerId, String name, String thema, Date start, Date stop) {
        jdbc.update(updateLager, name, thema, new java.sql.Date(start.getTime()), new java.sql.Date(stop.getTime()),
                lagerId);
    }
}
