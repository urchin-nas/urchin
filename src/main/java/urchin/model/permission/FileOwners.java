package urchin.model.permission;

import org.immutables.value.Value;

@Value.Immutable
public interface FileOwners {

    String getUser();

    String getGroup();

}
