package de.zlvp;

import static java.lang.String.format;

import javax.swing.UIManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;

import de.javasoft.plaf.synthetica.SyntheticaBlackStarLookAndFeel;

public class SyntheticaInitializer implements InitializingBean {
    private static Logger log = LoggerFactory.getLogger(SyntheticaInitializer.class);

    private String licensee;
    private String registrationNumber;
    private String syntheticaKey;
    private String syntheticaAddonKey;

    @Override
    public void afterPropertiesSet() throws Exception {
        String[] li = { format("Licensee=%s", licensee), format("LicenseRegistrationNumber=%s", registrationNumber),
                "Product=Synthetica", "LicenseType=Non Commercial", "ExpireDate=--.--.----", "MaxVersion=2.999.999" };
        UIManager.put("Synthetica.license.info", li);
        UIManager.put("Synthetica.license.key", syntheticaKey);

        String[] li2 = { format("Licensee=%s", licensee), format("LicenseRegistrationNumber=%s", registrationNumber),
                "Product=SyntheticaAddons", "LicenseType=Non Commercial", "ExpireDate=--.--.----",
                "MaxVersion=1.999.999" };
        UIManager.put("SyntheticaAddons.license.info", li2);
        UIManager.put("SyntheticaAddons.license.key", syntheticaAddonKey);

        try {
            UIManager.setLookAndFeel(new SyntheticaBlackStarLookAndFeel());
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }

    public void setLicensee(String licensee) {
        this.licensee = licensee;
    }

    public void setRegistrationNumber(String registrationNumber) {
        this.registrationNumber = registrationNumber;
    }

    public void setSyntheticaKey(String syntheticaKey) {
        this.syntheticaKey = syntheticaKey;
    }

    public void setSyntheticaAddonKey(String syntheticaAddonKey) {
        this.syntheticaAddonKey = syntheticaAddonKey;
    }

}
