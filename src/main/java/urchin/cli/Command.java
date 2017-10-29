package urchin.cli;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.env.PropertySource;
import org.springframework.stereotype.Component;

@Component
public class Command {

    private final PropertySource propertySource;

    public Command(@Qualifier("commands") PropertySource propertySource) {
        this.propertySource = propertySource;
    }

    public String getUserCommand(String command) {
        return propertySource.getProperty("user." + command).toString();
    }

    public String getGroupCommand(String command) {
        return propertySource.getProperty("group." + command).toString();
    }
}
