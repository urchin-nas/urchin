package urchin.domain.model;

import org.immutables.value.Value;

@Value.Immutable
public interface GroupId {

    @Value.Parameter
    int getId();
}
