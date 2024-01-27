package de.zlvp.dao;

import de.zlvp.entity.Lager;
import java.util.Date;
import java.util.List;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.RowMapper;

public class LagerDao extends AbstractDao<Lager> {

    private static final String findAllFromJahr =
            "SELECT l.* FROM LAGER l INNER JOIN stLaJa st ON st.lager = l.laid WHERE st.jahr = ? order by l.name";
    private static final String findAllFromPerson =
            "select root.* from ( select j.jahr as jahr, lager.*, 'Stab' as dabeiAls from lager left join stlaja laJa on lager.laid = " + "laJa.lager left join jahr j on laJa.jahr = j.jaid left join stlast laSt on lager.laid = laSt.lager where laSt.person" + " = ? union all select j.jahr as jahr, lager.*, 'Teilnehmer' as dabeiAls from lager left join stlaja laJa on lager" + ".laid = laJa.lager left join jahr j on laJa.jahr = j.jaid left join stgrlat grLa on lager.laid = grLa.lager left " + "join gruppe g on grLa.gruppe = g.grid left join sttegr teGr on g.grid = teGr.gruppe where teGr.person = ? union all " + "select j.jahr as jahr, lager.*, 'Leiter' as dabeiAls from lager left join stlaja laJa on lager.laid = laJa.lager " + "left join jahr j on laJa.jahr = j.jaid left join stgrlat grLa on lager.laid = grLa.lager left join gruppe g on grLa" + ".gruppe = g.grid left join stgrle leGr on g.grid = leGr.gruppe where leGr.person = ?) as root order by jahr";
    private static final String find = "SELECT l.* FROM LAGER l WHERE l.laid = ?";
    private static final String getFromGruppe = "select l.* from lager l inner join stgrla grla on l.laid = grla.lager where grla.gruppe = ?";
    private static final String insertLager = "INSERT INTO LAGER (name, thema, datumstart, datumstop) VALUES (?, ?, ?, ?)";
    private static final String updateLager = "update LAGER set name=?, thema=?, datumstart=?, datumstop=? where laid = ?";
    private static final String insertLagerort = "INSERT INTO stLaLo (lager, lagerort) VALUES (?, ?)";
    private static final String updateLagerort = "update stLaLo set lagerort = ? where lager = ?";
    private static final String insertJahr = "INSERT INTO stLaJa (lager, jahr) VALUES (?, ?)";

    private RSE<Lager> rse =
            rs -> new Lager(rs.getInt("laid"), rs.getString("name"), rs.getString("thema"), new Date(rs.getDate("datumStart").getTime()),
                    new Date(rs.getDate("datumStop").getTime()));

    public List<Lager> getAll(int jahrId) {
        return select(findAllFromJahr, ps -> ps.setInt(1, jahrId), rse);
    }

    public Lager get(int lagerId) {
        return selectOne(find, ps -> ps.setInt(1, lagerId), rse);
    }

    public int speichern(Integer lagerId, String name, String thema, Date start, Date stop, int lagerortId, int jahrId) {
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
            return id;
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
            return lagerId;
        }
    }

    public void aendern(Integer lagerId, String name, String thema, Date start, Date stop) {
        jdbc.update(updateLager, name, thema, new java.sql.Date(start.getTime()), new java.sql.Date(stop.getTime()), lagerId);
    }

    public List<LagerDTO> getAllOfPerson(int personId) {
        return jdbc.query(findAllFromPerson, (PreparedStatementSetter) ps -> {
            ps.setInt(1, personId);
            ps.setInt(2, personId);
            ps.setInt(3, personId);
        }, (RowMapper<LagerDTO>) (rs, rowNum) -> new LagerDTO(rs.getInt("laid"), rs.getString("name"), rs.getString("thema"),
                new Date(rs.getDate("datumStart").getTime()), new Date(rs.getDate("datumStop").getTime()), rs.getString("jahr"),
                rs.getString("dabeials")));
    }

    public Lager getFromGruppe(int gruppeId) {
        return selectOne(getFromGruppe, ps -> ps.setInt(1, gruppeId), rse);
    }

    public static class LagerDTO extends Lager {
        private String jahr;
        private String dabeiAls;

        public LagerDTO(int id, String name, String thema, Date datumStart, Date datumStop, String jahr, String dabeiAls) {
            super(id, name, thema, datumStart, datumStop);
            this.jahr = jahr;
            this.dabeiAls = dabeiAls;
        }

        public String getJahrAsString() {
            return jahr;
        }

        public String getDabeiAls() {
            return dabeiAls;
        }
    }
}
