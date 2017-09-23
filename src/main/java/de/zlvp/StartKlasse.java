package de.zlvp;

import java.awt.SplashScreen;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import de.zlvp.gui.FensterKlasse;

public class StartKlasse {

    public static void main(String[] args) {
        SplashScreen.getSplashScreen();
        @SuppressWarnings("resource")
        ClassPathXmlApplicationContext ctxt = new ClassPathXmlApplicationContext("spring-beans.xml");
        FensterKlasse fensterKlasse = ctxt.getBean(FensterKlasse.class);
        fensterKlasse.setVisible(true);
    }

}
