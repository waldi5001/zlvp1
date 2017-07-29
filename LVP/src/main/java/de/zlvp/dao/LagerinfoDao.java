package de.zlvp.dao;

import java.util.List;

import de.zlvp.entity.Geschlecht;
import de.zlvp.entity.Lagerinfo;

public class LagerinfoDao extends AbstractDao<Lagerinfo> {

    private static final String findAll = "select li.liid, p.* from person p inner join lagerinfo li on li.person = p.peid order by p.nachname, p.vorname";
    private static final String insertLagerinfo = "insert into lagerinfo (person) values (?)";
    private static final String deleteLagerinfo = "delete from lagerinfo where liid = ?";

    public List<Lagerinfo> getAll() {
        return select(findAll,
                rs -> new Lagerinfo(rs.getInt("liid"), Geschlecht.fromDbId(rs.getInt("geschlecht")), rs.getInt("peid"),
                        rs.getString("vorname"), rs.getString("nachname"), rs.getString("strasse"), rs.getString("plz"),
                        rs.getString("ort"), rs.getDate("gebDat"), rs.getString("handy"), rs.getString("telnr"),
                        rs.getString("email")));
    }

    public void speichereLagerinfo(int personId) {
        insertOrUpdate(insertLagerinfo, ps -> ps.setInt(1, personId));
    }

    public void loesche(Integer id) {
        delete(deleteLagerinfo, ps -> ps.setInt(1, id));
    }
}
