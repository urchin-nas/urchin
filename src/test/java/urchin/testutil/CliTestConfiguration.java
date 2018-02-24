package urchin.testutil;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import urchin.cli.BasicCommand;
import urchin.configuration.CommandConfiguration;
import urchin.configuration.RuntimeConfiguration;

@TestConfiguration
@ComponentScan(basePackageClasses = {BasicCommand.class})
@Import({
        RuntimeConfiguration.class,
        CommandConfiguration.class,
        UnixUserAndGroupCleanup.class
})
public class CliTestConfiguration {
}
