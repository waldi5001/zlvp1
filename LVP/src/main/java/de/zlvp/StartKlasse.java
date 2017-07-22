package de.zlvp;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import de.javasoft.plaf.synthetica.SyntheticaBlackStarLookAndFeel;
import de.zlvp.gui.FensterKlasse;

public class StartKlasse {

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(new SyntheticaBlackStarLookAndFeel());
        } catch (Exception e) {
            e.printStackTrace();
        }

        @SuppressWarnings("resource")
        ClassPathXmlApplicationContext ctxt = new ClassPathXmlApplicationContext("spring-beans.xml");
        FensterKlasse fensterKlasse = ctxt.getBean(FensterKlasse.class);
        SwingUtilities.invokeLater(() -> {
            fensterKlasse.setVisible(true);
        });
    }

}
