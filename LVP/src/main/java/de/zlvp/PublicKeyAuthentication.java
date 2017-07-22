package de.zlvp;

import java.io.CharArrayWriter;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.swing.JOptionPane;

import ch.ethz.ssh2.Connection;

public class PublicKeyAuthentication {
    public PublicKeyAuthentication() {
        String hostname = "NbI-267.NbIServ.com";
        String username = "lager";

        String keyfilePass = "XXLrulz";
        try {
            InputStreamReader ir = new InputStreamReader(getClass().getResource("/id_rsa").openStream());
            CharArrayWriter cw = new CharArrayWriter();

            char[] buffer = new char[256];

            while (true) {
                int len = ir.read(buffer);
                if (len < 0)
                    break;
                cw.write(buffer, 0, len);
            }

            Connection conn = new Connection(hostname);
            conn.connect();

            boolean isAuthenticated = conn.authenticateWithPublicKey(username, cw.toCharArray(), keyfilePass);

            if (isAuthenticated == false)
                throw new IOException("Authentication failed.");

            conn.createLocalPortForwarder(5432, "127.0.0.1", 5432);
        } catch (IOException e) {
            e.printStackTrace(System.err);
            JOptionPane.showMessageDialog(null, e.getMessage());
            System.exit(2);
        }
    }
}