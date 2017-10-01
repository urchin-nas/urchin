package urchin.model.user;

import org.immutables.value.Value;

import java.time.LocalDateTime;

@Value.Immutable
public interface User {

    UserId getUserId();

    Username getUsername();

    LocalDateTime getCreated();

}
