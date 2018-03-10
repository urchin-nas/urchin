package urchin.configuration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.env.YamlPropertySourceLoader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.PropertySource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.util.List;

@Configuration
public class CommandConfiguration {

    private static final String COMMANDS_YML = "commands.yml";

    private final Logger log = LoggerFactory.getLogger(CommandConfiguration.class);

    @Bean("commands")
    public PropertySource yamlPropertySourceLoader() throws IOException {
        log.info("loading commands from {}", COMMANDS_YML);
        YamlPropertySourceLoader yamlPropertySourceLoader = new YamlPropertySourceLoader();
        Resource resource = new ClassPathResource(COMMANDS_YML);
        List<PropertySource<?>> propertySources = yamlPropertySourceLoader.load(resource.getFilename(), resource);
        return propertySources.get(0);
    }

}
