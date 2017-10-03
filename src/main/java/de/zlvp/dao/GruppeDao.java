package de.zlvp.dao;

import java.util.List;

import de.zlvp.entity.Gruppe;

public class GruppeDao extends AbstractDao<Gruppe> {
    private static final String find = "select g.* from gruppe g where grid = ?";
    private static final String findAll = "SELECT grid, name, schlachtruf FROM gruppe ORDER BY name;";
    private static final String findAllUnassigned = "select g.* from gruppe g where not exists (select gruppe from stgrla st where g.grid = st.gruppe) ORDER BY g.name";
    private static final String findAllFromLager = "select g.* from gruppe g inner join stgrla st on g.grid = st.gruppe where st.lager = ? ORDER BY g.name";

    private static final String insertGruppeZuLager = "INSERT INTO stGrLaT (lager,gruppe) values (?,?)";
    private static final String deleteGruppeZuLager = "delete from stGrLaT where lager = ? and gruppe = ?";

    private static final String updateGruppe = "update gruppe set name = ?, schlachtruf = ? where grid = ?";
    private static final String insertGruppe = "insert into gruppe (name, schlachtruf) values (?,?)";

    public List<Gruppe> getAll() {
        return select(findAll, rs -> new Gruppe(rs.getInt("grid"), rs.getString("name"), rs.getString("schlachtruf")));
    }

    public List<Gruppe> getAllUnasigned() {
        return select(findAllUnassigned,
                rs -> new Gruppe(rs.getInt("grid"), rs.getString("name"), rs.getString("schlachtruf")));
    }

    public List<Gruppe> getAllFromLager(int lagerId) {
        return select(findAllFromLager, ps -> ps.setInt(1, lagerId),
                rs -> new Gruppe(rs.getInt("grid"), rs.getString("name"), rs.getString("schlachtruf")));

    }

    public Gruppe speichereGruppe(Integer gruppenId, String bezeichnung, String schlachtruf) {
        Integer grid = null;
        if (gruppenId == null) {
            grid = (Integer) insertOrUpdate(insertGruppe, ps -> {
                ps.setString(1, bezeichnung);
                ps.setString(2, schlachtruf);
            }).get("grid");
        } else {
            grid = gruppenId;
            insertOrUpdate(updateGruppe, ps -> {
                ps.setString(1, bezeichnung);
                ps.setString(2, schlachtruf);
                ps.setInt(3, gruppenId);
            });
        }

        return get(grid);
    }

    public void speicherenZuLager(int lagerId, int gruppeId) {
        jdbc.update(insertGruppeZuLager, lagerId, gruppeId);
    }

    public void loeschenVonLager(int lagerId, int gruppeId) {
        jdbc.update(deleteGruppeZuLager, lagerId, gruppeId);
    }

    public Gruppe get(int gruppeId) {
        return selectOne(find, ps -> ps.setInt(1, gruppeId),
                rs -> new Gruppe(rs.getInt("grid"), rs.getString("name"), rs.getString("schlachtruf")));
    }

}
