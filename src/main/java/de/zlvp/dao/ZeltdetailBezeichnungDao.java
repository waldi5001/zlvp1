package de.zlvp.dao;

import java.util.List;

import de.zlvp.entity.ZeltdetailBezeichnung;

public class ZeltdetailBezeichnungDao extends AbstractDao<ZeltdetailBezeichnung> {

    private static final String find = "select zdb.* from zdbez zdb inner join zeltdetail zd on zd.bezeichnung = zdb.zdbid where zd.zdid = ?";
    private static final String findAll = "SELECT zdbid, bezeichnung FROM zdbez ORDER BY bezeichnung";
    private static final String insertZeltdetailBezeichnung = "INSERT INTO zdbez (bezeichnung) VALUES (?)";
    private static final String delete = "delete from zdbez where zdbid = ?";

    private RSE<ZeltdetailBezeichnung> rse = rs -> new ZeltdetailBezeichnung(rs.getInt("zdbid"),
            rs.getString("bezeichnung"));

    public List<ZeltdetailBezeichnung> getAll() {
        return select(findAll, rse);
    }

    public ZeltdetailBezeichnung getForZeltdetail(Integer id) {
        return selectOne(find, ps -> ps.setInt(1, id), rse);
    }

    public void speichern(String bezeichnung) {
        insertOrUpdate(insertZeltdetailBezeichnung, ps -> {
            ps.setString(1, bezeichnung);
        });
    }

    public void loeschen(int id) {
        jdbc.update(delete, id);
    }

}
