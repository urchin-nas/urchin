package urchin.controller.api.user;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.sun.istack.internal.Nullable;
import org.immutables.value.Value;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Value.Immutable
@JsonSerialize(as = ImmutableAddUserDto.class)
@JsonDeserialize(as = ImmutableAddUserDto.class)
public interface AddUserDto {

    @Nullable
    @NotNull
    @Size(min = 3, max = 32)
    String getUsername();

    @Nullable
    @NotNull
    @Size(min = 6)
    String getPassword();
}
