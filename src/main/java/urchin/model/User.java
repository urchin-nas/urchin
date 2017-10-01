package urchin.model;

import org.immutables.value.Value;

import java.time.LocalDateTime;

@Value.Immutable
public interface User {

    UserId getUserId();

    String getUsername();

    LocalDateTime getCreated();

}
