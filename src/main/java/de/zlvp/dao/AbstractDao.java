package de.zlvp.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;

public class AbstractDao<R> {

    protected JdbcOperations jdbc;

    protected List<R> select(String sql, RSE<R> rse) {
        return jdbc.query(sql, (RowMapper<R>) (rs, rowNum) -> rse.get(rs));
    }

    protected List<R> select(String sql, StatementSetter psSetter, RSE<R> rse) {
        return jdbc.query(sql, (PreparedStatementSetter) ps -> psSetter.setValue(ps),
                (RowMapper<R>) (rs, rowNum) -> rse.get(rs));
    }

    protected Map<String, Object> insertOrUpdate(String sql, StatementSetter psSetter) {
        GeneratedKeyHolder generatedKeyHolder = new GeneratedKeyHolder();
        jdbc.update((PreparedStatementCreator) con -> {
            PreparedStatement prepareStatement = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            psSetter.setValue(prepareStatement);
            return prepareStatement;
        }, generatedKeyHolder);
        return generatedKeyHolder.getKeyList().isEmpty() ? null : generatedKeyHolder.getKeys();
    }

    protected void delete(String sql, StatementSetter psSetter) {
        jdbc.update(sql, (PreparedStatementSetter) ps -> psSetter.setValue(ps));
    }

    protected R selectOne(String sql, StatementSetter psSetter, RSE<R> rse) {
        return jdbc.query(sql, (PreparedStatementSetter) ps -> psSetter.setValue(ps), (ResultSetExtractor<R>) rs -> {
            if (rs.next()) {
                return rse.get(rs);
            }
            return null;
        });
    }

    @FunctionalInterface
    public static interface StatementSetter {
        void setValue(PreparedStatement ps) throws SQLException;
    }

    @FunctionalInterface
    public static interface RSE<T> {
        T get(ResultSet rs) throws SQLException;
    }

    public void setJdbc(JdbcOperations jdbc) {
        this.jdbc = jdbc;
    }

}
