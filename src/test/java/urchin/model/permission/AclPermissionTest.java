package urchin.model.permission;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class AclPermissionTest {

    @Test(expected = IllegalArgumentException.class)
    public void invalidLengthThrowsException() {
        ImmutableAclPermission.of("invalid length");
    }

    @Test(expected = IllegalArgumentException.class)
    public void invalidPermissionThrowsException() {
        ImmutableAclPermission.of("-a-");
    }

    @Test
    public void aclPermissionReturnedForOKPermission() {
        AclPermission aclPermission = ImmutableAclPermission.of("---");

        assertThat(aclPermission).isNotNull();
    }

}