package urchin.domain.model;

import org.immutables.value.Value;

@Value.Immutable
public interface FileOwners {

    String getUser();

    String getGroup();

}
