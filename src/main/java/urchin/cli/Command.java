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

    public String getFolderCommand(String command) {
        return propertySource.getProperty("folder." + command).toString();
    }

    public String getGroupCommand(String command) {
        return propertySource.getProperty("group." + command).toString();
    }

    public String getUserCommand(String command) {
        return propertySource.getProperty("user." + command).toString();
    }

    public String getPermissionCommand(String command) {
        return propertySource.getProperty("permission." + command).toString();
    }
}
