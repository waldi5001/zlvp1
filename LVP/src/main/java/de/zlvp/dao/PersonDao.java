package de.zlvp.dao;

import static java.lang.String.format;
import static java.util.Calendar.YEAR;

import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import de.zlvp.entity.Geschlecht;
import de.zlvp.entity.Person;

public class PersonDao extends AbstractDao<Person> {

    private static final String findAll = "select p.*, g.* from person p inner join geschlecht g on p.geschlecht = g.geid order by nachname, vorname";
    private static final String find = "select p.*, g.* from person p inner join geschlecht g on p.geschlecht = g.geid where p.vorname ilike ? or p.nachname ilike ? order by p.nachname, p.vorname";

    private static final String insertPerson = "insert into persont "
            + "(vorname, nachname, gebdat, strasse, plz, ort, telnr, email, jahrgang, vornamenachname, geschlecht, handy, nottel) "
            + "values (?,?,?,?,?,?,?,?,?,?,?,?,?)";

    private static final String updatePerson = "update persont set "
            + "vorname = ?, nachname = ?, gebdat = ?, strasse = ?, plz = ?, ort = ?, telnr = ?, email = ?, jahrgang = ?, vornamenachname = ?, geschlecht = ?, handy = ?, nottel = ? "
            + "where peid = ?";

    public List<Person> getAll() {
        return select(findAll, rs -> {
            Person person = new Person(rs.getInt("peid"), rs.getString("vorname"), rs.getString("nachname"),
                    rs.getString("strasse"), rs.getString("plz"), rs.getString("ort"), rs.getDate("gebDat"),
                    rs.getString("handy"), rs.getString("telnr"), rs.getString("email"));

            person.setGeschlecht(new Geschlecht(rs.getInt("geid"), rs.getString("name")));

            return person;
        });
    }

    public void speichern(Integer id, String vorname, String nachname, Date gebdat, String strasse, String plz,
            String ort, String telnr, String email, int geschlecht, String handy, String nottel) {
        if (id == null) {
            insertOrUpdate(insertPerson, ps -> {
                GregorianCalendar gc = new GregorianCalendar();
                gc.setTime(gebdat);
                int gebJahr = gc.get(YEAR);
                ps.setString(1, vorname);
                ps.setString(2, nachname);
                ps.setDate(3, new java.sql.Date(gebdat.getTime()));
                ps.setString(4, strasse);
                ps.setString(5, plz);
                ps.setString(6, ort);
                ps.setString(7, telnr);
                ps.setString(8, email);
                ps.setInt(9, gebJahr);
                ps.setString(10, format("%s%s%s", vorname, nachname, gebJahr));
                ps.setInt(11, geschlecht);
                ps.setString(12, handy);
                ps.setString(13, nottel);
            });
        } else {
            insertOrUpdate(updatePerson, ps -> {
                GregorianCalendar gc = new GregorianCalendar();
                gc.setTime(gebdat);
                int gebJahr = gc.get(YEAR);
                ps.setString(1, vorname);
                ps.setString(2, nachname);
                ps.setDate(3, new java.sql.Date(gebdat.getTime()));
                ps.setString(4, strasse);
                ps.setString(5, plz);
                ps.setString(6, ort);
                ps.setString(7, telnr);
                ps.setString(8, email);
                ps.setInt(9, gebJahr);
                ps.setString(10, format("%s%s%s", vorname, nachname, gebJahr));
                ps.setInt(11, geschlecht);
                ps.setString(12, handy);
                ps.setString(13, nottel);
                ps.setInt(14, id);
            });
        }
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

            ps.setString(2, nachname);
        }, rs -> {
            Person person = new Person(rs.getInt("peid"), rs.getString("vorname"), rs.getString("nachname"),
                    rs.getString("strasse"), rs.getString("plz"), rs.getString("ort"), rs.getDate("gebDat"),
                    rs.getString("handy"), rs.getString("telnr"), rs.getString("email"));

            person.setGeschlecht(new Geschlecht(rs.getInt("geid"), rs.getString("name")));

            return person;
        });
    }

}
