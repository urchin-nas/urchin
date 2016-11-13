package urchin.testutil;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import urchin.configuration.RuntimeConfiguration;

@TestConfiguration
@ComponentScan(basePackages = "urchin.domain.cli")
@Import({RuntimeConfiguration.class, UnixUserAndGroupCleanup.class})
public class CliTestConfiguration {
}
