package urchin.controller.api.mapper;

import org.junit.Test;
import urchin.controller.api.group.GroupDto;
import urchin.model.group.Group;
import urchin.model.group.GroupId;
import urchin.model.group.GroupName;
import urchin.model.group.ImmutableGroup;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static urchin.controller.api.mapper.GroupMapper.mapToGroupsDto;

public class GroupMapperTest {

    @Test
    public void mappedToDto() {
        Group group = ImmutableGroup.builder()
                .groupId(GroupId.of(1))
                .name(GroupName.of("groupName"))
                .created(LocalDateTime.now())
                .build();

        List<GroupDto> groupDtos = mapToGroupsDto(Collections.singletonList(group));

        assertEquals(1, groupDtos.size());
        GroupDto groupDto = groupDtos.get(0);
        assertEquals(group.getGroupId().getValue(), groupDto.getGroupId());
        assertEquals(group.getName().getValue(), groupDto.getGroupName());
    }

}