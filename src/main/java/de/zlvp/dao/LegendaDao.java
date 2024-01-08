package de.zlvp.dao;

import java.sql.Types;
import java.util.Date;
import java.util.List;

import de.zlvp.entity.Legenda;

public class LegendaDao extends AbstractDao<Legenda> {

    private static final String findAllFromLagerort = "select lg.* from legenda lg "
            + "left join legendatyp lt on lg.typ = lt.tyid where lg.lagerort_id = ? order by lt.typ";

    private static final String insertLegenda = "INSERT INTO legenda (typ,name,vorname,strasse,plz,ort,tel,handy,fax,email,anrede,firma,bemerkung,lagerort_id) "
            + "VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

    private static final String updateLegenda = "UPDATE legenda "
            + "SET typ = ?,  name = ?,  vorname = ?,  strasse = ?,  plz = ?,  ort = ?, "
            + " tel = ?,  handy = ?,  fax = ?,  email = ?,  anrede = ?,  firma = ?,  bemerkung = ? where lgid = ?";
    
    private static final String delete = "delete from legenda where lgid = ?";

    public List<Legenda> getAllFromLagerort(int lagerortId) {
        return select(findAllFromLagerort, ps -> ps.setInt(1, lagerortId), rs -> {
            Legenda lg = new Legenda(rs.getInt("lgid"), rs.getString("vorname"), rs.getString("name"),
                    rs.getString("strasse"), rs.getString("plz"), rs.getString("ort"), new Date(),
                    rs.getString("handy"), rs.getString("tel"), rs.getString("email"));
            lg.setFax(rs.getString("fax"));
            lg.setFirma(rs.getString("firma"));
            lg.setBemerkung(rs.getString("bemerkung"));
            return lg;
        });
    }

    public void speichern(final Integer id, int lageortId, String nachname, String vorname, String firma,
            String strasse, String plz, String ort, Integer legendatypId, Integer anredeId, String tel, String fax,
            String handy, String email, String bemerkung) {
        if (id == null) {
            insertOrUpdate(insertLegenda, ps -> {
                if (legendatypId == null) {
                    ps.setNull(1, Types.INTEGER);
                } else {
                    ps.setInt(1, legendatypId);
                }
                ps.setString(2, nachname);
                ps.setString(3, vorname);
                ps.setString(4, strasse);
                ps.setString(5, plz);
                ps.setString(6, ort);
                ps.setString(7, tel);
                ps.setString(8, handy);
                ps.setString(9, fax);
                ps.setString(10, email);
                if (anredeId == null) {
                    ps.setNull(11, Types.INTEGER);
                } else {
                    ps.setInt(11, anredeId);
                }
                ps.setString(12, firma);
                ps.setString(13, bemerkung);
                ps.setInt(14, lageortId);
            });
        } else {
            insertOrUpdate(updateLegenda, ps -> {
                if (legendatypId == null) {
                    ps.setNull(1, Types.INTEGER);
                } else {
                    ps.setInt(1, legendatypId);
                }
                ps.setString(2, nachname);
                ps.setString(3, vorname);
                ps.setString(4, strasse);
                ps.setString(5, plz);
                ps.setString(6, ort);
                ps.setString(7, tel);
                ps.setString(8, handy);
                ps.setString(9, fax);
                ps.setString(10, email);
                if (anredeId == null) {
                    ps.setNull(11, Types.INTEGER);
                } else {
                    ps.setInt(11, anredeId);
                }
                ps.setString(12, firma);
                ps.setString(13, bemerkung);
                ps.setInt(14, id);
            });
        }
    }
    
    public void delete(int id) {
        jdbc.update(delete, id);
    }

}
