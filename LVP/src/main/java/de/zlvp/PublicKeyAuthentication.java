package de.zlvp;

import java.io.CharArrayWriter;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.swing.JOptionPane;

import org.springframework.beans.factory.InitializingBean;

import ch.ethz.ssh2.Connection;

public class PublicKeyAuthentication implements InitializingBean {

    private String hostname;
    private String sshUsername;
    private String keyfilePassword;
    private int sshLocalport;

    @Override
    public void afterPropertiesSet() throws Exception {
        try {
            InputStreamReader ir = new InputStreamReader(getClass().getResource("/id_rsa").openStream());
            CharArrayWriter cw = new CharArrayWriter();

            char[] buffer = new char[256];

            while (true) {
                int len = ir.read(buffer);
                if (len < 0) {
                    break;
                }
                cw.write(buffer, 0, len);
            }

            Connection conn = new Connection(hostname);
            conn.connect();

            boolean isAuthenticated = conn.authenticateWithPublicKey(sshUsername, cw.toCharArray(), keyfilePassword);

            if (isAuthenticated == false) {
                throw new IOException("Authentication failed.");
            }

            conn.createLocalPortForwarder(sshLocalport, "127.0.0.1", 5432);
        } catch (IOException e) {
            e.printStackTrace(System.err);
            JOptionPane.showMessageDialog(null, e.getMessage());
            System.exit(2);
        }
    }

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
}