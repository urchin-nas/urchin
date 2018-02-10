package urchin.selenium.testutil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static urchin.selenium.testutil.ProfileEvaluator.executeJar;

public class SeleniumUrl {

    private static final Logger log = LoggerFactory.getLogger(SeleniumUrl.class.getName());
    static final int PORT = 8080;
    private static final int WEBPACK_PORT = 3000;
    private static final String LOCALHOST = "http://localhost:";

    private static String url;

    public static String getUrl() {
        if (url == null) {
            if (executeJar()) {
                url = LOCALHOST + PORT;
            } else {
                log.info("Expecting webpack-dev-server to be running and listening on port " + WEBPACK_PORT);
                url = LOCALHOST + WEBPACK_PORT;
            }
        }
        return url;
    }
}
