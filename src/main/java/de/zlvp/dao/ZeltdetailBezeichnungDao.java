package de.zlvp.dao;

import java.util.List;

import de.zlvp.entity.ZeltdetailBezeichnung;

public class ZeltdetailBezeichnungDao extends AbstractDao<ZeltdetailBezeichnung> {

    private static final String findAll = "SELECT zdbid, bezeichnung FROM zdbez ORDER BY bezeichnung";
    private static final String find = "select zdb.* from zdbez zdb inner join zeltdetail zd on zd.bezeichnung = zdb.zdbid where zd.zdid = ?";
    private static final String insertZeltdetailBezeichnung = "INSERT INTO zdbez (bezeichnung) VALUES (?);";
    private static final String delete = "delete from zdbez where zdbid = ?";

    public List<ZeltdetailBezeichnung> getAll() {
        return select(findAll, rs -> new ZeltdetailBezeichnung(rs.getInt("zdbid"), rs.getString("bezeichnung")));
    }

    public void speichern(String bezeichnung) {
        insertOrUpdate(insertZeltdetailBezeichnung, ps -> {
            ps.setString(1, bezeichnung);
        });
    }

    public ZeltdetailBezeichnung getForZeltdetail(Integer id) {
        return selectOne(find, ps -> ps.setInt(1, id),
                rs -> new ZeltdetailBezeichnung(rs.getInt("zdbid"), rs.getString("bezeichnung")));
    }

    public void loeschen(int id) {
        delete(delete, ps -> ps.setInt(1, id));
    }

}
