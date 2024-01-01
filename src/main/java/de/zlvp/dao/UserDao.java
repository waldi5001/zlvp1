package de.zlvp.dao;

import static java.lang.String.format;

import java.util.List;
import java.util.Objects;

public class UserDao extends AbstractDao<Void> {

    public void changePasswort(String user, String passwort) {
        jdbc.execute(format("alter user %s with password '%s'", user, passwort));
    }

    public List<String> getAllUsers() {
        return jdbc.queryForList("select usename from pg_user where usesuper = false order by usename", String.class);
    }

    public List<String> getAllGroups() {
        return jdbc.queryForList("select groname from pg_group where grosysid >= 10000 order by groname", String.class);
    }

    public void createUser(String username, String password) {
        jdbc.execute(format("create user %s with password '%s'", username, password));
    }

    public void dropUser(String username) {
        jdbc.execute(format("drop role %s", username));
    }

    public void grantUser(String username, String group) {
        jdbc.execute(format("grant \"%s\" to %s", group, username));
    }

    public void revokeUser(String username, String group) {
        jdbc.execute(format("revoke \"%s\" from %s", group, username));
    }

    public List<String> getGroupsForUser(String username) {
        List<String> result = jdbc.queryForList(format("select rolname from pg_user "
                + "left join pg_auth_members on (pg_user.usesysid=pg_auth_members.member) "
                + "left join pg_roles on (pg_roles.oid=pg_auth_members.roleid) "
                + "where pg_user.usesuper = false and usename= '%s'", username), String.class);
        result.removeIf(Objects::isNull);
        return result;
    }

}
