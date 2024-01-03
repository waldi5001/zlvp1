package de.zlvp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JSchLoggerAdapter implements com.jcraft.jsch.Logger {

    private static Logger log = LoggerFactory.getLogger("JSch");

    public boolean isEnabled(int level) {
        return true;
    }

    public void log(int level, String message) {
        if (level == DEBUG) {
            log.debug(message);
        } else if (level == INFO) {
            log.info(message);
        } else if (level == WARN) {
            log.warn(message);
        } else if (level == ERROR) {
            log.error(message);
        } else if (level == FATAL) {
            log.error(message);
        } else {
            log.error(message);
        }
    }
}
