package urchin.model.user;

import org.immutables.value.Value;

import java.time.LocalDateTime;

@Value.Immutable
public abstract class Admin {

    public abstract AdminId getAdminId();

    public abstract Username getUsername();

    public abstract LocalDateTime getCreated();

    public LinuxUser asLinuxUser() {
        return ImmutableLinuxUser.of(getUsername());
    }

}
