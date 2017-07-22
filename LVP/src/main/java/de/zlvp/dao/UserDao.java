package de.zlvp.dao;

import static java.lang.String.format;

public class UserDao extends AbstractDao<Void> {

    public void changePasswort(String user, String passwort) {
        jdbc.execute(format("ALTER USER %s WITH PASSWORD '%s';", user, passwort));
    }

}
