package urchin.domain.model;

import org.immutables.value.Value;

@Value.Immutable
public interface UserId {

    @Value.Parameter
    int getValue();
}
