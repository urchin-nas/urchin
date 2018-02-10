package urchin.controller.mapper;

import org.junit.Test;
import urchin.controller.api.user.UserResponse;
import urchin.model.user.ImmutableUser;
import urchin.model.user.User;
import urchin.model.user.UserId;
import urchin.model.user.Username;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class UserMapperTest {

    @Test
    public void mappedToResponse() {
        User user = ImmutableUser.builder()
                .userId(UserId.of(1))
                .username(Username.of("username"))
                .created(LocalDateTime.now())
                .build();

        List<UserResponse> userResponses = UserMapper.mapToUsersResponses(Collections.singletonList(user));

        assertThat(userResponses).hasSize(1);
        UserResponse userResponse = userResponses.get(0);
        assertThat(userResponse.getUserId()).isEqualTo(user.getUserId().getValue());
        assertThat(userResponse.getUsername()).isEqualTo(user.getUsername().getValue());
    }

}