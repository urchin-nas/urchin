package urchin.controller.api.mapper;

import org.junit.Test;
import urchin.controller.api.user.UserDto;
import urchin.model.user.ImmutableUser;
import urchin.model.user.User;
import urchin.model.user.UserId;
import urchin.model.user.Username;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class UserMapperTest {

    @Test
    public void mappedToDto() {
        User user = ImmutableUser.builder()
                .userId(UserId.of(1))
                .username(Username.of("username"))
                .created(LocalDateTime.now())
                .build();

        List<UserDto> userDtos = UserMapper.mapToUsersDto(Collections.singletonList(user));

        assertEquals(1, userDtos.size());
        UserDto userDto = userDtos.get(0);
        assertEquals(user.getUserId().getValue(), userDto.getUserId());
        assertEquals(user.getUsername().getValue(), userDto.getUsername());
    }

}