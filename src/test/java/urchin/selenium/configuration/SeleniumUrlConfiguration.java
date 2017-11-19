package urchin.selenium.configuration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;

@TestConfiguration
public class SeleniumUrlConfiguration {

    public static final int PORT = 8080;
    private static final int WEBPACK_PORT = 3000;
    private static final String URL = "http://localhost:";

    private final Logger log = LoggerFactory.getLogger(this.getClass().getName());

    @Bean
    @Qualifier("seleniumUrl")
    @Profile("production")
    public String jarUrl() {
        return URL + PORT;
    }

    @Bean("seleniumUrl")
    @Profile("!production")
    public String webpackUrl() {
        log.info("Expecting webpack-dev-server to be running and listening on port " + WEBPACK_PORT);
        return URL + WEBPACK_PORT;
    }
}
