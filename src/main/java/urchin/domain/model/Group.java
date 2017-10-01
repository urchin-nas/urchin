package urchin.domain.model;

import org.immutables.value.Value;

import java.time.LocalDateTime;

@Value.Immutable
public interface Group {

    GroupId getGroupId();

    String getName();

    LocalDateTime getCreated();

}
