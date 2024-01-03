package de.zlvp;

import static java.lang.String.format;
import static javax.swing.JOptionPane.INFORMATION_MESSAGE;
import static javax.swing.JOptionPane.YES_NO_OPTION;
import static javax.swing.JOptionPane.YES_OPTION;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import javax.swing.JOptionPane;
import javax.swing.ProgressMonitor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.util.DigestUtils;

import com.google.common.io.ByteProcessor;
import com.google.common.io.ByteStreams;
import com.google.common.io.CharStreams;
import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;

public class Updater implements InitializingBean {

    private static final Logger log = LoggerFactory.getLogger(Updater.class);

    private Session sshConnection;

    private final ProgressMonitor progressMonitor = new ProgressMonitor(null, "ZLVP Update", "", 0, 100);

    @Override
    public void afterPropertiesSet() throws Exception {
        File localJarFile = new File("./zlvp.jar");
        try {
            if (localJarFile.exists()) {
                String localChecksum = DigestUtils.md5DigestAsHex(new FileInputStream(localJarFile));
                String remoteChecksum = executeCommand("md5sum /home/lager/zlvp.jar");

                if (!remoteChecksum.equals(localChecksum)) {
                    int answer = JOptionPane.showConfirmDialog(null, "Es gibt eine neue ZLVP Version. Aktualisieren?", "Neue ZLVP Version",
                            YES_NO_OPTION, INFORMATION_MESSAGE);
                    if (answer == YES_OPTION) {

                        progressMonitor.setProgress(0);
                        progressMonitor.setMillisToPopup(10);

                        ChannelSftp channel = (ChannelSftp) sshConnection.openChannel("sftp");
                        channel.connect();

                        Long bytesToRead = Long.valueOf(executeCommand("wc -c /home/lager/zlvp.jar"));

                        File tempFile = new File("./zlvp.jar.tmp");

                        final CancelInfo ci = new CancelInfo();

                        try (OutputStream outputStream = new FileOutputStream(tempFile)) {
                            try (InputStream is = channel.get("/home/lager/zlvp.jar")) {
                                ByteStreams.readBytes(is, new ByteProcessor<Void>() {
                                    long progress = 0;

                                    @Override
                                    public boolean processBytes(byte[] buffer, int offset, int length) throws IOException {
                                        outputStream.write(buffer, offset, length);
                                        progress += length;
                                        int percent = (int) (progress * 100 / bytesToRead);
                                        progressMonitor.setProgress(percent);
                                        progressMonitor.setNote(format("Fertig: %d%%.\n", percent));
                                        ci.setCanceled(progressMonitor.isCanceled());
                                        return !ci.isCanceled();
                                    }

                                    @Override
                                    public Void getResult() {
                                        return null;
                                    }
                                });
                            } finally {
                                progressMonitor.setProgress(100);
                                channel.disconnect();
                            }

                            if (!ci.isCanceled()) {
                                Files.delete(Paths.get(localJarFile.toURI()));

                                Files.move(Paths.get(tempFile.toURI()), Paths.get(localJarFile.toURI()), StandardCopyOption.ATOMIC_MOVE);

                                ProcessBuilder pb = new ProcessBuilder("java", "-jar", localJarFile.getCanonicalPath());
                                pb.start();
                                System.exit(0);
                            }
                        }
                    }
                }
            }
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
    }

    private static class CancelInfo {
        private boolean canceled = false;

        public boolean isCanceled() {
            return canceled;
        }

        public void setCanceled(boolean canceled) {
            this.canceled = canceled;
        }
    }

    private String executeCommand(String command) throws JSchException, IOException {
        ChannelExec channel = (ChannelExec) sshConnection.openChannel("exec");
        channel.setCommand(command);
        // getInputStream() should be called before connect()
        InputStream inputStream = channel.getInputStream();
        channel.connect();
        String result = CharStreams.toString(new InputStreamReader(inputStream)).split(" ")[0].trim();
        channel.disconnect();
        return result;
    }

    public void setSshConnection(Session sshConnection) {
        this.sshConnection = sshConnection;
    }

}
