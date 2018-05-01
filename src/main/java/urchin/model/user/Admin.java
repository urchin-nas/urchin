package urchin.model.user;

import org.immutables.value.Value;

import java.time.LocalDateTime;

@Value.Immutable
public interface Admin {

    AdminId getAdminId();

    Username getUsername();

    LocalDateTime getCreated();

}
