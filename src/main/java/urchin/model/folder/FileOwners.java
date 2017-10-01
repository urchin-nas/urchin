package urchin.model.folder;

import org.immutables.value.Value;

@Value.Immutable
public interface FileOwners {

    String getUser();

    String getGroup();

}
