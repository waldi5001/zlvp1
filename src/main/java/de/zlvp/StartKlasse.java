package de.zlvp;

import java.awt.SplashScreen;
import java.io.IOException;
import java.io.InputStream;
import java.util.Locale;
import java.util.logging.LogManager;
import java.util.logging.Logger;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import de.zlvp.gui.FensterKlasse;

public class StartKlasse {

    static {
        final InputStream inputStream = StartKlasse.class.getResourceAsStream("/logging.properties");
        try {
            LogManager.getLogManager().readConfiguration(inputStream);
        } catch (final IOException e) {
            Logger.getAnonymousLogger().severe("Could not load default logging.properties file");
            Logger.getAnonymousLogger().severe(e.getMessage());
        }
    }

    public static void main(String[] args) {
        Locale.setDefault(Locale.GERMAN);
        SplashScreen.getSplashScreen();
        @SuppressWarnings("resource")
        ClassPathXmlApplicationContext ctxt = new ClassPathXmlApplicationContext("spring-beans.xml");
        FensterKlasse fensterKlasse = ctxt.getBean(FensterKlasse.class);
        fensterKlasse.setVisible(true);
        if (args.length == 2) {
            if (Client.login(args[0], args[1].toCharArray())) {
                Events.get().fireLoginSuccessfull(args[0]);
            }
        }
    }

}
