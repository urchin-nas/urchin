package urchin.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RuntimeConfiguration {

    @Bean
    public Runtime runtime() {
        return Runtime.getRuntime();
    }
}
