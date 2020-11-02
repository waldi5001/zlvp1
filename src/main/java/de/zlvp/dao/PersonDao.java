package de.zlvp.dao;

import java.util.Date;
import java.util.List;

import de.zlvp.entity.Geschlecht;
import de.zlvp.entity.Person;

public class PersonDao extends AbstractDao<Person> {

    private static final String findAll = "select p.* from person p order by nachname, vorname";
    private static final String findOne = "select p.* from person p where p.peid = ?";
    private static final String find = "select p.* from person p where p.vorname ilike ? or p.nachname ilike ? order by p.nachname, p.vorname";

    private static final String insertPerson = "insert into persont "
            + "(vorname, nachname, gebdat, strasse, plz, ort, telnr, email, geschlecht, handy, nottel) "
            + "values (?,?,?,?,?,?,?,?,?,?,?)";

    private static final String updatePerson = "update persont set "
            + "vorname = ?, nachname = ?, gebdat = ?, strasse = ?, plz = ?, ort = ?, telnr = ?, email = ?, geschlecht = ?, handy = ?, nottel = ? "
            + "where peid = ?";

    private RSE<Person> rse = rs -> {
        return new Person(rs.getInt("peid"), Geschlecht.fromDbId(rs.getInt("geschlecht")), rs.getString("vorname"),
                rs.getString("nachname"), rs.getString("strasse"), rs.getString("plz"), rs.getString("ort"),
                new Date(rs.getDate("gebDat").getTime()), rs.getString("handy"), rs.getString("telnr"),
                rs.getString("email"), rs.getString("nottel"));
    };

    public List<Person> getAll() {
        return select(findAll, rse);
    }

    public Person speichern(Integer id, String vorname, String nachname, Date gebdat, String strasse, String plz,
            String ort, String telnr, String email, Geschlecht geschlecht, String handy, String nottel) {
        Integer peid;
        if (id == null) {
            peid = (Integer) insertOrUpdate(insertPerson, ps -> {
                ps.setString(1, vorname);
                ps.setString(2, nachname);
                ps.setDate(3, new java.sql.Date(gebdat.getTime()));
                ps.setString(4, strasse);
                ps.setString(5, plz);
                ps.setString(6, ort);
                ps.setString(7, telnr);
                ps.setString(8, email);
                ps.setInt(9, geschlecht.getDbId());
                ps.setString(10, handy);
                ps.setString(11, nottel);
            }).get("peid");
        } else {
            jdbc.update(updatePerson, vorname, nachname, new java.sql.Date(gebdat.getTime()), strasse, plz, ort, telnr,
                    email, geschlecht.getDbId(), handy, nottel, id);
            peid = id;
        }
        return selectOne(findOne, ps -> ps.setInt(1, peid), rse);
    }

    public List<Person> findPerson(String vorname, String nachname) {
        return select(find, ps -> {
            if (vorname != null && !vorname.isEmpty()) {
                ps.setString(1, "%" + vorname + "%");
            } else {
                ps.setString(1, vorname);
            }

            if (nachname != null && !nachname.isEmpty()) {
                ps.setString(2, "%" + nachname + "%");
            } else {
                ps.setString(2, nachname);
            }
        }, rse);
    }

}
