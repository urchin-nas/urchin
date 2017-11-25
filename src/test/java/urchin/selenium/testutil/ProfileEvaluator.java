package urchin.selenium.testutil;

import java.util.Arrays;

class ProfileEvaluator {

    private static final String PROFILE = "production";

    static boolean isProduction() {
        String activeProfiles = System.getProperty("spring.profiles.active");
        return activeProfiles != null && Arrays.asList(activeProfiles.split(",")).contains(PROFILE);
    }
}
