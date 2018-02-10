package urchin.selenium.testutil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;

class ProfileEvaluator {

    static final String PROFILE = "selenium";
    private static final Logger log = LoggerFactory.getLogger(ProfileEvaluator.class.getName());


    static boolean executeJar() {
        String activeProfiles = System.getProperty("spring.profiles.active");
        log.info("Active profiles: {}", activeProfiles);
        return activeProfiles != null && Arrays.asList(activeProfiles.split(",")).contains(PROFILE);
    }
}
