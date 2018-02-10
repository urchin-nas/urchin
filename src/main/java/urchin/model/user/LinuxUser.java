package urchin.model.user;

import org.immutables.value.Value;

@Value.Immutable
public interface LinuxUser {

    @Value.Parameter
    Username getUsername();
}
