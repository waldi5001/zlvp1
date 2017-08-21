package de.zlvp.dao;

import java.sql.Types;
import java.util.Date;
import java.util.List;

import de.zlvp.entity.Waehrung;
import de.zlvp.entity.Zelt;

public class ZeltDao extends AbstractDao<Zelt> {

    private static final String findAll = "select z.* from zelt z order by z.bezeichnung;";
    private static final String findAllFromLager = "select z.*,st.stlazeid from zelt z INNER JOIN stLaZe st ON z.zeid = st.zelt where st.lager = ? order by z.bezeichnung;";
    private static final String findAllFromGruppe = "select z.*,st.stgrzeid from zelt z INNER JOIN stGrZe st ON z.zeid = st.zelt where st.gruppe = ? order by z.bezeichnung;";

    private static final String insert = "insert into zelt (bezeichnung,preis,angeschafft,waehrung) values (?,?,?,?)";
    private static final String update = "update zelt set bezeichnung = ?, preis = ?, angeschafft = ?, waehrung = ? where zeid = ?";

    private static final String addToLager = "insert into stlaze (lager,zelt) values (?,?)";
    private static final String addToGruppe = "insert into stgrze (gruppe,zelt) values (?,?)";
    private static final String deleteFromLager = "delete from stlaze where stlazeid = ?";
    private static final String deleteFromGruppe = "delete from stgrze where stgrzeid = ?";

    public List<Zelt> getAll() {
        return select(findAll,
                rs -> new Zelt(rs.getInt("zeid"), rs.getInt("zeid"), rs.getString("bezeichnung"), rs.getDouble("preis"),
                        rs.getDate("angeschafft"),
                        rs.getInt("waehrung") != 0 ? Waehrung.fromDbId(rs.getInt("waehrung")) : null));
    }

    public List<Zelt> getAllFromLager(int lagerId) {
        return select(findAllFromLager, ps -> ps.setInt(1, lagerId),
                rs -> new Zelt(rs.getInt("stlazeid"), rs.getInt("zeid"), rs.getString("bezeichnung"),
                        rs.getDouble("preis"), rs.getDate("angeschafft"),
                        rs.getInt("waehrung") != 0 ? Waehrung.fromDbId(rs.getInt("waehrung")) : null));
    }

    public List<Zelt> getAllFromGruppe(int gruppeId) {
        return select(findAllFromGruppe, ps -> ps.setInt(1, gruppeId),
                rs -> new Zelt(rs.getInt("stgrzeid"), rs.getInt("zeid"), rs.getString("bezeichnung"),
                        rs.getDouble("preis"), rs.getDate("angeschafft"),
                        rs.getInt("waehrung") != 0 ? Waehrung.fromDbId(rs.getInt("waehrung")) : null));
    }

    public void speichern(Integer zeltId, String bezeichnung, Date angeschafft, double preis, Waehrung waehrung) {
        if (zeltId == null) {
            insertOrUpdate(insert, ps -> {
                ps.setString(1, bezeichnung);
                ps.setDouble(2, preis);
                if (angeschafft != null) {
                    ps.setDate(3, new java.sql.Date(angeschafft.getTime()));
                } else {
                    ps.setDate(3, null);
                }
                if (waehrung == null) {
                    ps.setNull(4, Types.INTEGER);
                } else {
                    ps.setInt(4, waehrung.getDbId());
                }
            });
        } else {
            insertOrUpdate(update, ps -> {
                ps.setString(1, bezeichnung);
                ps.setDouble(2, preis);
                if (angeschafft != null) {
                    ps.setDate(3, new java.sql.Date(angeschafft.getTime()));
                } else {
                    ps.setDate(3, null);
                }
                if (waehrung == null) {
                    ps.setNull(4, Types.INTEGER);
                } else {
                    ps.setInt(4, waehrung.getDbId());
                }
                ps.setInt(5, zeltId);
            });
        }
    }

    public void speichernZuLager(int zeltId, Integer lagerId) {
        insertOrUpdate(addToLager, ps -> {
            ps.setInt(1, lagerId);
            ps.setInt(2, zeltId);
        });
    }

    public void speichernZuGruppe(int zeltId, Integer gruppeId) {
        insertOrUpdate(addToGruppe, ps -> {
            ps.setInt(1, gruppeId);
            ps.setInt(2, zeltId);
        });
    }

    public void loeschenZuLager(int zeltId) {
        delete(deleteFromLager, ps -> ps.setInt(1, zeltId));
    }

    public void loeschenZuGruppe(int zeltId) {
        delete(deleteFromGruppe, ps -> ps.setInt(1, zeltId));
    }
}
