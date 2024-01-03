package de.zlvp;

import static com.google.common.io.ByteStreams.toByteArray;

import java.io.IOException;

import javax.swing.JOptionPane;

import org.springframework.beans.factory.FactoryBean;

import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.UserInfo;

public class SSHConnection implements FactoryBean<Session> {

    private String hostname;
    private String sshUsername;
    private String keyfilePassword;
    private int sshLocalport;

    public void setHostname(String hostname) {
        this.hostname = hostname;
    }

    public void setSshUsername(String sshUsername) {
        this.sshUsername = sshUsername;
    }

    public void setKeyfilePassword(String keyfilePassword) {
        this.keyfilePassword = keyfilePassword;
    }

    public void setSshLocalport(int sshLocalport) {
        this.sshLocalport = sshLocalport;
    }

    @Override
    public Session getObject() throws Exception {
        try {
            JSch jsch = new JSch();
            jsch.setInstanceLogger(new JSchLoggerAdapter());
            jsch.setKnownHosts(getClass().getResourceAsStream("/known_host"));
            jsch.addIdentity("lager", toByteArray(getClass().getResourceAsStream("/id_rsa")), null, keyfilePassword.getBytes());

            Session session = jsch.getSession(sshUsername, hostname, 22);
            session.setUserInfo(userInfo());
            session.connect();
            session.setPortForwardingL(sshLocalport, "localhost", sshLocalport);
            return session;
        } catch (IOException e) {
            e.printStackTrace(System.err);
            JOptionPane.showMessageDialog(null, e.getMessage());
            System.exit(2);
        }
        return null;
    }

    private UserInfo userInfo() {
        return new UserInfo() {
            @Override
            public void showMessage(String message) {
            }

            @Override
            public boolean promptYesNo(String message) {
                return false;
            }

            @Override
            public boolean promptPassword(String message) {
                return false;
            }

            @Override
            public boolean promptPassphrase(String message) {
                return false;
            }

            @Override
            public String getPassword() {
                return null;
            }

            @Override
            public String getPassphrase() {
                return null;
            }
        };
    }

    @Override
    public Class<?> getObjectType() {
        return Session.class;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }
}