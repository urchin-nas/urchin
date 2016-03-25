package urchin;

import org.springframework.context.annotation.Bean;

@org.springframework.context.annotation.Configuration
public class Configuration {

    @Bean
    public Runtime runtime() {
        return Runtime.getRuntime();
    }
}
